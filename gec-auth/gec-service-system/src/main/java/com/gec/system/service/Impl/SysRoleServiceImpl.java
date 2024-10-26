package com.gec.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.model.system.SysRole;
import com.gec.model.system.SysUserRole;
import com.gec.model.vo.AssginRoleVo;
import com.gec.model.vo.SysRoleQueryVo;
import com.gec.system.mapper.SysRoleMapper;
import com.gec.system.mapper.SysUserRoleMapper;
import com.gec.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    @Override
    public IPage<SysRole> selectPage(IPage<SysRole> page1, SysRoleQueryVo roleQueryVo) {
        IPage<SysRole> iPage =   this.baseMapper.selectPage(page1,roleQueryVo);
        return iPage;
    }
    @Override
    public Map<String, Object> getRolesByUserId(Long userId){
        List<SysRole> roles = this.baseMapper.selectList(null);
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<SysUserRole> sysRoles = this.sysUserRoleMapper.selectList(queryWrapper);
        List<Long> userRoleIds=new ArrayList<>();
        for (SysUserRole sysRole : sysRoles) {
            userRoleIds.add(sysRole.getRoleId());
        }
        Map<String,Object> result=new HashMap<>();
        result.put("allRoles",roles);
        result.put("userRoleIds",userRoleIds);
        return result;
    }
    
    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",assginRoleVo.getUserId());
        this.sysUserRoleMapper.delete(queryWrapper);
        List<Long> result = assginRoleVo.getRoleIdList();
        for (Long aLong : result) {
            if(aLong!=null){
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(aLong);
                sysUserRole.setUserId(assginRoleVo.getUserId());
                this.sysUserRoleMapper.insert(sysUserRole);
            }
        }
    }
    
    
}
