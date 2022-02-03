package com.example.tryout

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    open fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
//        val permission = ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE)
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            // We don't have permission so prompt the user
//            ActivityCompat.requestPermissions(
//                    activity!!,
//                    PERMISSIONS_STORAGE,
//                    REQUEST_EXTERNAL_STORAGE
//            )
//        }

        // MANAGE ALL ACCESS
        val uri = Uri.parse("package:${BuildConfig.APPLICATION_ID}")

        startActivity(
                Intent(
                        Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                        uri
                )
        )

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyStoragePermissions(this)
        setContentView(R.layout.activity_main)
        tts = TextToSpeech(this, this)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            //        CONVERT AUDIO TTS KE MP3 ATO WAV
            val path = this.getExternalFilesDir(null)

            val folder = File(path, "Audio_File")
            if(!folder.exists()){
                folder.mkdirs()
            }
            val myHashRender: HashMap<String, String> = HashMap()
            val textToConvert = "this is a demo for saving a WAV file"

            myHashRender[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = textToConvert

            tts!!.synthesizeToFile(textToConvert, myHashRender, path.toString()+"/Audio_File/test.wav");
////            tts!!.addSpeech("a salam", path.toString()+"/Audio_File/test.wav")
////            tts!!.speak("a salam", TextToSpeech.QUEUE_ADD, null)
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()

            //coba mediaplayer
//            val dir = this.getExternalFilesDir(null).toString() +"/Audio_File/test.wav"
//            val myUri: Uri =  dir!!.toUri()// initialize Uri here
//            val mediaPlayer = MediaPlayer().apply {
//                setAudioAttributes(
//                        AudioAttributes.Builder()
//                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                                .setUsage(AudioAttributes.USAGE_MEDIA)
//                                .build()
//                )
//                setDataSource(applicationContext, myUri)
//                prepare()
//                start()
//            }
//            tts!!.addSpeech("A_salam", File((this.getExternalFilesDir(null).toString() +"/Audio_File/test.wav")))
            tts!!.addSpeech("A salam",this.getExternalFilesDir(null).toString()+"/Audio_File/test.wav")
            tts!!.speak("A salam", TextToSpeech.QUEUE_ADD, null)

        }

    }
    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // Ganti bahasa
            val result = tts!!.setLanguage(Locale("id"))
            tts!!.setLanguage(Locale("id"))

            // // Coba ganti voice name nya
//            val a = hashSetOf<String>()
//            a.add("MALE")
//            val v = Voice("id-ID-Standard-B", Locale("id","ID"), 400, 200, true,a )
//            tts!!.setVoice(v)

            Log.d("coba cek", tts!!.getVoices().toString())
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            } else {

            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}