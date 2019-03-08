package org.wubin.sample.netty.spring.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 命令号
 * 
 * @author wubin
 * @date 2019/03/07
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketCmd {

    /**
         * 命令号
     * @return
     */
    short cmd();
}
