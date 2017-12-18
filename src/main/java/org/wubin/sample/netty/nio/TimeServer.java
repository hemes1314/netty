package org.wubin.sample.netty.nio;

/**
 * @author wubin
 * @date 2017/12/12
 **/
public class TimeServer {

    /**
     *
     * @author wubin
     * @date 2017/12/13
     **/
    public static void main(String[] args) {

        int port = 8080;
        if(args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                //
            }
        }

        // 多路复用器，轮询多路复用器Selector，处理多个客户端并打接入
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiolexerTimeServer-001").start();
    }
}
