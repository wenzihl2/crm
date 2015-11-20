package com.wzbuaa.crm.plugin.SysDataConvert;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import com.wzbuaa.crm.domain.sso.user.DeptDomain;
import com.wzbuaa.crm.service.sso.DeptService;
import com.wzbuaa.crm.util.BeanUtils;

public class DeptConvertPlugin extends AbstractDataConvertPlugin  {

	@Resource private DeptService deptService;
	
	@Override
	public String getType() {
		return null;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public String getName() {
		return DataType.dept.name();
	}

	@Override
	public String getVersion() {
		return null;
	}

	@Override
	public String getAuthor() {
		return null;
	}

	@Override
	public String convert(String id, String key) {
		DeptDomain dept = deptService.findOne(Long.parseLong(id));
		try {
			return BeanUtils.beanUtilsBean.getProperty(dept, key);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void register() {
		
	}

	@Override
	public void perform(Object... params) {
		// TODO Auto-generated method stub
		
	}

}
