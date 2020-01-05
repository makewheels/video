package com.eg.video.video;

import com.eg.video.utils.Constants;
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
            @RequestParam("description") String description,
            @RequestParam("password") String password) {
        if (password.equals("666666") == false) {
            return "fail";
        }
        String videoKey = videoService.uploadVideo(title, videoFile, coverFile, description);
        return Constants.HTTP_PROTOCOL + "://" + Constants.DOMAIN + "/" + Constants.PROJECT_NAME
                + "/video/watch?key=" + videoKey;
    }

    /**
     * 观看视频
     *
     * @return
     */
    @RequestMapping("/watch")
    public String uploadVideo(@RequestParam("key") String key) {

    }

}
