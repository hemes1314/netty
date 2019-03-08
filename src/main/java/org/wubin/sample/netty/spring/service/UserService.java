 package org.wubin.sample.netty.spring.service;

import org.wubin.sample.netty.spring.annotion.SocketCmd;
import org.wubin.sample.netty.spring.annotion.SocketModule;

/**
 * @author wubin
 * @date 2019/03/07
 */
 @SocketModule(module = 1)
public interface UserService {

     /**
          * 登录
      */
    @SocketCmd(cmd = 1)
    public void login();
    
    /**
         * 获取信息
     */
    @SocketCmd(cmd = 2)
    public void getInfo();
}
