 package org.wubin.sample.netty.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wubin.sample.netty.spring.annotion.Invoker;
import org.wubin.sample.netty.spring.annotion.InvokerHolder;

/**
 * @author wubin
 * @date 2019/03/08
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = 
            new ClassPathXmlApplicationContext("applicationContext.xml");
        
        Invoker invoker = InvokerHolder.getInvoker((short) 1, (short) 1);
        invoker.invoke(null);
        
        invoker = InvokerHolder.getInvoker((short) 1, (short) 2);
        invoker.invoke(null);
    }
}
