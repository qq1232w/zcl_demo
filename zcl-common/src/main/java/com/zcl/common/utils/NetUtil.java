package com.zcl.common.utils;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
/**
 * @description: 网络工具类
 * @author: 周长乐
 * @CreateDate: 2020-12-29 23:10
 * @UpdateDate: 2020-12-29 23:10
 * @version: 0.0.1
 */
public class NetUtil {
    public static final  int MAX_PORT = 65535;
    /**
     * 检测本地端口是否有效或者可用
     * @param port 端口号
     * @return 是否可用
     */
    public static boolean isUsableLocalPort(int port){
        //检测端口是否在标准端口0-65536中
        if (!isValidPort(port)){
            return false;
        }
        //tcp
        try (ServerSocket ss = new ServerSocket(port)){
            //连接关闭时可以立即使用该端口
            ss.setReuseAddress(true);
        }catch (IOException e){
            return false;
        }
        //upd
        try (DatagramSocket ds = new DatagramSocket(port)){
            ds.setReuseAddress(true);
        }catch (IOException e){
            return false;
        }
        return true;
    }

    /**
     * 检测端口号是否在可用范围中
     * @param port 端口号
     * @return 是否有效
     */
    private static boolean isValidPort(int port) {
        return port >= 0 && port <= MAX_PORT;
    }
}
