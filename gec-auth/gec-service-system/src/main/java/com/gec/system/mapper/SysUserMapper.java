package com.gec.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.model.vo.SysUserQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    IPage<SysUser> selectPage(IPage<SysUser> page1, @Param("vo") SysUserQueryVo roleQueryVo);
    
}




