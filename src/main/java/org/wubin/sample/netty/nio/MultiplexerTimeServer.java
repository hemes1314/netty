package org.wubin.sample.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * @author wubin
 * @date 2017/12/12
 **/
public class MultiplexerTimeServer implements Runnable {

    private Selector selector;

    private ServerSocketChannel servChannel;

    private volatile boolean stop;

    /**
     * 初始化多路复用器，绑定监听端口
     * @author wubin
     * @date 2017/12/12
     **/
    public MultiplexerTimeServer(int port) {
        try {
            servChannel = ServerSocketChannel.open();// 打开ServerSocketChannel，监听客户端连接，所有客户端连接的父管道
            servChannel.socket().bind(new InetSocketAddress(port), 1024);// 绑定端口
            servChannel.configureBlocking(false);// 设置非阻塞模式
            selector = Selector.open(); // 创建多路复用器
            servChannel.register(selector, SelectionKey.OP_ACCEPT);// 将ServerSocketChannel注册到Reactor线程的多路复用器selector上，监听ACCEPT事件
            System.out.println("The time server is start inport : " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                // 轮询准备就绪的key
                selector.select(1000);//每隔一秒被唤醒一次
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    // ... deal with I/O event ...
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if(key != null) {
                            key.cancel();
                            if(key.channel() != null)
                                key.channel().close();
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        // 多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动取注册并关闭，所以不需要重复释放资源
        if(selector != null) {
            try {
                selector.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()) {
            // 处理新接入的请求消息
            if(key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                // 监听新的客户端接入，处理新的接入请求，完成TCP三次握手，建立物理链路
                SocketChannel sc = ssc.accept();
                // 设置客户端链路为非阻塞模式
                sc.configureBlocking(false);
//                sc.socket().setReuseAddress(true);
                // 将新接入的客户端连接注册到Reactor线程的多路复用器上，监听都操作，读取客户端发送的网络消息
                sc.register(selector, SelectionKey.OP_READ);
            }
            if(key.isReadable()) {
                // Read the data
                SocketChannel sc = (SocketChannel)key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                // 一步都去客户端请求消息到缓冲区
                int readBytes = sc.read(readBuffer);
                if(readBytes > 0) {
                    // 对ByteBuffer进行编解码，如果有半包消息指针reset，继续读取后续的豹纹，将解码成功的消息封装成task，投递到业务线程池中，进行业务逻辑编编排
                    readBuffer.flip();//把buffer的当前位置更改为buffer缓冲区的第一个位置
                    byte[] bytes = new byte[readBuffer.remaining()];// 剩余可用长度
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order : " + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                    doWrite(sc, currentTime);
                } else if(readBytes < 0) {
                    // 对端链路关闭
                    key.cancel();
                    sc.close();
                } else {// 没有读取到字节，属于正常场景

                }
            }
        }
    }

    private void doWrite(SocketChannel channel, String response) throws IOException {
        if(response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);// SocketChannel是异步非阻塞的，并不保证一次能够把需要发送的字节数组
            // 发送完，此时会出现"写半包"问题。需要注册写操作，不断轮询Selector将没有发送完的ByteBuffer发送完毕，
            // 然后通过ByteBuffer的hasRemain()方法判断消息是否发送完成。
        }
    }
}
