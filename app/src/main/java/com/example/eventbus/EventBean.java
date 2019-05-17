package com.example.eventbus;

/**
 * Created  by Billy_Cui on 2019/5/16
 * Describe:
 */
public class EventBean {

    private String time;
    private String name;

    @Override
    public String toString() {
        return "EventBean{" +
                "time='" + time + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public EventBean(String time, String name) {
        this.time = time;
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
