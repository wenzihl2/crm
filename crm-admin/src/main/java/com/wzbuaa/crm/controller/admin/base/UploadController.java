package com.wzbuaa.crm.controller.admin.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wzbuaa.crm.bean.FileInfo;
import com.wzbuaa.crm.bean.FileInfo.FileType;
import com.wzbuaa.crm.bean.FileInfo.OrderType;
import com.wzbuaa.crm.controller.admin.BaseController;
import com.wzbuaa.crm.plugin.storage.StorageType;
import com.wzbuaa.crm.service.base.FileService;
import com.wzbuaa.crm.util.UploadUtil;

import framework.Message;
import framework.util.ResponseUtils;

/**
 * 后台Action类 - 文件上传
 */
@Controller
@RequestMapping("/admin/upload")
public class UploadController extends BaseController {
	
	@Resource private FileService fileService;
	
	@RequestMapping(value="/browser", method=RequestMethod.GET)
	@ResponseBody
	public List<FileInfo> browser(String path, FileType fileType, OrderType orderType, StorageType storageType){
		return fileService.browser(path, fileType, orderType, storageType);
	}
	
	@RequestMapping(value="/submit", method=RequestMethod.POST)
	public String submit(FileType fileType, @RequestParam(value = "file", required = false) MultipartFile multipartFile, 
			StorageType storageType, HttpServletResponse response){
		if(storageType == null) {
			storageType = StorageType.FilePlugin;
		}
		if (!fileService.isValid(fileType, multipartFile)) {
			return ResponseUtils.renderJSON(Message.warn("admin.upload.invalid", new Object[0]), response);
		} else {
			String path = fileService.upload(fileType, multipartFile, storageType);
			if (path == null){
				ResponseUtils.renderJSON(Message.warn("admin.upload.invalid", new Object[0]), response);
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("url", path);
			map.put("store_path", UploadUtil.pathToNative(path, storageType));
			return ResponseUtils.renderJSON(map, response);
		}
	}
	
//	@RequestMapping(value="/upload", method=RequestMethod.POST)
//	@ResponseBody
//	public Message upload(FileType fileType, @RequestParam(value = "file", required = false) MultipartFile multipartFile, 
//			String pluginId, StorageType storageType, Model model){
//		if (!fileService.isValid(fileType, multipartFile)) {
//			return Message.warn("admin.upload.invalid", new Object[0]);
//		} else {
//			if(storageType == null) {
//				storageType = StorageType.FilePlugin;
//			}
//			String path = fileService.upload(fileType, multipartFile, storageType);
//			if (path == null){
//				return Message.warn("admin.upload.invalid", new Object[0]);
//			}
//			// 异步执行文件导入
//			String filePath = AppContext.getAppRealPath("./") + excelDataService.pathToNative(path, storageType).split("://")[1];
//			File file = new File(filePath);
//			if(!file.exists()) {
//				Message message = Message.error("文件不存在, 文件上传失败!", new Object[0]);
//				return message;
//			}
//			
//			excelDataService.importData(file, filePath, pluginId);
//			return successMessage;
//		}
//	}
}
