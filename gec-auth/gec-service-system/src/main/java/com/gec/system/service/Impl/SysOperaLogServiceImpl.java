package com.gec.system.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.model.system.SysOperLog;
import com.gec.model.vo.SysOperLogQueryVo;
import com.gec.system.mapper.SysOperLogMapper;
import com.gec.system.service.SysOperaLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("SysOperaLogService")
@Transactional
public class SysOperaLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperaLogService {
    @Override
    public IPage<SysOperLog> selectPage(IPage<SysOperLog> page1, SysOperLogQueryVo roleQueryVo) {
        IPage<SysOperLog> iPage = this.baseMapper.selectPage(page1, roleQueryVo);
        return iPage;
    }
}
