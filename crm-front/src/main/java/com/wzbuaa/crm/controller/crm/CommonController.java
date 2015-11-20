package com.wzbuaa.crm.controller.crm;

import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.wzbuaa.crm.bean.ZTree;
import com.wzbuaa.crm.domain.DictionaryType;
import com.wzbuaa.crm.domain.base.DictionaryDomain;
import com.wzbuaa.crm.service.base.DictionaryService;
import com.wzbuaa.crm.service.base.RSAService;
import com.wzbuaa.crm.util.ShopUtil;

import framework.entity.search.Searchable;

/**
 * 后台Action类 - 首页
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

	@Resource private DictionaryService dictionaryService;
	@Resource private RSAService rSAService;
	
	@RequestMapping("publicKey")
	@ResponseBody
	public JSONObject publicKey(HttpServletRequest request){
		RSAPublicKey rsapublickey = rSAService.generateKey(request);
		JSONObject obj = new JSONObject();
		obj.put("modulus", Base64.encodeBase64String(rsapublickey.getModulus().toByteArray()));
		obj.put("exponent", Base64.encodeBase64String(rsapublickey.getPublicExponent().toByteArray()));
		return obj;
	}

	/**
	 * 地区树
	 */
	@RequestMapping(value = "region/tree", method = RequestMethod.GET)
	@ResponseBody
	public List<ZTree<Long>> tree(Long parentId, Model model, HttpServletRequest request) {
		Searchable searchable = Searchable.newSearchable().addSearchParam("type_eq", DictionaryType.region);
		List<DictionaryDomain> parents = new ArrayList<DictionaryDomain>();
		parents.add(dictionaryService.findOne(parentId));
		List<DictionaryDomain> dics = dictionaryService.findChildren(parents, searchable);
		return convertToZtreeList(request.getContextPath(), dics, false, true);
	}
	
	@RequestMapping(value="isAuth")
	@ResponseBody
	public String isAuth(){
		boolean result = false;
		if(ShopUtil.getUserId() != null){
			result = true;
		}
		return String.valueOf(result);
	}
	

	protected List<ZTree<Long>> convertToZtreeList(String contextPath, List<DictionaryDomain> models, 
			boolean async, boolean onlySelectLeaf) {
        List<ZTree<Long>> zTrees = Lists.newArrayList();

        if (models == null || models.isEmpty()) {
            return zTrees;
        }

        for (DictionaryDomain m : models) {
            ZTree<Long> zTree = convertToZtree(m, !async, onlySelectLeaf);
            zTrees.add(zTree);
        }
        return zTrees;
    }

    private ZTree<Long> convertToZtree(DictionaryDomain m, boolean open, boolean onlyCheckLeaf) {
        ZTree<Long> zTree = new ZTree<Long>();
        zTree.setId(m.getId());
        zTree.setpId(m.getParentId());
        zTree.setName(m.getName());
        zTree.setIconSkin(m.getIcon());
        zTree.setOpen(open);
        zTree.setRoot(m.isRoot());
        zTree.setIsParent(m.getHasChildren());

        if (onlyCheckLeaf && zTree.isIsParent()) {
            zTree.setNocheck(true);
        } else {
            zTree.setNocheck(false);
        }

        return zTree;
    }
}