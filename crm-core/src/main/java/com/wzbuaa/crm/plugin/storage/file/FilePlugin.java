package com.wzbuaa.crm.plugin.storage.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import com.wzbuaa.crm.bean.FileInfo;
import com.wzbuaa.crm.exception.ApplicationException;
import com.wzbuaa.crm.plugin.storage.StoragePlugin;
import com.wzbuaa.crm.plugin.storage.StorageType;

import framework.util.AppContext;

@Component
public class FilePlugin extends StoragePlugin {

	public void upload(String path, File file, String contentType){
		path = AppContext.getAppRealPath(path);
		File file1 = new File(path);
		try {
			FileUtils.moveFile(file, file1);
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
			throw new ApplicationException("上传文件失败!");
		}
	}

	@Override
	public String getUrl(String path) {
		return getConfig().getMap().get("url") + path;
	}

	@Override
	public List<FileInfo> browser(String path) {
		List<FileInfo> arraylist = new ArrayList<FileInfo>();
		File file = new File(AppContext.getAppRealPath(path));
		if (file.exists() && file.isDirectory()) {
			File[] afile = file.listFiles();
			int j = afile.length;
			for (int i = 0; i < j; i++) {
				File file1 = afile[i];
				FileInfo fileinfo = new FileInfo();
				fileinfo.setName(file1.getName());
				fileinfo.setStorePath(this.getId() + "://" + path + file1.getName());
				fileinfo.setUrl(path + file1.getName());
				fileinfo.setIsDirectory(file1.isDirectory());
				fileinfo.setSize(file1.length());
				fileinfo.setLastModified(new Date(file1.lastModified()));
				arraylist.add(fileinfo);
			}

		}
		return arraylist;
	}

	@Override
	public String getType() {
		return "storage";
	}
	
	@Override
	public String getId() {
		return StorageType.FilePlugin.name();
	}

	@Override
	public void register() {
		
	}

	@Override
	public String getName() {
		return StorageType.FilePlugin.name();
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getAuthor() {
		return "yangrongchang";
	}

	@Override
	public void perform(Object... params) {
		
	}
}
