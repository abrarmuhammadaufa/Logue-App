from tensorflow.keras.models import load_model
from utils import preprocess_audio, predict_from_file, CTCLoss
import sounddevice as sd
import soundfile as sf

samplerate = 16000  
duration = 6 # seconds
filename = 'test_1.wav'
print("start")
mydata = sd.rec(int(samplerate * duration), samplerate=samplerate,
    channels=1, blocking=True)
print("end")
sd.wait()
sf.write(filename, mydata, samplerate)


loaded_model = load_model("model.h5", custom_objects={'CTCLoss': CTCLoss})

file_path = filename  # Replace with the path to your audio file
predictions = predict_from_file(file_path, loaded_model)
print("Predictions:", predictions)
