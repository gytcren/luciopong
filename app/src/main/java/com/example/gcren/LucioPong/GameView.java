package com.example.gcren.LucioPong;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements View.OnTouchListener
{
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    Paint paint = new Paint();
    float screenWidth = getScreenWidth();
    float screenHeight = getScreenHeight();
    //Top bar X
    float tbx = screenWidth * 0.5f;
    //Bottom bar X
    float bbx = screenWidth * 0.5f;
    //Top bar Y
    float tby = screenHeight * 0.07f;
    //Bottom bar Y
    float bby = screenHeight * 0.8f;
    // Ball X Y
    float bx = screenWidth / 2;
    float by = screenHeight / 2;
    // Ball speed
    float bsx = 10;
    float bsy = 10;
    // Top bar speed
    float tbs = 8;
    //Score
    int tscore = 0;
    int bscore = 0;
    Bitmap bmball = BitmapFactory.decodeResource(getResources(), R.drawable.lucioball);
    MediaPlayer justgettingstarted;
    MediaPlayer pushoff;
    MediaPlayer getback;
    MediaPlayer whoa;
    Vibrator vibro;

    public GameView(Context context) {
        super(context);
        this.setOnTouchListener(this);
        this.setBackgroundResource(R.drawable.background);
        justgettingstarted = MediaPlayer.create(getContext(), R.raw.justgettingstarted);
        pushoff = MediaPlayer.create(getContext(), R.raw.pushoff);
        getback = MediaPlayer.create(getContext(), R.raw.getback);
        whoa = MediaPlayer.create(getContext(), R.raw.whoa);
        vibro = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        paint.setColor(Color.WHITE);
        canvas.drawRect(tbx, tby, tbx + 400, tby + 50, paint);
        canvas.drawRect(bbx, bby, bbx + 400, bby + 50, paint);
        paint.setTextSize(50);
        canvas.drawText(Integer.toString(tscore), 50, 50, paint);
        canvas.drawText(Integer.toString(bscore), screenWidth - 50, screenHeight - 250, paint);
        canvas.drawBitmap(bmball, bx - (bmball.getWidth() / 2), by - (bmball.getHeight() / 2), paint);

        bx += bsx;
        by += bsy;

        //DEATH!
        if(by + 20 >= screenHeight || by - 20 <= 0)
        {
            if (by + 20 >= screenHeight) {
                vibro.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                whoa.start();
                tscore ++;
            }
            else {
                justgettingstarted.start();
                bscore ++;
            }
            bx = screenWidth/2;
            by = screenHeight/2;
            bsy = 10;
        }
        if ((bx + 20 > bbx && bx + 20 <= bbx + 500) && (by >= bby-40 && by <= bby + 50)) {

            pushoff.start();
            bsy *= -1.1;
            if (bsy > 40)
                bsy = 40;
        }

        if ((bx - 20 > tbx && bx - 20 <= tbx + 500) && (by >= tby && by <= tby + 90)) {
            getback.start();
            bsy *= -1.1;
        }
        if (bsy > 40)
            bsy = 40;
        if (bx - 20 == 0 || bx + 20 == screenWidth)
            bsx *= -1;
        if (bx > (tbx + tbx + 500)/2)
            tbx += tbs;
        else
            tbx -= tbs;


        invalidate();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        bbx = event.getX() - 250;
        return true;
    }
}