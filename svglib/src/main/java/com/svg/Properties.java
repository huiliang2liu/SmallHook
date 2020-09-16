package com.svg;

import com.util.PathUtil;
import com.utils.LogUtil;

/**
 * com.svg
 * 2019/1/11 11:06
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class Properties {
    private SVGPars.StyleSet styles = null;
    private String display;
    private String fill;
    private String stroke;
    private float strokeWidth;
    private String strokeLinecap;
    private String strokeLinejoin;
    private Float opacity;
    private Float fillOpacity;
    private Float strokeOpacity = 1.0f;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getStrokeOpacity() {
        return strokeOpacity;
    }

    public void setStrokeOpacity(String strokeOpacity) {
        this.strokeOpacity = Float.valueOf(strokeOpacity);
    }

    public Float getOpacity() {
        return opacity;
    }

    public Float getFillOpacity() {
        return fillOpacity;
    }

    public void setFillOpacity(String fillOpacity) {
        this.fillOpacity = Float.valueOf(fillOpacity);
    }

    public void setOpacity(String opacity) {
        this.opacity = Float.valueOf(opacity);
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(String strokeWidth) {
        this.strokeWidth = PathUtil.string2float(strokeWidth);
    }

    public String getStrokeLinecap() {
        return strokeLinecap;
    }

    public void setStrokeLinecap(String strokeLinecap) {
        this.strokeLinecap = strokeLinecap;
    }

    public String getStrokeLinejoin() {
        return strokeLinejoin;
    }

    public void setStrokeLinejoin(String strokeLinejoin) {
        this.strokeLinejoin = strokeLinejoin;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public SVGPars.StyleSet getStyles() {
        return styles;
    }

    public void setStyles(SVGPars.StyleSet styles) {
        this.styles = styles;
    }

    public Properties() {
    }

    public Integer getHex(String value) {
        LogUtil.e("getHex", value);
        if (value == null || !value.startsWith("#")) {
            return null;
        } else {
            try {
                return Integer.parseInt(value.substring(1), 16);
            } catch (NumberFormatException nfe) {
                // todo - parse word-based color here
                nfe.printStackTrace();
                return null;
            }
        }
    }
}
