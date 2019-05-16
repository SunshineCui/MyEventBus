package com.example.eventbus;

import android.os.Handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created  by Billy_Cui on 2019/5/16
 * Describe:
 */
public class EventBus {

    private Map<Object, List<SubcribleMethod>> cacheMap;
    private Handler mHandler;

    private static volatile  EventBus instance;

    private EventBus() {
        this.cacheMap = new HashMap<>();
        this.mHandler = new Handler();
    }

    public static EventBus getDefault() {
        if (instance == null){
            synchronized (EventBus.class){
                if (instance == null){
                    instance  = new EventBus();
                }
            }
        }
        return instance;
    }


    public void register(Object obj) {

    }
}
