package com.wzbuaa.crm.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Bean类 - 系统配置
 * 
 */
public class Setting implements Serializable {

	private static final long serialVersionUID = 5539786245179492300L;
	public static final String CACHE_NAME = "setting";
	public static final Integer CACHE_KEY = 0;
	
	// 货币种类（人民币、美元、欧元、英磅、加拿大元、澳元、卢布、港币、新台币、韩元、新加坡元、新西兰元、日元、马元、瑞士法郎、瑞典克朗、丹麦克朗、兹罗提、挪威克朗、福林、捷克克朗、葡币）
	public enum CurrencyType {
		CNY, USD, EUR, GBP, CAD, AUD, RUB, HKD, TWD, KRW, SGD, NZD, JPY, MYR, CHF, SEK, DKK, PLZ, NOK, HUF, CSK, MOP
	};
	
	// 小数位精确方式（四舍五入、向上取整、向下取整）
	public enum RoundType {
		roundHalfUp, roundUp, roundDown
	}
	
	// 评论权限（任何访问者、注册会员、已购买会员）
	public enum ReviewAuthority {
		anyone, member, purchased
	}
	
	//  咨询权限（任何访问者、注册会员）
	public enum ConsultationAuthority {
		anyone, member
	}
	
	// 库存预占时间点（下订单、订单付款、订单发货）
	public enum StoreFreezeTime {
		order, payment, ship
	}
	
	// 水印位置（无、随机、左上、右上、居中、左下、右下）
	public enum WatermarkPosition {
		no, random, topLeft, topRight, center, bottomLeft, bottomRight
	}
	
	// 积分获取方式（禁用积分获取、按订单总额计算、为商品单独设置）
	public enum PointType {
		disable, orderAmount, goodsSet
	}
	
	public static final String HOT_SEARCH_SEPARATOR = ",";// 热门搜索分隔符
	public static final String EXTENSION_SEPARATOR = ",";// 文件扩展名分隔符

	public static final String LOGO_UPLOAD_NAME = "logo";// Logo图片文件名称(不包含扩张名)
	public static final String DEFAULT_BIG_GOODS_IMAGE_FILE_NAME = "default_big";// 默认商品图片（大）文件名称(不包含扩展名)
	public static final String DEFAULT_SMALL_GOODS_IMAGE_FILE_NAME = "default_medium";// 默认商品图片（中）文件名称(不包含扩展名)
	public static final String DEFAULT_THUMBNAIL_GOODS_IMAGE_FILE_NAME = "default_thumbnail";// 商品缩略图文件名称(不包含扩展名)
	public static final String WATERMARK_IMAGE_FILE_NAME = "watermark";// 水印图片文件名称(不包含扩展名)
	public static final String UPLOAD_IMAGE_DIR = "/upload/image/";// 图片文件上传目录
	public static final String UPLOAD_IMAGE_ADVERT_JPG = "/upload/image/advert/jpg/";// 图片文件上传目录
	public static final String UPLOAD_IMAGE_ADVERT_OTHER = "/upload/image/advert/other/";// 图片文件上传目录

	public static final String UPLOAD_MEDIA_DIR = "/upload/media/";// 媒体文件上传目录
	public static final String UPLOAD_FILE_DIR = "/upload/file/";// 其它文件上传目录
	
    // ========网站基本信息===================================
    /** 网站名称 **/
    private String name;
    /** 网站logo **/
    private String logo;
    private String hotSearch;// 热门搜索关键词
    /** 域名 **/
    private String domain;
    /** 域名别名 **/
    private String domainAlias;
    /** 资源地址 **/
    private String res_domain;
    /** 资源路径 **/
    private String res_path;
    /** 静态页访问后缀 **/
    private String staticSuffix;
    /** 动态页访问后缀 **/
    private String dynamicSuffix;
    /** 系统cookie识别码 **/
    private String cookie_key;
    /** 公司名称 **/
    private String company;
    /** 网站拥有者姓名 **/
    private String owner_name;
    /** 网站拥有者身份证号 **/
    private String owner_identity;
    /** 电话号码 **/
    private String phone;
    /** 联系地址 **/
    private String address;
    /** 手机号码 **/
    private String mobile;
    /** 网站拥有者电子邮箱 **/
    private String owner_email;
    /** 首页页面关键词 **/
    private String metaKeywords;
    /** 首页页面描述 **/
    private String metaDescription;
    /** 版权信息 **/
    private String copyright;
    /** 备案号 **/
    private String certtext;
    /** 开启静态首页 **/
    private Boolean staticIndex;
    /** 静态页目录 **/
    private String staticDir;
    /** 使用根首页 **/
    private Boolean indexToRoot;
    /** 是否使用相对路径 **/
    private Boolean is_relativePath;
    /** 模板解决方案 **/
    private String solution;
    private String zipCode;// 邮编
    // =======网站显示设置=============================
    /** 商品大图片宽度 **/
    private Integer bigGoodsImageWidth;
    /** 商品大图片高度 **/
    private Integer bigGoodsImageHeight;

    /** 商品小图片宽度 **/
    private Integer smallGoodsImageWidth;
    /** 商品小图片高度 **/
    private Integer smallGoodsImageHeight;

    /** 商品缩略图宽度 **/
    private Integer thumbnailGoodsImageWidth;
    /** 商品缩略图高度 **/
    private Integer thumbnailGoodsImageHeight;
    /** 文章标题图宽度 **/
    private String thumbnailArticleImageWidth;
    /** 文章标题图高度**/
    private String thumbnailArticleImageHeight;
    
    /** 商品默认图片大路径 **/
    private String defaultBigGoodsImage;
    /** 商品默认图片小路径 **/
    private String defaultSmallGoodsImage;
   
    /** 是否开启水印 **/
    private Boolean isWatermark;
    /** 默认缩略图 **/
    private String defaultThumbnailGoodsImage;
    private WatermarkPosition watermarkPosition; //水印位置
    private String watermarkImage; // 水印图片路径
    private Integer watermarkAlpha; //水印透明度
    private Integer watermarkMinWidth; //图片尺寸控制(最小值)
    private int watermarkMinHeight; //图片尺寸控制(最大值)
    private String watermarkFront; //水印文字
    private int watermarkFrontSize; //水印文字大小
    private String watermarkFrontColor; //水印文字颜色
    private int watermarkOffsetX; //水平偏移量
    private int watermarkOffsetY; //垂直偏移量
    private String themeId; //模板ID
    private String shopTheme;//网站样式
    private Boolean isStatic;//是否使用静态页面
    
    // ===============积分设置===========================
    /** 积分兑余额的汇率 **/
    private Integer creditsRate;
    /** 积分兑换开关 **/
    private Boolean creditsExchange;
    /** 积分使用限制（与商品总额的比例） **/
    private BigDecimal creditsLimit;
    
    // ============网站安全设置====================
    /** 前台允许范文的Ip **/
    private String control_front_ips;
    /** 后台允许范文的IP **/
    private String control_admin_ips;
    /** 是否开放注册 **/
    private Boolean isRegister;
    /** 是否自动锁定账号 **/
    private Boolean isLoginFailureLock;
    /** 连续登录失败最大次数 **/
    private Integer loginFailureLockCount;
    /** 自动解锁时间 **/
    private Integer loginFailureLockTime;
    /** 允许上传的文件大小 **/
    private Integer uploadLimit;
    private Boolean isSiteEnabled; //网站是否启用
    /** 关闭原因 **/
    private String close_reason;
    private String uploadFlashExtension; //允许上传的flash文件扩展名
    private String uploadImageExtension;// 允许上传的图片文件扩展名（为空表示不允许上传图片文件）
	private String uploadMediaExtension;// 允许上传的媒体文件扩展名（为空表示不允许上传媒体文件）
	private String uploadFileExtension;// 允许上传的文件扩展名（为空表示不允许上传文件）
	private String imageUploadPath;
	private String flashUploadPath;
	private String mediaUploadPath;
	private String fileUploadPath;
	private String luceneKeyWordsPath;  //首页关键字Lucene索引文件地址
	private String luceneGoodsPath;     //商品Lucene索引文件地址
    // ===============其他设置===========================

    /** 货币种类 **/
    private CurrencyType currencyType;
    /** 货币符号 **/
    private String currencySign;
    /** 货币单位 **/
    private String currencyUnit;
    /** 商品价格精确位数 **/
    private Integer priceScale;
    /** 商品价格精确方式 **/
    private RoundType priceRoundType;
    /** 订单金额精确位数 **/
    private Integer orderScale;
    /** 订单金额精确方式 **/
    private RoundType orderRoundType;
    /** 库存预占时间点 **/
    private StoreFreezeTime storeFreezeTime;
    /** 是否开启评论 **/
    private Boolean isReviewEnabled;
    /** 是否审核评论 **/
    private Boolean isReviewCheck;
    /** 评论权限 **/
    private ReviewAuthority reviewAuthority;
    /** 是否开启咨询 **/
    private Boolean isConsultationEnabled;
    /** 是否审核咨询 **/
    private Boolean isConsultationCheck;
    /** 咨询权限 **/
    private ConsultationAuthority consultationAuthority;
    /** 是否开启含税价 **/
    private Boolean isTaxPriceEnabled;
    /** 税率 **/
    private String taxRate;
    /** 商品库存报警数量 **/
    private Integer storeAlertCount;
    /** 积分获取方式 **/
    private PointType pointType;
    /** 是否打开友情链接注册 **/
    private Boolean isOpenFriendLinkRegist;
    /** 用于为静态系统服务的url **/
    private String serviceUrl;
    /** 团购时限 **/
    private Integer groupBuyLimit;
    /** 订单自动确认收货时限 **/
    private Integer autoConfirm;
    
    // ===============系统设置===========================
    /** 部署路径 **/
    private String contextPath;
    /** 端口号 **/
    private Integer port;
    
    // ===============邮件设置===========================
    /** 发件人邮箱 **/
    private String smtpFromMail;
    /** SMTP服务器地址 **/
    private String smtpHost;
    /** SMTP服务器端口 **/
    private Integer smtpPort;
    /** SMTP用户名 **/
    private String smtpUsername;
    /** SMTP密码 **/
    private String smtpPassword;
    /** HTML自动生成延时 **/
    private Integer buildHtmlDelayTime;
    
	/**
	 * 获得站点url,根据网站设置是否静态化首页来获取首页url
	 * 
	 * @return
	 */
	public String getUrl() {
		return getUrlBuff(true).append("/").toString();
	}
	
	// 获取热门搜索关键词集合
	public List<String> getHotSearchList() {
		return StringUtils.isNotEmpty(hotSearch) ? Arrays.asList(hotSearch.split(HOT_SEARCH_SEPARATOR)) : new ArrayList<String>();
	}
	
	public StringBuilder getUrlBuff(boolean flag){
        StringBuilder stringbuilder = new StringBuilder();
        if(flag || !getIs_relativePath().booleanValue()){
            stringbuilder = (new StringBuilder("http://")).append(getDomain());
            Integer integer = getPort();
            if(integer != null && integer.intValue() != 80 && integer.intValue() != 0){
                stringbuilder.append(":").append(integer);
            }
        }
        stringbuilder.append(getContextPath() == null ? "" : getContextPath());
        return stringbuilder;
    }
    
    /**
     * 获得资源站点的URL。如：http://res.nc138.com 或 http://res.nc138.com:8080/CmsSys
     * 如没有指定资源域名，则和网站访问地址一样。为远程附件做准备。
     * @return
     */
    public StringBuilder getResUrlBuf() {
        if (StringUtils.isBlank(getRes_domain())) {
            return new StringBuilder(getUrl());
        } else {
            return new StringBuilder(getRes_domain());
        }
    }
    
//===========系统生成的get和set=======================================================================
    @NotBlank(message="网站名称为必填项")
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
        return logo;
    }
    
	public void setLogo(String logo) {
        this.logo = logo;
    }
	
	@NotBlank(message="域名为必填项")
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomainAlias() {
		return domainAlias;
	}

	public void setDomainAlias(String domainAlias) {
		if (domainAlias != null)
			domainAlias = domainAlias.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
		this.domainAlias = domainAlias;
	}

	public String getRes_domain() {
        return res_domain;
    }

    public void setRes_domain(String res_domain) {
        this.res_domain = res_domain;
    }

    @NotNull(message="商品图片（大）宽不允许为空!")
    public Integer getBigGoodsImageWidth() {
		return bigGoodsImageWidth;
	}

	public void setBigGoodsImageWidth(Integer bigGoodsImageWidth) {
		this.bigGoodsImageWidth = bigGoodsImageWidth;
	}

	@NotNull(message="商品图片（大）高不允许为空!")
	public Integer getBigGoodsImageHeight() {
		return bigGoodsImageHeight;
	}

	public void setBigGoodsImageHeight(Integer bigGoodsImageHeight) {
		this.bigGoodsImageHeight = bigGoodsImageHeight;
	}
	
	@NotNull(message="商品图片（小）宽不允许为空!")
	public Integer getSmallGoodsImageWidth() {
		return smallGoodsImageWidth;
	}

	public void setSmallGoodsImageWidth(Integer smallGoodsImageWidth) {
		this.smallGoodsImageWidth = smallGoodsImageWidth;
	}

	@NotNull(message="商品图片（小）高不允许为空!")
	public Integer getSmallGoodsImageHeight() {
		return smallGoodsImageHeight;
	}

	public void setSmallGoodsImageHeight(Integer smallGoodsImageHeight) {
		this.smallGoodsImageHeight = smallGoodsImageHeight;
	}

	@NotNull(message="商品缩略图宽不允许为空!")
	public Integer getThumbnailGoodsImageWidth() {
		return thumbnailGoodsImageWidth;
	}

	public void setThumbnailGoodsImageWidth(Integer thumbnailGoodsImageWidth) {
		this.thumbnailGoodsImageWidth = thumbnailGoodsImageWidth;
	}
	
	@NotNull(message="商品缩略图高不允许为空!")
	public Integer getThumbnailGoodsImageHeight() {
		return thumbnailGoodsImageHeight;
	}

	public void setThumbnailGoodsImageHeight(Integer thumbnailGoodsImageHeight) {
		this.thumbnailGoodsImageHeight = thumbnailGoodsImageHeight;
	}

	@NotNull(message="商品缩略图高不允许为空!")
	public String getDefaultBigGoodsImage() {
		return defaultBigGoodsImage;
	}

	public void setDefaultBigGoodsImage(String defaultBigGoodsImage) {
		this.defaultBigGoodsImage = defaultBigGoodsImage;
	}

	public String getDefaultSmallGoodsImage() {
		return defaultSmallGoodsImage;
	}

	public void setDefaultSmallGoodsImage(String defaultSmallGoodsImage) {
		this.defaultSmallGoodsImage = defaultSmallGoodsImage;
	}

	public String getDefaultThumbnailGoodsImage() {
		return defaultThumbnailGoodsImage;
	}

	public void setDefaultThumbnailGoodsImage(String defaultThumbnailGoodsImage) {
		this.defaultThumbnailGoodsImage = defaultThumbnailGoodsImage;
	}

	public String getWatermarkImage() {
		return watermarkImage;
	}

	public void setWatermarkImage(String watermarkImage) {
		this.watermarkImage = watermarkImage;
	}

	public String getRes_path() {
        return res_path;
    }

    public void setRes_path(String res_path) {
        this.res_path = res_path;
    }

    public String getCookie_key() {
        return cookie_key;
    }

    public void setCookie_key(String cookie_key) {
        this.cookie_key = cookie_key;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getOwner_identity() {
        return owner_identity;
    }

    public void setOwner_identity(String owner_identity) {
        this.owner_identity = owner_identity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Email
    public String getOwner_email() {
        return owner_email;
    }

    public void setOwner_email(String owner_email) {
        this.owner_email = owner_email;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }


	public String getCerttext() {
		return certtext;
	}

	public void setCerttext(String certtext) {
		this.certtext = certtext;
	}

	public WatermarkPosition getWatermarkPosition() {
        return watermarkPosition;
    }

    public void setWatermarkPosition(WatermarkPosition watermarkPosition) {
        this.watermarkPosition = watermarkPosition;
    }

    @NotNull(message="水印透明度不允许为空!")
    public Integer getWatermarkAlpha() {
        return watermarkAlpha;
    }

    public void setWatermarkAlpha(Integer watermarkAlpha) {
        this.watermarkAlpha = watermarkAlpha;
    }

    public String getControl_front_ips() {
        return control_front_ips;
    }

    public void setControl_front_ips(String control_front_ips) {
        this.control_front_ips = control_front_ips;
    }

    public String getControl_admin_ips() {
        return control_admin_ips;
    }

    public void setControl_admin_ips(String control_admin_ips) {
        this.control_admin_ips = control_admin_ips;
    }

    @NotNull(message="是否开启自动锁定账号功能不允许为空!")
    public Boolean getIsLoginFailureLock() {
		return isLoginFailureLock;
	}

	public void setIsLoginFailureLock(Boolean isLoginFailureLock) {
		this.isLoginFailureLock = isLoginFailureLock;
	}

	@NotNull(message="连续登录失败最大次数不允许为空!")
	public Integer getLoginFailureLockCount() {
		return loginFailureLockCount;
	}

	public void setLoginFailureLockCount(Integer loginFailureLockCount) {
		this.loginFailureLockCount = loginFailureLockCount;
	}

	@NotNull(message="自动解锁时间不允许为空!")
    public Integer getLoginFailureLockTime() {
		return loginFailureLockTime;
	}

	public void setLoginFailureLockTime(Integer loginFailureLockTime) {
		this.loginFailureLockTime = loginFailureLockTime;
	}

	public void setUploadFlashExtension(String uploadFlashExtension) {
		if (uploadFlashExtension != null)
			uploadFlashExtension = uploadFlashExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
		this.uploadFlashExtension = uploadFlashExtension;
	}

	public void setUploadImageExtension(String uploadImageExtension) {
		if (uploadImageExtension != null)
			uploadImageExtension = uploadImageExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
		this.uploadImageExtension = uploadImageExtension;
	}
	
	public void setUploadMediaExtension(String uploadMediaExtension) {
		if (uploadMediaExtension != null)
			uploadMediaExtension = uploadMediaExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
		this.uploadMediaExtension = uploadMediaExtension;
	}

	public String getUploadFlashExtension() {
		return uploadFlashExtension;
	}

	public String getUploadImageExtension() {
		return uploadImageExtension;
	}

	public String getUploadMediaExtension() {
		return uploadMediaExtension;
	}

	public String getUploadFileExtension() {
		return uploadFileExtension;
	}

	public void setUploadFileExtension(String uploadFileExtension) {
		if (uploadFileExtension != null)
			uploadFileExtension = uploadFileExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
		this.uploadFileExtension = uploadFileExtension;
	}

	public String getImageUploadPath() {
		return imageUploadPath;
	}

	public void setImageUploadPath(String imageUploadPath) {
		if (imageUploadPath != null) {
			if (!imageUploadPath.startsWith("/")){
				imageUploadPath = new StringBuilder("/").append(imageUploadPath).toString();
			}
			if (!imageUploadPath.endsWith("/")){
				imageUploadPath = new StringBuilder(imageUploadPath).append("/").toString();
			}
		}
		this.imageUploadPath = imageUploadPath;
	}

	public String getFlashUploadPath() {
		return flashUploadPath;
	}

	public void setFlashUploadPath(String flashUploadPath) {
		if (flashUploadPath != null) {
			if (!flashUploadPath.startsWith("/")){
				flashUploadPath = new StringBuilder("/").append(flashUploadPath).toString();
			}
			if (!flashUploadPath.endsWith("/")){
				flashUploadPath = new StringBuilder(flashUploadPath).append("/").toString();
			}
		}
		this.flashUploadPath = flashUploadPath;
	}

	public String getMediaUploadPath() {
		return mediaUploadPath;
	}

	public void setMediaUploadPath(String mediaUploadPath) {
		if (mediaUploadPath != null) {
			if (!mediaUploadPath.startsWith("/")){
				mediaUploadPath = new StringBuilder("/").append(mediaUploadPath).toString();
			}
			if (!mediaUploadPath.endsWith("/")){
				mediaUploadPath = new StringBuilder(mediaUploadPath).append("/").toString();
			}
		}
		this.mediaUploadPath = mediaUploadPath;
	}

	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		if (fileUploadPath != null) {
			if (!fileUploadPath.startsWith("/")){
				fileUploadPath = new StringBuilder("/").append(fileUploadPath).toString();
			}
			if (!fileUploadPath.endsWith("/")){
				fileUploadPath = new StringBuilder(fileUploadPath).append("/").toString();
			}
		}
		this.fileUploadPath = fileUploadPath;
	}

	public String getLuceneKeyWordsPath() {
		return luceneKeyWordsPath;
	}

	public void setLuceneKeyWordsPath(String luceneKeyWordsPath) {
		this.luceneKeyWordsPath = luceneKeyWordsPath;
	}

	public String getLuceneGoodsPath() {
		return luceneGoodsPath;
	}

	public void setLuceneGoodsPath(String luceneGoodsPath) {
		this.luceneGoodsPath = luceneGoodsPath;
	}

	public String getClose_reason() {
        return close_reason;
    }

    public void setClose_reason(String close_reason) {
        this.close_reason = close_reason;
    }

    @NotBlank(message="货币种类不允许为空!")
    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    @NotBlank(message="货币符号不允许为空!")
    public String getCurrencySign() {
        return currencySign;
    }

    public void setCurrencySign(String currencySign) {
        this.currencySign = currencySign;
    }

    @NotBlank(message="货币单位不允许为空!")
    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    @NotBlank(message="商品价格精确位数不允许为空!")
    @Size(min = 0, max = 14, message="长度应该介于0和14之间")
    public Integer getPriceScale() {
        return priceScale;
    }

    public void setPriceScale(Integer priceScale) {
        this.priceScale = priceScale;
    }

    @NotBlank(message="商品价格精确方式不允许为空!")
    public RoundType getPriceRoundType() {
        return priceRoundType;
    }

    public void setPriceRoundType(RoundType priceRoundType) {
        this.priceRoundType = priceRoundType;
    }

    @NotBlank(message="订单金额精确位数不允许为空!")
    @Size(min=0, max=4, message="订单金额精确位数必须为零或小于4!")
    public Integer getOrderScale() {
        return orderScale;
    }

    public void setOrderScale(Integer orderScale) {
        this.orderScale = orderScale;
    }

    @NotBlank(message="订单金额精确方式不允许为空!")
    public RoundType getOrderRoundType() {
        return orderRoundType;
    }

    public void setOrderRoundType(RoundType orderRoundType) {
        this.orderRoundType = orderRoundType;
    }

    @NotBlank(message="库存预占时间点不允许为空!")
    public StoreFreezeTime getStoreFreezeTime() {
		return storeFreezeTime;
	}

	public void setStoreFreezeTime(StoreFreezeTime storeFreezeTime) {
		this.storeFreezeTime = storeFreezeTime;
	}

    public Boolean getIsReviewEnabled() {
		return isReviewEnabled;
	}

	public void setIsReviewEnabled(Boolean isReviewEnabled) {
		this.isReviewEnabled = isReviewEnabled;
	}

	public Boolean getIsReviewCheck() {
		return isReviewCheck;
	}

	public void setIsReviewCheck(Boolean isReviewCheck) {
		this.isReviewCheck = isReviewCheck;
	}

	public ReviewAuthority getReviewAuthority() {
		return reviewAuthority;
	}

	public void setReviewAuthority(ReviewAuthority reviewAuthority) {
		this.reviewAuthority = reviewAuthority;
	}

	public Boolean getIsConsultationEnabled() {
		return isConsultationEnabled;
	}

	public void setIsConsultationEnabled(Boolean isConsultationEnabled) {
		this.isConsultationEnabled = isConsultationEnabled;
	}

	public Boolean getIsConsultationCheck() {
		return isConsultationCheck;
	}

	public void setIsConsultationCheck(Boolean isConsultationCheck) {
		this.isConsultationCheck = isConsultationCheck;
	}

	public ConsultationAuthority getConsultationAuthority() {
		return consultationAuthority;
	}

	public void setConsultationAuthority(ConsultationAuthority consultationAuthority) {
		this.consultationAuthority = consultationAuthority;
	}

	public Boolean getIsTaxPriceEnabled() {
		return isTaxPriceEnabled;
	}

	public void setIsTaxPriceEnabled(Boolean isTaxPriceEnabled) {
		this.isTaxPriceEnabled = isTaxPriceEnabled;
	}

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

	@NotBlank(message="商品库存报警数量不允许为空!")
	public Integer getStoreAlertCount() {
		return storeAlertCount;
	}

	public void setStoreAlertCount(Integer storeAlertCount) {
		this.storeAlertCount = storeAlertCount;
	}

    public PointType getPointType() {
		return pointType;
	}

	public void setPointType(PointType pointType) {
		this.pointType = pointType;
	}

	public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @NotNull(message="文件上传最大值不允许为空!")
    public Integer getUploadLimit() {
        return uploadLimit;
    }

    public void setUploadLimit(Integer uploadLimit) {
        this.uploadLimit = uploadLimit;
    }

	public String getThumbnailArticleImageWidth() {
        return thumbnailArticleImageWidth;
    }

    public void setThumbnailArticleImageWidth(String thumbnailArticleImageWidth) {
        this.thumbnailArticleImageWidth = thumbnailArticleImageWidth;
    }

    public String getThumbnailArticleImageHeight() {
        return thumbnailArticleImageHeight;
    }

    public void setThumbnailArticleImageHeight(String thumbnailArticleImageHeight) {
        this.thumbnailArticleImageHeight = thumbnailArticleImageHeight;
    }

    @Email
	public String getSmtpFromMail() {
		return smtpFromMail;
	}

	public void setSmtpFromMail(String smtpFromMail) {
		this.smtpFromMail = smtpFromMail;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpUsername() {
		return smtpUsername;
	}

	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public Boolean getStaticIndex() {
		return staticIndex;
	}

	public void setStaticIndex(Boolean staticIndex) {
		this.staticIndex = staticIndex;
	}

	public String getStaticDir() {
		return staticDir;
	}

	public void setStaticDir(String staticDir) {
		this.staticDir = staticDir;
	}

	public Boolean getIndexToRoot() {
		return indexToRoot;
	}

	public void setIndexToRoot(Boolean indexToRoot) {
		this.indexToRoot = indexToRoot;
	}

	public String getStaticSuffix() {
		return staticSuffix;
	}

	public void setStaticSuffix(String staticSuffix) {
		this.staticSuffix = staticSuffix;
	}

	public String getDynamicSuffix() {
		return dynamicSuffix;
	}

	public void setDynamicSuffix(String dynamicSuffix) {
		this.dynamicSuffix = dynamicSuffix;
	}

	public Boolean getIs_relativePath() {
		return is_relativePath;
	}

	public void setIs_relativePath(Boolean is_relativePath) {
		this.is_relativePath = is_relativePath;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public Boolean getIsWatermark() {
		return isWatermark;
	}

	public void setIsWatermark(Boolean isWatermark) {
		this.isWatermark = isWatermark;
	}

	public int getWatermarkMinWidth() {
		return watermarkMinWidth;
	}

	public void setWatermarkMinWidth(int watermarkMinWidth) {
		this.watermarkMinWidth = watermarkMinWidth;
	}

	public int getWatermarkMinHeight() {
		return watermarkMinHeight;
	}

	public void setWatermarkMinHeight(int watermarkMinHeight) {
		this.watermarkMinHeight = watermarkMinHeight;
	}

	public void setWatermarkOffsetX(int watermarkOffsetX) {
		this.watermarkOffsetX = watermarkOffsetX;
	}

	public void setWatermarkOffsetY(int watermarkOffsetY) {
		this.watermarkOffsetY = watermarkOffsetY;
	}

	public String getWatermarkFront() {
		return watermarkFront;
	}

	public void setWatermarkFront(String watermarkFront) {
		this.watermarkFront = watermarkFront;
	}

	public String getWatermarkFrontColor() {
		return watermarkFrontColor;
	}

	public void setWatermarkFrontColor(String watermarkFrontColor) {
		this.watermarkFrontColor = watermarkFrontColor;
	}

	public int getWatermarkOffsetX() {
		return watermarkOffsetX;
	}

	public int getWatermarkOffsetY() {
		return watermarkOffsetY;
	}

	public int getWatermarkFrontSize() {
		return watermarkFrontSize;
	}

	public void setWatermarkFrontSize(int watermarkFrontSize) {
		this.watermarkFrontSize = watermarkFrontSize;
	}

	public Boolean getIsOpenFriendLinkRegist() {
		return isOpenFriendLinkRegist;
	}

	public void setIsOpenFriendLinkRegist(Boolean isOpenFriendLinkRegist) {
		this.isOpenFriendLinkRegist = isOpenFriendLinkRegist;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getHotSearch() {
		return hotSearch;
	}

	public void setHotSearch(String hotSearch) {
		this.hotSearch = hotSearch;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@NotNull(message="是否开放注册不允许为空!")
	public Boolean getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(Boolean isRegister) {
		this.isRegister = isRegister;
	}

	@NotNull(message="HTML自动生成延时不允许为空!")
	public Integer getBuildHtmlDelayTime() {
		return buildHtmlDelayTime;
	}

	public void setBuildHtmlDelayTime(Integer buildHtmlDelayTime) {
		this.buildHtmlDelayTime = buildHtmlDelayTime;
	}

	public void setWatermarkMinWidth(Integer watermarkMinWidth) {
		this.watermarkMinWidth = watermarkMinWidth;
	}

	public String getThemeId() {
		return themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	public String[] getUploadImageExtensions() {
		return StringUtils.split(uploadImageExtension, ",");
	}
	
	public String[] getUploadMediaExtensions() {
		return StringUtils.split(uploadMediaExtension, ",");
	}

	public String[] getUploadFileExtensions() {
		return StringUtils.split(uploadFileExtension, ",");
	}

	public String[] getUploadFlashExtensions() {
		return StringUtils.split(uploadFlashExtension, ",");
	}

	public String getShopTheme() {
		return shopTheme;
	}

	public void setShopTheme(String shopTheme) {
		this.shopTheme = shopTheme;
	}

	//价格处理精度
	public BigDecimal setScale(BigDecimal amount) {
		if (amount == null){
			return null;
		}
		int roundingMode;
		if (getPriceRoundType() == RoundType.roundUp){
			roundingMode = 0;
		} else {
			if (getPriceRoundType() == RoundType.roundDown){
				roundingMode = 1;
			} else {
				roundingMode = 4;
			}
		}
		return amount.setScale(getPriceScale().intValue(), roundingMode);
	}

	public Integer getGroupBuyLimit() {
		return groupBuyLimit;
	}

	public void setGroupBuyLimit(Integer groupBuyLimit) {
		this.groupBuyLimit = groupBuyLimit;
	}

	public Integer getCreditsRate() {
		return creditsRate;
	}

	public void setCreditsRate(Integer creditsRate) {
		this.creditsRate = creditsRate;
	}

	public Boolean getCreditsExchange() {
		return creditsExchange;
	}

	public void setCreditsExchange(Boolean creditsExchange) {
		this.creditsExchange = creditsExchange;
	}

	public BigDecimal getCreditsLimit() {
		return creditsLimit;
	}

	public void setCreditsLimit(BigDecimal creditsLimit) {
		this.creditsLimit = creditsLimit;
	}

	public Boolean getIsStatic() {
		return isStatic;
	}

	public void setIsStatic(Boolean isStatic) {
		this.isStatic = isStatic;
	}

	@NotNull(message="自动确认收货时限不允许为空!")
	public Integer getAutoConfirm() {
		return autoConfirm;
	}

	public void setAutoConfirm(Integer autoConfirm) {
		this.autoConfirm = autoConfirm;
	}

	public Boolean getIsSiteEnabled() {
		return isSiteEnabled;
	}

	public void setIsSiteEnabled(Boolean isSiteEnabled) {
		this.isSiteEnabled = isSiteEnabled;
	}
}
