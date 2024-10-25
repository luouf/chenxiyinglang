package com.gec.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysOperLog;
import com.gec.model.vo.SysOperLogQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {
    IPage<SysOperLog> selectPage(IPage<SysOperLog> page1, @Param("vo") SysOperLogQueryVo roleQueryVo);
}
