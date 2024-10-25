package com.gec.system.controller;

import com.gec.system.aop.Log;
import com.gec.model.enums.BusinessType;
import com.gec.model.system.SysMenu;
import com.gec.model.vo.AssginMenuVo;
import com.gec.system.service.SysMenuService;
import com.gec.system.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "菜单管理控制器")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;
    
    @PreAuthorize("hasAuthority('bnt.sysMenu.list')")
    // 加载树形菜单
    @ApiOperation("菜单列表")
    @GetMapping("/findNodes")
    public Result findNodes() {
        List<SysMenu> menuList=  this.sysMenuService.findNodes();
       return Result.ok(menuList);
    }
    @PreAuthorize("hasAuthority('bnt.sysMenu.add')")
    
    // 添加菜单
    @Log(title="添加菜单",businessType = BusinessType.INSERT)
    
    @ApiOperation("添加菜单")
    @PostMapping("/addMenu")
    public Result addMenu(@RequestBody SysMenu sysMenu){
        this.sysMenuService.save(sysMenu);
        return Result.ok();
    }
    @PreAuthorize("hasAuthority('bnt.sysMenu.list')")
    @ApiOperation("根据id去查询菜单")
    // 根据id去查询菜单
    @GetMapping("/findNodeById/{id}")
    public Result findNodeById(@PathVariable Long id){
        SysMenu sysMenu = this.sysMenuService.getById(id);
        return Result.ok(sysMenu);
    }
    @Log(title="修改菜单",businessType = BusinessType.UPDATE)
    
    @PreAuthorize("hasAuthority('bnt.sysMenu.update')")
    @ApiOperation(value = "修改菜单")
    @PostMapping("/updateMenu")
    public Result updateById(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return Result.ok();
    }
    
    
    @Log(title="删除菜单",businessType = BusinessType.DELETE)
    
    @PreAuthorize("hasAuthority('bnt.sysMenu.remove')")
    
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/removeMenu/{id}")
    public Result removeMenu(@PathVariable Long id) {
        sysMenuService.removeMenuById(id);
        return Result.ok();
    }
    @PreAuthorize("hasAuthority('bnt.sysMenu.list')")
    // 根据角色分配菜单
    @ApiOperation("根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId){
        List<SysMenu> list =   this.sysMenuService.findSysMenuByRoleId(roleId);
        return Result.ok(list);
    }
    @Log(title="给角色分配权限",businessType = BusinessType.UPDATE)
    
    @PreAuthorize("hasAuthority('bnt.sysRole.assignAuth')")
    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assginMenuVo) {
        sysMenuService.doAssign(assginMenuVo);
        return Result.ok();
    }

}