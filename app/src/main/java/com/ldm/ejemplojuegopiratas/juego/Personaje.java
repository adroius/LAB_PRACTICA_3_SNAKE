package com.ldm.ejemplojuegopiratas.juego;

import java.util.ArrayList;
import java.util.List;

public class Personaje {
    public static final int ARRIBA = 0;
    public static final int IZQUIERDA= 1;
    public static final int ABAJO = 2;
    public static final int DERECHA = 3;

    public List<Pokemon> pokemons = new ArrayList<Pokemon>();
    public int direccion;

    public Personaje() {
        direccion = ARRIBA;
        pokemons.add(new Pokemon(5, 6));
        pokemons.add(new Pokemon(5, 7));
        pokemons.add(new Pokemon(5, 8));
    }

    public void girarIzquierda() {
        direccion += 1;
        if(direccion > DERECHA)
            direccion = ARRIBA;
    }

    public void girarDerecha() {
        direccion -= 1;
        if(direccion < ARRIBA)
            direccion = DERECHA;
    }

    public void addPokemon() {
        Pokemon end = pokemons.get(pokemons.size()-1);
        pokemons.add(new Pokemon(end.x, end.y));
    }

    public void avance() {
        Pokemon pokemon = pokemons.get(0);

        int len = pokemons.size() - 1;
        for(int i = len; i > 0; i--) {
            Pokemon antes = pokemons.get(i-1);
            Pokemon parte = pokemons.get(i);
            parte.x = antes.x;
            parte.y = antes.y;
        }

        if(direccion == ARRIBA)
            pokemon.y -= 1;
        if(direccion == IZQUIERDA)
            pokemon.x -= 1;
        if(direccion == ABAJO)
            pokemon.y += 1;
        if(direccion == DERECHA)
            pokemon.x += 1;

        if(pokemon.x < 0)
            pokemon.x = 9;
        if(pokemon.x > 9)
            pokemon.x = 0;
        if(pokemon.y < 0)
            pokemon.y = 12;
        if(pokemon.y > 12)
            pokemon.y = 0;
    }

    public boolean comprobarChoque() {
        int len = pokemons.size();
        Pokemon barco = pokemons.get(0);
        for(int i = 1; i < len; i++) {
            Pokemon parte = pokemons.get(i);
            if(parte.x == barco.x && parte.y == barco.y)
                return true;
        }
        return false;
    }
}

