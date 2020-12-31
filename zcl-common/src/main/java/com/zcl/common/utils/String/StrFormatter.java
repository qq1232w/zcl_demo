package com.zcl.common.utils.String;

import com.zcl.common.utils.Arrays.ArraysUtils;

/**
 * @description: 字符串格式化工具
 * @author: 周长乐
 * @CreateDate: 2020-12-31 23:10
 * @UpdateDate: 2020-12-31 23:10
 * @version: 0.0.1
 */
public class StrFormatter {
    public static String format(final String strPatten,final Object... argArray){
        if (StrUtil.isBlank(strPatten) || ArraysUtils.isEmpty(argArray)){
            return strPatten;
        }
        //1.获取模板长度
        final int strPatternLength = strPatten.length();
        //初始化定义模板长度
        StringBuilder sb = new StringBuilder(strPatternLength + 50);
        //记录已经处理到的位置
        int handledPosition = 0;
        //占位符所处的位置
        int delimIndex;
        for (int i = 0; i < argArray.length; i++) {
            delimIndex = strPatten.indexOf(StrUtil.EMPTY_JSON,handledPosition);
            if (delimIndex == -1){
                //剩余部分无占位符
                if (handledPosition == 0){
                    //不带占位符的模板直接返回
                    return strPatten;
                }
                //字符串模板剩余部分不在包含占位符，加入剩余部分否直接返回
                sb.append(strPatten,handledPosition,strPatternLength);
                return sb.toString();
            }
            //转义
            if (delimIndex > 0 && strPatten.charAt(delimIndex - 1)==CharUtil.BACKSLASH){
                //1.双转义
                if (delimIndex > 1 && strPatten.charAt(delimIndex -2) == CharUtil.BACKSLASH){
                    //转义字符串还有一个转义符，占位符依然有效
                    sb.append(strPatten,handledPosition,delimIndex-1);
                    sb.append(StrUtil.utf8Str(argArray[i]));
                    handledPosition = delimIndex + 2;
                }else{
                    i--;
                    sb.append(strPatten,handledPosition,delimIndex - 1);
                    sb.append(CharUtil.DELIM_START);
                    handledPosition = delimIndex + 1;
                }
            }else{
                //正常占位符
                sb.append(strPatten,handledPosition,delimIndex);
                sb.append(StrUtil.utf8Str(argArray[i]));
                handledPosition = delimIndex + 2;
            }
        }
        //加入最后一个占位符后所有的字符
        sb.append(strPatten,handledPosition,strPatten.length());
        return sb.toString();
    }
}
