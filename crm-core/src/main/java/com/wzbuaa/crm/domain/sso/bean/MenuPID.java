package com.wzbuaa.crm.domain.sso.bean;

import java.io.Serializable;

/**
 * 界面是那个使用的菜单对象
 */
@SuppressWarnings("serial")
public class MenuPID implements Serializable {
    
    private Long id;
    private Long pId;
    private String name;
    private String identity;
    private String icon;
    private String url;
    private String html;
    private boolean open;
    private boolean root;
    private boolean isParent;
    private boolean nocheck = false;

    public MenuPID(Long id, Long pId, String name, String identity, 
    		String icon, String url, String html) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.identity = identity;
        this.icon = icon;
        this.url = url;
        this.html = html;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

    public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	@Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
