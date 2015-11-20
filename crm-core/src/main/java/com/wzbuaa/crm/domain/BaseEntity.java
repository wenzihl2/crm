package com.wzbuaa.crm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * <p> 抽象实体基类，提供统一的ID，和相关的基本功能方法
 * 如果是oracle请参考{@link BaseOracleEntity}
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-12 下午4:05
 * <p>Version: 1.0
 */
@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})  
public class BaseEntity<ID extends Serializable> extends AbstractEntity<ID> {

    private ID id;
    @CreatedDate
    private Date createDate;// 创建日期
    @LastModifiedDate
	private Date modifyDate;// 修改日期

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }

    @Column(updatable = false, name="createDate")
	@JSONField(serialize=false, format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JSONField(serialize=false, format="yyyy-MM-dd HH:mm:ss")
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}