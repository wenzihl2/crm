<!doctype html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.error.title")}</title>
<link href="${base}/resource/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resource/css/error.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="wrap">
		<div class="error">
			<dl>
				<dt>${message("admin.error.message")}</dt>
				[#if content??]
					<dd>${content}</dd>
				[/#if]
				[#if constraintViolations?has_content]
					[#list constraintViolations as constraintViolation]
						<dd>[${constraintViolation.propertyPath}] ${constraintViolation.message}</dd>
					[/#list]
				[/#if]
				[#if errors?has_content && errors.errors?has_content]
					[#list errors.errors as error]
						<dd>${error}</dd>
					[/#list]
				[/#if]
				<dd>
					<a href="javascript:;" onclick="window.history.back(); return false;">${message("admin.error.back")}</a>
				</dd>
			</dl>
		</div>
	</div>
</body>
</html>