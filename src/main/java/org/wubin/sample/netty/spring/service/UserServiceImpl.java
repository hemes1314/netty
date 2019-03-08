 package org.wubin.sample.netty.spring.service;

import org.springframework.stereotype.Component;

/**
 * @author wubin
 * @date 2019/03/07
 */
@Component
public class UserServiceImpl implements UserService {

    @Override
    public void login() {
        System.out.println("login");
    }

    @Override
    public void getInfo() {
        System.out.println("getInfo");
    }

}
