package com.wzbuaa.crm.domain.sso.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.wzbuaa.crm.domain.BaseEntity;

/**
 * 用户状态更改历史
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sso_user_status_history")
public class UserStatusHistory extends BaseEntity<Long> {

    /**
     * 锁定的用户
     */
    private S_userDomain user;

    /**
     * 备注信息
     */
    private String reason;

    /**
     * 操作的状态
     */
    private UserStatus status;

    /**
     * 操作的管理员
     */
    private S_userDomain opUser;

    /**
     * 操作时间
     */
    private Date opDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    public S_userDomain getUser() {
        return user;
    }

    public void setUser(S_userDomain user) {
        this.user = user;
    }

    @Enumerated(EnumType.STRING)
    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "op_user_id")
    public S_userDomain getOpUser() {
        return opUser;
    }

    public void setOpUser(S_userDomain opUser) {
        this.opUser = opUser;
    }

    @Column(name = "op_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }
}
