package com.wzbuaa.crm.domain.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.repository.support.annotation.EnableQueryCache;

/**
 * 图标管理
 * class方式 和
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-19 上午7:13
 * <p>Version: 1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "base_maintain_icon")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IconDomain extends BaseEntity<Long> {

    /**
     * 标识 前台使用时的名称
     */
    private String identity;
    /**
     * 类名称
     */
    private String cssClass;

    //////和 class、css sprite 三者选一
    /**
     * 图片地址
     */
    private String imgSrc;

    //////和 class、css sprite 三者选一
    /**
     * css 背景图 位置
     * 绝对地址  如http://a.com/a.png
     * 相对于上下文地址 如/a/a.png 不加上下文
     */
    private String spriteSrc;

    /**
     * 距离sprite图片左边多少
     */
    private Integer left = 0;

    /**
     * 距离sprite图片上边多少
     */
    private Integer top = 0;


    /**
     * 宽度
     */
    private Integer width = 13;
    /**
     * 高度
     */
    private Integer height = 13;

    /**
     * 额外添加的css样式
     */
    private String style = "";

    private IconType type;

    /**
     * 描述
     */
    private String description;


    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Column(name = "css_class")
    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    @Column(name = "img_src")
    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Column(name = "sprite_src")
    public String getSpriteSrc() {
        return spriteSrc;
    }

    public void setSpriteSrc(String spriteSrc) {
        this.spriteSrc = spriteSrc;
    }

    @Column(name = "s_left")
    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    @Enumerated(EnumType.STRING)
    public IconType getType() {
        return type;
    }

    public void setType(IconType type) {
        this.type = type;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
