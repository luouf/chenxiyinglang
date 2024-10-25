package com.gec.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.model.system.SysUser;
import com.gec.model.vo.RouterVo;
import com.gec.model.vo.SysUserQueryVo;
import com.gec.system.mapper.SysUserMapper;
import com.gec.system.service.SysMenuService;
import com.gec.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 罗
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 * @createDate 2024-09-23 13:55:57
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysMenuService sysMenuService;
    @Override
    public IPage<SysUser> selectPage(IPage<SysUser> page1, SysUserQueryVo roleQueryVo) {
        IPage<SysUser> iPage = this.baseMapper.selectPage(page1, roleQueryVo);
        return iPage;
    }
    @Override
    public SysUser getUserInfoUserName(String username) {
        
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("username",username);
        
        SysUser sysUser = this.baseMapper.selectOne(queryWrapper);
        
        return sysUser;
    }
    @Override
    public Map<String, Object> getUserInfo(String username) {
        
        Map<String, Object> map = new HashMap<>();
        SysUser sysUser = this.getUserInfoUserName(username);
        
        //根据用户id获取菜单权限值
        List<RouterVo> routerVoList = sysMenuService.findUserMenuList(sysUser.getId());
        //根据用户id获取用户按钮权限
        List<String> permsList = sysMenuService.findUserPermsList(sysUser.getId());
        
        //当前权限控制使用不到，我们暂时忽略
        map.put("name", sysUser.getName());
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("roles",  "[admin]");
        
        
        map.put("buttons", permsList);
        map.put("routers", routerVoList);
        return map;
    }
    
}




