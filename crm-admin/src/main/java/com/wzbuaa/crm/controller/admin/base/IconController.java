package com.wzbuaa.crm.controller.admin.base;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.wzbuaa.crm.bean.FileInfo.FileType;
import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.Constants;
import com.wzbuaa.crm.domain.base.IconDomain;
import com.wzbuaa.crm.domain.base.IconType;
import com.wzbuaa.crm.plugin.storage.StorageType;
import com.wzbuaa.crm.service.base.FileService;
import com.wzbuaa.crm.service.base.IconService;
import com.wzbuaa.crm.util.LogUtils;

import framework.entity.search.Searchable;
import framework.spring.mvc.response.ValidateResponse;
import framework.util.AppContext;

/**
 * <p>User: zhenglong
 * <p>Date: 2015年5月27日
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/base/icon")
public class IconController extends BaseCRUDController<IconDomain, Long> {
 
	@Resource private FileService fileService;
	
    private IconService getIconService() {
        return (IconService) baseService;
    }

    @Value("${icon.css.file.src}")
    private String iconClassFile;

    public IconController() {
        setListAlsoSetCommonData(true);
        setResourceIdentity("maintain:icon");
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("types", IconType.values());
    }

    //不再是默认的create，因为下边的create具有多个参数，因此无法覆盖默认的create，因此为了使用该url 我们把父类的url改掉
    @RequestMapping(value = "create/discard", method = RequestMethod.POST)
    @Override
    public String create(Model model, @Valid @ModelAttribute("m") IconDomain icon, BindingResult result, 
    		RedirectAttributes redirectAttributes,
    		HttpServletRequest request, HttpServletResponse response) {
        throw new RuntimeException("discarded method");
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        return super.showCreateForm(model);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(HttpServletRequest request, HttpServletResponse response, 
            Model model,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @Valid @ModelAttribute("icon") IconDomain icon, BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (file != null && !file.isEmpty()) {
        	String path = fileService.upload(FileType.image, file, StorageType.FilePlugin);
        	if(path.startsWith("/")) {
        		path = path.substring(1);
        	}
            icon.setImgSrc(fileService.upload(FileType.image, file, StorageType.FilePlugin));
        }
        String view = super.create(model, icon, result, redirectAttributes, request, response);
        genIconCssFile(request);
        return view;
    }

    @RequestMapping(value = "{id}/update/discard", method = RequestMethod.POST)
    @Override
    public String update(Model model, @ModelAttribute("m") IconDomain m, BindingResult result, 
    		@RequestParam(value = "BackURL", required = false) String backURL, RedirectAttributes redirectAttributes,
    		HttpServletRequest request, HttpServletResponse response) {
        throw new RuntimeException("discarded method");
    }

    @RequestMapping(value = "{id}/update", method = RequestMethod.POST)
    public String update(
            HttpServletRequest request, HttpServletResponse response, 
            Model model,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @Valid @ModelAttribute("m") IconDomain m, BindingResult result,
            @RequestParam(value = "BackURL") String backURL,
            RedirectAttributes redirectAttributes) {
    	
        if (file != null && !file.isEmpty()) {
        	m.setImgSrc(fileService.upload(FileType.image, file, StorageType.FilePlugin));
        }
        String view = super.update(model, m, result, backURL, redirectAttributes,request, response);
        genIconCssFile(request);
        return view;
    }

    @RequestMapping(value = "{id}/delete/discard", method = RequestMethod.POST)
    @Override
    public String delete(
            @PathVariable("id") Long id,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

        throw new RuntimeException("discarded method");
    }

    @RequestMapping(value = "{id}/delete", method = RequestMethod.POST)
    public String delete(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable("id") Long id,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {
    	
        String view = super.delete(id, backURL, redirectAttributes, request, response);
        genIconCssFile(request);
        return view;
    }

    @RequestMapping(value = "batch/delete/discard")
    public String deleteInBatch(
            @RequestParam(value = "ids", required = false) Long[] ids,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {

        throw new RuntimeException("discarded method");
    }

    @RequestMapping(value = "batch/delete")
    public String deleteInBatch(
            HttpServletRequest request,
            @RequestParam(value = "ids", required = false) Long[] ids,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {

        String view = super.deleteInBatch(ids, backURL, null, null, redirectAttributes);

        genIconCssFile(request);

        return view;
    }


    @RequestMapping(value = "/select")
    public String select(Model model) {
        setCommonData(model);
        model.addAttribute("icons", baseService.findAll());
        return viewName("select");
    }

 
    /**
     * 如果量大 建议 在页面设置按钮 然后点击生成
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/genCssFile")
    @ResponseBody
    public String genIconCssFile(HttpServletRequest request) {

//        this.permissionList.assertHasEditPermission();

        String uploadFileTemplate = ".%1$s{background:url(%2$s/%3$s);width:%4$spx;height:%5$spx;display:inline-block;vertical-align: middle;%6$s}";
        String cssSpriteTemplate = ".%1$s{background:url(%2$s/%3$s) no-repeat -%4$spx -%5$spx;width:%6$spx;height:%7$spx;display:inline-block;vertical-align: middle;%8$s}";

//        ServletContext sc = request.getServletContext();
//        String ctx = sc.getContextPath();

        List<String> cssList = Lists.newArrayList();

        Searchable searchable = Searchable.newSearchable()
                .addSearchParam("type_in", new IconType[]{IconType.upload_file, IconType.css_sprite});

        List<IconDomain> iconList = baseService.findAllWithNoPageNoSort(searchable);

        for (IconDomain icon : iconList) {

            if (icon.getType() == IconType.upload_file) {
                cssList.add(String.format(
                        uploadFileTemplate,
                        icon.getIdentity(),
                        AppContext.getContextPath(), icon.getImgSrc(),
                        icon.getWidth(), icon.getHeight(),
                        icon.getStyle()));
                continue;
            }

            if (icon.getType() == IconType.css_sprite) {
                cssList.add(String.format(
                        cssSpriteTemplate,
                        icon.getIdentity(),
                        AppContext.getContextPath(), icon.getSpriteSrc(),
                        icon.getLeft(), icon.getTop(),
                        icon.getWidth(), icon.getHeight(),
                        icon.getStyle()));
                continue;
            }

        }

        try {
            ServletContextResource resource = new ServletContextResource(AppContext.getContext(), iconClassFile);
            FileUtils.writeLines(resource.getFile(), cssList);
        } catch (Exception e) {
            LogUtils.logError("gen icon error", e);
            return "生成失败：" + e.getMessage();
        }

        return "生成成功";
    }


    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {
        ValidateResponse response = ValidateResponse.newInstance();

        if ("identity".equals(fieldId)) {
        	IconDomain icon = getIconService().findByIdentity(fieldValue);
            if (icon == null || (icon.getId().equals(id) && icon.getIdentity().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "该标识符已被其他人使用");
            }
        }
        return response.result();
    }


}
