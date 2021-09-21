/*
 * Copyright 2021 唐义泓
 *
 * Title:   Sight
 * Version: 1
 * Contact: tyh@bupt.edu.cn
 */

package com.bupt.FinalProj_TYH.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;

import com.bupt.FinalProj_TYH.utils.CameraController;
import com.bupt.FinalProj_TYH.R;

/*
 * Class:   VisualEnhanceActivity
 * 职能:    用于初始化视觉增强界面，开启相机，提供视觉提升选项的接口
 */
public class VisualEnhanceActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final int REQUEST_WRITESTORAGE_PERMISSION = 102;
    private static final int REQUEST_READSTORAGE_PERMISSION = 103;
    private FrameLayout container;
    private CameraController renderer;
    private TextureView textureView;
    private int filterId = R.id.filter0;
    private int mCurrentFilterId = 0;

    String[] TITLES = {"原始", "边缘检测", "Fun   像素",
            "Fun   电磁干扰", "Fun   三角马赛克", "Fun   乐高",
            "Fun   瓦片马赛克", "蓝橙重构", "Fun   色差",
            "Fun   流动", "高对比度", "Fun   噪波变形", "Fun   空间重构",
            "Fun   映射", "Fun   阴影", "Fun   利希滕施泰因式",
            "Fun   字符", "Fun   钱币", "Fun   破碎", "Fun   多边形",
            "Fun   泰森多边形", "黑白", "灰度", "颜色取反",
            "Fun   怀乡", "Fun   铸", "Fun   浮雕", "Fun   漩涡", "Fun   六边形马赛克",
            "Fun   横镜像", "Fun   重复", "Fun   卡通", "Fun   纵镜像"
    };

    Integer[] FILTER_RES_IDS = {R.id.filter0, R.id.filter1, R.id.filter2, R.id.filter3, R.id.filter4,
            R.id.filter5, R.id.filter6, R.id.filter7, R.id.filter8, R.id.filter9, R.id.filter10,
            R.id.filter11, R.id.filter12, R.id.filter13, R.id.filter14, R.id.filter15, R.id.filter16,
            R.id.filter17, R.id.filter18, R.id.filter19, R.id.filter20,
            R.id.filter21, R.id.filter22, R.id.filter23, R.id.filter24,
            R.id.filter25, R.id.filter26, R.id.filter27, R.id.filter28,
            R.id.filter29, R.id.filter30, R.id.filter31, R.id.filter32};

    ArrayList<Integer> mFilterArray = new ArrayList<>(Arrays.asList(FILTER_RES_IDS));

    GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(container = new FrameLayout(this));
        setTitle(TITLES[mCurrentFilterId]);


        if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED  ||
            ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if ( ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "Write storage access is required.", Toast.LENGTH_SHORT).show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_WRITESTORAGE_PERMISSION);
                }
            }

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "Read storage access is required.", Toast.LENGTH_SHORT).show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_READSTORAGE_PERMISSION);
                }
            }

        }

        setupCameraPreviewView();

        mGestureDetector = new GestureDetector(this, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupCameraPreviewView();
                }
            }
        }
    }

    void setupCameraPreviewView() {
        renderer = new CameraController(this);
        textureView = new TextureView(this);
        container.addView(textureView);
        textureView.setSurfaceTextureListener(renderer);

//        textureView.setOnTouchListener(this);
        textureView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });

        textureView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                renderer.onSurfaceTextureSizeChanged(null, v.getWidth(), v.getHeight());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        filterId = item.getItemId();

        setTitle(item.getTitle());

        if (renderer != null)
            renderer.setSelectedFilter(filterId);
        mCurrentFilterId = mFilterArray.indexOf(filterId);
        return true;
    }

    private boolean capture() {
        String mPath = genSaveFileName(getTitle().toString() + "_", ".png");
        File imageFile = new File(mPath);
        if (imageFile.exists()) {
            imageFile.delete();
        }

        // create bitmap screen capture
        Bitmap bitmap = textureView.getBitmap();
        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private String genSaveFileName(String prefix, String suffix) {
        Date date = new Date();
        SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyyMMdd_hhmmss");
        String timeString = dateformat1.format(date);
        String externalPath = Environment.getExternalStorageDirectory().toString();
        return externalPath + "/" + prefix + timeString + suffix;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {

        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float velocity = Math.abs(velocityX) > Math.abs(velocityY) ? velocityX : velocityY;
        int step = velocity > 0 ? -1 : 1;
        mCurrentFilterId = circleLoop(TITLES.length, mCurrentFilterId, step);
        setTitle(TITLES[mCurrentFilterId]);
        if (renderer != null) {
            renderer.setSelectedFilter(FILTER_RES_IDS[mCurrentFilterId]);
        }
        return true;
    }

    private int circleLoop(int size, int currentPos, int step) {
        if (step == 0) {
            return currentPos;
        }

        if (step > 0) {
            if (currentPos + step >= size) {
                return (currentPos + step) % size;
            } else {
                return currentPos + step;
            }
        } else {
            if (currentPos + step < 0) {
                return currentPos + step + size;
            } else {
                return currentPos + step;
            }
        }
    }
}
