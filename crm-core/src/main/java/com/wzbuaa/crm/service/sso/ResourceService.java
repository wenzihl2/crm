package com.wzbuaa.crm.service.sso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wzbuaa.crm.component.service.BaseTreeableService;
import com.wzbuaa.crm.domain.sso.Resource;
import com.wzbuaa.crm.domain.sso.Resource.ResourceType;
import com.wzbuaa.crm.domain.sso.bean.MenuChild;
import com.wzbuaa.crm.domain.sso.bean.MenuPID;
import com.wzbuaa.crm.domain.sso.user.S_userDomain;
import com.wzbuaa.crm.repository.sso.ResourceRepository;

import framework.entity.search.SearchOperator;
import framework.entity.search.Searchable;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class ResourceService extends BaseTreeableService<Resource, Long> {

//    @Autowired
//    private UserAuthService userAuthService;

	private ResourceRepository getResourceRepository() {
		 return (ResourceRepository) baseRepository;
	}
	
    /**
     * 得到真实的资源标识  即 父亲:儿子
     * @param resource
     * @return
     */
    public String findActualResourceIdentity(Resource resource) {

        if(resource == null) {
            return null;
        }

        StringBuilder s = new StringBuilder(resource.getIdentity());

        boolean hasResourceIdentity = !StringUtils.isEmpty(resource.getIdentity());

        Resource parent = findOne(resource.getParentId());
        while(parent != null) {
            if(!StringUtils.isEmpty(parent.getIdentity())) {
                s.insert(0, parent.getIdentity() + ":");
                hasResourceIdentity = true;
            }
            parent = findOne(parent.getParentId());
        }

        //如果用户没有声明 资源标识  且父也没有，那么就为空
        if(!hasResourceIdentity) {
            return "";
        }


        //如果最后一个字符是: 因为不需要，所以删除之
        int length = s.length();
        if(length > 0 && s.lastIndexOf(":") == length - 1) {
            s.deleteCharAt(length - 1);
        }

        //如果有儿子 最后拼一个*
        boolean hasChildren = false;
        for(Resource r : findAll()) {
            if(resource.getId().equals(r.getParentId())) {
                hasChildren = true;
                break;
            }
        }
        if(hasChildren) {
            s.append(":*");
        }

        return s.toString();
    }
    
    public Resource findByUrl(String url) {
    	return getResourceRepository().findByUrl(url);
    }

    public List<Resource> findBarButton(S_userDomain user, Resource resource) {
        Searchable searchable =
                Searchable.newSearchable()
                        .addSearchFilter("show", SearchOperator.eq, true)
                        .addSearchFilter("parentId", SearchOperator.eq, resource.getId())
                        .addSearchFilter("type", SearchOperator.eq, ResourceType.Operate)
                        .addSort(new Sort(Sort.Direction.ASC, "parentId", "priority"));

        List<Resource> resources = findAllWithSort(searchable);
        return resources;
    }
    
    public List<Resource> findMenusPID(S_userDomain user) {
        Searchable searchable =
                Searchable.newSearchable()
                        .addSearchFilter("show", SearchOperator.eq, true)
                        .addSearchFilter("type", SearchOperator.eq, ResourceType.Menu)
                        .addSort(new Sort(Sort.Direction.ASC, "parentId", "priority"));

        List<Resource> resources = findAllWithSort(searchable);
        return resources;
    }
    
    public List<MenuChild> findMenus(S_userDomain user) {
        Searchable searchable =
                Searchable.newSearchable()
                        .addSearchFilter("show", SearchOperator.eq, true)
                        .addSort(new Sort(Sort.Direction.ASC, "parentId", "priority"));
        List<Resource> resources = findAllWithSort(searchable);

//        Set<String> userPermissions = userAuthService.findStringPermissions(user);
//
//        Iterator<Resource> iter = resources.iterator();
//        while (iter.hasNext()) {
//            if (!hasPermission(iter.next(), userPermissions)) {
//                iter.remove();
//            }
//        }

        return convertToMenuChilds(resources);
    }

    private boolean hasPermission(Resource resource, Set<String> userPermissions) {
        String actualResourceIdentity = findActualResourceIdentity(resource);
        if (StringUtils.isEmpty(actualResourceIdentity)) {
            return true;
        }

        for (String permission : userPermissions) {
            if (hasPermission(permission, actualResourceIdentity)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasPermission(String permission, String actualResourceIdentity) {

        //得到权限字符串中的 资源部分，如a:b:create --->资源是a:b
        String permissionResourceIdentity = permission.substring(0, permission.lastIndexOf(":"));

        //如果权限字符串中的资源 是 以资源为前缀 则有权限 如a:b 具有a:b的权限
        if(permissionResourceIdentity.startsWith(actualResourceIdentity)) {
            return true;
        }


        //模式匹配
        WildcardPermission p1 = new WildcardPermission(permissionResourceIdentity);
        WildcardPermission p2 = new WildcardPermission(actualResourceIdentity);

        return p1.implies(p2) || p2.implies(p1);
    }

    @SuppressWarnings("unchecked")
    public static List<MenuPID> convertToMenuPID(List<Resource> resources) {
        if (resources.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        List<MenuPID> list = new ArrayList<MenuPID>();
        for (int i=0; i<resources.size(); i++) {
            Resource resource = resources.get(i);
            list.add(convertToMenuPID(resource));
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public static List<MenuChild> convertToMenuChilds(List<Resource> resources) {

        if (resources.size() == 0) {
            return Collections.EMPTY_LIST;
        }

        MenuChild root = convertToMenuChild(resources.remove(resources.size() - 1));

        recursiveMenuChild(root, resources);
        List<MenuChild> menus = root.getChildren();
        removeNoLeafMenu(menus);

        return menus;
    }

    private static void removeNoLeafMenu(List<MenuChild> menus) {
        if (menus.size() == 0) {
            return;
        }
        for (int i = menus.size() - 1; i >= 0; i--) {
        	MenuChild m = menus.get(i);
            removeNoLeafMenu(m.getChildren());
            if (!m.isHasChildren() && StringUtils.isEmpty(m.getUrl())) {
                menus.remove(i);
            }
        }
    }

    private static void recursiveMenuChild(MenuChild menu, List<Resource> resources) {
        for (int i = resources.size() - 1; i >= 0; i--) {
            Resource resource = resources.get(i);
            if (resource.getParentId().equals(menu.getId())) {
                menu.getChildren().add(convertToMenuChild(resource));
                resources.remove(i);
            }
        }

        for (MenuChild subMenu : menu.getChildren()) {
            recursiveMenuChild(subMenu, resources);
        }
    }

    private static MenuChild convertToMenuChild(Resource resource) {
        return new MenuChild(resource.getId(), resource.getName(), resource.getIcon(), resource.getUrl());
    }
    
    private static MenuPID convertToMenuPID(Resource resource) {
        return new MenuPID(resource.getId(), resource.getParentId(), 
        		resource.getName(), resource.getIdentity(), 
        		resource.getIcon(), resource.getUrl(), resource.getHtml());
    }

}
