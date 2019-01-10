package com.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * com.utils
 * 2018/10/25 16:59
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class MathUtil {
    /**
     * 计算
     *
     * @param calculate 算数表达式
     * @return 计算结果
     */
    public static double calculate(String calculate) {
        return calculateSuffix(infix2suffix(calculate));
    }

    /**
     * 计算后缀表达式
     *
     * @param list 后缀表达式
     * @return 计算结果
     */
    private static double calculateSuffix(List<String> list) {
        Stack<Double> num = new Stack<>();
        for (String c : list) {
            if ("+".equals(c)) {
                double a = num.pop();
                double b = num.pop();
                num.push(b + a);
            } else if ("-".equals(c)) {
                double a = num.pop();
                double b = num.pop();
                num.push(b - a);
            } else if ("*".equals(c)) {
                double a = num.pop();
                double b = num.pop();
                num.push(b * a);
            } else if ("/".equals(c)) {
                double a = num.pop();
                double b = num.pop();
                num.push(b / a);
            } else if ("^".equals(c)) {
                double a = num.pop();
                double b = num.pop();
                num.push(Math.pow(b, a));
            } else
                num.push(Double.valueOf(c));
        }
        return num.pop();
    }

    /**
     * 中缀表达式转化为后缀表达式
     *
     * @param infix 中缀表达式
     * @return 后缀表达式列表
     */
    private static List<String> infix2suffix(String infix) {
        Stack<Character> symbol = new Stack<>();
        List<String> list = new ArrayList<>();
        char[] cs = infix.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : cs) {
            if (c == '(') {
                symbol.push(c);
                if (sb.length() > 0) {
                    list.add(sb.toString());
                    sb.setLength(0);
                }
            } else if (c == ')') {
                char ch = symbol.pop();
                if (sb.length() > 0) {
                    list.add(sb.toString());
                    sb.setLength(0);
                }
                while (ch != '(') {
                    list.add(ch + "");
                    if (symbol.isEmpty())
                        break;
                    ch = symbol.pop();
                }
            } else if (c == '+' || c == '-') {
                if (sb.length() > 0) {
                    list.add(sb.toString());
                    sb.setLength(0);
                }
                if (symbol.isEmpty()) {
                    symbol.push(c);
                    continue;
                }
                char ch = symbol.peek();
                while (ch != '(') {
                    list.add(symbol.pop() + "");
                    if (symbol.isEmpty())
                        break;
                    ch = symbol.peek();
                }
                symbol.push(c);
            } else if (c == '*' || c == '/') {
                if (sb.length() > 0) {
                    list.add(sb.toString());
                    sb.setLength(0);
                }
                if (symbol.isEmpty()) {
                    symbol.push(c);
                    continue;
                }
                char ch = symbol.peek();
                while (ch != '(' && ch != '+' && ch != '-') {
                    list.add(symbol.pop() + "");
                    if (symbol.isEmpty())
                        break;
                    ch = symbol.peek();
                }
                symbol.push(c);
            } else if (c == '^') {
                if (sb.length() > 0) {
                    list.add(sb.toString());
                    sb.setLength(0);
                }
                if (symbol.isEmpty()) {
                    symbol.push(c);
                    continue;
                }
                char ch = symbol.peek();
                while (ch != '(' && ch != '+' && ch != '-' && ch != '*'
                        && ch != '/') {
                    list.add(symbol.pop() + "");
                    if (symbol.isEmpty())
                        break;
                    ch = symbol.peek();
                }
                symbol.push(c);
            } else
                sb.append(c);

        }
        if (sb.length() > 0) {
            list.add(sb.toString());
            sb.setLength(0);
        }
        while (!symbol.isEmpty()) {
            list.add(symbol.pop() + "");
        }
        return list;
    }

}
