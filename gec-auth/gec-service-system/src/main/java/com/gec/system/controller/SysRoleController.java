package com.gec.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.system.aop.Log;
import com.gec.model.enums.BusinessType;
import com.gec.model.system.SysRole;
import com.gec.model.vo.AssginRoleVo;
import com.gec.model.vo.SysRoleQueryVo;
import com.gec.system.service.SysRoleService;
import com.gec.system.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

;


@Api(tags = "角色控制器")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;


//    @ApiOperation("获取全部角色列表")
//    // http://localhost:8085/admin/system/sysRole/findAll
//    @GetMapping("/findAll")
//    public List<SysRole> findAll()
//    {
//        List<SysRole> list = this.sysRoleService.list();
//        return list;
//    }

    @ApiOperation("获取全部角色列表")
    // http://localhost:8085/admin/system/sysRole/findAll
    @GetMapping("/findAll")
    public Result findAll()
    {

//        try {
//            int i = 1/0;
//        }
//        catch (Exception e)
//        {
//          // 手动抛出异常
//          throw new MyCustomerException(2001,"自定义异常了");
//        }

        List<SysRole> list = this.sysRoleService.list();
        
        return Result.ok(list);
    }

    // http://localhost:8085/admin/system/sysRole/removeRole/14
//
//    @ApiOperation("根据id去移除一个角色")
//    // 测试删除
//    @DeleteMapping("/removeRole/{id}")
//    public boolean removeRole(@PathVariable Long id)
//    {
//        boolean b = this.sysRoleService.removeById(id);
//        return b;
//    }
    @Log(title="根据id去移除一个角色",businessType = BusinessType.DELETE)

    @PreAuthorize("hasAuthority('bnt.sysRole.remove')") //bnt.sysRole.remove
    @ApiOperation("根据id去移除一个角色")
    // 测试删除
    @DeleteMapping("/removeRole/{id}")
    public Result removeRole(@PathVariable Long id)
    {
        boolean b = this.sysRoleService.removeById(id);
        if (b)
        {
            return Result.ok();
        }
        else
        {
            return Result.fail();
        }
    }
    // 分页和条件查询
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @GetMapping("/{page}/{limit}")
    public Result findRoleByPageQuery(@PathVariable Long page,
                                      @PathVariable Long limit,
                                      SysRoleQueryVo sysRoleQueryVo)
    {
        //1.创建分页对象
        IPage<SysRole> p1 = new Page<SysRole>(page,limit);
        //2.调用方法
        p1 =    this.sysRoleService.selectPage(p1,sysRoleQueryVo);
        //3.返回
        return Result.ok(p1);
    }
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    // 添加角色
    @Log(title="添加角色",businessType = BusinessType.INSERT)

    @PostMapping("/addRole")
    public Result addRole(@RequestBody SysRole sysRole)
    {
        boolean res = sysRoleService.save(sysRole);
        if (res)
        {
            return Result.ok();
        }
        else
        {
            return Result.fail();
        }
    }
    // 修改
    //1.根据id 去得到一个角色
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @GetMapping("/findRoleById/{id}")
    public Result findRoleById(@PathVariable Long id)
    {
        SysRole sysRole = this.sysRoleService.getById(id);
        return Result.ok(sysRole);
    }
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    // 实现修改
    @Log(title="修改角色",businessType = BusinessType.UPDATE)

    @PostMapping("/updateRole")
    public Result updateRole(@RequestBody SysRole sysRole)
    {
        boolean b = this.sysRoleService.updateById(sysRole);
        if (b)
        {
            return Result.ok();
        }
        else
        {
            return Result.fail();
        }
    }
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    // 批量删除
    @Log(title="批量删除角色",businessType = BusinessType.DELETE)

    @DeleteMapping("/batchRemove")
    public Result removeRoleByIds(@RequestBody List<Long> ids)
    {
        boolean b = this.sysRoleService.removeByIds(ids);
        if (b)
        {
            return Result.ok();
        }
        else
        {
            return Result.fail();
        }
    }
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    // 根据用户获取角色信息
    @ApiOperation("根据用户获取角色信息")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId)
    {
        Map<String,Object> map =  this.sysRoleService.getRolesByUserId(userId);
        return Result.ok(map);
    }
    @PreAuthorize("hasAuthority('bnt.sysUser.assignRole')")
    // 根据用户分配角色
    @Log(title="根据用户分配角色",businessType = BusinessType.UPDATE)
    
    @ApiOperation("根据用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo){
        
        this.sysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }
    /**
     * spring security异常
     * @param e
     * @return
     */

}