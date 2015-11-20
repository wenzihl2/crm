package com.wzbuaa.crm.domain.sso.bean;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 界面是那个使用的菜单对象
 */
@SuppressWarnings("serial")
public class MenuChild implements Serializable {
    
    private Long id;
    private String name;
    private String icon;
    private String url;
    private boolean open;
    private boolean root;
    private boolean isParent;
    private boolean nocheck = false;

    private List<MenuChild> children;

    public MenuChild(Long id, String name, String icon, String url) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<MenuChild> getChildren() {
        if (children == null) {
            children = Lists.newArrayList();
        }
        return children;
    }

    public void setChildren(List<MenuChild> children) {
        this.children = children;
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

	/**
     * @return
     */
    public boolean isHasChildren() {
        return !getChildren().isEmpty();
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", url='" + url + '\'' +
                ", children=" + children +
                '}';
    }
}
