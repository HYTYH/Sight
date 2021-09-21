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
 * Class:   MappingFilter
 * 职能:    继承自CameraFilter，实现一个映射的视觉增强效果
 */
public class MappingFilter extends CameraFilter {
    private int program;
    private int texture2Id;

    public MappingFilter(Context context) {
        super(context);

        // Build shaders
        program = MyOpenGL.buildProgram(context, R.raw.vertext, R.raw.mapping);

        // Load the texture will need for the shader
        texture2Id = MyOpenGL.loadTexture(context, R.raw.tex00, new int[2]);
    }

    @Override
    public void onDraw(int cameraTexId, int canvasWidth, int canvasHeight) {
        setupShaderInputs(program,
                new int[]{canvasWidth, canvasHeight},
                new int[]{cameraTexId, texture2Id},
                new int[][]{});
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }
}