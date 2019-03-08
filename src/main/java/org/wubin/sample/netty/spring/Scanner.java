package org.wubin.sample.netty.spring;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.wubin.sample.netty.spring.annotion.Invoker;
import org.wubin.sample.netty.spring.annotion.InvokerHolder;
import org.wubin.sample.netty.spring.annotion.SocketCmd;
import org.wubin.sample.netty.spring.annotion.SocketModule;

/**
 * 扫描器
 * 
 * @author wubin
 * @date 2019/03/07
 */
@Component
public class Scanner implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<? extends Object> clazz = bean.getClass();
        Class<?>[] interfaces = clazz.getInterfaces();
        
        if(interfaces != null && interfaces.length > 0) {
            // 扫描所有接口
            for(Class<?> interFace : interfaces) {
                SocketModule socketModule = interFace.getAnnotation(SocketModule.class);
                if(socketModule == null) {
                    continue;
                }
                
                Method[] methods = interFace.getMethods();
                if(methods != null && methods.length > 0) {
                    for(Method method : methods) {
                       SocketCmd socketCmd = method.getAnnotation(SocketCmd.class); 
                       if(socketCmd == null) {
                          continue; 
                       }
                       short module = socketModule.module();
                       short cmd = socketCmd.cmd();
                       
                       Invoker invoker = Invoker.valueOf(bean, method);
                       if(InvokerHolder.getInvoker(module, cmd) == null) {
                           InvokerHolder.addInvoker(module, cmd, invoker);
                       } else {
                           System.out.println("重复注册执行器module:" + module + " cmd:" + cmd);
                       }
                    }
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        return bean;
    }
}
