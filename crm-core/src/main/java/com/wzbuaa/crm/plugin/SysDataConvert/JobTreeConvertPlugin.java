package com.wzbuaa.crm.plugin.SysDataConvert;

import java.util.List;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.wzbuaa.crm.domain.sso.user.JobDomain;
import com.wzbuaa.crm.service.sso.JobService;

public class JobTreeConvertPlugin extends AbstractDataConvertPlugin  {

	@Resource private JobService jobService;
	
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
		return DataType.jobTree.name();
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
		JobDomain job = jobService.findOne(Long.parseLong(id));
		
		List<String> names = Lists.newArrayList();
	    if (job != null) {
	        names.add(job.getName());
            List<JobDomain> parents = jobService.findAncestor(job.getParentIds());
            for(JobDomain o : parents) {
                names.add(o.getName());
            }
	    }
	    
	    StringBuilder s = new StringBuilder();
	    for(int l = names.size() - 1, i = l; i >= 0; i--) {
	        if(i != l) {
	            s.append(" &gt; ");
	        }
	        s.append(names.get(i));
	    }
		return s.toString();
	}

	@Override
	public void register() {
		
	}
	
	@Override
	public void perform(Object... params) {
		// TODO Auto-generated method stub
		
	}

}
