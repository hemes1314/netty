package org.wubin.sample.netty.spring.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模块号
 * @author wubin
 * @date 2019/03/07
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketModule {

    /**
         * 模块
     * @return
     */
    short module();
}
