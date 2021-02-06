package com.yipage.leanmarketing.utils;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页返回工具类
 */
public class MapUtil {

    public static Map PageResult(PageInfo pageInfo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 200);
        map.put("status", 1);
        map.put("data", pageInfo.getList());
        map.put("count", pageInfo.getTotal());
        return map;
    }


}
