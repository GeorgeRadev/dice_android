package org.dice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View {
    Paint square;
    Paint circle;
    static int diceValue = rollTheDice();
    static boolean inRollingMode = false;

    public MainView(Context context) {
        super(context);
        square = new Paint();
        square.setColor(Color.rgb(0x3D, 0x84, 0xDC));

        circle = new Paint();
        circle.setColor(Color.WHITE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                inRollingMode = true;
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
                inRollingMode = false;
                invalidate();
                performClick();
                return true;
        }
        return false;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private static final float[][] dots = {
            //1
            {0, 0},
            //2
            {0.5f, 0.5f, -0.5f, -0.5f},
            //3
            {0, 0, 0.6f, 0.6f, -0.6f, -0.6f},
            //4
            {0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f},
            //5
            {0, 0, 0.6f, 0.6f, -0.6f, -0.6f, 0.6f, -0.6f, -0.6f, 0.6f},
            //6
            {0, 0.6f, 0, -0.6f, 0.6f, 0.6f, -0.6f, -0.6f, 0.6f, -0.6f, -0.6f, 0.6f},
    };

    private static int rollTheDice() {
        return (int) (Math.random() * 6);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float xc = getWidth() >> 1;
        float yc = getHeight() >> 1;
        float r = Math.min(xc * 0.70f, yc * 0.70f);
        float r1 = r * 0.2f;

        canvas.drawRoundRect(xc - r, yc - r, xc + r, yc + r, r1, r1, square);
        float[] dot = dots[diceValue];
        for (int i = 0, l = (diceValue + 1) << 1; i < l; i += 2) {
            canvas.drawCircle(xc + r * dot[i], yc + r * dot[i + 1], r1, circle);
        }

        if (inRollingMode) {
            diceValue = rollTheDice();
            invalidate();
        }
    }
}
