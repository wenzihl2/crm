package com.wzbuaa.crm.domain.crm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import com.wzbuaa.crm.domain.BaseEntity;

/**
 * 实体类 - 会员等级
 */
@SuppressWarnings("serial")
@Entity
@Table(name="crm_member_rank")
public class MemberRankDomain extends BaseEntity<Long> {

	private String name;// 等級名称
	private Integer scale;// 优惠百分比
	private Integer point;// 所需积分
	private Boolean isDefault = Boolean.FALSE;// 是否为默认等级
	private BigDecimal rankPrice;

	
	@Transient
	public void init(){
		if(this.isDefault == null){
			this.setIsDefault(false);
		}
	}
	
	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	@Column(nullable = false, unique = true)
	@XmlTransient
	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	@Column(nullable = false)
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Transient
	public BigDecimal getRankPrice() {
		return rankPrice;
	}

	public void setRankPrice(BigDecimal rankPrice) {
		this.rankPrice = rankPrice;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MemberRankDomain){
			MemberRankDomain rank = (MemberRankDomain) obj;
			if(this.getId().equals(rank.getId())){
				return true;
			}
		}
		return false;
	}

	
}