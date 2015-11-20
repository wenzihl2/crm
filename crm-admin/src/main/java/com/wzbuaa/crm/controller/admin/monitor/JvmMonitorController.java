package com.wzbuaa.crm.controller.admin.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wzbuaa.crm.controller.admin.BaseController;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-27 下午6:50
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/admin/monitor/jvm")
@RequiresPermissions("monitor:jvm:*")
public class JvmMonitorController extends BaseController {

    @RequestMapping("")
    public String index(Model model) {
    	ThreadMXBean tm = ManagementFactory.getThreadMXBean();
    	tm.setThreadContentionMonitoringEnabled(true);
    	long [] tid = tm.getAllThreadIds();
    	ThreadInfo [] tia = tm.getThreadInfo(tid, Integer.MAX_VALUE);
    	long [][] threadArray = new long[tia.length][2];
    	for (int i = 0; i < tia.length; i++) {          
    	    long threadId = tia[i].getThreadId();

    	    long cpuTime = tm.getThreadCpuTime(tia[i].getThreadId())/(1000*1000*1000);
    	    threadArray[i][0] = threadId;
    	    threadArray[i][1] = cpuTime;
    	}
    	long [] temp = new long[2];
    	for (int j = 0; j < threadArray.length - 1; j ++){
    		for (int k = j + 1; k < threadArray.length; k++ ){
	    	    if (threadArray[j][1] < threadArray[k][1]){
	    	        temp = threadArray[j];
	    	        threadArray[j] = threadArray[k];
	    	        threadArray[k] = temp;  
	    	    }
    		}
    	}
    	model.addAttribute("threadArray", threadArray);
    	model.addAttribute("tm", tm);
    	model.addAttribute("max", Integer.MAX_VALUE);
        return viewName("index");
    }


}
