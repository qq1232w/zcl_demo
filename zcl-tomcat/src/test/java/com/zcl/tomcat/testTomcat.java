package com.zcl.tomcat;

import com.zcl.common.utils.NetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @description: Tomcat测试类
 * @author: 周长乐
 * @CreateDate: 2020-12-31 21:37
 * @UpdateDate: 2020-12-31 21:37
 * @version: 0.0.1
 */
public class testTomcat {
    private static final String HOST ="127.0.0.1";
    private static final int PORT = 9000;
    private static Logger logger= LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    @BeforeAll
    public static void beforeClass(){
        new Thread(StartTomcat::runTomcat).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.debug("启动Tomcat睡眠时产生异常");
        }
        if (NetUtil.isUsableLocalPort(PORT)){
            logger.info("请先启动Tomcat!端口号为:"+PORT);
            System.exit(1);
        }else{
            logger.info("Tomcat已经启动开始测试!");
        }
    }


    @Test
    void test(){
       String html = getContextString("/");
        Assertions.assertEquals(html,"Tomcat!");
    }

    private String getContextString(String s) {
        String url = "http://"+HOST+":"+PORT;
        return MiniBrowser.getContextString(url);
    }
}
