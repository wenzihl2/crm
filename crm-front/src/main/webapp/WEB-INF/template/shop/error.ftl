<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${message("shop.error.title")} - ${setting.name}</title>
[#include "/shop/common/include.ftl" /]
<script type="text/javascript">
	function back() {
		var histories = window.history;
		if (histories.length == 1) {
			window.close();
		} else {
			window.history.back();
		}
	}
</script>
</head>
<body style="background:none;">
	[#include "/shop/common/head.ftl"]
	<div class="error clearfix" id="content">
		<div class="span24">
			<div class="main"  style="position:relative;width:464px;height:180px;margin:40px auto;background:url(${base}/theme/default/images/errorbg.jpg) no-repeat" >
				<div style="position: absolute;top: 50px;left: 200px;">
					<p style="font-size:14px;font-weight:bold;padding:0px 0 10px 0">
						<strong>${message("shop.error.message")}</strong>
					</p>
					[#if errors?has_content && errors.errors?has_content]
						[#list errors.errors as error]
						<p>
							${error}
						</p>
						[/#list]
					[#elseif WSException??]
						${WSException}
					[#else]
						您的操作出现错误!
					[/#if]
					<p>
						<a class="lan" href="${base}/">&lt;&lt; ${message("shop.error.home")}</a>
					</p>
				</div>
			</div>
		</div>
	</div>
	[#include "/shop/common/footer.ftl"]
</body>
</html>