[#assign rt = ManagementFactory.getRuntimeMXBean()]
<b>Vm Name</b>: ${rt.vmName}<br>
<b>Vm Version</b>: ${rt.vmVersion}<br>
<b>Vm Vendor</b>: ${rt.vmVendor}<br>
<b>Up Time</b>: ${rt.uptime/(1000*60*60)} hours<br>
<b>Input Arguments</b>: ${rt.inputArguments}<br>
<b>Library Path</b>: ${rt.libraryPath}<br>
<b>Class Path</b>: ${rt.classPath}<br>