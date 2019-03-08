package org.wubin.sample.netty.spring.annotion;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wubin
 * @date 2019/03/07
 */
public class Invoker {

    private Object target;

    private Method method;

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
    
    public static Invoker valueOf(Object target, Method method) {
        
        Invoker invoker = new Invoker();
        invoker.setTarget(target);
        invoker.setMethod(method);
        return invoker;
    }

    /**
         * 执行
     * @param args
     */
    public void invoke(Object[] args) {
        try {
            method.invoke(target, args);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
