package com.example.parejascartas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button jugar, salir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jugar = findViewById(R.id.buttonJugar);
        salir = findViewById(R.id.buttonSalir);
        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarJuego();
            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Método que arranca la clase Parejas a través de la clase Intent
     */
    private void iniciarJuego(){
        //señal que envía a otra clase para que le diga a la clase Parejas que se inicie
        Intent i = new Intent(this, Parejas.class);
        startActivity(i);
    }
}