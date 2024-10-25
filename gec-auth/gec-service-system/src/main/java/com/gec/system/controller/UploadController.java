package com.gec.system.controller;


import com.gec.system.service.SysMovieService;
import com.gec.system.util.MyThreadPoolFactory;
import com.gec.system.util.OssTemplate;
import com.gec.system.util.Result;
import com.gec.system.util.VodTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@RequestMapping(value = "/admin/system/upload")
@RestController
@Transactional
@CrossOrigin //  处理 跨域问题
public class UploadController {
    
    @Autowired
    private OssTemplate ossTemplate;
    
    @Autowired
    private VodTemplate vodTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Autowired
    private SysMovieService sysMovieService;
    
    //  http://localhost:8085/admin/system/upload/uploadImage
    // 上传文件 (图片)
    @PostMapping(value = "/uploadImage")
    public Result uploadImage(@RequestParam MultipartFile uploadImage) throws IOException, ExecutionException, InterruptedException {
        Callable<Result> callable=() -> {
        String imageUrl = this.ossTemplate.upload(uploadImage.getOriginalFilename(), uploadImage.getInputStream());
        System.out.println("imageUrl=====" + imageUrl);
        return Result.ok(imageUrl);
        };
        Future<Result> submit = MyThreadPoolFactory.myThreadPool.submit(callable);
        return submit.get();
    }
    
    //  "http://localhost:8085/admin/system/upload/uploadVideo
    
    // 上传视频操作
    @PostMapping(value = "/uploadVideo")
    public Result uploadVideo(@RequestParam MultipartFile uploadVideo) throws IOException, ExecutionException, InterruptedException {
        
        Callable<Result> callable=() -> {
            System.out.println("uploadVideo.getName() = " + uploadVideo.getName());
            String videoId = this.vodTemplate.uploadVideo(uploadVideo.getOriginalFilename(), uploadVideo.getInputStream());
            System.out.println("videoId=====" + videoId);
            return Result.ok(videoId);
        };
        Future<Result> submit = MyThreadPoolFactory.myThreadPool.submit(callable);
        return submit.get();
    }
}
