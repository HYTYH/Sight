/*
 * Copyright 2021 唐义泓
 * Sight Version 1
 * Contact: tyh@bupt.edu.cn
 */

package com.bupt.FinalProj_TYH.filters;

import android.content.Context;
import android.opengl.GLES20;

import com.bupt.FinalProj_TYH.R;
import com.bupt.FinalProj_TYH.utils.MyOpenGL;
import com.bupt.FinalProj_TYH.utils.CameraFilter;

/*
 * Class:   AsciiArtFilter
 * 职能:    继承自CameraFilter，实现一个字符画面的视觉增强效果
 */
public class AsciiArtFilter extends CameraFilter {
    private int program;

    public AsciiArtFilter(Context context) {
        super(context);

        program = MyOpenGL.buildProgram(context, R.raw.vertext, R.raw.ascii_art);
    }

    @Override
    public void onDraw(int cameraTexId, int canvasWidth, int canvasHeight) {
        setupShaderInputs(program,
                new int[]{canvasWidth, canvasHeight},
                new int[]{cameraTexId},
                new int[][]{});
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }
}
