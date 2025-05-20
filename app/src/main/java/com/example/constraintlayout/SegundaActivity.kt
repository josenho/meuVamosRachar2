package com.example.constraintlayout

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.constraintlayout.databinding.ActivitySegundaBinding

class SegundaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda)

        val editText = findViewById<EditText>(R.id.editTextMensagem)
        val btnCompartilhar = findViewById<Button>(R.id.btnCompartilhar)

        btnCompartilhar.setOnClickListener {
            val textoParaCompartilhar = editText.text.toString()
            if (textoParaCompartilhar.isNotEmpty()){
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, textoParaCompartilhar)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(sendIntent, "Compartilhar via"))
            } else {
                Toast.makeText(this, "digite algo primeiro", Toast.LENGTH_SHORT).show()
            }
        }
    }
}