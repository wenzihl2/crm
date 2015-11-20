<b>Thread Count: </b>${tm.threadCount}<br>
<b>Started Thread Count: </b>${tm.totalStartedThreadCount}<br>
<b>thread contention monitoring is enabled? </b>${tm.threadContentionMonitoringEnabled?string("true", "false")}<br>
<b>if the Java virtual machine supports thread contention monitoring? </b>${tm.threadContentionMonitoringSupported?string("true", "false")}<br>
<b>thread CPU time measurement is enabled? </b>${tm.threadCpuTimeEnabled?string("true", "false")}<br>
<b>if the Java virtual machine implementation supports CPU time measurement for any thread? </b>${tm.threadCpuTimeSupported?string("true", "false")}<br>
<hr>
[#list threadArray as thread]
	[#assign ti = tm.getThreadInfo(thread[0], max) /]
  	[#if ti != null]
	<b>Thread ID: </b>${thread[0]}<br>
	<b>Thread Name: </b>${ti.threadName}<br>
	<b>Thread State: </b>${ti.threadState}<br>
	<b>Thread Lock Name: </b>${ti.lockName}<br>
	<b>Thread Lock Owner Name: </b>${ti.lockOwnerName}<br>
	<b>Thread CPU Time: </b>${thread[1]} sec<br>
	<b>Stack Info: (depth:${ti.stackTrace.length})</b><br>
		[#assign stes = ti.stackTrace]
		[#list stes as ste]
			&nbsp;&nbsp;&nbsp;&nbsp;+${ste}<br>
		[/#list]
	[/#if]
<hr>
[/#list]
