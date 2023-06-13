import numpy as np
import tensorflow as tf
from tensorflow import keras
from tensorflow.keras.models import load_model
from utils import preprocess_audio, decode_batch_predictions, CTCLoss
from flask import Flask, jsonify, request

app = Flask(__name__)

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
