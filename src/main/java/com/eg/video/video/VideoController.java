package com.eg.video.video;

import com.eg.video.utils.Constants;
import com.eg.video.video.bean.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 通过user agent查看是否来自手机
     * 如果是手机，返回true
     * 如果是平板或者pc，返回false
     *
     * @param userAgent
     * @return
     */
    public boolean isFromMobilePhone(String userAgent) {
        //1. 获得请求UA
        userAgent = userAgent.toLowerCase();

        //2.声明手机和平板的UA的正则表达式
        // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
        // 字符串在编译时会被转码一次,所以是 "\\b"
        // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
        String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i" + "|windows (phone|ce)|blackberry"
                + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp" + "|laystation portable)|nokia|fennec|htc[-_]"
                + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser" + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

        // 3.移动设备正则匹配：手机端、平板
        Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
        Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);
        if (null == userAgent) {
            userAgent = "";
        }
        // 4.匹配
        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);
        if (matcherPhone.find()) {
            return true; //来自手机
        } else {
            return false; //来自PC或者平板
        }
    }

    /**
     * 请求新建视频
     *
     * @return
     */
    @RequestMapping("/newVideo")
    public String newVideo(Map<String, String> map) {
        Video video = videoService.prepareNewVideo();
        String videoKey = video.getKey();
        map.put("videoUrl", Constants.BASE_URL + "/watch?key=" + videoKey);
        map.put("videoKey", videoKey);
        map.put("uploadUrl", Constants.BASE_URL + "/video/upload?key=" + videoKey);
        map.put("indexPageUrl", Constants.BASE_URL);
        return "newVideo";
    }

    /**
     * 上传视频
     *
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
}
