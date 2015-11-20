package com.wzbuaa.crm.service.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.wzbuaa.crm.bean.FileInfo;
import com.wzbuaa.crm.bean.FileInfo.FileType;
import com.wzbuaa.crm.bean.FileInfo.OrderType;
import com.wzbuaa.crm.bean.Setting;
import com.wzbuaa.crm.plugin.storage.StoragePlugin;
import com.wzbuaa.crm.plugin.storage.StoragePluginBundle;
import com.wzbuaa.crm.plugin.storage.StorageType;
import com.wzbuaa.crm.util.SettingUtils;

import framework.util.FreemarkerUtils;

@Repository
public class FileService {

	@Resource private StoragePluginBundle storagePluginBundle;
	
	//根据系统设置的文件存储路径模板获取路径(包含生成的文件名)
	private String getPathByFileType(FileType fileType, MultipartFile multipartFile){
		Setting setting = SettingUtils.get();
		String path;
		if (fileType == FileType.flash) {
			path = setting.getFlashUploadPath();
		} else if (fileType == FileType.media){
			path = setting.getMediaUploadPath();
		} else if (fileType == FileType.file){
			path = setting.getFileUploadPath();
		} else {
			path = setting.getImageUploadPath();
		}
		Map<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("uuid", UUID.randomUUID().toString());
		//文件路径名
		String pathPrefix = FreemarkerUtils.process(path, hashmap); 
		return new StringBuilder(pathPrefix).append(UUID.randomUUID()).append(".")
			.append(FilenameUtils.getExtension(multipartFile.getOriginalFilename())).toString();
	}
	
	public boolean isValid(FileType fileType, MultipartFile multipartFile) {
		if (multipartFile == null){
			return false;
		}
		Setting setting = SettingUtils.get();
		if(setting.getUploadLimit() != null && setting.getUploadLimit() == 0) {
			return false;
		}
		if (setting.getUploadLimit() != null && setting.getUploadLimit() != -1 
				&& multipartFile.getSize() > (long)setting.getUploadLimit().intValue() * 1024L){
			return false;
		}
		String extensions[];
		if (fileType == FileType.flash) {
			extensions = setting.getUploadFlashExtensions();
		} else if (fileType == FileType.media){
			extensions = setting.getUploadMediaExtensions();
		} else if (fileType == FileType.file){
			extensions = setting.getUploadFileExtensions();
		} else {
			extensions = setting.getUploadImageExtensions();
		}
		if (ArrayUtils.isNotEmpty(extensions)){
			return FilenameUtils.isExtension(multipartFile.getOriginalFilename().toLowerCase(), extensions);
		} else {
			return false;
		}
	}
	
	public String upload(File uploadFile, String contentType, String filePath, StorageType storageType) {
		if (uploadFile == null || storageType == null){
			return null;
		}
		StoragePlugin storageplugin = storagePluginBundle.getPluginMap().get(storageType);
		storageplugin.upload(filePath, uploadFile, contentType);
		return storageplugin.getUrl(filePath);
	}
	
	public String upload(FileType fileType, MultipartFile multipartFile, StorageType storageType) {
		if (multipartFile == null || storageType == null){
			return null;
		}
		//系统根据文件类型生成的文件路径和文件名
		String destFilePath = getPathByFileType(fileType, multipartFile);
		File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + ".tmp");
		try {
			multipartFile.transferTo(tempFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return upload(tempFile, multipartFile.getContentType(), destFilePath, storageType);
	}

	public List<FileInfo> browser(String path, FileType fileType, OrderType orderType, StorageType storageType) {
		if (path != null) {
			if (!path.startsWith("/"))
				path = new StringBuilder("/").append(path).toString();
			if (!path.endsWith("/"))
				path = new StringBuilder(path).append("/").toString();
		} else {
			path = "/";
		}
		Setting setting = SettingUtils.get();
		String path2 = null;
		if (fileType == FileType.flash){
			path2 = setting.getFlashUploadPath();
		} else if (fileType == FileType.media) {
			path2 = setting.getMediaUploadPath();
		} else if (fileType == FileType.file) {
			path2 = setting.getFileUploadPath();
		} else {
			path2 = setting.getImageUploadPath();
		}
		
		String s1 = StringUtils.substringBefore(path2, "${");
		s1 = StringUtils.substringBeforeLast(s1, "/") + path;
		
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		if (s1.indexOf("..") >= 0){
			return fileList;
		}
		if(storageType == null) {
			storageType = StorageType.FilePlugin;
		}
		StoragePlugin storageplugin = storagePluginBundle.getPluginMap().get(storageType);
		fileList.addAll(storageplugin.browser(s1));
		
		if (orderType == OrderType.size){
			Collections.sort(fileList, new SizeComparator());
		} else if (orderType == OrderType.type){
			Collections.sort(fileList, new TypeComparator());
		} else if (orderType == OrderType.date){
			Collections.sort(fileList, new DateComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		return fileList;
	}

	class SizeComparator implements Comparator<FileInfo> {

		@Override
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory())
								  .append(fileInfos1.getSize(), fileInfos2.getSize()).toComparison();
		}

	}
	
	class DateComparator implements Comparator<FileInfo> {

		@Override
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory())
								  .append(fileInfos2.getLastModified(), fileInfos1.getLastModified()).toComparison();
		}

	}


	class TypeComparator implements Comparator<FileInfo> {

		@Override
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory())
								  .append(FilenameUtils.getExtension(fileInfos1.getName()), 
										  FilenameUtils.getExtension(fileInfos2.getName())).toComparison();
		}
	}


	class NameComparator implements Comparator<FileInfo> {
		@Override
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory())
								  .append(fileInfos1.getName(), fileInfos2.getName()).toComparison();
		}
	}
	
}
