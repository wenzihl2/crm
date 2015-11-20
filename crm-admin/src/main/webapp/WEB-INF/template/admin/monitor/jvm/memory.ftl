[#assign HeapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage()]
<b>Init Memory: </b>${HeapMemoryUsage.init/1000000}M<br>
<b>MAX Memory: </b>${HeapMemoryUsage.max/1000000}M<br>
<b>Used Memory: </b>${HeapMemoryUsage.used/1000000}M<br>

