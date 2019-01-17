package com.svg;

import android.graphics.Color;
import android.graphics.RectF;
import android.util.Log;

import com.iface.ISVG;
import com.utils.StringUtil;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * com.svg
 * 2019/1/11 12:27
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class SVGEntityHandler extends ASVGPars.SAXParasHandler {
    private final static String TAG = "SVGEntityHandler";
    private final static String X = "x";
    private final static String Y = "y";
    private final static String WIDTH = "width";
    private final static String HEIGHT = "height";
    private final static String VIEW_BOX = "viewBox";
    private final static String TRANSFORM = "transform";
    private final static String X1 = "x1";
    private final static String X2 = "x2";
    private final static String Y1 = "y1";
    private final static String Y2 = "y2";
    private final static String CX = "cx";
    private final static String CY = "cy";
    private final static String R = "r";
    private final static String RX = "rx";
    private final static String RY = "ry";
    private final static String POLYGON = "polygon";
    private final static String POLYLINE = "polyline";
    private final static String POINTS = "points";
    private final static String D = "d";
    private final static String ID = "id";
    private final static String GRADIENT_TRANSFORM = "gradientTransform";
    private final static String HREF = "href";
    private final static String OFFSET = "offset";
    private final static String STYLE = "style";
    private final static String DISPLAY = "display";
    private final static String BOUNDS = "bounds";
    private final static String NONE = "none";
    private final static String PATH = "path";
    private final static String ELLIPSE = "ellipse";
    private final static String CIRCLE = "circle";
    private final static String LINE = "line";
    private final static String RECT = "rect";
    private final static String G = "g";
    private final static String STOP = "stop";
    private final static String RADIAL_GRADIENT = "radialGradient";
    private final static String LINEAR_GRADIENT = "linearGradient";
    private final static String DEFS = "defs";
    private final static String SVG = "svg";
    private final static String FILL = "fill";
    private final static String STROKE = "stroke";
    private final static String STROKE_WIDTH = "stroke-width";
    private final static String STROKE_LINECAP = "stroke-linecap";
    private final static String STROKE_LINEJOIN = "stroke-linejoin";
    private final static String OPACITY = "opacity";
    private final static String FILL_OPACITY = "fill-opacity";
    private final static String STROKE_OPACITY = "stroke-opacity";
    private final static String TEXT = "text";
    private final static String TSPAN = "tspan";
    private final static String FONT_FAMILY = "font-family";
    private final static String FONT_SIZE = "font-size";
    private Gradient gradient = null;
    boolean pushed = false;
    private SVGEntity entity;
    private boolean whiteMode = false;
    private Integer searchColor = null;
    private Integer replaceColor = null;
    private StringBuffer sb;
    private SVGText svgText;
    private SVGText.Tspan tspan;

    public void setColorSwap(Integer searchColor, Integer replaceColor) {
        this.searchColor = searchColor;
        this.replaceColor = replaceColor;
    }

    public void setWhiteMode(boolean whiteMode) {
        this.whiteMode = whiteMode;
    }

    public SVGEntity getEntity() {
        return entity;
    }



    @Override
    public void startDocument() throws SAXException {
        // Set up prior to parsing a doc
        entity = new SVGEntity();
        entity.setColorSwap(searchColor, replaceColor);
        entity.setWhiteMode(whiteMode);
        sb = new StringBuffer();
    }

    @Override
    public void endDocument() throws SAXException {
        // Clean up after parsing a doc
    }

    private boolean hidden = false;
    private int hiddenLevel = 0;
    private boolean boundsMode = false;

    private boolean attsIsNotEmpty(Attributes atts) {
        return atts != null && atts.getLength() > 0;
    }

    private void parserBounds(Attributes atts) {
        float x = 0, y = 0, width = 0, height = 0;
        for (int i = 0; i < atts.getLength(); i++) {
            String key = atts.getLocalName(i);
            String value = atts.getValue(i);
            float fValue = Float.valueOf(value);
            if (key.equals(WIDTH))
                width = fValue;
            else if (key.equals(HEIGHT))
                height = fValue;
            else if (key.equals(X))
                x = fValue;
            else if (key.equals(Y))
                y = fValue;
        }
        entity.setBounds(new RectF(x, y, x + width, y + height));
    }

    private void parserSvg(Attributes atts) {
        for (int i = 0; i < atts.getLength(); i++) {
            String key = atts.getLocalName(i);
            String value = atts.getValue(i);
            if (key.equals(X))
                entity.setX(StringUtil.width(value));
            else if (key.equals(Y))
                entity.setY(StringUtil.width(value));
            else if (key.equals(WIDTH))
                entity.setWidth(value);
            else if (key.equals(HEIGHT))
                entity.setHeigth(value);
            else if (key.equals(VIEW_BOX))
                entity.setViewBox(value);
        }
    }

    private void parserDefs(Attributes atts) {

    }

    private void parserStop(Attributes atts) {
        if (gradient != null) {
            for (int i = 0; i < atts.getLength(); i++) {
                String key = atts.getLocalName(i);
                String value = atts.getValue(i);
                if (key.equals(OFFSET)) {
                    float offset = Float.valueOf(value);
                    gradient.positions.add(offset);
                } else if (key.equals(STYLE)) {
                    int color = Color.BLACK;
                    SVGPars.StyleSet styleSet = new SVGPars.StyleSet(value);
                    String colorStyle = styleSet.getStyle("stop-color");
                    if (colorStyle != null) {
                        if (colorStyle.startsWith("#")) {
                            color = Integer.parseInt(colorStyle.substring(1),
                                    16);
                        } else {
                            color = Integer.parseInt(colorStyle, 16);
                        }
                    }
                    String opacityStyle = styleSet.getStyle("stop-opacity");
                    if (opacityStyle != null) {
                        float alpha = Float.parseFloat(opacityStyle);
                        int alphaInt = Math.round(255 * alpha);
                        color |= (alphaInt << 24);
                    } else {
                        color |= 0xFF000000;
                    }
                    gradient.colors.add(color);
                }
            }
        }
    }

    private void parserG(Attributes atts) {
        for (int i = 0; i < atts.getLength(); i++) {
            String key = atts.getLocalName(i);
            String value = atts.getValue(i);
            if (key.equals(ID)) {
                if (BOUNDS.equalsIgnoreCase(value))
                    boundsMode = true;
            } else if (key.equals(DISPLAY)) {
                if (NONE.equals(value) && !hidden) {
                    hidden = true;
                    hiddenLevel = 1;
                }
            }
        }
        if (hidden) {
            hiddenLevel++;
        }
    }

    private void parserRect(Attributes atts) {
        SVGRect rect = new SVGRect(entity);
        Properties props = new Properties();
        for (int i = 0; i < atts.getLength(); i++) {
            String key = atts.getLocalName(i);
            String value = atts.getValue(i);
            if (key.equals(X))
                rect.setX(Float.valueOf(value));
            else if (key.equals(Y))
                rect.setY(Float.valueOf(value));
            else if (key.equals(WIDTH))
                rect.setWidth(Float.valueOf(value));
            else if (key.equals(HEIGHT))
                rect.setHeight(Float.valueOf(value));
            else if (key.equals(TRANSFORM))
                rect.setTransform(value);
            else
                setProperty(props, key, value);
        }
        rect.setProps(props);
        entity.add(rect);
    }

    private void parserLine(Attributes atts) {
        SVGLine line = new SVGLine(entity);
        Properties props = new Properties();
        line.setProps(props);
        for (int i = 0; i < atts.getLength(); i++) {
            String key = atts.getLocalName(i);
            String value = atts.getValue(i);
            if (key.equals(X1))
                line.setX1(Float.valueOf(value));
            else if (key.equals(X2))
                line.setX2(Float.valueOf(value));
            else if (key.equals(Y1))
                line.setY1(Float.valueOf(value));
            else if (key.equals(Y2))
                line.setY2(Float.valueOf(value));
            else if (key.equals(TRANSFORM))
                line.setTransform(value);
            else
                setProperty(props, key, value);
        }
        entity.add(line);
    }

    private void parserCircle(Attributes atts) {
        SVGCircle circle = new SVGCircle(entity);
        Properties props = new Properties();
        circle.setProps(props);
        circle.setProps(props);
        for (int i = 0; i < atts.getLength(); i++) {
            String key = atts.getLocalName(i);
            String value = atts.getValue(i);
            if (key.equals(CX))
                circle.setCenterX(Float.valueOf(value));
            else if (key.equals(CY))
                circle.setCenterY(Float.valueOf(value));
            else if (key.equals(R))
                circle.setRadius(Float.valueOf(value));
            else if (key.equals(TRANSFORM))
                circle.setTransform(value);
            else
                setProperty(props, key, value);
        }
        entity.add(circle);
    }

    private void parserEllipse(Attributes atts) {
        SVGEllipse ellipse = new SVGEllipse(entity);
        Properties props = new Properties();
        ellipse.setProps(props);
        for (int i = 0; i < atts.getLength(); i++) {
            String key = atts.getLocalName(i);
            String value = atts.getValue(i);
            if (key.equals(CX))
                ellipse.setCenterX(Float.valueOf(value));
            else if (key.equals(CY))
                ellipse.setCenterY(Float.valueOf(value));
            else if (key.equals(RX))
                ellipse.setRadiusX(Float.valueOf(value));
            else if (key.equals(RY))
                ellipse.setRadiusY(Float.valueOf(value));
            else if (key.equals(TRANSFORM))
                ellipse.setTransform(value);
            else
                setProperty(props, key, value);
        }
        entity.add(ellipse);
    }

    private void parserPolygon(String localName, Attributes atts) {
        SVGPolygon polygon = new SVGPolygon(entity);
        Properties props = new Properties();
        polygon.setProps(props);
        for (int i = 0; i < atts.getLength(); i++) {
            String key = atts.getLocalName(i);
            String value = atts.getValue(i);
            if (key.equals(POINTS))
                polygon.setNumbers(SVGPars.parseNumbers(value));
            else if (key.equals(TRANSFORM))
                polygon.setTransform(value);
            else
                setProperty(props, key, value);
        }
        if (localName.equals(POLYGON))
            polygon.setClose(true);
        entity.add(polygon);
    }

    private void parserPath(Attributes atts) {
        SVGPath path = new SVGPath(entity);
        Properties props = new Properties();
        path.setProps(props);
        for (int i = 0; i < atts.getLength(); i++) {
            String key = atts.getLocalName(i);
            String value = atts.getValue(i);
            if (key.equals(D))
                path.setPath(SVGPars.doPath(value));
            else if (key.equals(TRANSFORM))
                path.setTransform(value);
            else
                setProperty(props, key, value);
        }
        entity.add(path);
    }

    private void setProperty(Properties props, String key, String value) {
        if (key.equals(STYLE))
            props.setStyles(new SVGPars.StyleSet(value));
        else if (key.equals(DISPLAY))
            props.setDisplay(value);
        else if (key.equals(FILL))
            props.setFill(value);
        else if (key.equals(STROKE))
            props.setStroke(value);
        else if (key.equals(STROKE_WIDTH))
            props.setStrokeWidth(value);
        else if (key.equals(STROKE_LINECAP))
            props.setStrokeLinecap(value);
        else if (key.equals(STROKE_LINEJOIN))
            props.setStrokeLinejoin(value);
        else if (key.equals(OPACITY))
            props.setOpacity(value);
        else if (key.equals(FILL_OPACITY))
            props.setFillOpacity(value);
        else if (key.equals(STROKE_OPACITY))
            props.setStrokeOpacity(value);
    }

    private void parserText(Attributes atts) {
        svgText = new SVGText(entity);
        Properties props = new Properties();
        svgText.setProps(props);
        for (int i = 0; i < atts.getLength(); i++) {
            String key = atts.getLocalName(i);
            String value = atts.getValue(i);
            if (key.equals(TRANSFORM))
                svgText.setTransform(value);
        }
        entity.add(svgText);
    }

    private void parserTspan(Attributes atts) {
        // TODO Auto-generated method stub
        tspan = new SVGText.Tspan();
        for (int i = 0; i < atts.getLength(); i++) {
            String key = atts.getLocalName(i);
            String value = atts.getValue(i);
            if (key.equals(X))
                tspan.setX(value);
            else if (key.equals(Y))
                tspan.setY(value);
            else if (key.equals(FILL))
                tspan.setFill(value);
            else if (key.equals(FONT_FAMILY))
                tspan.setFont_family(value);
            else if (key.equals(FONT_SIZE))
                tspan.setFont_size(value);
        }
        svgText.add(tspan);
    }

    @Override
    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts) throws SAXException {
        if (!attsIsNotEmpty(atts))
            return;
        if (boundsMode) {
            if (localName.equals(RECT))
                parserBounds(atts);
            return;
        }
        if (localName.equals(SVG)) {
            parserSvg(atts);
        } else if (localName.equals(DEFS)) {
            parserDefs(atts);
        } else if (localName.equals(LINEAR_GRADIENT)) {
            gradient = doGradient(true, atts);
        } else if (localName.equals(RADIAL_GRADIENT)) {
            gradient = doGradient(false, atts);
        } else if (localName.equals(STOP)) {
            parserStop(atts);
        } else if (localName.equals(G)) {
            parserG(atts);
        } else if (!hidden && localName.equals(RECT)) {
            parserRect(atts);
        } else if (!hidden && localName.equals(LINE)) {
            parserLine(atts);
        } else if (!hidden && localName.equals(CIRCLE)) {
            parserCircle(atts);
        } else if (!hidden && localName.equals(ELLIPSE)) {
            parserEllipse(atts);
        } else if (!hidden
                && (localName.equals(POLYGON) || localName.equals(POLYLINE))) {
            parserPolygon(localName, atts);
        } else if (!hidden && localName.equals(PATH)) {
            parserPath(atts);
        } else if (!hidden && localName.equals(TEXT)) {
            parserText(atts);
        } else if (!hidden && localName.equals(TSPAN)) {
            parserTspan(atts);
        } else if (!hidden) {
            Log.d(TAG, "UNRECOGNIZED SVG COMMAND: " + localName);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) {
        // no-op
        sb.append(ch, start, length);
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if (localName.equals(SVG)) {
        } else if (localName.equals(LINEAR_GRADIENT)) {
            if (gradient.id != null)
                entity.addGradient(gradient.id, gradient);
        } else if (localName.equals(RADIAL_GRADIENT)) {
            if (gradient.id != null) {
                gradient.isRadial = true;
                entity.addGradient(gradient.id, gradient);
            }
        } else if (localName.equals(G)) {
            if (boundsMode) {
                boundsMode = false;
            }
            // Break out of hidden mode
            if (hidden) {
                hiddenLevel--;
                // PathUtil.debug("Hidden down: " + hiddenLevel);
                if (hiddenLevel == 0) {
                    hidden = false;
                }
            }
            // Clear gradient map
        } else if (localName.equals(TEXT))
            svgText = null;
        else if (localName.equals(TSPAN)) {
            tspan.setText(sb.toString());
            sb.delete(0, sb.length());
            tspan = null;
        }
    }

    private Gradient doGradient(boolean isLinear, Attributes atts) {
        Gradient gradient = new Gradient();
        gradient.isLinear = isLinear;
        for (int i = 0; i < atts.getLength(); i++) {
            String key = atts.getLocalName(i);
            String value = atts.getValue(i);
            if (key.equals(ID))
                gradient.id = value;
            else if (key.equals(GRADIENT_TRANSFORM))
                gradient.matrix = SVGPars.parseTransform(value);
            else if (key.equals(HREF)) {
                if (value.startsWith("#")) {
                    value = value.substring(1);
                }
                gradient.xlink = value;
            } else if (key.equals(X1))
                gradient.x1 = Float.valueOf(value);
            else if (key.equals(X2))
                gradient.x2 = Float.valueOf(value);
            else if (key.equals(Y1))
                gradient.y1 = Float.valueOf(value);
            else if (key.equals(Y2))
                gradient.y2 = Float.valueOf(value);
            else if (key.equals(CX))
                gradient.x = Float.valueOf(value);
            else if (key.equals(CY))
                gradient.y = Float.valueOf(value);
            else if (key.equals(R))
                gradient.radius = Float.valueOf(value);
        }
        return gradient;
    }

    @Override
    public ISVG svg() {
        // TODO Auto-generated method stub
        return entity;
    }
}
