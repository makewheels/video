package com.eg.video.video;

import com.eg.video.utils.Constants;
import com.eg.video.video.bean.Video;
import com.eg.video.video.bean.ViewLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private ViewLogService viewLogService;

    /**
     * 主页
     *
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model) {
        List<Video> videoList = videoService.findVideosByVideoFileNotNull();
        model.addAttribute("videoList", videoList);
        return "index";
    }

    /**
     * 请求新建视频
     *
     * @param map
     * @return
     */
    @RequestMapping("/newVideo")
    public String newVideo(Map<String, String> map) {
        Video video = videoService.prepareNewVideo();
        String videoKey = video.getKey();
        map.put("videoUrl", Constants.BASE_URL + "/video/watch?key=" + videoKey);
        map.put("videoKey", videoKey);
        map.put("uploadUrl", Constants.BASE_URL + "/video/upload?key=" + videoKey);
        map.put("indexPageUrl", Constants.BASE_URL);
        return "newVideo";
    }

    /**
     * 上传视频
     *
     * @param key
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam String key,
                         @RequestParam MultipartFile file) {
        //处理上传视频
        videoService.handleUploadVideo(key, file);
        return "success";
    }

    /**
     * 观看视频
     *
     * @param key
     * @param map
     * @return
     */
    @RequestMapping("/watch")
    public String watch(@RequestParam String key, Map<String, String> map, HttpServletRequest request,
                        @RequestHeader("User-Agent") String userAgent) {
        Video video = videoService.getVideoByKey(key);
        if (video == null) {
            return null;
        }
        //保存观看记录
        ViewLog viewLog = new ViewLog();
        viewLog.setVideoKey(key);
        viewLog.setViewTime(new Date());
        viewLog.setIp(request.getRemoteAddr());
        viewLog.setUserAgent(userAgent);
        viewLogService.save(viewLog);
        //观看次数加1
        video.setViewCount(video.getViewCount() + 1);
        videoService.save(video);
        //设置前端参数
        map.put("title", video.getTitle());
        map.put("videoSourceUrl", Constants.CDN_BASE_URL + video.getVideoFile().getRelativePath());
        return "playVideo";
    }
}
