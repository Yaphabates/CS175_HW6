package com.example.cs175_hw6
import android.graphics.PixelFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.*

class VideoActivity : AppCompatActivity() {
    companion object{
        const val PLAY = 0
        const val PAUSE = 1
    }
    private var playBtn: ImageView? = null
    private var seekBar: SeekBar? = null
    private var replayBtn: Button? = null
    private var videoView: VideoView? = null
    private var status = PAUSE
    private var duration: Int? = null
    private var durationMin: String? = null
    private var position: TextView? = null

    private val handler = Handler(Looper.getMainLooper())
    private inner class Runner: Runnable{
        override fun run() {
            if (status == PLAY) {
                val current: Int = videoView!!.currentPosition
                seekBar?.progress = (current.toFloat() / duration!!.toFloat() * 100).toInt()
                position?.text = "${ms2min(current)}/$durationMin"
            }

            handler.postDelayed(this, 1000)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        playBtn = findViewById(R.id.pause_btn)
        seekBar = findViewById(R.id.seek_bar)
        replayBtn = findViewById(R.id.replay_btn)
        videoView = findViewById(R.id.videoView)
        position = findViewById(R.id.tv_time)

        playBtn?.setOnClickListener{
            if (status == PLAY) {
                videoView!!.pause()
                playBtn!!.setImageResource(R.drawable.play_icon)
                status = PAUSE
            }
            else {
                videoView!!.start()
                playBtn!!.setImageResource(R.drawable.pause_icon)
                status = PLAY
            }
        }
        replayBtn?.setOnClickListener{
            videoView!!.resume()
            playBtn!!.setImageResource(R.drawable.pause_icon)
            status = PLAY
            position?.text = "00:00/$durationMin"
        }
        seekBar?.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val ms = (progress * duration!! / 100)
                    videoView!!.seekTo(ms)
                    position?.text = "${ms2min(ms)}:$durationMin"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        videoView!!.holder.setFormat(PixelFormat.TRANSPARENT)
        videoView!!.setZOrderOnTop(true)
        videoView!!.setVideoPath("android.resource://" + this.packageName + "/" + R.raw.big_buck_bunny)
        videoView!!.setOnPreparedListener {
            duration = videoView!!.duration
            durationMin = ms2min(duration!!)
            position?.text = "00:00/$durationMin"
            videoView!!.start()
            playBtn!!.setImageResource(R.drawable.pause_icon)
            status = PLAY
        }

        videoView!!.setOnCompletionListener {
            videoView!!.stopPlayback()
            playBtn!!.setImageResource(R.drawable.play_icon)
            seekBar!!.progress = 0
            status = PAUSE
            position?.text = "00:00/$durationMin"
        }
        handler.post(Runner())

    }


    private fun ms2min(ms: Int): String {
        val totalSec: Int = ms / 1000
        val min: String = if((totalSec / 60) < 10) {
            "0" + (totalSec / 60).toString()
        } else {
            (totalSec / 60).toString()
        }

        val sec: String = if((totalSec % 60) < 10) {
            "0" + (totalSec % 60).toString()
        } else {
            (totalSec % 60).toString()
        }
        return "$min:$sec"

    }


}
