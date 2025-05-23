package com.example.constraintlayout

import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EmergencyActivity : AppCompatActivity() {
    private var ringtone: Ringtone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(this, alarmSound)
        ringtone?.play()

        val location = intent.getStringExtra("location") ?: "Local desconhecido"
        val battery = intent.getIntExtra("battery", 0)
        val timestamp = intent.getLongExtra("timestamp", 0)
        val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(timestamp))

        findViewById<TextView>(R.id.tvLocation).text = "Ultimo local: $location"
        findViewById<TextView>(R.id.tvBattery).text = "Bateria: $battery%"
        findViewById<TextView>(R.id.tvTime).text = "Acionado as: $time"

        findViewById<Button>(R.id.btnCancel).setOnClickListener {
            ringtone?.stop()
            setResult(RESULT_OK)
            finish()
        }
    }

    override fun onDestroy() {
        ringtone?.stop()
        super.onDestroy()
    }
}