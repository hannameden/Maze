package com.example.hanna.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class InputManager implements View.OnTouchListener {

    private int controllerWidth;
    private int controllerHeight;


    public InputManager() {

        controllerWidth = MainActivity.width / 12;
        controllerHeight = MainActivity.height / 10;

    }

    public void update() {

    }

    public void render(Canvas canvas) {

        //Paint paint = new Paint(Color.argb(0, 50, 50, 50));
        //canvas.drawRect(MainActivity.width / 2 - controllerWidth, MainActivity.height / 2 + MainActivity.height / 4, MainActivity.width / 2 + controllerWidth, MainActivity.height / 2 + MainActivity.height / 4 + controllerHeight, paint);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
