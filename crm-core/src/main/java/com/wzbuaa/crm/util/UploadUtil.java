package com.wzbuaa.crm.util;

import java.io.File;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import com.wzbuaa.crm.domain.base.PluginDomain;
import com.wzbuaa.crm.exception.ApplicationException;
import com.wzbuaa.crm.plugin.storage.StoragePlugin;
import com.wzbuaa.crm.plugin.storage.StoragePluginBundle;
import com.wzbuaa.crm.plugin.storage.StorageType;
import com.wzbuaa.crm.service.base.PluginService;
import framework.util.AppContext;
import framework.util.SpringUtils;
import framework.util.StringHelper;

public class UploadUtil  {
	
	private static final String UPLOAD_PATH = "upload";
	
	private UploadUtil(){
		
	}
	
	/**
	 * 直接将文件上传并复制到subFolder对应的目录下
	 * @param file 上传的文件
	 * @param fileFileName 上床的文件名
	 * @param subFolder upload的子目录
	 * @return
	 * @throws Exception
	 */
	public static String upload(File file, String fileFileName, String subFolder) {
		if(file==null || fileFileName==null) throw new IllegalArgumentException("file or filename object is null");
		if(subFolder == null) throw new IllegalArgumentException("subFolder is null");
 
		String ext = FileUtil.getFileExt(fileFileName);
		String fileName = DateHelper.date2String(new Date(), "yyyyMMddHHmmss") + StringHelper.getRandStr(4) + "." + ext;
		
		String filePath = AppContext.getAppRealPath(UPLOAD_PATH) + "/";
		if(subFolder != null) {
			filePath += subFolder + "/";
		}
		filePath += fileName;
		FileUtil.createFile(file, filePath);
		file.delete();
		return "/" + filePath;	
	}

	/**
	 * 替换为上传组件的链接地址(针对附件)
	 * @param path
	 * @return
	 */
	public static String replacePath(String path){
		if(StringUtils.isEmpty(path)) {
			return path;
		}
		
		if(path.startsWith("http://")){
			return path;
		}
		//通过正则表达式获取组件的标识和ID
		String pattern = "(.*)" + "://" + "(.*)";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(path);
		String componentSign = null;
		String file_path = null;
		if (m.find()) {
			componentSign = m.replaceAll("$1");
			file_path = m.replaceAll("$2");
		}else{
			return path;
		}
		
		StoragePluginBundle storagePluginBundle = (StoragePluginBundle) SpringUtils.getBean("storagePluginBundle");
		StoragePlugin storagePlugin = storagePluginBundle.getPluginMap().get(StorageType.valueOf(componentSign));
		return storagePlugin.getConfig().getMap().get("url") + file_path;
	}
	
	/**
	 * 将图片路径转化为本地的组件路径
	 * @param path
	 * @param componentId
	 * @return
	 */
	public static String pathToNative(String path, StorageType storageType){
		PluginService pluginService = (PluginService) SpringUtils.getBean("pluginService");
		PluginDomain plugin = pluginService.findEnableComponent(storageType.name());
		if(plugin == null){
			throw new ApplicationException("组件ID:" + storageType.name() + "不存在");
		}
		path = path.replaceFirst((String)plugin.getConfigparam().getMap().get("url"), "");
		return plugin.getId() + "://" + path;
	}
	
	public static void main(String[] args) {
//		String str = "http://localhost/upload/image/201308/02499049-81e3-4c54-970e-9cb2a3c8d0f7.jpg";
//		str = str.replaceFirst("http://localhost/", "filePlugin-7/");
		
		String str = "http://img1.zgwanshan.com//upload/image/201310/8866a216-f931-4206-8aea-0572f69dab31.jpg";
		String bb = "http://img1.zgwanshan.com/";
		
		System.out.println(str.replaceFirst(bb, ""));
		System.out.println(str);
	}
	
//	public  static String getObjectName( String fileFileName, String subFolder)  {
//		String ext=FileUtil.getFileExt(fileFileName);
//		String logoName = DateHelper.date2String(new Date(), "yyyyMMddHHmmss") + StringHelper.getRandStr(4) +ext;;
//		String objectName=(subFolder == null ? "" : subFolder)+"/"+logoName;
//		
//		return objectName;
//	}
	
//	public  static String getFileName( String fileFileName)  {
//		String ext = FileUtil.getFileExt(fileFileName);		
//		String logoName = DateHelper.date2String(new Date(), "yyyyMMddHHmmss") + StringHelper.getRandStr(4)+"." +ext;;		
//		return logoName;
//	}
		
//	public static String getFileName(File file) {
//
//		String logoName = file.getName().substring(0,file.getName().lastIndexOf("."));
//		String ext = file.getName().substring(file.getName().lastIndexOf("."));
//		String fileName = logoName
//				+ DateHelper.date2String(new Date(), "yyyyMMddHHmmss")
//				+ StringHelper.getRandStr(4) + ext;
//
//		return fileName;
//	}
	
	
	/**
	 * 上传图片<br/>
	 * 图片会被上传至用户上下文的attacment文件夹的subFolder子文件夹中<br/>
	 * 
	 * @param file  图片file对象
	 * @param fileFileName 上传的图片原名
	 * @param subFolder  子文件夹名
	 * @return 
	 * @throws Exception 
	 * @since 上传后的本地路径，如:fs:/attachment/goods/2001010101030.jpg<br/>
	 * 
	 */
	
		
/*	public static String replacePath(String path){
		if(StringHelper.isEmpty(path)) return path;
		return path.replaceAll(EopSetting.FILE_STORE_PREFIX, 
				EopSetting.IMG_SERVER_DOMAIN + 
				EopContext.getContext().getContextPath());
	}*/
	
	
	/** * 上传图片并生成缩略图
	 * 图片会被上传至用户上下文的upload文件夹的subFolder子文件夹中<br/>
	 * 
	 * @param file  图片file对象
	 * @param fileFileName 上传的图片原名
	 * @param subFolder  子文件夹名
	 * @param width 缩略图的宽
	 * @param height 缩略图的高
	 * @return 上传后的图版全路径，如:http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg<br/>
	 * 返回值为大小为2的String数组，第一个元素为上传后的原图全路径，第二个为缩略图全路径
 	 */
	
	/*public static String[] upload(File file, String fileFileName, String subFolder, int width,int height ){
		if(file==null || fileFileName==null) throw new IllegalArgumentException("file or filename object is null");
		if(subFolder == null)throw new IllegalArgumentException("subFolder is null");
		String fileName = null;
		String filePath = "";
		String [] path = new String[2];
 
		String ext = FileUtil.getFileExt(fileFileName);
		fileName = DateHelper.date2String(new Date(), "yyyyMMddHHmmss") + StringHelper.getRandStr(4) + "." + ext;
		
		filePath =  EopSetting.IMG_SERVER_PATH + getContextFolder() + "/" + UPLOAD_PATH + "/";
		if(subFolder!=null){
			filePath+=subFolder +"/";
		}
		
		path[0]  = EopSetting.IMG_SERVER_DOMAIN + getContextFolder()+ "/" + UPLOAD_PATH + "/" +(subFolder==null?"":subFolder)+ "/"+fileName;
		filePath += fileName;
		FileUtil.createFile(file, filePath);
		String thumbName= getThumbPath(filePath,"_thumbnail");
	 
		IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator( filePath, thumbName);
		thumbnailCreator.resize(width, height);	
		path[1] = getThumbPath(path[0], "_thumbnail");
		return path;
	}*/
		
	
	/**
	 * 删除某个上传的文件
	 * @param filePath 文件全路径如：http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg
	 */
	public static void deleteFile(String filePath){
//		filePath = filePath.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
		FileUtil.delete(filePath);		
	}	
	
	/**
	 * 删除某个上传的文件
	 * @param filePath 文件全路径如：http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg
	 */
	public static void deleteFileAndThumb(String filePath){
//		filePath = filePath.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
		FileUtil.delete(filePath);		
		FileUtil.delete(getImagePath(filePath, "_thumbnail"));		
	}
	
	/**
	 * 转换图片的名称
	 * @param filePath  如：http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg
	 * @param shortName 
	 * @return
	 */
	public static String getImagePath(String filePath, String shortName) {
		if(StringUtils.isEmpty(filePath)){
			return null;
		}
		if(StringUtils.isEmpty(shortName)){
			return filePath;
		}
		String pattern = "(.*)([\\.])(.*)";
		String thumbPath = "$1" + shortName + "$2$3";

		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(filePath);
		if (m.find()) {
			String s = m.replaceAll(thumbPath);
			return s;
		}
		return null;
	}	

}
