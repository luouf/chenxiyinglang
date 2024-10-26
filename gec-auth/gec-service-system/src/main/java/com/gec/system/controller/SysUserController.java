package com.gec.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.enums.BusinessType;
import com.gec.model.system.SysUser;
import com.gec.model.vo.SysUserQueryVo;
import com.gec.system.aop.Log;
import com.gec.system.service.SysUserService;
import com.gec.system.util.MD5Helper;
import com.gec.system.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.TimeZone;

@RestController
@Api(tags = "用户管理控制器")
@RequestMapping("/admin/system/sysUser")
//@EnableGlobalMethodSecurity(prePostEnabled = true)//开启注解功能，默认禁用注解
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @PreAuthorize("hasAuthority('bnt.sysUser.list')")
    @ApiOperation("分页和条件查询")
    @GetMapping("/{page}/{limit}")
    public Result selectUserPageByVo(@PathVariable Long page,
                                     @PathVariable Long limit,
                                     SysUserQueryVo sysUserQueryVo){
        IPage<SysUser> iPage = new Page<>(page,limit);
        System.out.println("Default TimeZone: " + TimeZone.getDefault().getID());
        System.out.println("sysUserQueryVo = " + sysUserQueryVo);
        iPage =   this.sysUserService.selectPage(iPage,sysUserQueryVo);
        return Result.ok(iPage);
    }
    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation("根据id更改状态")
    @PostMapping("/{id}/{status}")
    @Log(title="根据id更改状态",businessType = BusinessType.UPDATE)
    public Result updateStatusById(@PathVariable Long id,@PathVariable int status){
        SysUser sysUser = this.sysUserService.getById(id);
        sysUser.setStatus(status);
        boolean update = this.sysUserService.updateById(sysUser);
        return update?Result.ok():Result.fail();
    }
    @PreAuthorize("hasAuthority('bnt.sysUser.add')")
    @ApiOperation("添加用户")
    @Log(title="添加用户",businessType = BusinessType.INSERT)

    @PostMapping("/addUser")
    public Result addUser(@RequestBody SysUser user){
        // 通过MD5 工具类
        String passwordWithMD5 = MD5Helper.encrypt(user.getPassword());
        // 将加密密码设置给sysUser
        user.setPassword(passwordWithMD5);
        boolean isOk = this.sysUserService.save(user);
        return isOk?Result.ok():Result.fail();
    }
    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation("修改用户")
    @Log(title="修改用户",businessType = BusinessType.UPDATE)

    @PostMapping("/editUserById")
    public Result editUserById(@RequestBody SysUser user){
        boolean isOk = this.sysUserService.updateById(user);
        return isOk?Result.ok():Result.fail();
    }
    @PreAuthorize("hasAuthority('bnt.sysUser.remove')")
    @ApiOperation("根据id删除用户")
    @Log(title="根据id删除用户",businessType = BusinessType.DELETE)

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id){
        boolean isOk = this.sysUserService.removeById(id);
        return isOk?Result.ok():Result.fail();
    }
    @PreAuthorize("hasAuthority('bnt.sysUser.remove')")
    @ApiOperation("批量删除用户")
    @Log(title="批量删除用户",businessType = BusinessType.DELETE)
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids){
        boolean isOk = this.sysUserService.removeByIds(ids);
        return isOk?Result.ok():Result.fail();
    }

}
