package com.wzbuaa.crm.controller.admin.crm;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.crm.CreditsDetailDomain;
import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.service.crm.CreditsDetailService;
import com.wzbuaa.crm.service.crm.MemberService;
import com.wzbuaa.crm.util.SysUtil;

import framework.Message;

/**
 * 积分明细
 * @author zl
 *
 */
@Controller
@RequestMapping("/admin/crm/credits")
public class CreditsController extends BaseCRUDController<CreditsDetailDomain, Long> {
	
	@Resource private CreditsDetailService creditsDetailService;
	@Resource private MemberService memberService;

	public CreditsController() {
        setResourceIdentity("sys:credits");
    }
	
	@Override
	@RequestMapping(value="create/discard", method=RequestMethod.POST)
	public String create(Model model, CreditsDetailDomain m, BindingResult result,
			RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) {
		throw new RuntimeException("discarded method");
	}
	
	@Override
	@RequestMapping(value="create/discard", method=RequestMethod.GET)
	public String showCreateForm(Model model) {
		throw new RuntimeException("discarded method");
	}
	
	/**
	 * 添加积分
	 * @return
	 */
	@RequestMapping(value="create", method=RequestMethod.GET)
	public String create(Long memberId, Model model){
		MemberDomain member = memberService.findOne(memberId);
		if(member == null){
			this.addActionError("会员不存在!");
			return null;
		}
		model.addAttribute("member", member);
		return viewName("input");
	}

	@RequestMapping(value="create", method=RequestMethod.POST)
	@ResponseBody
	public Message create(String username, CreditsDetailDomain detail){
		MemberDomain member = memberService.findByUsername(username);
		if(member == null){
			return errorMessage;
		}
		detail.setOperator(SysUtil.getUser().getLoginName());
		creditsDetailService.modifyCredits(member, detail);
 		return successMessage;
	}
}
