/*
 * Copyright 2021 唐义泓
 *
 * Title:   Sight
 * Version: 1
 * Contact: tyh@bupt.edu.cn
 */

package com.bupt.FinalProj_TYH;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

import com.bupt.FinalProj_TYH.activity.ColorRecognitionActivity;
import com.bupt.FinalProj_TYH.activity.VisualEnhanceActivity;
import com.bupt.FinalProj_TYH.activity.ContactActivity;


/*
 * Class:   MainActivity
 * 职能:    主activity用于初始化初始界面以及起其他activity
 */
public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "Camera access is required.", Toast.LENGTH_SHORT).show();

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA_PERMISSION);
            }
        }

        // 获取button的点击事件，开启颜色识别 ColorRecognitionActivity
        findViewById(R.id.CR).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ColorRecognitionActivity.class));
        });

        // 获取button的点击事件，开启视觉强化 VisualEnhanceActivity
        findViewById(R.id.VE).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, VisualEnhanceActivity.class));
        });

        // 获取button的点击事件，开启联系我们 ContactActivity
        findViewById(R.id.ACK).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ContactActivity.class));
        });

        // TODO: more function of program
        //findViewById(R.id.MORE).setOnClickListener(v -> {
        //    startActivity(new Intent(MainActivity.this, MoreToComeActivity.class));
        //});
    }
}
