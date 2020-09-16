package com.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @version 创建时间：2017-12-20 下午3:41:02 项目：repair 包名：com.xh.string
 * 文件名：StringUtil.java 作者：lhl 说明:
 */

public class StringUtil {
    public static final int F = Integer.valueOf("f", 16);
    public static final int FF = Integer.valueOf("ff", 16);
    private final static String PX = "px";
    private final static String DIP = "dip";
    private final static String DP = "dp";
    private final static String SP = "sp";

    public static Integer string2int(String string) {
        if (NullUtil.isEmpty(string))
            return null;
        if (string.startsWith("#"))
            return Integer.valueOf(string.substring(1), 16);
        if (string.startsWith("0x"))
            return Integer.valueOf(string.substring(2), 16);
        if (string.startsWith("0"))
            return Integer.valueOf(string.substring(2), 8);
        return Integer.valueOf(string.substring(2), 10);
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    private static final Map<String, Integer> colourKeywords = new HashMap<String, Integer>(47);

    static {
        colourKeywords.put("aliceblue", 0xf0f8ff);
        colourKeywords.put("antiquewhite", 0xfaebd7);
        colourKeywords.put("aqua", 0x00ffff);
        colourKeywords.put("aquamarine", 0x7fffd4);
        colourKeywords.put("azure", 0xf0ffff);
        colourKeywords.put("beige", 0xf5f5dc);
        colourKeywords.put("bisque", 0xffe4c4);
        colourKeywords.put("black", 0x000000);
        colourKeywords.put("blanchedalmond", 0xffebcd);
        colourKeywords.put("blue", 0x0000ff);
        colourKeywords.put("blueviolet", 0x8a2be2);
        colourKeywords.put("brown", 0xa52a2a);
        colourKeywords.put("burlywood", 0xdeb887);
        colourKeywords.put("cadetblue", 0x5f9ea0);
        colourKeywords.put("chartreuse", 0x7fff00);
        colourKeywords.put("chocolate", 0xd2691e);
        colourKeywords.put("coral", 0xff7f50);
        colourKeywords.put("cornflowerblue", 0x6495ed);
        colourKeywords.put("cornsilk", 0xfff8dc);
        colourKeywords.put("crimson", 0xdc143c);
        colourKeywords.put("cyan", 0x00ffff);
        colourKeywords.put("darkblue", 0x00008b);
        colourKeywords.put("darkcyan", 0x008b8b);
        colourKeywords.put("darkgoldenrod", 0xb8860b);
        colourKeywords.put("darkgray", 0xa9a9a9);
        colourKeywords.put("darkgreen", 0x006400);
        colourKeywords.put("darkgrey", 0xa9a9a9);
        colourKeywords.put("darkkhaki", 0xbdb76b);
        colourKeywords.put("darkmagenta", 0x8b008b);
        colourKeywords.put("darkolivegreen", 0x556b2f);
        colourKeywords.put("darkorange", 0xff8c00);
        colourKeywords.put("darkorchid", 0x9932cc);
        colourKeywords.put("darkred", 0x8b0000);
        colourKeywords.put("darksalmon", 0xe9967a);
        colourKeywords.put("darkseagreen", 0x8fbc8f);
        colourKeywords.put("darkslateblue", 0x483d8b);
        colourKeywords.put("darkslategray", 0x2f4f4f);
        colourKeywords.put("darkslategrey", 0x2f4f4f);
        colourKeywords.put("darkturquoise", 0x00ced1);
        colourKeywords.put("darkviolet", 0x9400d3);
        colourKeywords.put("deeppink", 0xff1493);
        colourKeywords.put("deepskyblue", 0x00bfff);
        colourKeywords.put("dimgray", 0x696969);
        colourKeywords.put("dimgrey", 0x696969);
        colourKeywords.put("dodgerblue", 0x1e90ff);
        colourKeywords.put("firebrick", 0xb22222);
        colourKeywords.put("floralwhite", 0xfffaf0);
        colourKeywords.put("forestgreen", 0x228b22);
        colourKeywords.put("fuchsia", 0xff00ff);
        colourKeywords.put("gainsboro", 0xdcdcdc);
        colourKeywords.put("ghostwhite", 0xf8f8ff);
        colourKeywords.put("gold", 0xffd700);
        colourKeywords.put("goldenrod", 0xdaa520);
        colourKeywords.put("gray", 0x808080);
        colourKeywords.put("green", 0x008000);
        colourKeywords.put("greenyellow", 0xadff2f);
        colourKeywords.put("grey", 0x808080);
        colourKeywords.put("honeydew", 0xf0fff0);
        colourKeywords.put("hotpink", 0xff69b4);
        colourKeywords.put("indianred", 0xcd5c5c);
        colourKeywords.put("indigo", 0x4b0082);
        colourKeywords.put("ivory", 0xfffff0);
        colourKeywords.put("khaki", 0xf0e68c);
        colourKeywords.put("lavender", 0xe6e6fa);
        colourKeywords.put("lavenderblush", 0xfff0f5);
        colourKeywords.put("lawngreen", 0x7cfc00);
        colourKeywords.put("lemonchiffon", 0xfffacd);
        colourKeywords.put("lightblue", 0xadd8e6);
        colourKeywords.put("lightcoral", 0xf08080);
        colourKeywords.put("lightcyan", 0xe0ffff);
        colourKeywords.put("lightgoldenrodyellow", 0xfafad2);
        colourKeywords.put("lightgray", 0xd3d3d3);
        colourKeywords.put("lightgreen", 0x90ee90);
        colourKeywords.put("lightgrey", 0xd3d3d3);
        colourKeywords.put("lightpink", 0xffb6c1);
        colourKeywords.put("lightsalmon", 0xffa07a);
        colourKeywords.put("lightseagreen", 0x20b2aa);
        colourKeywords.put("lightskyblue", 0x87cefa);
        colourKeywords.put("lightslategray", 0x778899);
        colourKeywords.put("lightslategrey", 0x778899);
        colourKeywords.put("lightsteelblue", 0xb0c4de);
        colourKeywords.put("lightyellow", 0xffffe0);
        colourKeywords.put("lime", 0x00ff00);
        colourKeywords.put("limegreen", 0x32cd32);
        colourKeywords.put("linen", 0xfaf0e6);
        colourKeywords.put("magenta", 0xff00ff);
        colourKeywords.put("maroon", 0x800000);
        colourKeywords.put("mediumaquamarine", 0x66cdaa);
        colourKeywords.put("mediumblue", 0x0000cd);
        colourKeywords.put("mediumorchid", 0xba55d3);
        colourKeywords.put("mediumpurple", 0x9370db);
        colourKeywords.put("mediumseagreen", 0x3cb371);
        colourKeywords.put("mediumslateblue", 0x7b68ee);
        colourKeywords.put("mediumspringgreen", 0x00fa9a);
        colourKeywords.put("mediumturquoise", 0x48d1cc);
        colourKeywords.put("mediumvioletred", 0xc71585);
        colourKeywords.put("midnightblue", 0x191970);
        colourKeywords.put("mintcream", 0xf5fffa);
        colourKeywords.put("mistyrose", 0xffe4e1);
        colourKeywords.put("moccasin", 0xffe4b5);
        colourKeywords.put("navajowhite", 0xffdead);
        colourKeywords.put("navy", 0x000080);
        colourKeywords.put("oldlace", 0xfdf5e6);
        colourKeywords.put("olive", 0x808000);
        colourKeywords.put("olivedrab", 0x6b8e23);
        colourKeywords.put("orange", 0xffa500);
        colourKeywords.put("orangered", 0xff4500);
        colourKeywords.put("orchid", 0xda70d6);
        colourKeywords.put("palegoldenrod", 0xeee8aa);
        colourKeywords.put("palegreen", 0x98fb98);
        colourKeywords.put("paleturquoise", 0xafeeee);
        colourKeywords.put("palevioletred", 0xdb7093);
        colourKeywords.put("papayawhip", 0xffefd5);
        colourKeywords.put("peachpuff", 0xffdab9);
        colourKeywords.put("peru", 0xcd853f);
        colourKeywords.put("pink", 0xffc0cb);
        colourKeywords.put("plum", 0xdda0dd);
        colourKeywords.put("powderblue", 0xb0e0e6);
        colourKeywords.put("purple", 0x800080);
        colourKeywords.put("red", 0xff0000);
        colourKeywords.put("rosybrown", 0xbc8f8f);
        colourKeywords.put("royalblue", 0x4169e1);
        colourKeywords.put("saddlebrown", 0x8b4513);
        colourKeywords.put("salmon", 0xfa8072);
        colourKeywords.put("sandybrown", 0xf4a460);
        colourKeywords.put("seagreen", 0x2e8b57);
        colourKeywords.put("seashell", 0xfff5ee);
        colourKeywords.put("sienna", 0xa0522d);
        colourKeywords.put("silver", 0xc0c0c0);
        colourKeywords.put("skyblue", 0x87ceeb);
        colourKeywords.put("slateblue", 0x6a5acd);
        colourKeywords.put("slategray", 0x708090);
        colourKeywords.put("slategrey", 0x708090);
        colourKeywords.put("snow", 0xfffafa);
        colourKeywords.put("springgreen", 0x00ff7f);
        colourKeywords.put("steelblue", 0x4682b4);
        colourKeywords.put("tan", 0xd2b48c);
        colourKeywords.put("teal", 0x008080);
        colourKeywords.put("thistle", 0xd8bfd8);
        colourKeywords.put("tomato", 0xff6347);
        colourKeywords.put("turquoise", 0x40e0d0);
        colourKeywords.put("violet", 0xee82ee);
        colourKeywords.put("wheat", 0xf5deb3);
        colourKeywords.put("white", 0xffffff);
        colourKeywords.put("whitesmoke", 0xf5f5f5);
        colourKeywords.put("yellow", 0xffff00);
        colourKeywords.put("yellowgreen", 0x9acd32);
    }

    public static Integer color(String string) {
        if (NullUtil.isEmpty(string))
            return null;
        if (colourKeywords.containsKey(string))
            return colourKeywords.get(string);
        boolean is16 = false;
        if (string.startsWith("#")) {
            is16 = true;
            string = string.substring(1);
        } else if (string.startsWith("0x")) {
            is16 = true;
            string = string.substring(2);
        }
        int len = string.length();
        if (len != 3 && len != 4 && len != 6 && len != 8)
            return null;
        switch (len) {
            case 3: {
                int r = string2int(string.substring(0, 1), is16);
                r = r * FF / F;
                int g = string2int(string.substring(1, 2), is16);
                g = g * FF / F;
                int b = string2int(string.substring(2, 3), is16);
                b = b * FF / F;
                return Color.rgb(r, g, b);
            }
            case 4: {
                int a = string2int(string.substring(0, 1), is16);
                a = a * FF / F;
                int r = string2int(string.substring(1, 2), is16);
                r = r * FF / F;
                int g = string2int(string.substring(2, 3), is16);
                g = g * FF / F;
                int b = string2int(string.substring(3, 4), is16);
                b = b * FF / F;
                return Color.argb(a, r, g, b);
            }
            case 6: {
                int r = string2int(string.substring(0, 2), is16);
                int g = string2int(string.substring(2, 4), is16);
                int b = string2int(string.substring(4, 6), is16);
                return Color.rgb(r, g, b);
            }
            default: {
                int a = string2int(string.substring(0, 2), is16);
                int r = string2int(string.substring(2, 4), is16);
                int g = string2int(string.substring(4, 6), is16);
                int b = string2int(string.substring(6, 8), is16);
                return Color.argb(a, r, g, b);
            }
        }
    }

    public static int width(String string) {
        if (string.endsWith(PX))
            return Integer.valueOf(string.substring(0, string.length() - 2));
        if (string.endsWith(DP)) {
            int w = Integer.valueOf(string.substring(0, string.length() - 2));
            return PhonUtil.dip2px(w);
        }
        if (string.endsWith(DIP)) {
            int w = Integer.valueOf(string.substring(0, string.length() - 3));
            return PhonUtil.dip2px(w);
        }
        if (string.endsWith(SP)) {
            int w = Integer.valueOf(string.substring(0, string.length() - 2));
            return PhonUtil.sp2px(w);
        }
        return Integer.valueOf(string);
    }

    public static int string2int(String string, boolean is16) {
        return Integer.valueOf(string, is16 ? 16 : 10);
    }

    private static final SparseArray<float[]> sPointList;

    static {
        sPointList = new SparseArray<float[]>();
        float[][] LETTERS = new float[][]{
                new float[]{
                        // A
                        24, 0, 1, 22, 1, 22, 1, 72, 24, 0, 47, 22, 47, 22, 47,
                        72, 1, 48, 47, 48},

                new float[]{
                        // B
                        0, 0, 0, 72, 0, 0, 37, 0, 37, 0, 47, 11, 47, 11, 47,
                        26, 47, 26, 38, 36, 38, 36, 0, 36, 38, 36, 47, 46, 47,
                        46, 47, 61, 47, 61, 38, 71, 37, 72, 0, 72,},

                new float[]{
                        // C
                        47, 0, 0, 0, 0, 0, 0, 72, 0, 72, 47, 72,},

                new float[]{
                        // D
                        0, 0, 0, 72, 0, 0, 24, 0, 24, 0, 47, 22, 47, 22, 47,
                        48, 47, 48, 23, 72, 23, 72, 0, 72,},

                new float[]{
                        // E
                        0, 0, 0, 72, 0, 0, 47, 0, 0, 36, 37, 36, 0, 72, 47, 72,},

                new float[]{
                        // F
                        0, 0, 0, 72, 0, 0, 47, 0, 0, 36, 37, 36,},

                new float[]{
                        // G
                        47, 23, 47, 0, 47, 0, 0, 0, 0, 0, 0, 72, 0, 72, 47, 72,
                        47, 72, 47, 48, 47, 48, 24, 48,},

                new float[]{
                        // H
                        0, 0, 0, 72, 0, 36, 47, 36, 47, 0, 47, 72,},

                new float[]{
                        // I
                        0, 0, 47, 0, 24, 0, 24, 72, 0, 72, 47, 72,},

                new float[]{
                        // J
                        47, 0, 47, 72, 47, 72, 24, 72, 24, 72, 0, 48,},

                new float[]{
                        // K
                        0, 0, 0, 72, 47, 0, 3, 33, 3, 38, 47, 72,},

                new float[]{
                        // L
                        0, 0, 0, 72, 0, 72, 47, 72,},

                new float[]{
                        // M
                        0, 0, 0, 72, 0, 0, 24, 23, 24, 23, 47, 0, 47, 0, 47,
                        72,},

                new float[]{
                        // N
                        0, 0, 0, 72, 0, 0, 47, 72, 47, 72, 47, 0,},

                new float[]{
                        // O
                        0, 0, 0, 72, 0, 72, 47, 72, 47, 72, 47, 0, 47, 0, 0, 0,},

                new float[]{
                        // P
                        0, 0, 0, 72, 0, 0, 47, 0, 47, 0, 47, 36, 47, 36, 0, 36,},

                new float[]{
                        // Q
                        0, 0, 0, 72, 0, 72, 23, 72, 23, 72, 47, 48, 47, 48, 47,
                        0, 47, 0, 0, 0, 24, 28, 47, 71,},

                new float[]{
                        // R
                        0, 0, 0, 72, 0, 0, 47, 0, 47, 0, 47, 36, 47, 36, 0, 36,
                        0, 37, 47, 72,},

                new float[]{
                        // S
                        47, 0, 0, 0, 0, 0, 0, 36, 0, 36, 47, 36, 47, 36, 47,
                        72, 47, 72, 0, 72,},

                new float[]{
                        // T
                        0, 0, 47, 0, 24, 0, 24, 72,},

                new float[]{
                        // U
                        0, 0, 0, 72, 0, 72, 47, 72, 47, 72, 47, 0,},

                new float[]{
                        // V
                        0, 0, 24, 72, 24, 72, 47, 0,},

                new float[]{
                        // W
                        0, 0, 0, 72, 0, 72, 24, 49, 24, 49, 47, 72, 47, 72, 47,
                        0},

                new float[]{
                        // X
                        0, 0, 47, 72, 47, 0, 0, 72},

                new float[]{
                        // Y
                        0, 0, 24, 23, 47, 0, 24, 23, 24, 23, 24, 72},

                new float[]{
                        // Z
                        0, 0, 47, 0, 47, 0, 0, 72, 0, 72, 47, 72},};
        final float[][] NUMBERS = new float[][]{
                new float[]{
                        // 0
                        0, 0, 0, 72, 0, 72, 47, 72, 47, 72, 47, 0, 47, 0, 0, 0,},
                new float[]{
                        // 1
                        24, 0, 24, 72,},

                new float[]{
                        // 2
                        0, 0, 47, 0, 47, 0, 47, 36, 47, 36, 0, 36, 0, 36, 0,
                        72, 0, 72, 47, 72},

                new float[]{
                        // 3
                        0, 0, 47, 0, 47, 0, 47, 36, 47, 36, 0, 36, 47, 36, 47,
                        72, 47, 72, 0, 72,},

                new float[]{
                        // 4
                        0, 0, 0, 36, 0, 36, 47, 36, 47, 0, 47, 72,},

                new float[]{
                        // 5
                        0, 0, 0, 36, 0, 36, 47, 36, 47, 36, 47, 72, 47, 72, 0,
                        72, 0, 0, 47, 0},

                new float[]{
                        // 6
                        0, 0, 0, 72, 0, 72, 47, 72, 47, 72, 47, 36, 47, 36, 0,
                        36},

                new float[]{
                        // 7
                        0, 0, 47, 0, 47, 0, 47, 72},

                new float[]{
                        // 8
                        0, 0, 0, 72, 0, 72, 47, 72, 47, 72, 47, 0, 47, 0, 0, 0,
                        0, 36, 47, 36},

                new float[]{
                        // 9
                        47, 0, 0, 0, 0, 0, 0, 36, 0, 36, 47, 36, 47, 0, 47, 72,}};
        // A - Z
        for (int i = 0; i < LETTERS.length; i++) {
            sPointList.append(i + 65, LETTERS[i]);
        }
        // a - z
        for (int i = 0; i < LETTERS.length; i++) {
            sPointList.append(i + 65 + 32, LETTERS[i]);
        }
        // 0 - 9
        for (int i = 0; i < NUMBERS.length; i++) {
            sPointList.append(i + 48, NUMBERS[i]);
        }
        // blank
        addChar(' ', new float[]{});
        // -
        addChar('-', new float[]{0, 36, 47, 36});
        // .
        addChar('.', new float[]{24, 60, 24, 72});
    }

    public static void addChar(char c, float[] points) {
        sPointList.append(c, points);
    }

    /**
     * lhl 2018-1-9 下午3:59:39 说明：将字母转化为路径
     *
     * @param str
     * @return ArrayList<float [ ]>
     */
    private static ArrayList<float[]> getPathFloats(String str) {
        return getPathFloats(str, 1, 14);
    }

    /**
     * lhl 2018-1-9 下午4:00:05 说明：将字母转化为路径
     *
     * @param str
     * @param scale
     * @param gapBetweenLetter 字母之间的距离
     * @return ArrayList<float [ ]>
     */
    private static ArrayList<float[]> getPathFloats(String str, float scale,
                                                    int gapBetweenLetter) {
        ArrayList<float[]> list = new ArrayList<float[]>();
        float offsetForWidth = 0;
        for (int i = 0; i < str.length(); i++) {
            int pos = str.charAt(i);
            int key = sPointList.indexOfKey(pos);
            if (key == -1) {
                continue;
            }
            float[] points = sPointList.get(pos);
            int pointCount = points.length / 4;

            for (int j = 0; j < pointCount; j++) {
                float[] line = new float[4];
                for (int k = 0; k < 4; k++) {
                    float l = points[j * 4 + k];
                    // x
                    if (k % 2 == 0) {
                        line[k] = (l + offsetForWidth) * scale;
                    }
                    // y
                    else {
                        line[k] = l * scale;
                    }
                }
                list.add(line);
            }
            offsetForWidth += 57 + gapBetweenLetter;
        }
        return list;
    }

    /**
     * 从R.array.xxx里取出点阵，
     *
     * @param context
     * @param arrayId
     * @param zoomSize
     * @return
     */
    public static Path getPath(Context context, int arrayId, float zoomSize) {
        Path path = new Path();
        String[] points = context.getResources().getStringArray(arrayId);
        for (int i = 0; i < points.length; i++) {
            String[] x = points[i].split(",");
            for (int j = 0; j < x.length; j = j + 2) {
                if (j == 0) {
                    path.moveTo(Float.parseFloat(x[j]) * zoomSize,
                            Float.parseFloat(x[j + 1]) * zoomSize);
                } else {
                    path.lineTo(Float.parseFloat(x[j]) * zoomSize,
                            Float.parseFloat(x[j + 1]) * zoomSize);
                }
            }
        }
        return path;
    }

    /**
     * 根据ArrayList<float[]> path 解析
     *
     * @param path
     * @return
     */
    private static Path getPath(ArrayList<float[]> path) {
        Path sPath = new Path();
        for (int i = 0; i < path.size(); i++) {
            float[] floats = path.get(i);
            sPath.moveTo(floats[0], floats[1]);
            sPath.lineTo(floats[2], floats[3]);
        }
        return sPath;
    }

    /**
     * lhl 2018-1-9 下午3:59:39 说明：将字母转化为路径
     *
     * @param str
     * @return ArrayList<float [ ]>
     */
    public static Path string2path(String str) {
        return getPath(getPathFloats(str));
    }

    /**
     * lhl 2018-1-9 下午4:00:05 说明：将字母转化为路径
     *
     * @param str
     * @param scale
     * @param gapBetweenLetter 字母之间的距离
     * @return ArrayList<float [ ]>
     */
    public static Path string2path(String str, float scale, int gapBetweenLetter) {
        return getPath(getPathFloats(str, scale, gapBetweenLetter));
    }

    // 过滤特殊字符
    public static String StringFilter(String str) {
        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        try {
            String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            return m.replaceAll("").trim();
        } catch (Exception e) {
            return str;
        }
    }

    /**
     * 判断是否为中文
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否有乱码
     */
    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {//判断不是字母和数字
                if (!isChinese(c)) {//判断不是中文
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
    }
}