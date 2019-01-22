package org.wubin.sample.netty.biopool;

import org.wubin.sample.netty.bio.TimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 伪异步
 * @author wubin
 * @date 2017/12/11
 **/
public class TimeServer {

    public static void main(String[] args) throws IOException  {

        int port = 8080;
        if(args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                //
            }
        }

        ServerSocket server = null;
        try {

            server = new ServerSocket(port);
            System.out.println("The time server is start in port : " + port);
            Socket socket = null;

            // 一个客户端一个线程，消耗资源大，老tomcat socket，短连接，无法做长连接
            TimeServerHandlerExecutePool singleExector = new TimeServerHandlerExecutePool(50, 10000);// 创建I/O任务线程池

            while(true) {

                socket = server.accept();
                singleExector.execute(new TimeServerHandler(socket));
            }
        } finally {

            if(server != null) {

                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }
}
