package com.zcl.common.utils.Arrays;


import java.util.Arrays;

/**
 * @description: 数组工具类
 * @author: 周长乐
 * @CreateDate: 2020-12-31 22:58
 * @UpdateDate: 2020-12-31 22:58
 * @version: 0.0.1
 */
public class ArraysUtils {
    /**
     * 数组是否为空
     * @param array 数组
     * @param <T> 数组类型
     * @return 是否为空
     */
    public static<T> boolean isEmpty(T[] array){
        return array == null || array.length ==0;
    }

    /**
     * 对象是否为数组
     * @param obj 对象
     * @return 是否为数组对象 null 返回false
     */
    public static boolean isArray(Object obj){
        if (null == obj){
            return false;
        }
        return obj.getClass().isArray();
    }

    /**
     * 数组或集合转String
     * @param obj 集合或数组对象
     * @return 数组字符串与 集合转字符串格式相同
     */
    public static String toString(Object obj) {
        if (null == obj){
            return null;
        }

        if (obj instanceof long[]) {
            return Arrays.toString((long[]) obj);
        } else if (obj instanceof int[]) {
            return Arrays.toString((int[]) obj);
        } else if (obj instanceof short[]) {
            return Arrays.toString((short[]) obj);
        } else if (obj instanceof char[]) {
            return Arrays.toString((char[]) obj);
        } else if (obj instanceof byte[]) {
            return Arrays.toString((byte[]) obj);
        } else if (obj instanceof boolean[]) {
            return Arrays.toString((boolean[]) obj);
        } else if (obj instanceof float[]) {
            return Arrays.toString((float[]) obj);
        } else if (obj instanceof double[]) {
            return Arrays.toString((double[]) obj);
        } else if (ArraysUtils.isArray(obj)) {
            // 对象数组
            try {
                return Arrays.deepToString((Object[]) obj);
            } catch (Exception ignore) {
                //ignore
            }
        }

        return obj.toString();
    }

    /**
     * 数组复制
     * @param src 原数组
     * @param srcPos 源数组开始位置
     * @param dest 目标数组
     * @param destPos 目标数组开始位置
     * @param length 拷贝数组长度
     * @return 复制完成的目标数组
     */
    public static Object copy(Object src,int srcPos,Object dest,int destPos,int length){
        System.arraycopy(src,srcPos,dest,destPos,length);
        return dest;
    }
}
