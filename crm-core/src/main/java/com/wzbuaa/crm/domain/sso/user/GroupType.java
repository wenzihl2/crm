package com.wzbuaa.crm.domain.sso.user;

/**
 * 用户组分类
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-19 上午8:57
 * <p>Version: 1.0
 */
public enum GroupType {

    user("用户组"), dept("组织机构组");

    private final String info;

    private GroupType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
