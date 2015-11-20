package com.wzbuaa.crm.plugin.SysDataConvert;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import com.wzbuaa.crm.domain.sso.user.S_userDomain;
import com.wzbuaa.crm.service.sso.S_userService;
import com.wzbuaa.crm.util.BeanUtils;

public class UserConvertPlugin extends AbstractDataConvertPlugin  {

	@Resource private S_userService userService;
	
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
		return DataType.user.name();
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
		S_userDomain user = userService.findOne(Long.parseLong(id));
		if(user == null) return null;
		try {
			return BeanUtils.beanUtilsBean.getProperty(user, key);
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
