package com.ldm.pokesnake;

import com.ldm.pokesnake.Graficos.PixmapFormat;

public interface Pixmap {
    public int getWidth();

    public int getHeight();

    public PixmapFormat getFormat();

    public void dispose();
}

