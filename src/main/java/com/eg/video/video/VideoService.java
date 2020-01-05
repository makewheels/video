package com.eg.video.video;

import com.eg.video.utils.Constants;
import com.eg.video.utils.UuidUtil;
import com.eg.video.video.bean.UploadFile;
import com.eg.video.video.bean.Video;
import com.eg.video.video.play.PlayInfo;
import com.eg.video.video.play.PlaySetting;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class VideoService {
    @Autowired
    VideoRepository videoRepository;

    public UploadFile saveVideoFile(MultipartFile videoFile, String videoKey) {
        //先搞定上传文件
        UploadFile uploadFile = new UploadFile();
        uploadFile.setCreateTime(new Date());
        uploadFile.setOriginalFilename(videoFile.getOriginalFilename());
        uploadFile.setFileSize(videoFile.getSize());
        String extension = FilenameUtils.getExtension(videoFile.getOriginalFilename());
        uploadFile.setExtension(extension);
        uploadFile.setProvider("local");
        //例如：D:/temp/video-project/video/
        String relativePath = "/" + Constants.REQUEST_FILE_URI + "/" + Constants.VIDEO_FOLDER_NAME
                + "/" + videoKey + "/" + UuidUtil.getUuid() + "." + extension;
        uploadFile.setRelativePath(relativePath);
        //要保存到本地的文件
        File saveFile = new File(Constants.LOCAL_FILE_ROOT_PATH + "/" + relativePath);
        try {
            File folder = saveFile.getParentFile();
            if (folder.exists() == false) {
                folder.mkdirs();
            }
            //保存到本地
            IOUtils.copy(videoFile.getInputStream(), new FileOutputStream(saveFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadFile;
    }

    private UploadFile saveCoverFile(MultipartFile coverFile, String videoKey) {
        //先搞定上传文件
        UploadFile uploadFile = new UploadFile();
        uploadFile.setCreateTime(new Date());
        uploadFile.setOriginalFilename(coverFile.getOriginalFilename());
        uploadFile.setFileSize(coverFile.getSize());
        String extension = FilenameUtils.getExtension(coverFile.getOriginalFilename());
        uploadFile.setExtension(extension);
        String relativePath = "/" + Constants.REQUEST_FILE_URI + "/" + Constants.VIDEO_FOLDER_NAME
                + "/" + videoKey + "/" + UuidUtil.getUuid() + "." + extension;
        uploadFile.setRelativePath(relativePath);
        //要保存到本地的文件
        File saveFile = new File(Constants.LOCAL_FILE_ROOT_PATH + relativePath);
        try {
            File folder = saveFile.getParentFile();
            if (folder.exists() == false) {
                folder.mkdirs();
            }
            //保存到本地
            IOUtils.copy(coverFile.getInputStream(), new FileOutputStream(saveFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadFile;
    }

    /**
     * 上传单视频
     *
     * @param title
     * @param videoFile
     * @param coverFile
     */
    public String uploadVideo(String title, MultipartFile videoFile,
                              MultipartFile coverFile, String description) {
        String videoKey = UuidUtil.getUuid();
        Video video = new Video();
        video.setTitle(title);
        video.setCreateTime(new Date());
        video.setDescription(description);
        video.setKey(videoKey);
        video.setViewCount(0);
        video.setPlaySetting(new PlaySetting());
        video.setVideoFile(saveVideoFile(videoFile, videoKey));
        //如果没有封面文件上传
        if (coverFile.isEmpty() == false) {
            video.setCoverFile(saveCoverFile(coverFile, videoKey));
        }
        //保存video对象到mangodb
        videoRepository.save(video);
        return video.getKey();
    }

    /**
     * 组装播放信息
     *
     * @param key
     * @return
     */
    public PlayInfo getVideoPlayInfoByKey(String key) {
        PlayInfo playInfo = new PlayInfo();
        List<Video> videoList = videoRepository.findVideoByKey(key);
        if (CollectionUtils.isEmpty(videoList)) {
            return null;
        }
        Video video = videoList.get(0);
        playInfo.setTitle(video.getTitle());
        playInfo.setDescription(video.getDescription());
        playInfo.setCreateTime(video.getCreateTime());
        playInfo.setViewCount(video.getViewCount());
        playInfo.setPlaySetting(video.getPlaySetting());
        UploadFile coverFile = video.getCoverFile();
        if (coverFile == null) {
            playInfo.setCoverUrl(null);
        } else {
            playInfo.setCoverUrl(Constants.FILE_STORAGE_BASE_URL + coverFile.getRelativePath());
        }
        playInfo.setVideoUrl(Constants.FILE_STORAGE_BASE_URL + video.getVideoFile().getRelativePath());
        return playInfo;
    }
}
