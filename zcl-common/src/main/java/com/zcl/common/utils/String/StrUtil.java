package com.zcl.common.utils.String;


import com.zcl.common.utils.Arrays.ArraysUtils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @description: String工具类
 * @author: 周长乐
 * @CreateDate: 2020-12-31 21:54
 * @UpdateDate: 2020-12-31 21:54
 * @version: 0.0.1
 */
public class StrUtil {
    public static final int INDEX_NOT_FOUND = -1;
    /**
     * 字符串常量null "null" != null
     */
    public static final String NULL = "null";
    /**
     * 空字符串
     */
    public static final String EMPTY = "";
    /**
     * 空格
     */
    public static final String SPACE = " ";
    /**
     * 空JSON
     */
    public static final String EMPTY_JSON = "{}";
    /**
     * 判断是否为空 不包含空格
     * @param obj obj
     * @return boolean true 空 false 非空
     */
    public static boolean isEmpty(Object obj){
        if (obj == null){
            return true;
        }
        if (obj instanceof CharSequence){
            return ((CharSequence) obj).length()==0;
        }
        return false;
    }

    /**
     * 检测对象字符串是否为空，
     * 1. null
     * 2. ""
     * 3. 空格 全角空格 制表符，换行符，和其他不可见字符
     * @param obj 对象
     * @return 如果为字符串是否为空串
     */
    public static boolean isBlank(Object obj){
        if (null == obj){
            return true;
        }else if (obj instanceof CharSequence){
            return isBlank((CharSequence)obj);
        }
        return false;
    }

    /**
     * 校验字符串是否为空白
     * 1.null
     * 2.空字符串：""
     * 3.空格、全角空格、制表符、换行符，等不可见字符
     * @param str 被检测的字符串
     * @return 空白返回true
     */
    public static boolean isBlank(CharSequence str){
        int length;
        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (!CharUtil.isBlankChar(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * 截取指定字符串的中间部分，不包括起使标识符
     * @param str  原始字符串
     * @param before 起使字符串标识
     * @param after 结束字符串标识
     * @return 截取后的字符串
     */
    public static String subBetween(CharSequence str,CharSequence before,CharSequence after){
        if (str == null || before == null || after == null){
            return null;
        }
        String str2 = str.toString();
        String before2 = before.toString();
        String after2 = after.toString();
        //检索什么位置包含这个字符串,就是起使位置 -1表示没有检索到
        int start = str2.indexOf(before2);
        if (start != INDEX_NOT_FOUND){
            //检索截取到的标识符从起使标识符开始加上起使标识符的长度开始，就是结束位置
            int end = str2.indexOf(after2, start + before2.length());
            if (end != INDEX_NOT_FOUND){
                //截取，从起使位置开始，加上起使标识符的长度，到结束位置
                return str2.substring(start+before2.length(),end);
            }
        }
        return null;
    }

    /**
     * 查找字符串是否在指定的字符串中出现
     * @param str 字符串
     * @param searchChar 被查找的字符
     * @return 是否包含
     */
    public static boolean contains(CharSequence str,char searchChar){
        return indexOf(str,searchChar)>-1;
    }

    /**
     * 查找字符串是否在指定的字符串中出现
     * @param str 字符串
     * @param searchStr 被查找的字符
     * @return 是否包含
     */
    public static boolean contains(CharSequence str,CharSequence searchStr){
        if (null == str || null == searchStr){
            return  false;
        }
        return str.toString().contains(searchStr);
    }

    /**
     * 指定范围内查找指定字符
     * @param str 字符串
     * @param searchChar 被查找的字符
     * @return 位置
     */
    public static int indexOf(final  CharSequence str,char searchChar){
        return indexOf(str,searchChar,0);
    }

    /**
     * 指定范围内查找指定的字符
     * @param str 字符串
     * @param searchChar 被查找的字符
     * @param start 索引位置，小于0则从0开始查找
     * @return 位置
     */
    public static int indexOf(final  CharSequence str,char searchChar,int start){
        if (str instanceof String){
            return ((String) str).indexOf(searchChar,start);
        }else{
            return indexOf(str,searchChar,start,-1);
        }
    }

    /**
     * 查找指定范围内的字符串
     * @param str 字符串
     * @param searchChar 被查找的字符串
     * @param start 起始索引 小于0则从0开始
     * @param end 结束索引 超出字符串长度则只查找字符串的末尾
     * @return 位置
     */
    public static int indexOf(final  CharSequence str,char searchChar,int start,int end){
        if (isEmpty(str)){
            return INDEX_NOT_FOUND;
        }
        int length = str.length();
        //1.起使位置小于0 或者起使位置大于字符串长度，默认从0开始
        if (start < 0 || start > length){
            start = 0;
        }
        //2.结束位置大于字符串长度，或者结束位置小于0 则默认字符串长度
        if (end > length || end < 0 ){
            end = length;
        }
        //3.从起使位置开始循环到结束索引位置
        for (int i = start; i < end; i++) {
            if (str.charAt(i) == searchChar){
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 截取分割字符串之前的字符，不包含分割字符串
     * 如果分割字符串为null 或者 ""则返回原字符串
     * 如果分割字符串未找到，返回原字符串
     * StrUtil.subBefore(null, *, false)      = null
     * StrUtil.subBefore("", *, false)        = ""
     * StrUtil.subBefore("abc", 'a', false)   = ""
     * StrUtil.subBefore("abcba", 'b', false) = "a"
     * StrUtil.subBefore("abc", 'c', false)   = "ab"
     * StrUtil.subBefore("abc", 'd', false)   = "abc"
     * @param string 被查找的字符串
     * @param separator 分割字符串 不包含
     * @param isLastSeparator 是否查找最后一个分割字符串， 如果多次分割取最后出现的字符串
     * @return 切割后的字符串
     */
    public static String subBefore(CharSequence string,char separator, boolean isLastSeparator){
        if (isEmpty(string)){
            return null == string ? null : EMPTY;
        }
        String str = string.toString();
       int pos = isLastSeparator ? str.lastIndexOf(separator):str.indexOf(separator);
        if (INDEX_NOT_FOUND == pos){
            return str;
        }
        if (0 == pos){
            return EMPTY;
        }
        return str.substring(0,pos);
    }

    public static String format(CharSequence template,Object... params){
        if (null == template){
            return NULL;
        }
        if (ArraysUtils.isEmpty(params)|| isBlank(template)){
            return template.toString();
        }
        return StrFormatter.format(template.toString(),params);
    }

    /**
     * 把对象转换为字符串
     *  1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 	2、对象数组会调用Arrays.toString方法
     * @param obj 对象
     * @return 字符串
     */
    public static String utf8Str(Object obj){
        return str(obj, CharsetUtil.UTF_8);
    }

    /**
     * 将对象转为字符串
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     * @param obj 字符串
     * @param charsetName 字符集
     * @return 字符串
     */
    public static String str(Object obj, String charsetName) {
        return str(obj, Charset.forName(charsetName));
    }
    /**
     * 将对象转为字符串
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     * @param obj 字符串
     * @param charset 字符集
     * @return 字符串
     */
    public static String str(Object obj,Charset charset){
        if (null == obj) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return str((byte[]) obj, charset);
        } else if (obj instanceof Byte[]) {
            return str((Byte[]) obj, charset);
        } else if (obj instanceof ByteBuffer) {
            return str((ByteBuffer) obj, charset);
        } else if (ArraysUtils.isArray(obj)) {
            return ArraysUtils.toString(obj);
        }
        return obj.toString();
    }

}
