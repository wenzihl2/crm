package com.wzbuaa.crm.controller.crm;

import java.util.List;
import javax.annotation.Resource;
import static com.wzbuaa.crm.domain.Constants.CURRENT_FRONT_USER;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.wzbuaa.crm.domain.cms.NavigationDomain;
import com.wzbuaa.crm.domain.cms.NavigationDomain.NavigationPosition;
import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.service.cms.NavigationService;
import com.wzbuaa.crm.service.crm.MemberService;
import framework.spring.mvc.bind.annotation.CurrentUser;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {

	@Resource private MemberService memberService;
	@Resource private NavigationService navigationService;
//	@Resource private AreaService areaService;
//	@Resource private AccountService accountService;
//	@Resource private RSAService rSAService;

	/**
	 * 会员首页
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(@CurrentUser(value=CURRENT_FRONT_USER) MemberDomain member, Model model, String error) {
		List<NavigationDomain> navigations = navigationService.findByPosition(NavigationPosition.left);
		//AppContext.setSessionAttr("profileToken", profileToken);
		model.addAttribute("navigations", navigations);
		model.addAttribute("member", member);
		//model.addAttribute("token", profileToken);
		return viewName("index");
	}
//	
//	/**
//	 * 更新个人信息
//	 * @param token
//	 * @return
//	 */
//	@RequestMapping(value="update", method=RequestMethod.POST)
//	public String update(String token, MemberDomain member) {
//		if(StringUtils.isBlank(token) || !token.equals(AppContext.getSessionAttr("profileToken"))){
//			addActionError("信息已过期!");
//			return FRONT_ERROR_PAGE;
//		}
//		if(member.getA == null 
//				|| member.getArea().getId() == null){
//			member.setArea(null);
//		}else{
//			member.setArea(areaService.find(member.getArea().getId()));
//		}
//		//TODO 数据验证
//		memberService.updateByUpdater(member);
//		AppContext.removeAttribute("profileToken");
//		return MEMBER_SUCCESS + "?redirectionUrl=/member/profile.jhtml";
//	}
//	
//	/**
//	 * 成功页面
//	 * @return
//	 */
//	@RequestMapping(value="success", method=RequestMethod.GET)
//	public String success(){
//		return viewName("usccess");
//	}
//	
//	/**
//	 * 失败页面
//	 * @return
//	 */
//	@RequestMapping(value="error", method=RequestMethod.GET)
//	public String error(){
//		return viewName("error");
//	}
//	
//	/**
//	 * 银行卡列表
//	 * 
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping("bankList")
//	public String bankList(Model model) {
//		try {
//			model.addAttribute("accountInfo", accountService.getAccountInfo(ShopUtil.getMemberId()));
//		} catch (Exception e) {
//			this.addActionError(getWebApplicationExceptionMessage(e.getMessage()));
//			return FRONT_ERROR_PAGE;
//		}
//		return "/member/bankList";
//	}
//
//	/**
//	 * 新增银行卡页面
//	 * 
//	 * @return
//	 */
//	@RequestMapping("/bank")
//	public String bankPage(Model model, String entry) {
//		model.addAttribute("payBanks", SettingUtils.getDictionaryDataListForJSON("100001"));
//		if("deposit".equals(entry)){
//			model.addAttribute("backUrl", "trade/deposit.jhtml");
//		}else if("outcome".equals(entry)){
//			model.addAttribute("backUrl", "trade/outcome/false.jhtml");
//		}
//		model.addAttribute("entry", entry);
//		return "/member/bank";
//	}
//
//	/**
//	 * 修改银行卡
//	 * 
//	 * @param id
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping("/bank/{id}")
//	public String editBank(@PathVariable("id") Long id, Model model) {
//		AccountDomain account = accountService.getAccountByMember(id, ShopUtil.getMemberId());
//		if (account == null) {
//			this.addActionError("银行卡不存在!");
//			return FRONT_ERROR_PAGE;
//		}
//		model.addAttribute("account", account);
//		model.addAttribute("payBanks", SettingUtils.getDictionaryDataListForJSON("100001"));
//		return "/member/bank";
//	}
//	
//	/**
//	 * 删除银行卡
//	 * 
//	 * @param id
//	 * @param model
//	 * @return
//	 */
//	@Validations(
//		requiredFields={
//			@RequiredFieldValidator(fieldName="id", message="银行卡不存在!")
//		}
//	)
//	@RequestMapping(value="/bank/delete", method=RequestMethod.POST)
//	public String deleteBank(Long id, HttpServletResponse response) {
//		AccountDomain account = accountService.getAccountByMember(id, ShopUtil.getMemberId());
//		if (account == null) {
//			return ResponseUtils.renderJSON(errorMessage, response);
//		}
//		accountService.remove(id);
//		return ResponseUtils.renderJSON(successMessage, response);
//	}
//	
//	/**
//	 * 保存银行卡
//	 * 
//	 * @return
//	 */
//	@Validations(
//		requiredStrings={
//			@RequiredStringValidator(fieldName="identityCard", message="身份证不能为空!"),
//			@RequiredStringValidator(fieldName="userName", message="开户名不能为空!"),
//			@RequiredStringValidator(fieldName="organization", message="开户行不能为空!"),
//			@RequiredStringValidator(fieldName="cardNum", message="卡号不能为空!"),
//			@RequiredStringValidator(fieldName="decode", message="所属银行不能为空!")
//		}
//	)
//	@RequestMapping("/savebank")
//	public String savebankPage(AccountBean accountBean, String decode, String entry) {
//		accountBean.setCode(decode);
//		try {
//			accountService.saveAccount(ShopUtil.getMemberId(), accountBean);
//		} catch (Exception e) {
//			this.addActionError(getWebApplicationExceptionMessage(e.getMessage()));
//			return FRONT_ERROR_PAGE;
//		}
//		if("deposit".equals(entry)){
//			return "redirect:/trade/deposit.jhtml";
//		}else if("outcome".equals(entry)){
//			return "redirect:/trade/outcome/false.jhtml";
//		}
//		return "redirect:/member/bankList.jhtml";
//	}
//
//	/**
//	 * 会员签到
//	 * @return
//	 */
//	@RequestMapping(value="/signIn", method=RequestMethod.GET)
//	public void signIn(String callback, HttpServletResponse response){
//		JSONObject jObj = new JSONObject();
//		String error = memberService.signIn(ShopUtil.getMemberId());
//		if(StringUtils.isBlank(error)){
//			jObj.put("message", "签到成功");
//		}else{
//			jObj.put("error", error);
//		}
//		ResponseUtils.renderText(callback + "(" + jObj.toString() + ")", response);
//	}
//
//	/**
//	 * 验证支付密码
//	 * @return
//	 */
//	@RequestMapping(value="/checkPayPassword", method=RequestMethod.GET)
//	public String checkPayPassword(String enPassword, HttpServletResponse response){
//		MemberDomain member = ShopUtil.getMember();
//		if(StringUtils.isBlank(enPassword)){
//			memberService.payLock(member);
//			return ResponseUtils.renderJSON(errorMessage, response);
//		}
//		if(memberService.isPayLocked(member)){
//			return ResponseUtils.renderJSON(Message.warn("支付密码已被锁定!", new Object[0]), response);
//		}
//		String password = rSAService.decryptParameter(enPassword);
//		if(DigestUtils.md5Hex(password).equals(member.getPay_passwd())){
//			memberService.unLockPay(member);
//			return ResponseUtils.renderJSON(successMessage, response);
//		}
//		memberService.payLock(member);
//		return ResponseUtils.renderJSON(Message.warn("支付密码错误!", new Object[0]), response);
//	}
	
}
