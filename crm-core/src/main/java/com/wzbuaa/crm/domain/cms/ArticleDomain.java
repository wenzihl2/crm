package com.wzbuaa.crm.domain.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.domain.base.DictionaryDomain;

/**
 * 文章实体
 * @author zhenglong 2015-11-09
 */
@SuppressWarnings("serial")
@Entity
@Table(name="cms_article")
public class ArticleDomain extends BaseEntity<Long> {

	private static String path;//静态路径
	private String title;// 标题
	private String author;// 作者
	private String content;// 用于文章接受内容
	private String source;//来源
	private String outerUrl; //外部链接
	private String metaKeywords;//页面关键词
	private String metaDescription;//页面描述
	private String related_ids; //相关文章
	private Boolean isPublication = Boolean.FALSE;// 是否发布
	private Boolean isTop = Boolean.FALSE;// 固顶级别
	private Date releaseDate; // 发布时间
	private Boolean isDisabled = Boolean.FALSE; // 是否禁用
	private Boolean isRecommend = Boolean.FALSE;// 是否为推荐文章
	private DictionaryDomain category;// 文章所属分类	
	
	@Column(nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Column(length = 10000)
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOuterUrl() {
		return outerUrl;
	}

	public void setOuterUrl(String outerUrl) {
		this.outerUrl = outerUrl;
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

	public String getRelated_ids() {
		return related_ids;
	}

	public void setRelated_ids(String related_ids) {
		this.related_ids = related_ids;
	}
	
	@Column(columnDefinition="bit(1) default 0")
	public Boolean getIsPublication() {
		return isPublication;
	}

	public void setIsPublication(Boolean isPublication) {
		this.isPublication = isPublication;
	}
	
	@Column(columnDefinition="bit(1) default 0")
	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	@Column(columnDefinition="bit(1) default 0")
	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	@Column(columnDefinition="bit(1) default 0")
	public Boolean getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}
	
	public static String getPath() {
		return path;
	}

	public static void setPath(String path) {
		ArticleDomain.path = path;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public DictionaryDomain getCategory() {
		return category;
	}

	public void setCategory(DictionaryDomain category) {
		this.category = category;
	}

//	static {
//		try {
//			File file = (new ClassPathResource("/wsshop.xml")).getFile();
//			Document document = new SAXReader().read(file);
//			Element element = (Element)document.selectSingleNode("/wsshop/template[@id='articleContent']");
//			path = element.attributeValue("staticPath");
//		} catch (Exception exception) {
//			exception.printStackTrace();
//		}
//	}
}
