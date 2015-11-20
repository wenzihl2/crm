package com.wzbuaa.crm.domain.trade;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wzbuaa.crm.bean.DepositDrawBean;
import com.wzbuaa.crm.bean.DrawState;
import com.wzbuaa.crm.bean.DrawType;
import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.util.DateHelper;


/**
 * 实体 - 提现记录
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "crm_deposit_draw")
public class DepositDrawDomain extends BaseEntity<Long> {

	private String drawNo;//提现编号
	private String name;// 开户姓名
	private String bankName;// 开户行
	private String cardNo;// 银行卡号
	private BigDecimal applyAmount;// 申请提现金额
	private BigDecimal realAmount;// 实际可提金额
	private DrawType drawType;// 提现类型
	private DrawState drawState;// 提现状态：1.申请提现 2.提现成功 3.拒绝提现
	private MemberDomain member;//会员
	private String remark;//备注
	private String applyName;//申请人姓名

	public DepositDrawDomain(){
		super();
	}
	
	public DepositDrawDomain(DepositDrawBean depositDrawBean){
		this.name = depositDrawBean.getName();
		this.bankName = depositDrawBean.getBankName();
		this.cardNo = depositDrawBean.getCardNo();
		this.applyAmount = depositDrawBean.getApplyAmount();
		this.realAmount = depositDrawBean.getRealAmount();
		this.remark = depositDrawBean.getRemark();
		this.applyName = depositDrawBean.getApplyName();
	}
	
	@Transient
	public String getOperateType(){
		if(DrawState.apply == drawState){
			return "1";
		}else if(DrawState.approve == drawState){
			return "2";
		}else{
			return "3";
		}
	}

	@Transient
	public String getCreateDateStr(){
		return DateHelper.date2String(getCreateDate(), "yyyy-MM-dd hh:mm:ss");
	}
	
	@Transient
	public String getDrawTypeName(){
		if(drawType != null){
			//return DictionaryUtils.getDrawType(drawType);
			return null;
		}else{
			return null;
		}
		
	}
	
	@Transient
	public String getDrawStateName(){
		if(drawState != null){
			//return DictionaryUtils.getDrawState(drawState);
			return null;
		}else{
			return null;
		}
	}

	@Transient
	public String getUserName(){
		if(member != null){
			return member.getUsername();
		}
		return null;
	}

	public String getDrawNo() {
		return drawNo;
	}

	public void setDrawNo(String drawNo) {
		this.drawNo = drawNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}

	public DrawType getDrawType() {
		return drawType;
	}

	public void setDrawType(DrawType drawType) {
		this.drawType = drawType;
	}

	public DrawState getDrawState() {
		return drawState;
	}

	public void setDrawState(DrawState drawState) {
		this.drawState = drawState;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public MemberDomain getMember() {
		return member;
	}

	public void setMember(MemberDomain member) {
		this.member = member;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

}
