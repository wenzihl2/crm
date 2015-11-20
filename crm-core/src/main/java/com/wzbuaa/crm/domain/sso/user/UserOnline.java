package com.wzbuaa.crm.domain.sso.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.shiro.session.mgt.OnlineSession;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.wzbuaa.crm.domain.AbstractEntity;

/**
 * 当前在线会话
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sso_user_online")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserOnline extends AbstractEntity<String> {

	/**
     * 用户会话id ===> uid
     */
    private String id;
	
    //当前登录的用户Id
    private Long userId = 0L;

    @Column(name = "username")
    private String username;

    /**
     * 用户主机地址
     */
    @Column(name = "host")
    private String host;

    /**
     * 用户登录时系统IP
     */
    private String systemHost;

    /**
     * 用户浏览器类型
     */
    private String userAgent;

    /**
     * 在线状态
     */
    private OnlineSession.OnlineStatus status = OnlineSession.OnlineStatus.on_line;

    /**
     * session创建时间
     */
    private Date startTimestamp;
    /**
     * session最后访问时间
     */
    private Date lastAccessTime;

    /**
     * 超时时间
     */
    private Long timeout;


    /**
     * 备份的当前用户会话
     */
    private OnlineSession session;

    @Id
    @GeneratedValue(generator = "assigned")
    @GenericGenerator(name = "assigned", strategy = "assigned")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_timestsamp")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_access_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    @Column(name = "timeout")
    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "user_agent")
    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public OnlineSession.OnlineStatus getStatus() {
        return status;
    }

    public void setStatus(OnlineSession.OnlineStatus status) {
        this.status = status;
    }

    @Column(name = "session")
    @Type(type = "com.wzbuaa.crm.repository.hibernate.type.ObjectSerializeUserType")
    public OnlineSession getSession() {
        return session;
    }

    public void setSession(OnlineSession session) {
        this.session = session;
    }

    @Column(name = "system_host")
    public String getSystemHost() {
        return systemHost;
    }

    public void setSystemHost(String systemHost) {
        this.systemHost = systemHost;
    }


    public static final UserOnline fromOnlineSession(OnlineSession session) {
        UserOnline online = new UserOnline();
        online.setId(String.valueOf(session.getId()));
        online.setUserId(session.getUserId());
        online.setUsername(session.getUsername());
        online.setStartTimestamp(session.getStartTimestamp());
        online.setLastAccessTime(session.getLastAccessTime());
        online.setTimeout(session.getTimeout());
        online.setHost(session.getHost());
        online.setUserAgent(session.getUserAgent());
        online.setSystemHost(session.getSystemHost());
        online.setSession(session);

        return online;
    }
}
