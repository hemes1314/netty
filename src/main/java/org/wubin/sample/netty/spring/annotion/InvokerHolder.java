 package org.wubin.sample.netty.spring.annotion;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wubin
 * @date 2019/03/07
 */
public class InvokerHolder {

    
    public static Map<Short, Map<Short, Invoker>> invokers = new HashMap<>();
    
    /**
         * 添加一个执行器
     * @param module
     * @param cmd
     * @param invoker
     */
    public static void addInvoker(short module, short cmd, Invoker invoker) {
        Map<Short, Invoker> map = invokers.get(module);
        if(map == null) {
            map = new HashMap<>();
            invokers.put(module, map);
        }
        map.put(cmd, invoker);
    }
    
    public static Invoker getInvoker(short module, short cmd) {
        Map<Short, Invoker> map = invokers.get(module);
        if(map != null) {
            return map.get(cmd);
        }
        return null;
    }
}
