package com.ldm.pokesnake.juego;

import java.util.Random;

public class Mundo {
    static final int MUNDO_ANCHO = 10;
    static final int MUNDO_ALTO = 13;
    static final int INCREMENTO_PUNTUACION = 10;
    static final float TICK_INICIAL = 0.5f;
    static final float TICK_DECREMENTO = 0.05f;

    public Personaje personaje;
    public Pokeball pokeball;
    public boolean finalJuego = false;
    public int puntuacion = 0;

    boolean campos[][] = new boolean[MUNDO_ANCHO][MUNDO_ALTO];
    Random random = new Random();
    float tiempoTick = 0;
    static float tick = TICK_INICIAL;

    public Mundo() {
        personaje = new Personaje();
        colocarpokeball();
    }

    private void colocarpokeball() {
        for (int x = 0; x < MUNDO_ANCHO; x++) {
            for (int y = 0; y < MUNDO_ALTO; y++) {
                campos[x][y] = false;
            }
        }

        int len = personaje.pokemons.size();
        for (int i = 0; i < len; i++) {
            Pokemon parte = personaje.pokemons.get(i);
            campos[parte.x][parte.y] = true;
        }

        int pokeballX = random.nextInt(MUNDO_ANCHO);
        int pokeballY = random.nextInt(MUNDO_ALTO);
        while (pokeballX < 2 && pokeballY < 2) {
            pokeballX = random.nextInt(MUNDO_ANCHO);
            pokeballY = random.nextInt(MUNDO_ALTO);
        }
        while (true) {
            if (campos[pokeballX][pokeballY] == false)
                break;
            pokeballX += 1;
            if (pokeballX >= MUNDO_ANCHO) {
                pokeballX = 0;
                pokeballY += 1;
                if (pokeballY >= MUNDO_ALTO) {
                    pokeballY = 2;
                }
            }
        }
        pokeball = new Pokeball(pokeballX, pokeballY, random.nextInt(3));
    }

    public void update(float deltaTime) {
        if (finalJuego)

            return;

        tiempoTick += deltaTime;

        while (tiempoTick > tick) {
            tiempoTick -= tick;
            personaje.avance();
            if (personaje.comprobarChoque()) {
                finalJuego = true;
                return;
            }

            Pokemon head = personaje.pokemons.get(0);
            if (head.x == pokeball.x && head.y == pokeball.y) {
                puntuacion += INCREMENTO_PUNTUACION;
                personaje.addPokemon();
                if (personaje.pokemons.size() == MUNDO_ANCHO * MUNDO_ALTO) {
                    finalJuego = true;
                    return;
                } else {
                    colocarpokeball();
                }

                if (puntuacion % 100 == 0 && tick - TICK_DECREMENTO > 0) {
                    tick -= TICK_DECREMENTO;
                }
            }
        }
    }
}

