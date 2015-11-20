package com.wzbuaa.crm.service.base;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Service;

import com.wzbuaa.crm.domain.SnCreateType;
import com.wzbuaa.crm.domain.base.SnCreaterDomain;
import com.wzbuaa.crm.domain.base.SnCreaterDomain.UpdateCycle;
import com.wzbuaa.crm.exception.ApplicationException;
import com.wzbuaa.crm.repository.base.SnCreaterRepository;
import com.wzbuaa.crm.service.BaseService;
import com.wzbuaa.crm.util.ShopUtil;
import com.wzbuaa.crm.util.SysUtil;

@Service
public class SnCreaterService extends BaseService<SnCreaterDomain, Long> {
	
	private SnCreaterRepository getSnCreaterRepository() {
        return (SnCreaterRepository) baseRepository;
    }
	
	public SnCreaterDomain findById(SnCreateType code, Long company){
		if(company == null) {
			company = SysUtil.getUser().companyId;
		}
		return getSnCreaterRepository().findByCode(code, company);
	}
	
	public String create(SnCreateType code) {
		SnCreaterDomain snCreater = this.findById(code, ShopUtil.getUser().companyId);
		if(snCreater == null) {
			throw new ApplicationException("序号创建规则:" + code + "不存在!");
		}
		Long dqxh = snCreater.getDqxh() + 1;//当前序号
		
		StringBuffer xh=new StringBuffer();
		xh.append(snCreater.getQz());//前缀
		
		Calendar current = Calendar.getInstance();
		//判断是否需要日期加入
		if(snCreater.getQyrq() != null && snCreater.getQyrq()){
			
			String rqgs = snCreater.getRqgs();//日期格式
			String rq = new SimpleDateFormat(rqgs).format(current.getTime());
			xh.append(rq);
			//根据日期的更新周期，进行统计
			UpdateCycle updateCycle = snCreater.getGxzq();
			Calendar update = Calendar.getInstance();
			update.setTime(snCreater.getModifyDate());
			switch(updateCycle){
				case No:
					break;
				case EveryDay://每天更新
					if(update.get(Calendar.DATE) == current.get(Calendar.DATE))
					break;
				case EveryMonth://每月
					if(update.get(Calendar.MONTH) == current.get(Calendar.MONTH))
					break;
				case EveryYear://每年
					if(update.get(Calendar.YEAR) == current.get(Calendar.YEAR))
					break;
				default:
					dqxh = 1L;
			}
		}
		snCreater.setDqxh(dqxh);
		Integer ws = snCreater.getWs();//序号位数
		DecimalFormat nf = new DecimalFormat("0000");
		if(ws != null && ws != 4) {
			StringBuffer ss= new StringBuffer();
			for(int i=0; i<ws; i++){
				ss.append("0");
			}
			nf = new DecimalFormat(ss.toString());
		}
		xh.append(nf.format(dqxh));//序号
		
		/**最后更新*/
		super.update(snCreater);
		return xh.toString();
	}
}
