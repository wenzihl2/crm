package com.wzbuaa.crm.domain.sso.user;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.repository.support.annotation.EnableQueryCache;

/**
 * 为了提高连表性能 使用数据冗余 而不是 组织机构(1)-----(*)职务的中间表
 * 即在该表中 用户--组织机构--职务 是唯一的  但 用户-组织机构可能重复
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sso_user_dept_job")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserDeptJob extends BaseEntity<Long> {

    private S_userDomain user;

    private Long deptId;

    private Long jobId;


    public UserDeptJob() {
    }

    public UserDeptJob(Long id) {
        setId(id);
    }

    public UserDeptJob(Long deptId, Long jobId) {
        this.deptId = deptId;
        this.jobId = jobId;
    }

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Basic(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    public S_userDomain getUser() {
        return user;
    }

    public void setUser(S_userDomain user) {
        this.user = user;
    }

    @Column(name = "dept_id")
    public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	
	@Column(name = "job_id")
	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	@Override
    public String toString() {
        return "UserDeptJob{id = " + this.getId() +
                ",deptId=" + deptId +
                ", jobId=" + jobId +
                ", userId=" + (user != null ? user.getId() : "null") +
                '}';
    }
}
