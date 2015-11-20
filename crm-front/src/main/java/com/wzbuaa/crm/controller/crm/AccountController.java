package com.wzbuaa.crm.controller.crm;

import static com.wzbuaa.crm.domain.Constants.CURRENT_FRONT_USER;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzbuaa.crm.domain.DictionaryType;
import com.wzbuaa.crm.domain.base.DictionaryDomain;
import com.wzbuaa.crm.domain.crm.AccountDomain;
import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.service.base.DictionaryService;
import com.wzbuaa.crm.service.crm.AccountService;
import com.wzbuaa.crm.service.crm.MemberService;
import com.wzbuaa.crm.util.JsonDataHelper;

import framework.Message;
import framework.Pager;
import framework.entity.search.Searchable;
import framework.spring.mvc.bind.annotation.CurrentUser;
import framework.spring.mvc.bind.annotation.PageableDefaults;

@Controller
@RequestMapping("/member/account")
public class AccountController extends BaseController {

	@Resource private MemberService memberService;
	@Resource private AccountService accountService;
	@Resource private DictionaryService dictionaryService;

	/**
	 * 会员首页
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("accounts", accountService.findList());
		return viewName("list");
	}
	
	//获取列表
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@PageableDefaults(sort = "id=desc")
	public Pager<AccountDomain> queryData(Searchable searchable) {
		Page<AccountDomain> page= accountService.findAll(searchable);
		return JsonDataHelper.createJSONData(page);
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="createForm", method=RequestMethod.GET)
	public String createForm(Model model) {
		List<DictionaryDomain> banks = dictionaryService.findByTypeOrderByPriority(DictionaryType.bank);
		model.addAttribute("banks", banks);
		return viewName("editForm");
	}
	
	/**
	 * 保存银行卡
	 */
	@RequestMapping(value="create", method=RequestMethod.POST)
	public String create(@CurrentUser(CURRENT_FRONT_USER) MemberDomain member, AccountDomain account) {
		account.setMember(member);
		accountService.save(account);
		return redirectToUrl("success");
	}

	/**
	 * 修改
	 */
	@RequestMapping(value="{id}/updateForm", method=RequestMethod.GET)
	public String editBank(@PathVariable("id") Long id, Model model) {
		AccountDomain account = accountService.findById(id);
		if (account == null) {
			this.addActionError("银行卡不存在!");
			return FRONT_ERROR_PAGE;
		}
		List<DictionaryDomain> banks = dictionaryService.findByTypeOrderByPriority(DictionaryType.bank);
		model.addAttribute("banks", banks);
		model.addAttribute("account", account);
		return viewName("editForm");
	}
	
	/**
	 * 删除银行卡
	 */
	@RequestMapping(value="{id}/delete", method=RequestMethod.POST)
	public Message deleteBank(@PathVariable("id") Long id, HttpServletResponse response) {
		AccountDomain account = accountService.findById(id);
		if (account == null) {
			this.addActionError("银行卡不存在!");
			return errorMessage;
		}
		accountService.delete(id);
		return successMessage;
	}

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
