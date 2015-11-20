package com.wzbuaa.crm.plugin.SysDataConvert;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import com.wzbuaa.crm.domain.sso.user.GroupDomain;
import com.wzbuaa.crm.service.sso.GroupService;
import com.wzbuaa.crm.util.BeanUtils;

public class GroupConvertPlugin extends AbstractDataConvertPlugin  {

	@Resource private GroupService groupService;
	
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
		return DataType.group.name();
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
		GroupDomain group = groupService.findOne(Long.parseLong(id));
		try {
			return BeanUtils.beanUtilsBean.getProperty(group, key);
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
