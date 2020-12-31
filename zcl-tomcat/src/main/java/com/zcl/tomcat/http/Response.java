package com.zcl.tomcat.http;

import com.zcl.common.Constant.Http.HttpConstant;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * @description: response 响应类
 * @author: 周长乐
 * @CreateDate: 2020-12-31 22:45
 * @UpdateDate: 2020-12-31 22:45
 * @version: 0.0.1
 */
public class Response {

    public StringWriter stringWriter;

    public PrintWriter  printWriter;
    public String contextType;
    public Response(){
        this.stringWriter = new StringWriter();
        this.printWriter = new PrintWriter(stringWriter);
        this.contextType = HttpConstant.HTML_TYPE;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public String getContextType() {
        return contextType;
    }

    public byte[] getBody(){
        return stringWriter.toString().getBytes();
    }


}
