package com.eg.video.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 跳转上传页
     *
     * @return
     */
    @RequestMapping("/upload")
    public String index() {
        return "/video/upload.html";
    }

    /**
     * 上传视频
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/uploadVideo")
    public String uploadVideo(
            @RequestParam("title") String title,
            @RequestParam("videoFile") MultipartFile videoFile,
            @RequestParam("coverFile") MultipartFile coverFile,
            @RequestParam("password") String password) {
        if (password.equals("666666") == false) {
            return "fail";
        }
        videoService.uploadVideo(title,videoFile,coverFile);
        return "success";
    }

}
