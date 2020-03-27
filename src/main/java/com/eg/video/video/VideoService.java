package com.eg.video.video;

import com.eg.video.utils.Constants;
import com.eg.video.utils.UuidUtil;
import com.eg.video.video.bean.Video;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class VideoService {
    @Autowired
    VideoDao videoDao;

    /**
     * 准备上传视频
     *
     * @return
     */
    public Video prepareNewVideo() {
        //生成视频id保存到数据库
        Video video = new Video();
        video.setKey(UuidUtil.getUuid());
        video.setCreateTime(new Date());
        videoDao.save(video);
        return video;
    }

    /**
     * 处理上传视频
     *
     * @param key
     * @param multipartFile
     */
    public void handleUploadVideo(String key, MultipartFile multipartFile) {
        //先查数据库
        Video video = videoDao.findVideoByKey(key);
        if (video == null) {
            return;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        video.setTitle(originalFilename);
        //文件夹
        File folder = new File(Constants.LOCAL_FILE_ROOT_PATH + "/" + key);
        if (folder.exists() == false) {
            folder.mkdirs();
        }
        //相对路径
        String relativePath = "/" + key + "/" + originalFilename;
        //上传文件
        File uploadFile = new File(Constants.LOCAL_FILE_ROOT_PATH + relativePath);
        //保存文件
        try {
            IOUtils.copy(multipartFile.getInputStream(), new FileOutputStream(uploadFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //转码

        //更新数据库
        //cdn预热
        //推送通知
    }
}
