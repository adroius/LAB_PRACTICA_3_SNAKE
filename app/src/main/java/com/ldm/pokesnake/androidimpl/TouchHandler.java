package com.ldm.pokesnake.androidimpl;

import java.util.List;

import android.view.View.OnTouchListener;

import com.ldm.pokesnake.Input.TouchEvent;


public interface TouchHandler extends OnTouchListener {
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<TouchEvent> getTouchEvents();
}

