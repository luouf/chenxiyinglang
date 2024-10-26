package com.gec.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysLoginLog;
import com.gec.model.vo.SysLoginLogQueryVo;
import com.gec.system.service.SysLoginLogService;
import com.gec.system.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("登录日志控制器")
@RestController
@RequestMapping("/admin/system/sysLoginLog")
public class SysLoginLogController {
    @Autowired
    private SysLoginLogService sysLoginLogService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result QueryLoginLog(
            @PathVariable Long page,
            @PathVariable Long limit,
                    SysLoginLogQueryVo sysLoginLogQueryVo) {
        IPage<SysLoginLog> iPage = new Page<>(page,limit);
        iPage=sysLoginLogService.selectPage(iPage,sysLoginLogQueryVo);
        return Result.ok(iPage);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        SysLoginLog sysLoginLog = sysLoginLogService.getById(id);
        return Result.ok(sysLoginLog);
    }
    @ApiOperation(value = "删除")
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable Long id){
        SysLoginLog sysLoginLog = sysLoginLogService.getById(id);
        return Result.ok(sysLoginLogService.removeById(sysLoginLog));
    }
}