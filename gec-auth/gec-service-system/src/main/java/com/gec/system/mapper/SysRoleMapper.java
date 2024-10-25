package com.gec.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysRole;
import com.gec.model.vo.SysRoleQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    
    IPage<SysRole> selectPage(IPage<SysRole> page1, @Param("vo") SysRoleQueryVo roleQueryVo);
    
}