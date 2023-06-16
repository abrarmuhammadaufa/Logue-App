package com.example.logue11.main.activity.speaking

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.logue11.R
import com.example.logue11.databinding.ActivitySpeakingBinding
import com.example.logue11.main.api.ApiVoiceConfig
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class SpeakingActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySpeakingBinding
    private lateinit var mediaRecorder: MediaRecorder

    private var permission = arrayOf(android.Manifest.permission.RECORD_AUDIO)
    private var permissionGranted = false

    private var tempDirectory: String = ""
    private var fileName: String = ""
    private var location: String = ""
    private var isRecord = false

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivitySpeakingBinding.inflate(LayoutInflater.from(this))

        supportActionBar?.title=("Speaking Bab 1")

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        permissionGranted = ActivityCompat.checkSelfPermission(this, permission[0]) == PackageManager.PERMISSION_GRANTED

        if(!permissionGranted){
            ActivityCompat.requestPermissions(this, permission, REQ_CODE)
        }

        binding.btnRecord.setOnClickListener {
            when{
                isRecord -> stopRecording()
                else -> startRecording()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == REQ_CODE){
            permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
        }

    }
    @SuppressLint("SimpleDateFormat")
    fun startRecording(){
        if(!permissionGranted){
            ActivityCompat.requestPermissions(this, permission, REQ_CODE)
            return
        }

        mediaRecorder = MediaRecorder()
        tempDirectory = "${externalCacheDir?.absolutePath}/"


        var dateFormat = SimpleDateFormat("yyyy.MM.DD_hh.mm.ss")
        var date = dateFormat.format(Date())
        fileName = "sample_voice_$date"
        location = "$tempDirectory$fileName.wav"

        mediaRecorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            setOutputFile(location)

            try{
                prepare()
            }catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }

            start()
        }

        binding.btnRecord.setImageResource(R.drawable.ic_stop_record)
        isRecord = true

    }

    private fun stopRecording(){
        binding.btnRecord.setImageResource(R.drawable.ic_mic_blue)
        mediaRecorder.stop()
        mediaRecorder.release()
        isRecord = false
    }

    fun uploadVoice(){

        val audioFile = File(location)
        val requestBody = RequestBody.create("audio/*".toMediaTypeOrNull(), audioFile)
        val audioPart = MultipartBody.Part.createFormData("audio", audioFile.name, requestBody)

        ApiVoiceConfig.instanceVoiceRetrofit.uploadFile(
            audioPart
        ).enqueue(object: Callback<SpeakingResponse> {
            override fun onResponse(
                call: Call<SpeakingResponse>,
                response: Response<SpeakingResponse>
            ) {
                val prediction: String? = response.body()?.predictions

                if(prediction != null){
                    if(prediction == binding.tvQuestion.toString()){
                        Toast.makeText(this@SpeakingActivity, "Jawaban benar, kamu keren!!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@SpeakingActivity, "Jawaban salah, ayo coba lagi!!", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Log.e(ContentValues.TAG, "Gagal mengambil respon")
                }
            }

            override fun onFailure(call: Call<SpeakingResponse>, t: Throwable) {
                Toast.makeText(this@SpeakingActivity, "Gagal merekam suara", Toast.LENGTH_SHORT).show()
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }

        })
    }
}

const val REQ_CODE = 100
const val LOG_TAG = "AudioRecordTest"