package com.wzbuaa.crm.domain.base;

/**
 * 图标类型
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-24 下午3:25
 * <p>Version: 1.0
 */
public enum IconType {
    css_class("css类图标"), upload_file("文件图标"), css_sprite("css精灵图标");
    private final String info;

    private IconType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
