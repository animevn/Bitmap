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

    private Point point = new Point();
    private Paint paint = new Paint();
    private Canvas canvas;
    private Bitmap bobBitmap;
    private float horizontal;
    private float vertical;
    private float horizontalIncrement;
    private float verticalIncrement;
    private Bitmap bitmapFill;

    private void hideActionBar(){
        if (getSupportActionBar() != null) getSupportActionBar().hide();
    }

    private void hideStatusBar(){
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setPortraitMode(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void getScreenSize(){
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(point);
    }

    private void setContentView(){
        bitmapFill = Bitmap.createBitmap(point.x, point.y, Bitmap.Config.ARGB_8888);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmapFill);
        setContentView(imageView);
    }

    private void fillScreenWithColor(){
        canvas = new Canvas(bitmapFill);
        canvas.drawColor(Color.MAGENTA);
    }

    private void drawText(){
        paint.setFakeBoldText(true);
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        String screenSize = point.x + " - " + point.y;
        float width = paint.measureText(screenSize);
        canvas.drawText(screenSize, point.x/2f - width/2f, 100, paint);
    }

    private void shrinkBob(){
        Bitmap bitmap = Bitmap.createScaledBitmap(bobBitmap, 30, 45, false);
        canvas.drawBitmap(bitmap, point.x/2f - 15, 110, paint );
    }

    private void enlargeBob(){
        Bitmap bitmap = Bitmap.createScaledBitmap(bobBitmap, 200, 300, false);
        canvas.drawBitmap(bitmap, point.x/2f - 100, 110, paint );
    }

    private void prepareBob(){
        bobBitmap = Bitmap.createScaledBitmap(bobBitmap, 60, 90, false);
        horizontal = 20;
        vertical = point.y - 150;
        horizontalIncrement = (point.x - 150)/13f;
        verticalIncrement = (point.y - 50)/13f;
    }

    private void rotateBob(){
        prepareBob();
        Matrix matrix = new Matrix();
        for (float rotate = 0; rotate <= 360f; rotate += 30f){
            matrix.reset();
            matrix.preRotate(rotate);
            Bitmap bitmap = Bitmap.createBitmap(bobBitmap, 0, 0, bobBitmap.getWidth(),
                    bobBitmap.getHeight(), matrix, true);
            canvas.drawBitmap(bitmap, horizontal, vertical, paint);
            horizontal += horizontalIncrement;
            vertical -= verticalIncrement;
        }
    }

    private void handleBob(){
        bobBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bob);
        enlargeBob();
        shrinkBob();
        rotateBob();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        hideStatusBar();
        setPortraitMode();
        getScreenSize();
        setContentView();
        fillScreenWithColor();
        drawText();
        handleBob();
    }
}
