import os
import numpy as np
import tensorflow as tf
from tensorflow import keras
from tensorflow.keras import layers
from tensorflow.keras.models import load_model
from flask import Flask, jsonify, request

# The set of characters accepted in the transcription
characters = [x for x in "abcdefghijklmnopqrstuvwxyz'?! "]

# Mapping characters to integers
char_to_num = keras.layers.StringLookup(vocabulary=characters, oov_token="")

# Mapping integers back to original characters
num_to_char = keras.layers.StringLookup(
                    vocabulary=char_to_num.get_vocabulary(),
                    oov_token="",
                    invert=True)

def CTCLoss(y_true, y_pred):
    batch_len = tf.cast(tf.shape(y_true)[0], dtype="int64")
    input_length = tf.cast(tf.shape(y_pred)[1], dtype="int64")
    label_length = tf.cast(tf.shape(y_true)[1], dtype="int64")
    input_length = input_length * tf.ones(shape=(batch_len, 1), dtype="int64")
    label_length = label_length * tf.ones(shape=(batch_len, 1), dtype="int64")
    loss = keras.backend.ctc_batch_cost(y_true, y_pred, input_length, label_length)
    return loss

def decode_batch_predictions(pred):
    input_len = np.ones(pred.shape[0]) * pred.shape[1]
    results = keras.backend.ctc_decode(pred, input_length=input_len, greedy=True)[0][0]
    output_text = []
    for result in results:
        result = tf.strings.reduce_join(num_to_char(result)).numpy().decode("UTF-8")
        output_text.append(result)
    return output_text

def preprocess_audio(upload_file):
    # Read audio file
    audio, _ = tf.audio.decode_wav(upload_file.read())
    audio = tf.squeeze(audio, axis=-1)
    audio = tf.cast(audio, tf.float32)

    # Define preprocessing parameters
    frame_length = 256
    frame_step = 160
    fft_length = 384

    # Get the spectrogram
    spectrogram = tf.signal.stft(
        audio, frame_length=frame_length, frame_step=frame_step, fft_length=fft_length
    )

    # Compute magnitude
    spectrogram = tf.abs(spectrogram)
    spectrogram = tf.math.pow(spectrogram, 0.5)

    # Normalization
    means = tf.math.reduce_mean(spectrogram, axis=1, keepdims=True)
    stddevs = tf.math.reduce_std(spectrogram, axis=1, keepdims=True)
    spectrogram = (spectrogram - means) / (stddevs + 1e-10)

    return spectrogram


app = Flask(__name__)

with keras.utils.custom_object_scope({'CTCLoss': CTCLoss}):
    model = keras.models.load_model('model.h5')

@app.route("/")
def index():
  return "Hello from ML Endpoint!"
    
@app.route("/predict", methods=["POST"])
def predict():
    file = request.files['file']
    if 'file' not in request.files or file.filename == "":
        return jsonify({'error': 'No file uploaded'})

    try:
        preprocessed_audio = preprocess_audio(file)
        preprocessed_audio = np.expand_dims(preprocessed_audio, axis=0)
        predictions = model.predict(preprocessed_audio)
        decoded_predictions = decode_batch_predictions(predictions)[0]
        return jsonify({"predictions": decoded_predictions})
    except:
        return jsonify({'error': str(e)})

if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port=int(os.environ.get("PORT", 8080)))
