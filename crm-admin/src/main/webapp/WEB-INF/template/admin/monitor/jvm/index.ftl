[#assign start = .now /]
<!doctype html>
<html>
<head>
<title>JDK1.5 management feature</title>
<link rel="stylesheet" href="${base}/resource/css/bootstrap.css" type="text/css" />
</head>
<body>
<h1>Java Runtime Info</h1>
	[#include "/admin/monitor/jvm/runtime.ftl"]<hr>
<h1>JVM OS Info</h1>
	[#include "/admin/monitor/jvm/OS.ftl"]<hr>
<h1>JVM Memory Info</h1>
	[#include "/admin/monitor/jvm/memory.ftl"]<hr>
<h1>JVM Thread Info</h1>
	[#include "/admin/monitor/jvm/thread.ftl"]<hr>
<h1> Execute Cost Time ${.now?long-start?long} </h1>
</body>
</html>