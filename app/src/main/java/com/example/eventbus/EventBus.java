package com.example.eventbus;

import android.os.Handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created  by Billy_Cui on 2019/5/16
 * Describe:
 */
public class EventBus {

    private Map<Object, List<SubcribleMethod>> cacheMap;
    private Handler mHandler;

    private static volatile EventBus instance;

    private EventBus() {
        this.cacheMap = new ConcurrentHashMap<>();
        this.mHandler = new Handler();
    }

    /**
     * 获取eventbus 实例
     *
     * @return
     */
    public static EventBus getDefault() {
        if (instance == null) {
            synchronized (EventBus.class) {
                if (instance == null) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }


    /**
     * 注册到eventbus
     *
     * @param obj 注册类的实例
     * @throws IllegalAccessException
     */
    public void register(Object obj) throws IllegalAccessException {
        List<SubcribleMethod> list = cacheMap.get(obj);
        if (list == null) {
            list = findSubscribleMethod(obj);
            cacheMap.put(obj, list);
        }
    }

    /**
     * 查找出类中Subscrible注解的方法
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    private List<SubcribleMethod> findSubscribleMethod(Object obj) throws IllegalAccessException {
        List<SubcribleMethod> list = new ArrayList<>();
        Class<?> clazz = obj.getClass();

        while (clazz != null) {
            //获取类对象的所有方法(不包括父类的)
            Method[] methods = clazz.getDeclaredMethods();
            //找父类,需要筛选系统级别的父类
            String name = clazz.getName();
            if (name.startsWith("java.") || name.startsWith("javax.")
                    || name.startsWith("android.")) {
                break;
            }

            for (Method method : methods) {
                //获取方法的 注解
                Subscrible subscrible = method.getAnnotation(Subscrible.class);
                if (subscrible == null) continue;
                //获取方法的 参数类型
                Class<?>[] types = method.getParameterTypes();
                if (types.length != 1) {
                    throw new IllegalAccessException("eventbus only accept one parameter ");
                }
                ThreadMode threadMode = subscrible.threadMode();
                SubcribleMethod subcribleMethod = new SubcribleMethod(method, threadMode, types[0]);
                list.add(subcribleMethod);
            }
            clazz = clazz.getSuperclass();
        }
        return list;
    }

    /**
     * 发送信息
     *
     * @param type
     */
    public void post(Object type) {
        Set<Map.Entry<Object, List<SubcribleMethod>>> entrySet = cacheMap.entrySet();
        for (Map.Entry<Object, List<SubcribleMethod>> entry : entrySet) {
            List<SubcribleMethod> list = entry.getValue();
            for (SubcribleMethod subcribleMethod : list) {
                if (subcribleMethod.getType().isAssignableFrom(type.getClass())) {
                    invoke(subcribleMethod, entry.getKey(), type);
                }
            }
        }
    }

    /**
     * method调用方法
     *
     * @param subcribleMethod
     * @param obj
     * @param type
     */
    private void invoke(SubcribleMethod subcribleMethod, Object obj, Object type) {
        Method method = subcribleMethod.getMethod();
        try {
            method.invoke(obj, type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解除绑定
     * @param obj
     */
    public void unRegister(Object obj) {
        if (cacheMap.containsKey(obj))
            cacheMap.remove(obj);
    }
}
