package com.example.parejascartas;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Parejas extends Activity {
    //variables componentes
    ImageButton ib00,ib01,ib02,ib03,ib04,ib05;
    //Al tener 6 casillas, creamos un array de 6 elementos de longitud
    ImageButton[] tablero = new ImageButton[6];
    Button botonReiniciar, botonSalirToMain;

    //img
    int[] imgs;
    int reverso;
    ArrayList<Integer> arrayCartasUnsorted;
    //Lo que hará esto es que la primera imagen que se pulse se quedará
    //guardada, y la siguiente imagen se comparará con la pulsada anteriormente
    //Se usan enteros para comparar las imágenes
    ImageButton primero;
    int numUno, numDos;
    //para que no se ralle y no se pulsen demás cartas
    boolean bloquearCartas = false;
    //para que tenga un pequeño delay para ver la carta errónea
    //seleccionada
    final Handler handler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parejas);
        iniciarPartida();
    }
    private void cargarTablero(){
        //se cargan los botones en el tablero
        ib00 = findViewById(R.id.boton00);
        ib01 = findViewById(R.id.boton01);
        ib02 = findViewById(R.id.boton02);
        ib03 = findViewById(R.id.boton03);
        ib04 = findViewById(R.id.boton04);
        ib05 = findViewById(R.id.boton05);
        tablero[0] = ib00;
        tablero[1] = ib01;
        tablero[2] = ib02;
        tablero[3] = ib03;
        tablero[4] = ib04;
        tablero[5] = ib05;
    }

    private void cargarBotonesMenu(){
        botonReiniciar = findViewById(R.id.botonReset);
        botonSalirToMain = findViewById(R.id.botonSalir);
        //reinicia el tablero de la partida
        botonReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarPartida();
            }
        });
        //se vuelve a la vista principal
        botonSalirToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Se asignan las imágenes a las variables declaradas (son tipo int
     * debido a que guardan su id)
     */
    private void cargarImgs(){
        imgs = new int[]{
                R.drawable.c1,
                R.drawable.c2,
                R.drawable.c3

        };
        reverso = R.drawable.reverso;
    }

    /**
     * Método que desordena las cartas con un ArrayList
     * @param l longitud del array de imágenes
     * @return devuelve el array desordenado
     */
    private ArrayList<Integer> randomizar(int l){
        ArrayList<Integer> result = new ArrayList<Integer>();
        /*Tiene que tener el doble de la longitud de las imágenes
        * porque hay que recorrer el array dos veces (debido a que hay que compararlo)
        * */
        for (int i=0; i<l*2; i++){
            result.add(i % l);
        }
        //desordena el ArrayList
        Collections.shuffle(result);
        return result;
    }

    /**
     * En este método comprueba:
     * 1. Si se pulsa la primera carta de una pareja
     * 2. Si se ha acertado (numUno == numDos)
     * 3. Que no se pulsen más de dos cartas a la vez
     * @param i
     * @param ib botón seleccionado
     */
    private void comprobar(int i, ImageButton ib){
        //
        if(primero == null){
            primero = ib;
            primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
            primero.setImageResource(imgs[arrayCartasUnsorted.get(i)]);
            primero.setEnabled(false);
            numUno = arrayCartasUnsorted.get(i);
        }else{
            //se iguala a true debido a que si hay dos cartas destapadas
            //no se podrá destapar otra, para que no falle el programa
            bloquearCartas = true;
            ib.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ib.setImageResource(imgs[arrayCartasUnsorted.get(i)]);
            ib.setEnabled(false);
            numDos = arrayCartasUnsorted.get(i);
            //el usuario ha acertado, por lo que se reinicia el bloqueo y la primera carta
            //seleccionada
            if(numUno == numDos){
                primero = null;
                bloquearCartas = false;
            }else{
                //si el usuario falla, muestra por 0.75s la carta errónea (debido al delay)
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //si no acierta se tapan de nuevo las dos cartas seleccionadas
                        primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        primero.setImageResource(reverso);
                        primero.setEnabled(true);
                        ib.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        ib.setImageResource(reverso);
                        ib.setEnabled(true);
                        bloquearCartas = false;
                        primero = null;
                    }
                }, 750);
            }
        }
    }
    private void iniciarPartida(){
        cargarTablero();
        cargarBotonesMenu();
        cargarImgs();
       arrayCartasUnsorted = randomizar(imgs.length);
       //carga de imágenes
        //asignación imagen del array ya aleatorizada
        for (int i = 0; i<tablero.length;i++) {
            //para que quede centrada la imagen
            tablero[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            tablero[i].setImageResource(imgs[arrayCartasUnsorted.get(i)]);
        }

        //asignación img reverso
        for (int i = 0; i<tablero.length;i++){
            //para que quede centrada la imagen
            tablero[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            tablero[i].setImageResource(reverso);
        }
        for (int i = 0; i<tablero.length; i++){
            //se inicializa una constante para que se pueda comparar
            //en la condición if
            final int j = i;
            tablero[i].setEnabled(true);
            tablero[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!bloquearCartas)
                        comprobar(j, tablero[j]);
                }
            });
        }
    }
}
