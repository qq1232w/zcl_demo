package com.zcl.tomcat.http;

import com.zcl.common.utils.String.StrUtil;
import com.zcl.tomcat.MiniBrowser;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @description: request请求
 * @author: 周长乐
 * @CreateDate: 2020-12-31 21:50
 * @UpdateDate: 2020-12-31 21:50
 * @version: 0.0.1
 */
public class Request {

    private String requestString;
    private String uri;
    private Socket socket;
    private static final char WEN = '?';
    public Request(Socket socket) throws IOException {
        this.socket = socket;
        parseHttpRequest();
        if (StrUtil.isEmpty(requestString)){
            return;
        }
        parseUri();
    }

    private void parseUri() {
        String tmp;
        tmp = StrUtil.subBetween(requestString," "," ");
        if (!StrUtil.contains(tmp, WEN)){
            uri = tmp;
            return;
        }
        tmp = StrUtil.subBefore(tmp,WEN,false);
        uri = tmp;
    }

    private void parseHttpRequest() throws IOException {
        InputStream is = this.socket.getInputStream();
        byte[] bytes = MiniBrowser.readBytes(is);
        requestString = new String(bytes, StandardCharsets.UTF_8);
    }


    public String getRequestString() {
        return requestString;
    }

    public String getUri() {
        return uri;
    }


}
