package com.zcl.tomcat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description: mini浏览器测试类
 * @author: 周长乐
 * @CreateDate: 2020-12-29 23:42
 * @UpdateDate: 2020-12-29 23:42
 * @version: 0.0.1
 */
public class MiniBrowser {
    private static Logger logger= LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    @Test
    void testBrowser(){
        String browser =  "http://static.how2j.cn/diytomcat.html";
        String contextString = getContextString(browser,false);
        logger.info(contextString);
        String httpString = getHttpString(browser,false);
        logger.info(httpString);

    }

    private String getHttpString(String browser, boolean gzip) {
        byte[]  bytes=getHttpBytes(browser,gzip);
        return new String(bytes).trim();
    }
    public String getContextString(String browser){
      return   this.getContextString(browser,false);
    }
    private String getContextString(String browser, boolean gzip) {
       byte[] result =  getContextBytes(browser,gzip);
       if (result == null){
           return null;
       }
        return new String(result, StandardCharsets.UTF_8).trim();
    }

    private byte[] getContextBytes(String browser, boolean gzip) {
        byte[] response = getHttpBytes(browser,gzip);
        byte[] doubleReturn = "\r\n\r\n".getBytes(StandardCharsets.UTF_8);
        int pos = -1;
        //减去换行的长度
        for (int i = 0; i < response.length-doubleReturn.length; i++) {
            byte[] tmp = Arrays.copyOfRange(response,i,i+doubleReturn.length);
            if (Arrays.equals(tmp,doubleReturn)){
                pos = i;
                break;
            }
        }
        if (-1 == pos){
            return null;
        }
        pos += doubleReturn.length;
        return Arrays.copyOfRange(response,pos,response.length);
    }

    private byte[] getHttpBytes(String browser, boolean gzip) {
        byte[] result = null;
        try {
            URL u = new URL(browser);
            Socket client = new Socket();
            int port = u.getPort();
            if (port == -1){
                port = 80;
            }
            InetSocketAddress inetSocketAddress = new InetSocketAddress(u.getHost(),port);
            client.connect(inetSocketAddress,1000);
            Map<String,String> requestHeaders = new HashMap<>();
            requestHeaders.put("Host", u.getHost()+":"+port);
            requestHeaders.put("Accept", "text/html");
            requestHeaders.put("Connection", "close");
            requestHeaders.put("User-Agent", "mini brower / java1.8");
            if (gzip){
                requestHeaders.put("Accept-Encoding", "gzip");
            }
            String path = u.getPath();
            if (path.length() == 0){
                path = "/";
            }
            String firstLine = "GET " + path + " HTTP/1.1\r\n";
            StringBuffer httpRequestString = new StringBuffer();
            httpRequestString.append(firstLine);
            Set<String> strings = requestHeaders.keySet();
            for (String header : strings) {
                String headerLine = header + ":" + requestHeaders.get(header)+"\r\n";
                httpRequestString.append(headerLine);
            }
            PrintWriter pWriter = new PrintWriter(client.getOutputStream(), true);
            pWriter.println(httpRequestString);
            InputStream is = client.getInputStream();
            int buffer_size = 1024;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[buffer_size];
            while (true){
                int read = is.read(bytes);
                if (-1 == read){
                    break;
                }
                baos.write(bytes,0,read);
                if (read!=buffer_size){
                    break;
                }
            }
            result = baos.toByteArray();
            client.close();

        }catch (Exception e){
            logger.debug(e.getMessage());
            result = e.toString().getBytes(StandardCharsets.UTF_8);
        }
        return result;
    }
}
