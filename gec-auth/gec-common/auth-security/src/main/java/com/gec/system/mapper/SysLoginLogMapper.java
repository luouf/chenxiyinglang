package com.gec.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysLoginLog;
import com.gec.model.vo.SysLoginLogQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {
    IPage<SysLoginLog> selectPage(IPage<SysLoginLog> page1, @Param("vo") SysLoginLogQueryVo roleQueryVo);
    
}