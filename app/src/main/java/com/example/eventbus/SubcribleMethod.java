package com.example.eventbus;

import java.lang.reflect.Method;

/**
 * Created  by Billy_Cui on 2019/5/16
 * Describe:
 */
public class SubcribleMethod {
    //回调方法
    private Method method;

    //线程模式
    private ThreadMode threadMode;

    //方法中的参数
    private Class<?> type;

    public SubcribleMethod(Method method, ThreadMode threadMode, Class<?> type) {
        this.method = method;
        this.threadMode = threadMode;
        this.type = type;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
