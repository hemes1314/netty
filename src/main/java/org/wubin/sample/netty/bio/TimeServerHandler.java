package org.wubin.sample.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author wubin
 * @date 2017/12/01
 **/
public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        BufferedReader in = null;
        PrintWriter out = null;

        try {

            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);

            String currentTime = null;
            String body = null;

            // 阻塞读取客户端发送内容
            // in.readLine() 阻塞点
            while ((body = in.readLine()) != null) {

                body = in.readLine();
                System.out.println("THe time server receive order : " + body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                        new java.util.Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                out.println(currentTime);
            }
        } catch (Exception e) {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if(out != null) {
                    out.close();
                    out = null;
                }
                if(this.socket != null) {
                   try {
                       this.socket.close();
                   } catch (IOException e1) {
                       e1.printStackTrace();
                   }
                   this.socket = null;
                }
            }
        }
    }
}
