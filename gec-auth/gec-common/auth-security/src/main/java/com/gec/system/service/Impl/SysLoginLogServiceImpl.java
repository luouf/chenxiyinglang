package com.gec.system.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.model.system.SysLoginLog;
import com.gec.model.vo.SysLoginLogQueryVo;
import com.gec.system.mapper.SysLoginLogMapper;
import com.gec.system.service.SysLoginLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {
    
    @Override
    public IPage<SysLoginLog> selectPage(IPage<SysLoginLog> page1, SysLoginLogQueryVo roleQueryVo) {
        IPage<SysLoginLog> iPage = this.baseMapper.selectPage(page1, roleQueryVo);
        return iPage;
    }
    
    @Override
    public void recordLoginLog(String username, Integer status, String ipaddr, String message) {

        SysLoginLog sysLoginLog = new
                SysLoginLog();
        sysLoginLog.setUsername(username);
        sysLoginLog.setStatus(status);
        sysLoginLog.setIpaddr(ipaddr);
        sysLoginLog.setMsg(message);
        this.baseMapper.insert(sysLoginLog);

    }
}
