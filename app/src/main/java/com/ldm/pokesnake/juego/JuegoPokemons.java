package com.ldm.pokesnake.juego;

import com.ldm.pokesnake.Pantalla;
import com.ldm.pokesnake.androidimpl.AndroidJuego;

public class JuegoPokemons extends AndroidJuego {
    @Override
    public Pantalla getStartScreen() {
        return new LoadingScreen(this);
    }
}
