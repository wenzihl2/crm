package com.wzbuaa.crm.service.base;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.wzbuaa.crm.domain.base.TemplateDomain;
import com.wzbuaa.crm.domain.base.TemplateDomain.TemplateType;
import com.wzbuaa.crm.repository.base.TemplateRepository;
import com.wzbuaa.crm.service.BaseService;
import com.wzbuaa.crm.util.SysUtil;

import framework.util.FreemarkerUtils;

/**
 * Service实现类 - 邮件模板
 * author zhenglong 2013-04-26
 */
@Service
public class TemplateService extends BaseService<TemplateDomain, Long> {

	@Resource private FreeMarkerConfigurer freeMarkerConfigurer;
	
	private TemplateRepository getTemplateRepository() {
        return (TemplateRepository) baseRepository;
    }
	
	public TemplateDomain findByName(String name) {
		return getTemplateRepository().findByNameAndCompany(name, SysUtil.getUser().companyId);
	}
	
	public TemplateDomain findByTypeAndIdentity(TemplateType type, String name) {
		return getTemplateRepository().findByTypeAndIdentity(type, name);
	}
	
	public TemplateDomain findByTypeAndIdentity(TemplateType type, String name, Long company) {
		return getTemplateRepository().findByTypeAndIdentityAndCompany(type, name, company);
	}
	
	@Transactional(readOnly=true)
	public String parseContent(TemplateDomain templateBean, Map<String, Object> param) {
		// 解析模板并替换动态数据，最终数据将替换模板文件中的${}标签。
		String htmlText = FreemarkerUtils.process(templateBean.getContent(), param);
		return htmlText;
	}
}