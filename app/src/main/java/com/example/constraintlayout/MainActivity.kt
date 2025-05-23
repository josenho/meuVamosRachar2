package com.example.constraintlayout

import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import com.google.android.material.floatingactionbutton.FloatingActionButton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import java.util.*

class MainActivity : AppCompatActivity() , TextWatcher, TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    private lateinit var edtConta: EditText
    private var ttsSucess: Boolean = false;
    private val REQUEST_CODE_EMERGENCY = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSOS = findViewById<Button>(R.id.btnSOS)

        btnSOS.setOnLongClickListener {

            val lastLocation = "Lat: -23.559, Long: -46.6333"
            val batteryLevel = 75

            val intent = Intent(this, EmergencyActivity::class.java).apply{
                putExtra("location", lastLocation)
                putExtra("battery", batteryLevel)
                putExtra("timestamp", System.currentTimeMillis())
            }

            startActivityForResult(intent, REQUEST_CODE_EMERGENCY)
            true
        }

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton).apply {
            setOnClickListener {
                Intent(Intent.ACTION_SEND).apply{
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "teste de compartilhamento")
                }.also { intent ->
                    startActivity(Intent.createChooser(intent, null))
                }
            }
        }

        edtConta = findViewById<EditText>(R.id.edtConta)
        edtConta.addTextChangedListener(this)
        // Initialize TTS engine
        tts = TextToSpeech(this, this)

    }

    private fun compartilharConteudo() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Estou compartilhando isso pelo meu app")

            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Compartilhar via")
        startActivity(shareIntent)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
       Log.d("PDM24","Antes de mudar")

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        Log.d("PDM24","Mudando")
    }

    override fun afterTextChanged(s: Editable?) {
        Log.d ("PDM24", "Depois de mudar")

        val valor: Double

        if(s.toString().length>0) {
             valor = s.toString().toDouble()
            Log.d("PDM24", "v: " + valor)
        //    edtConta.setText("9")
        }
    }

    fun clickFalar(v: View){
        if (tts.isSpeaking) {
            tts.stop()
        }
        if(ttsSucess) {
            Log.d ("PDM23", tts.language.toString())
            tts.speak("Openning Youtube...", TextToSpeech.QUEUE_FLUSH, null, null)

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"))
            startActivity(intent)
        }
    }

    // Função chamada pelo botão (deve ser pública e ter exatamente esta assinatura)
    fun irParaSegundaTela(view: View) {
        val intent = Intent(this, SegundaActivity::class.java)
        startActivity(intent)
    }

    fun clickCompartilhar(view: View){
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "texto que eu quero compartilhar")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Compartilhar via")
        startActivity(shareIntent)
    }

    override fun onDestroy() {
            // Release TTS engine resources
            tts.stop()
            tts.shutdown()
            super.onDestroy()
        }

    override fun onInit(status: Int) {
            if (status == TextToSpeech.SUCCESS) {
                // TTS engine is initialized successfully
                tts.language = Locale.getDefault()
                ttsSucess=true
                Log.d("PDM23","Sucesso na Inicialização")
            } else {
                // TTS engine failed to initialize
                Log.e("PDM23", "Failed to initialize TTS engine.")
                ttsSucess=false
            }
        }


}

