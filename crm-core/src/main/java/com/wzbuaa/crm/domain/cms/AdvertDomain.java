package com.wzbuaa.crm.domain.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.wzbuaa.crm.component.entity.Movable;
import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.domain.base.DictionaryDomain;
import com.wzbuaa.crm.util.UploadUtil;

/**
 * 广告
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "cms_advert")
public class AdvertDomain extends BaseEntity<Long> implements Movable {

	private String name;	//广告名字
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTime;//开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endTime;//结束时间
	private Boolean isOpen;//是否开启
	private Integer clickNumber;//点击数
	private String url;//广告链接
	private String logo;//上传文件
	private Integer priority;
	private String background;//背景图
	private Long company; //城市
	private DictionaryDomain position;//广告对应的广告位置
	
	@Transient
	public String getRealPath(){
		if(StringUtils.isNotEmpty(this.getLogo())){
			return UploadUtil.replacePath(logo);
		}
		return logo;
	}
	
	@Transient
	public String getRealBackground(){
		if(StringUtils.isNotEmpty(background)){
			return UploadUtil.replacePath(background);
		}
		return background;
	}
	
	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	@Column(unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.DATE)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.DATE)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getClickNumber() {
		return clickNumber;
	}

	public void setClickNumber(Integer clickNumber) {
		this.clickNumber = clickNumber;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(columnDefinition="int(11) default 0")
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public DictionaryDomain getPosition() {
		return position;
	}

	public void setPosition(DictionaryDomain position) {
		this.position = position;
	}

}
