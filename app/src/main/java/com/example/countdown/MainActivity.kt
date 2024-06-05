package com.example.countdown

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore.Audio.Media
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.countdown.databinding.ActivityMainBinding
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    private var countDownTimer:CountDownTimer?=null
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.button.setOnClickListener {
            val minutes=if(binding.editText.text.toString().isEmpty()) 0 else binding.editText.text.toString().toInt()
            val seconds=if(binding.editText2.text.toString().isEmpty()) 0 else binding.editText2.text.toString().toInt()
            val hours=if(binding.editText3.text.toString().isEmpty()) 0 else binding.editText3.text.toString().toInt()
            val milliSecond=((hours*3600)+(minutes*60)+seconds)*1000L
            if(milliSecond>0){
                startCountDown(milliSecond)
            }
            else{
                Toast.makeText(this,"Please enter a valid time",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun startCountDown(milliSecond:Long){
        countDownTimer?.cancel()
        countDownTimer=object :CountDownTimer(milliSecond,1000){
            override fun onTick(millisUntilFinished: Long) {
                val hours = (millisUntilFinished / 1000) / 3600
                val minutes = ((millisUntilFinished / 1000) / 60) % 60
                val seconds = (millisUntilFinished / 1000) % 60
                binding.textView.text="${(if(hours>9) hours else "0"+hours)}:${(if (minutes>9) minutes  else "0"+minutes)}:${(if (seconds>9) seconds else "0"+seconds)}"
            }
            override fun onFinish() {
                binding.textView.text="00:00:00"
                val alarm:MediaPlayer=MediaPlayer.create(applicationContext,R.raw.alarm)
                alarm.start()

            }

        }.start()
    }
}