package com.eg.video.video;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class VideoService {
    /**
     * 处理上传单视频
     *
     * @param title
     * @param videoFile
     * @param coverFile
     */
    public void uploadVideo(String title, MultipartFile videoFile, MultipartFile coverFile) {
        String videoFileOriginalFilename = videoFile.getOriginalFilename();
        String coverFileOriginalFilename = coverFile.getOriginalFilename();
        File videoSaveFile = new File("");
        File coverSaveFile = new File("");
        try {
            IOUtils.copy(multipartFile.getInputStream(), new FileOutputStream(saveFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
