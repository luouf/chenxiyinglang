package com.gec.system.exception;


import com.gec.system.util.Result;
import com.gec.system.util.ResultCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e)  {
        return Result.fail().code(ResultCodeEnum.PERMISSION.getCode()).message("没有当前操作权限");
    }
    //1.全局异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e)
    {
        
        System.out.println("全局执行....");;
        e.printStackTrace();
        return Result.fail().message("执行了全局异常处理...");
    }
    //2.特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e)
    {
        System.out.println("特定执行.");
        e.printStackTrace();
        return Result.fail().message("执行了特定异常处理..");
    }

}
