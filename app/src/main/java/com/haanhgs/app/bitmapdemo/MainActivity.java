package com.haanhgs.app.bitmapdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Point size = new Point();
    private Paint paint = new Paint();
    private Canvas canvas;
    private Bitmap background;
    private Bitmap bobBitmap;

    private float textPosY;
    private float horizon;
    private float vertical;
    private float horizonIncrement;
    private float verticIncrement;

    private void hideActionBarAndStatusBar(){
        if (getSupportActionBar() != null) getSupportActionBar().hide();
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void getScreensize(){
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
    }

    private void initContentView(){
        background = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_8888);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(background);
        setContentView(imageView);
    }

    private void fillBackground(){
        canvas = new Canvas(background);
        canvas.drawColor(Color.CYAN);
    }

    private void drawText(){
        paint.setFakeBoldText(true);
        paint.setColor(Color.RED);
        paint.setTextSize(size.x/10f);
        String screensize = "" + size.x + " - " + size.y;
        float width = paint.measureText(screensize);
        textPosY = size.x/10f + 20;
        canvas.drawText(screensize, size.x/2f - width/2, textPosY, paint);
    }

    private void drawShrinkBob(){
        bobBitmap = Bitmap.createScaledBitmap(bobBitmap, 30, 45, false);
        canvas.drawBitmap(bobBitmap, size.x/2f - 15, textPosY + 10, paint);
    }

    private void drawEnlargeBob(){
        bobBitmap = Bitmap.createScaledBitmap(bobBitmap, 200, 300, false);
        canvas.drawBitmap(
                bobBitmap,
                size.x/2f - 100,
                textPosY + 75,
                paint);
    }

    private void prepareRotateBob(){
        bobBitmap = Bitmap.createScaledBitmap(bobBitmap, 60, 90, false);
        horizon = 20;
        vertical = size.y - 110;
        horizonIncrement = (size.x - 100)/12f;
        verticIncrement = (size.y - 100)/12f;
    }

    private void drawRotateBob(){
        prepareRotateBob();
        Matrix matrix = new Matrix();
        for (float rotate = 0; rotate <= 360; rotate += 30){
            matrix.reset();
            matrix.preRotate(rotate);
            Bitmap bitmap = Bitmap.createBitmap(bobBitmap, 0, 0, bobBitmap.getWidth(),
                    bobBitmap.getHeight(), matrix, true);
            canvas.drawBitmap(bitmap, horizon, vertical, paint);
            horizon += horizonIncrement;
            vertical -= verticIncrement;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBarAndStatusBar();
        getScreensize();
        initContentView();
        fillBackground();
        drawText();
        bobBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bob);
        drawShrinkBob();
        drawEnlargeBob();
        drawRotateBob();
    }
}
