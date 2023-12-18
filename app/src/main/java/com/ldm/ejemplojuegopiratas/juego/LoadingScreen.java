package com.ldm.ejemplojuegopiratas.juego;

import com.ldm.ejemplojuegopiratas.Juego;
import com.ldm.ejemplojuegopiratas.Graficos;
import com.ldm.ejemplojuegopiratas.Pantalla;
import com.ldm.ejemplojuegopiratas.Graficos.PixmapFormat;

public class LoadingScreen extends Pantalla{
    public LoadingScreen(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        Graficos g = juego.getGraphics();
        Assets.fondo = g.newPixmap("fondoP.jpg", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("PokemonLogo.png", PixmapFormat.ARGB4444);
        Assets.menuprincipal = g.newPixmap("menuprincipal.png", PixmapFormat.ARGB4444);
        Assets.botones = g.newPixmap("botonesss.png", PixmapFormat.ARGB4444);
        Assets.ayuda1 = g.newPixmap("ayuda1.png", PixmapFormat.ARGB4444);
        Assets.ayuda2 = g.newPixmap("ayuda2.png", PixmapFormat.ARGB4444);
        Assets.ayuda3 = g.newPixmap("ayuda3.png", PixmapFormat.ARGB4444);
        Assets.numeros = g.newPixmap("nums.png", PixmapFormat.ARGB4444);
        Assets.preparado = g.newPixmap("preparado.png", PixmapFormat.ARGB4444);
        Assets.menupausa = g.newPixmap("menupausa.png", PixmapFormat.ARGB4444);
        Assets.finjuego = g.newPixmap("finjuego.png", PixmapFormat.ARGB4444);
        Assets.personajearriba = g.newPixmap("espalda.png", PixmapFormat.ARGB4444);
        Assets.personajeizquierda = g.newPixmap("izq.png", PixmapFormat.ARGB4444);
        Assets.personajeabajo = g.newPixmap("frente.png", PixmapFormat.ARGB4444);
        Assets.personajederecha = g.newPixmap("der.png", PixmapFormat.ARGB4444);
        Assets.pokemons = g.newPixmap("pikachu.png", PixmapFormat.ARGB4444);
        Assets.pokeball1 = g.newPixmap("pokebola.png", PixmapFormat.ARGB4444);
        Assets.pokeball2 = g.newPixmap("super.png", PixmapFormat.ARGB4444);
        Assets.pokeball3 = g.newPixmap("ultra.png", PixmapFormat.ARGB4444);
        Assets.gameSound = juego.getAudio().nuevaMusica("pokemon.ogg");
        Assets.pulsar = juego.getAudio().nuevoSonido("pulsar.ogg");
        Assets.captura = juego.getAudio().nuevoSonido("captura.ogg");
        Assets.derrota = juego.getAudio().nuevoSonido("error.ogg");


        Configuraciones.cargar(juego.getFileIO());
        juego.setScreen(new MainMenuScreen(juego));
    }

    @Override
    public void present(float deltaTime) {

    }

    @Override
    public void pause() {

    }


    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}