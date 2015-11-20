<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-银行账号</title>
[#include "/shop/common/include.ftl" /]
<link href="${base}/theme/default/css/usercenter.css" rel="stylesheet" type="text/css" />
</head>
<body mycenter="member_bank">
	<div id="content">
		[#include "/shop/common/member_left.ftl"]
		<div class="right">
			<div class="mydd">
				<div class="mydd_m">
					<span class="zxkf"></span>银行账号
				</div>
				<div class="mydd_c">
					<div class="ui-grid-19">
						<div class="card-box-list J-card-box">
							[#if accounts?? && accounts?size &gt; 0]
								[#list accounts as account]
									<div class="card-box" id="bank${account.id}">
										<div class="card-box-name">
											<span class="bank-logo"><a href="#"><img src="img/bank/${account.code}.png" width="16" height="16" alt=""></a></span>
											<span class="bank-name" title="${account.attribution}">${account.attribution}</span>
											<span class="bank-num4">尾号${account.lastCode}</span>
										</div>
										<div class="card-box-express">
											<div class="express-edit">
												<a class="alink" href="${base}/member/bank/${account.id}.jhtml">修改</a>
											</div>
											<div class="express-del">
												<a class="alink" href="javascript:f_delete(${account.id});">删除</a>
											</div>
										</div>
										<div class="card-detail">
											<div class="card-detail-list">
												<div class="card-detail-value" title="账单">
													<strong>开户名：</strong>
													<span>${account.userName}</span>
												</div>
											</div>
											<div class="card-detail-list">
												<div class="card-detail-value" title="账单">
													<strong>开户行：</strong>
													<span>${account.organization}</span>
												</div>
											</div>
										</div>
									</div>
								[/#list]
							[/#if]
							[#if accounts?? && accounts?size &lt; 6]
								<div class="add-card J-add-card">
									<a href="${base}/member/account/createForm.jhtml"><i class="iconfont"></i> 添加银行卡 </a>
								</div>
							[/#if]
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>