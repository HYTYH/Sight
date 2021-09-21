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
 * Class:   HexagonMosaicFilter
 * 职能:    继承自CameraFilter，实现一个六边形马赛克的视觉增强效果
 */
public class HexagonMosaicFilter extends CameraFilter {
    private int program;

    public HexagonMosaicFilter(Context context) {
        super(context);
        program = MyOpenGL.buildProgram(context, R.raw.vertext, R.raw.hexagon_mosaic);
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
