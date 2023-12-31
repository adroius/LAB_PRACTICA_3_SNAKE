package com.ldm.pokesnake.juego;

import java.util.List;
import android.graphics.Color;

import com.ldm.pokesnake.Juego;
import com.ldm.pokesnake.Graficos;
import com.ldm.pokesnake.Input.TouchEvent;
import com.ldm.pokesnake.Pixmap;
import com.ldm.pokesnake.Pantalla;

public class PantallaJuego extends Pantalla {
    enum EstadoJuego {
        Preparado,
        Ejecutandose,
        Pausado,
        FinJuego
    }

    EstadoJuego estado = EstadoJuego.Preparado;
    Mundo mundo;
    int antiguaPuntuacion = 0;
    String puntuacion = "0";

    public PantallaJuego(Juego juego) {
        super(juego);
        mundo = new Mundo();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        if(estado == EstadoJuego.Preparado)
            updateReady(touchEvents);
        if(estado == EstadoJuego.Ejecutandose)
            updateRunning(touchEvents, deltaTime);
        if(estado == EstadoJuego.Pausado)
            updatePaused(touchEvents);
        if(estado == EstadoJuego.FinJuego)
            updateGameOver(touchEvents);

    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if(touchEvents.size() > 0)
            estado = EstadoJuego.Ejecutandose;
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        if(Configuraciones.sonidoHabilitado){
            Assets.gameSound.setLooping(true);
            Assets.gameSound.play();
        }
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x < 64 && event.y < 64) {
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    estado = EstadoJuego.Pausado;
                    return;
                }
            }
            if(event.type == TouchEvent.TOUCH_DOWN) {
                if(event.x < 64 && event.y > 416) {
                    mundo.personaje.girarIzquierda();
                }
                if(event.x > 256 && event.y > 416) {
                    mundo.personaje.girarDerecha();
                }
            }
        }

        mundo.update(deltaTime);
        if(mundo.finalJuego) {
            if(Configuraciones.sonidoHabilitado){
                Assets.derrota.play(1);
                Assets.gameSound.stop();
            }
            estado = EstadoJuego.FinJuego;
        }
        if(antiguaPuntuacion != mundo.puntuacion) {
            antiguaPuntuacion = mundo.puntuacion;
            puntuacion = "" + antiguaPuntuacion;
            if(Configuraciones.sonidoHabilitado)
                Assets.captura.play(1);
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        if(Configuraciones.sonidoHabilitado)
            Assets.gameSound.pause();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 80 && event.x <= 240) {
                    if(event.y > 100 && event.y <= 148) {
                        if(Configuraciones.sonidoHabilitado)
                            Assets.pulsar.play(1);
                        estado = EstadoJuego.Ejecutandose;
                        return;
                    }
                    if(event.y > 148 && event.y < 196) {
                        if(Configuraciones.sonidoHabilitado)
                            Assets.pulsar.play(1);
                        juego.setScreen(new MainMenuScreen(juego));
                        return;
                    }
                }
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x >= 128 && event.x <= 192 &&
                        event.y >= 200 && event.y <= 264) {
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    juego.setScreen(new MainMenuScreen(juego));
                    return;
                }
            }
        }
    }


    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.fondo, 0, 0);
        drawWorld(mundo);
        if(estado == EstadoJuego.Preparado)
            drawReadyUI();
        if(estado == EstadoJuego.Ejecutandose)
            drawRunningUI();
        if(estado == EstadoJuego.Pausado)
            drawPausedUI();
        if(estado == EstadoJuego.FinJuego)
            drawGameOverUI();


        drawText(g, puntuacion, g.getWidth() / 2 - puntuacion.length()*20 / 2, g.getHeight() - 42);
    }

    private void drawWorld(Mundo mundo) {
        Graficos g = juego.getGraphics();
        Personaje jollyroger = mundo.personaje;
        Pokemon head = jollyroger.pokemons.get(0);
        Pokeball pokeball = mundo.pokeball;


        Pixmap stainPixmap = null;
        if(pokeball.tipo== Pokeball.TIPO_1)
            stainPixmap = Assets.pokeball1;
        if(pokeball.tipo == Pokeball.TIPO_2)
            stainPixmap = Assets.pokeball2;
        if(pokeball.tipo == Pokeball.TIPO_3)
            stainPixmap = Assets.pokeball3;
        int x = pokeball.x * 32;
        int y = pokeball.y * 32;
        g.drawPixmap(stainPixmap, x, y);

        int len = jollyroger.pokemons.size();
        int changePokemon = 0;
        for(int i = 1; i < len; i++) {
            Pokemon part = jollyroger.pokemons.get(i);
            x = part.x * 32;
            y = part.y * 32;
            if (changePokemon == 0){
                Assets.pokemons = g.newPixmap("pikachu.png", Graficos.PixmapFormat.ARGB4444);
                changePokemon++;
            } else if (changePokemon == 1) {
                Assets.pokemons = g.newPixmap("turtwig.png", Graficos.PixmapFormat.ARGB4444);
                changePokemon++;
            } else if (changePokemon == 2) {
                Assets.pokemons = g.newPixmap("chimchar.png", Graficos.PixmapFormat.ARGB4444);
                changePokemon++;
            } else {
                Assets.pokemons = g.newPixmap("piplup.png", Graficos.PixmapFormat.ARGB4444);
                changePokemon = 0;
            }
            g.drawPixmap(Assets.pokemons, x, y);
        }

        Pixmap headPixmap = null;
        if(jollyroger.direccion == Personaje.ARRIBA)
            headPixmap = Assets.personajearriba;
        if(jollyroger.direccion == Personaje.IZQUIERDA)
            headPixmap = Assets.personajeizquierda;
        if(jollyroger.direccion == Personaje.ABAJO)
            headPixmap = Assets.personajeabajo;
        if(jollyroger.direccion == Personaje.DERECHA)
            headPixmap = Assets.personajederecha;
        x = head.x * 32 + 16;
        y = head.y * 32 + 16;
        g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y - headPixmap.getHeight() / 2);
    }

    private void drawReadyUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.preparado, 47, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawRunningUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.botones, 0, 0, 64, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assets.botones, 0, 416, 64, 64, 64, 64);
        g.drawPixmap(Assets.botones, 256, 416, 0, 64, 64, 64);
    }

    private void drawPausedUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.menupausa, 80, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawGameOverUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.finjuego, 62, 100);
        g.drawPixmap(Assets.botones, 128, 200, 5, 125, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    public void drawText(Graficos g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assets.numeros, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    @Override
    public void pause() {
        if(estado == EstadoJuego.Ejecutandose)
            estado = EstadoJuego.Pausado;

        if(mundo.finalJuego) {
            Configuraciones.addScore(mundo.puntuacion);
            Configuraciones.save(juego.getFileIO());
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}