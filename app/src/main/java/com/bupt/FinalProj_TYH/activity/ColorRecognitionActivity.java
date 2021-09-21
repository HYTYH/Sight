/*
 * Copyright 2021 唐义泓
 *
 * Title:   Sight
 * Version: 1
 * Contact: tyh@bupt.edu.cn
 */


package com.bupt.FinalProj_TYH.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bupt.FinalProj_TYH.model.RealColor;
import com.bupt.FinalProj_TYH.R;

/*
 * Class:   ColorRecognitionActivity
 * 职能:    用于初始化颜色识别界面，并提供颜色识别相关操作的接口
 */
public class ColorRecognitionActivity extends AppCompatActivity {
    private Bitmap lastImage;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cr);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        tv = findViewById(R.id.color_name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cr, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            this.lastImage = imageBitmap;
            int pixel = getDominantColor();
            RealColor realColor = parseColor(pixel);
            addColorToList(realColor);
        }
    }

    private void addColorToList(RealColor color) {
        tv.setText(color.getName());
    }

    private RealColor parseColor(int pixel) {
        int redValue = Color.red(pixel);
        int blueValue = Color.blue(pixel);
        int greenValue = Color.green(pixel);

        int maxColor = Math.max(redValue, blueValue);
        maxColor = Math.max(maxColor, greenValue);

        String colorName = "";

        if(maxColor == redValue) {
            if(maxColor == blueValue) {
                if(maxColor == greenValue) {
                    colorName += "white";
                } else {
                    colorName += "purple";
                }
            } else {
                colorName += "red";
            }
        } else {
            if(maxColor == blueValue) {
                if(maxColor == greenValue) {
                    colorName += "cyan";
                } else {
                    colorName += "blue";
                }
            } else {
                colorName += "green";
            }
        }

        RealColor realColor = new RealColor(colorName, null, null);
        return realColor;
    }

    private int getDominantColor() {
        Bitmap newBitmap = Bitmap.createScaledBitmap(lastImage, 1, 1, true);
        final int color = newBitmap.getPixel(newBitmap.getWidth()/2, newBitmap.getHeight()/2);
        newBitmap.recycle();
        return color;
    }
}
