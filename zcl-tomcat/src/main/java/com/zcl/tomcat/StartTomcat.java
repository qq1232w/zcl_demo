package com.zcl.tomcat;

import cn.hutool.core.util.ArrayUtil;
import com.zcl.common.Constant.Http.HttpConstant;
import com.zcl.common.utils.Arrays.ArraysUtils;
import com.zcl.common.utils.NetUtil;
import com.zcl.common.utils.String.StrUtil;
import com.zcl.tomcat.http.Request;
import com.zcl.tomcat.http.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @description: 测试启动
 * @author: 周长乐
 * @CreateDate: 2020-12-29 23:03
 * @UpdateDate: 2020-12-29 23:03
 * @version: 0.0.1
 */
public class StartTomcat {
    private static Logger logger= LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    public static  void runTomcat(){
        try {
            int port = 9000;
            //检测端口是否可用
            if (!NetUtil.isUsableLocalPort(port)){
                logger.info("端口:"+port+" "+"端口已被占用!");
            }
            ServerSocket ss = new ServerSocket(port);
            while (true){
                Socket sk = ss.accept();
                Request re = new Request(sk);
                logger.info("浏览器输入信息:\r\n"+re.getRequestString());
                logger.info("uri:"+re.getUri());
                Response response = new Response();
                String html = "Tomcat!";
                response.getPrintWriter().println(html);
                handle200(sk,response);
            }
        }catch (Exception e){
            logger.debug(e.getMessage());
        }
    }

    private static void handle200(Socket sk, Response response) throws IOException {
        String contextType = response.getContextType();
        String text = HttpConstant.RESPONSE_HEAD_202;
        text = StrUtil.format(text, contextType);
        byte[] head = text.getBytes();
        byte[] body = response.getBody();
        byte[] responseBytes = new byte[head.length + body.length];
        ArraysUtils.copy(head,0,responseBytes,0,head.length);
        ArraysUtils.copy(body,0,responseBytes,head.length,body.length);
        OutputStream os = sk.getOutputStream();
        os.write(responseBytes);
        sk.close();
    }

    public static void main(String[] args) {

    }
}
