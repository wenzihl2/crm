package com.wzbuaa.crm.controller.admin.monitor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Statistics;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wzbuaa.crm.controller.admin.BaseController;
import com.wzbuaa.crm.util.PrettyMemoryUtils;

import framework.Message;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-8 下午5:17
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/admin/monitor/ehcache")
@RequiresPermissions("monitor:ehcache:*")
public class EhcacheMonitorController extends BaseController {

    @Autowired
    private CacheManager cacheManager;

    private void sort(final String[] cacheNames, final CacheManager cacheManager, final String sort) {
        Arrays.sort(cacheNames, new Comparator<String>() {
            public int compare(String n1, String n2) {
                Statistics s1 = cacheManager.getCache(n1).getStatistics();
                Statistics s2 = cacheManager.getCache(n2).getStatistics();
                if("hitCount".equals(sort)) {
                    double n1HitPercent = 1.0 * s1.getCacheHits() / Math.max(s1.getCacheHits() + s1.getCacheMisses(), 1);
                    double n2HitPercent = 1.0 * s2.getCacheHits() / Math.max(s2.getCacheHits() + s2.getCacheMisses(), 1);
                    return -Double.compare(n1HitPercent, n2HitPercent);
                } else if("objectCount".equals(sort)) {
                    return -Long.valueOf(s1.getObjectCount()).compareTo(Long.valueOf(s2.getObjectCount()));
                }
                return -n1.compareTo(n2);
            }

        });
    }
    
    @RequestMapping()
    public String index(Model model, String sort) {
        String[] cacheNames = cacheManager.getCacheNames();
        sort(cacheNames, cacheManager, sort);
        model.addAttribute("cacheNames", cacheNames);
        model.addAttribute("cacheManager", cacheManager);
        return viewName("index");
    }

    @RequestMapping("{cacheName}/details")
    public String details(
            @PathVariable("cacheName") String cacheName,
            @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
            Model model) {

        model.addAttribute("cacheName", cacheName);
        List allKeys = cacheManager.getCache(cacheName).getKeys();
        searchText = searchText == null ? "" : searchText;
        List showKeys = Lists.newArrayList();
        for(Object key : allKeys.toArray()) {
            if(key.toString().contains(searchText)) {
                showKeys.add(key);
            }
        }
        model.addAttribute("keys", showKeys);

        return viewName("details");
    }

    @RequestMapping("{cacheName}/{key}/details")
    @ResponseBody
    public Object keyDetail(
            @PathVariable("cacheName") String cacheName,
            @PathVariable("key") String key,
            Model model) {

        Element element = cacheManager.getCache(cacheName).get(key);


        String dataPattern = "yyyy-MM-dd hh:mm:ss";
        Map<String, Object> data = Maps.newHashMap();
        data.put("objectValue", element.getObjectValue().toString());
        data.put("size", PrettyMemoryUtils.prettyByteSize(element.getSerializedSize()));
        data.put("hitCount", element.getHitCount());

        Date latestOfCreationAndUpdateTime = new Date(element.getLatestOfCreationAndUpdateTime());
        data.put("latestOfCreationAndUpdateTime", DateFormatUtils.format(latestOfCreationAndUpdateTime, dataPattern));
        Date lastAccessTime = new Date(element.getLastAccessTime());
        data.put("lastAccessTime", DateFormatUtils.format(lastAccessTime, dataPattern));
        if(element.getExpirationTime() == Long.MAX_VALUE) {
            data.put("expirationTime", "不过期");
        } else {
            Date expirationTime = new Date(element.getExpirationTime());
            data.put("expirationTime", DateFormatUtils.format(expirationTime, dataPattern));
        }

        data.put("timeToIdle", element.getTimeToIdle());
        data.put("timeToLive", element.getTimeToLive());
        data.put("version", element.getVersion());

        return data;

    }


    @RequestMapping("{cacheName}/{key}/delete")
    @ResponseBody
    public Object delete(
            @PathVariable("cacheName") String cacheName,
            @PathVariable("key") String key
    ) {

        Cache cache = cacheManager.getCache(cacheName);

        cache.remove(key);

        return "操作成功！";

    }

    @RequestMapping("{cacheName}/clear")
    @ResponseBody
    public Message clear(@PathVariable("cacheName") String cacheName) {

        Cache cache = cacheManager.getCache(cacheName);
        cache.clearStatistics();
        cache.removeAll();

        return successMessage;

    }

}