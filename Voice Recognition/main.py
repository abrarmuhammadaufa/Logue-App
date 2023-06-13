import numpy as np
import tensorflow as tf
from tensorflow import keras
from tensorflow.keras.models import load_model
from utils import preprocess_audio, predict_from_file, decode_batch_predictions
from flask import Flask, jsonify, request

app = Flask(__name__)

@tf.function
def CTCLoss(y_true, y_pred):
    batch_len = tf.cast(tf.shape(y_true)[0], dtype="int64")
    input_length = tf.cast(tf.shape(y_pred)[1], dtype="int64")
    label_length = tf.cast(tf.shape(y_true)[1], dtype="int64")
    
    input_length = input_length * tf.ones(shape=(batch_len, 1), dtype="int64")
    label_length = label_length * tf.ones(shape=(batch_len, 1), dtype="int64")
    
    loss = keras.backend.ctc_batch_cost(y_true, y_pred, input_length, label_length)

    return loss


with keras.utils.custom_object_scope({'CTCLoss': CTCLoss}):
    model = keras.models.load_model('model.h5')

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
    app.run()
