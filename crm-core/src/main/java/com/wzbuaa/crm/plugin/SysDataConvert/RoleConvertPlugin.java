package com.wzbuaa.crm.plugin.SysDataConvert;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.wzbuaa.crm.domain.sso.Role;
import com.wzbuaa.crm.service.sso.RoleService;

public class RoleConvertPlugin extends AbstractDataConvertPlugin  {

	@Resource private RoleService roleService;
	
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
		return DataType.role.name();
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
		if(StringUtils.isEmpty(id)) {
			return "";
		}
		String[] ids = StringUtils.split(id, ",");
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<ids.length; i++) {
			Role role = roleService.findOne(Long.parseLong(ids[i]));
			sb.append(role.getName()).append("[").append(role.getValue()).append("]");
			if(i != ids.length - 1) {
				sb.append(" | ");
			}
		}
		return sb.toString();
	}

	@Override
	public void register() {
		
	}
	
	@Override
	public void perform(Object... params) {
		// TODO Auto-generated method stub
		
	}

}
