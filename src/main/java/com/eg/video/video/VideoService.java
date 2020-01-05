package com.eg.video.video;

import com.eg.video.file.UploadFile;
import com.eg.video.utils.Constants;
import com.eg.video.utils.UuidUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class VideoService {

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
        String relativePath = "/" + Constants.VIDEO_FOLDER_NAME + "/" + videoKey + "/" + UuidUtil.getUuid()
                + "." + extension;
        uploadFile.setRelativePath(relativePath);
        //要保存到本地的文件
        File saveFile = new File(Constants.LOCAL_FILE_ROOT_PATH + "/" + relativePath);
        System.out.println(Constants.LOCAL_FILE_ROOT_PATH + relativePath);
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
        String relativePath = "/" + Constants.VIDEO_FOLDER_NAME + "/" + videoKey + "/" + UuidUtil.getUuid()
                + "." + extension;
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
        return video.getKey();
    }


}
