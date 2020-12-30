package com.zcl.tomcat;

import com.zcl.common.utils.NetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @description: 测试启动
 * @author: 周长乐
 * @CreateDate: 2020-12-29 23:03
 * @UpdateDate: 2020-12-29 23:03
 * @version: 0.0.1
 */
public class start {
    private static Logger logger= LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    @Test
    void test(){
        try {
            int port = 9000;
            //检测端口是否可用
            if (!NetUtil.isUsableLocalPort(port)){
                logger.info("端口:"+port+" "+"端口已被占用!");
            }
            ServerSocket ss = new ServerSocket(port);
            while (true){
                Socket sk = ss.accept();
                InputStream is = sk.getInputStream();
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                is.read(buffer);
                String requestString = new String(buffer, StandardCharsets.UTF_8);
                logger.info("浏览器输入信息!\r\n"+requestString);
                OutputStream os = sk.getOutputStream();
                String responseHead = "HTTP/1.1 200 OK\r\n"+"Content-Type:text/html\r\n\r\n";
                String responseString = "Tomcat!";
                responseHead = responseHead + responseString;
                os.write(responseHead.getBytes(StandardCharsets.UTF_8));
                os.flush();
                sk.close();
            }
        }catch (Exception e){
            logger.debug(e.getMessage());
        }
    }
}
