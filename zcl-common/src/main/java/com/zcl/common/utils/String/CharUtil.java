package com.zcl.common.utils.String;

/**
 * @description: 字符工具类
 * @author: 周长乐
 * @CreateDate: 2020-12-31 23:05
 * @UpdateDate: 2020-12-31 23:05
 * @version: 0.0.1
 */
public class CharUtil {
    public static final  char BACKSLASH = '\\';
    /**
     * 字符常量：花括号（左）
     */
    public static final char DELIM_START = '{';
    /**
     * 字符常量：花括号（右）
     */
    public static final char DELIM_END = '}';
    /**
     * 是否为空白字符 空格 制表符全角空格和不间断空格
     * @param c 字符
     * @return 是否为空白字符
     */
    public static boolean isBlankChar(char c){
        return isBlankChar((int) c);
    }

    /**
     * 是否为空白字符
     * @param c 字符
     * @return 是否为空白字符
     */
    public static boolean isBlankChar(int c){
        return Character.isWhitespace(c)
                ||Character.isSpaceChar(c)
                || c == '\ufeff'
                || c == '\u202a';
    }
}
