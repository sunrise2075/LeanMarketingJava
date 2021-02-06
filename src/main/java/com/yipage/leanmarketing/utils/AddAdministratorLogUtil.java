package com.yipage.leanmarketing.utils;

import com.yipage.leanmarketing.model.AdministratorLog;

import java.util.Date;

/**
 * 添加日志工具类
 */
public class AddAdministratorLogUtil {


    public static AdministratorLog add(String content, Integer administratorId, String username, String operateIp, Integer operateType) {
        //添加一个日志记录
        AdministratorLog administratorLog = new AdministratorLog();
        administratorLog.setUpdateTime(new Date());
        administratorLog.setCreateTime(new Date());
        administratorLog.setOperateContent(content);
        administratorLog.setOperateId(administratorId);
        administratorLog.setOperateIp(operateIp);
        administratorLog.setOperateName(username);
        administratorLog.setOperateType(operateType);
        return administratorLog;
    }
}
