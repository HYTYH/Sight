/*
 * Copyright 2021 唐义泓
 *
 * Title:   Sight
 * Version: 1
 * Contact: tyh@bupt.edu.cn
 */

package com.bupt.FinalProj_TYH.model;

/*
 * Class:   RealColor
 * 职能:    用于获取颜色名
 */
public class RealColor {

    private String name;
    private String rgb;
    private String hex;

    public RealColor(String name, String rgb, String hex) {
        this.name = name;
        this.rgb = rgb;
        this.hex = hex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }
}
