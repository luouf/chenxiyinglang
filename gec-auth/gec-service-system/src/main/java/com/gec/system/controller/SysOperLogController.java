package com.gec.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysOperLog;
import com.gec.model.vo.SysOperLogQueryVo;
import com.gec.system.service.SysOperaLogService;
import com.gec.system.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("登录日志控制器")
@RestController
@RequestMapping("/admin/system/sysOperLog")
public class SysOperLogController {
    @Autowired
    private SysOperaLogService sysOperaLogService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result QueryLoginLog(
            @PathVariable Long page,
            @PathVariable Long limit,
                    SysOperLogQueryVo sysOperLogQueryVo) {
        IPage<SysOperLog> iPage = new Page<>(page,limit);
        iPage= sysOperaLogService.selectPage(iPage,sysOperLogQueryVo);
        return Result.ok(iPage);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        SysOperLog sysOperLog = sysOperaLogService.getById(id);
        return Result.ok(sysOperLog);
    }
    @ApiOperation(value = "删除")
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable Long id){
        SysOperLog sysOperLog = sysOperaLogService.getById(id);
        return Result.ok(sysOperaLogService.removeById(sysOperLog));
    }
}
