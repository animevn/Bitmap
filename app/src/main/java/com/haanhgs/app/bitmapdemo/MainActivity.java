package com.haanhgs.app.bitmapdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
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

    private final Point point = new Point();
    private Paint paint = new Paint();
    private Canvas canvas;
    private Bitmap bitmapFill;
    private Bitmap bobBitmap;
    private float textSize;

    private float horizon;
    private float vertical;
    private float horizonIncrement;
    private float verticalIncrement;

    private void setFullScreenAndPortrait(){
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void getScreenSize(){
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(point);
    }

    private void createView(){
        bitmapFill = Bitmap.createBitmap(point.x, point.y, Bitmap.Config.ARGB_8888);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmapFill);
        setContentView(imageView);
    }

    private void fillView(){
        canvas = new Canvas(bitmapFill);
        canvas.drawColor(Color.CYAN);
    }

    private void drawText(){
        paint.setFakeBoldText(true);
        paint.setColor(Color.BLACK);
        textSize = point.y/20f;
        paint.setTextSize(textSize);
        String screenSize = point.x + " - " + point.y;
        float width = paint.measureText(screenSize);
        canvas.drawText(screenSize, point.x/2f - width/2, textSize*1.1f, paint);
    }

    private void createBobBitmap(){
        bobBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bob);
    }

    private void shrinkBob(){
        Bitmap bitmap = Bitmap.createScaledBitmap(bobBitmap, 30, 45, false);
        canvas.drawBitmap(bitmap, point.x/2f - 15, textSize*1.1f + 10, paint);
    }

    private void enlargeBob(){
        Bitmap bitmap = Bitmap.createScaledBitmap(bobBitmap, 100, 150, false);
        canvas.drawBitmap(bitmap, point.x/2f - 50, textSize*1.1f + 65, paint);
    }

    private void prepareRotateBob(){
        bobBitmap = Bitmap.createScaledBitmap(bobBitmap, 60, 90, false);
        horizon = 20;
        vertical = point.y - 100;
        horizonIncrement = (point.x - 150)/12f;
        verticalIncrement = (point.y - 100)/12f;
    }

    private void rotateBob(){
        Matrix matrix = new Matrix();
        for (float rotate = 0f; rotate <= 360; rotate += 30){
            matrix.reset();
            matrix.preRotate(rotate);
            Bitmap bitmap = Bitmap.createBitmap(bobBitmap, 0, 0,
                    bobBitmap.getWidth(), bobBitmap.getHeight(), matrix, true);
            canvas.drawBitmap(bitmap, horizon, vertical, paint);
            horizon += horizonIncrement;
            vertical -= verticalIncrement;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenAndPortrait();
        getScreenSize();
        createView();
        fillView();
        drawText();
        createBobBitmap();
        shrinkBob();
        enlargeBob();
        prepareRotateBob();
        rotateBob();
    }
}
