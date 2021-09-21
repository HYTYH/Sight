/*
 * Copyright 2021 唐义泓
 *
 * Title:   Sight
 * Version: 1
 * Contact: tyh@bupt.edu.cn
 */

package com.bupt.FinalProj_TYH.filters;

import android.content.Context;
import android.opengl.GLES20;

import com.bupt.FinalProj_TYH.R;
import com.bupt.FinalProj_TYH.utils.MyOpenGL;
import com.bupt.FinalProj_TYH.utils.CameraFilter;

/*
 * Class:   BlueorangeFilter
 * 职能:    继承自CameraFilter，实现一个蓝橘的视觉增强效果
 */
public class BlueorangeFilter extends CameraFilter {
    private int program;

    public BlueorangeFilter(Context context) {
        super(context);

        program = MyOpenGL.buildProgram(context, R.raw.vertext, R.raw.blue_orange);
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
