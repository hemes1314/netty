package org.wubin.sample.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wubin
 * @date 2017/12/01
 **/
public class TimeServer {

    /**
     * Tomcat server - 解析协议
     **/
    public static void main(String[] args) throws IOException {

        int port = 8080;

        if(args != null && args.length > 0) {

            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }

        ServerSocket server = null;

        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port : " + port);
            Socket socket = null;
            while(true) {
                socket = server.accept();//阻塞
                new Thread(new TimeServerHandler(socket)).start();//一线程一连接
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
