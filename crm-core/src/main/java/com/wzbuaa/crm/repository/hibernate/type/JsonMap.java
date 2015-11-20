package com.wzbuaa.crm.repository.hibernate.type;

import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-20 下午7:45
 * <p>Version: 1.0
 */
public class JsonMap<T, B> implements Serializable {

    private Map<T, B> map;

    public JsonMap() {
    }

    public JsonMap(Map<T, B> map) {
        this.map = map;
    }

    public Map<T, B> getMap() {
        if (map == null) {
            map = Maps.newHashMap();
        }
        return map;
    }

    public void setMap(Map<T, B> map) {
        this.map = map;
    }
}
