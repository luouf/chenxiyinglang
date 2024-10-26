package com.gec.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.system.SysOperLog;
import com.gec.model.vo.SysOperLogQueryVo;

public interface SysOperaLogService extends IService<SysOperLog> {
    IPage<SysOperLog> selectPage(IPage<SysOperLog> page1, SysOperLogQueryVo roleQueryVo);
    
}
