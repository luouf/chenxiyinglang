package com.gec.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.system.SysLoginLog;
import com.gec.model.vo.SysLoginLogQueryVo;

public interface SysLoginLogService extends IService<SysLoginLog> {
    
    IPage<SysLoginLog> selectPage(IPage<SysLoginLog> page1, SysLoginLogQueryVo roleQueryVo);

    public void recordLoginLog(String username,Integer status,String ipaddr,String message);
}