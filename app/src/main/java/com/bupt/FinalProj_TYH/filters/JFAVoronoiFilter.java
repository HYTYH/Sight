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
import com.bupt.FinalProj_TYH.utils.MyGLBuffer;
import com.bupt.FinalProj_TYH.utils.MyOpenGL;
import com.bupt.FinalProj_TYH.utils.CameraFilter;

/*
 * Class:   JFAVoronoiFilter
 * 职能:    继承自CameraFilter，实现一个泰森多边形的视觉增强效果
 */
public class JFAVoronoiFilter extends CameraFilter {
    private int programImg;
    private int programA;
    private int programB;
    private int programC;

    private MyGLBuffer bufA;
    private MyGLBuffer bufB;
    private MyGLBuffer bufC;

    public JFAVoronoiFilter(Context context) {
        super(context);

        // Build shaders
        programImg = MyOpenGL.buildProgram(context, R.raw.vertext, R.raw.voronoi);
        programA = MyOpenGL.buildProgram(context, R.raw.vertext, R.raw.voronoi_buf_a);
        programB = MyOpenGL.buildProgram(context, R.raw.vertext, R.raw.voronoi_buf_b);
        programC = MyOpenGL.buildProgram(context, R.raw.vertext, R.raw.voronoi_buf_c);
    }

    @Override
    public void onDraw(int cameraTexId, int canvasWidth, int canvasHeight) {
        // TODO move?
        if (bufA == null || bufA.getWidth() != canvasWidth || bufB.getHeight() != canvasHeight) {
            // Create new textures for buffering
            bufA = new MyGLBuffer(canvasWidth, canvasHeight, GLES20.GL_TEXTURE4);
            bufB = new MyGLBuffer(canvasWidth, canvasHeight, GLES20.GL_TEXTURE5);
            bufC = new MyGLBuffer(canvasWidth, canvasHeight, GLES20.GL_TEXTURE6);
        }

        // Render to buf a
        setupShaderInputs(programA,
                new int[]{canvasWidth, canvasHeight},
                new int[]{cameraTexId, bufA.getTexId()},
                new int[][]{new int[]{canvasWidth, canvasHeight}, new int[]{canvasWidth, canvasHeight}});
        bufA.bind();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        bufA.unbind();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);


        // Render to buf b
        setupShaderInputs(programB,
                new int[]{canvasWidth, canvasHeight},
                new int[]{bufB.getTexId(), bufA.getTexId()},
                new int[][]{new int[]{canvasWidth, canvasHeight}, new int[]{canvasWidth, canvasHeight}});
        bufB.bind();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        bufB.unbind();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);


        // Render to buf c
        setupShaderInputs(programC,
                new int[]{canvasWidth, canvasHeight},
                new int[]{bufC.getTexId(), bufB.getTexId()},
                new int[][]{new int[]{canvasWidth, canvasHeight}, new int[]{canvasWidth, canvasHeight}});
        bufC.bind();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        bufC.unbind();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);


        // Render to screen
        setupShaderInputs(programImg,
                new int[]{canvasWidth, canvasHeight},
                new int[]{bufC.getTexId(), bufA.getTexId()},
                new int[][]{new int[]{canvasWidth, canvasHeight}, new int[]{canvasWidth, canvasHeight}});
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }
}
