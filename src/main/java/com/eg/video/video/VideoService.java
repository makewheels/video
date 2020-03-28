package com.eg.video.video;

import com.baidubce.services.cdn.model.PrefetchResponse;
import com.eg.video.utils.BaiduCdnUtil;
import com.eg.video.utils.Constants;
import com.eg.video.utils.UuidUtil;
import com.eg.video.video.bean.CdnPreload;
import com.eg.video.video.bean.PlaySetting;
import com.eg.video.video.bean.UploadFile;
import com.eg.video.video.bean.Video;
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
    private VideoDao videoDao;

    /**
     * 保存
     *
     * @param video
     * @return
     */
    public Video save(Video video) {
        return videoDao.save(video);
    }

    /**
     * 通过key查数据库，返回video对象
     *
     * @param key
     */
    public Video getVideoByKey(String key) {
        return videoDao.findVideoByKey(key);
    }

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
        //原始文件名
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);
        //本地磁盘保存文件名
        String diskFilename = key + "." + extension;
        //文件夹
        File folder = new File(Constants.LOCAL_FILE_ROOT_PATH + "/" + key);
        if (folder.exists() == false) {
            folder.mkdirs();
        }
        //相对路径
        String relativePath = "/" + key + "/" + diskFilename;
        //保存文件
        try {
            IOUtils.copy(multipartFile.getInputStream(),
                    new FileOutputStream(Constants.LOCAL_FILE_ROOT_PATH + relativePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //更新video
        video.setTitle(FilenameUtils.getBaseName(originalFilename));
        UploadFile uploadFile = new UploadFile();
        uploadFile.setCreateTime(new Date());
        uploadFile.setFileSize(multipartFile.getSize());
        uploadFile.setExtension(extension);
        uploadFile.setOriginalFilename(originalFilename);
        uploadFile.setDiskFilename(diskFilename);
        uploadFile.setRelativePath(relativePath);
        video.setVideoFile(uploadFile);

        PlaySetting playSetting = new PlaySetting();
        video.setPlaySetting(playSetting);
        video.setViewCount(0);
        videoDao.save(video);

        //转码
        if (extension.equals("mp4") == false) {
            System.out.println("not mp4，转码走着..***---");
        }

        //cdn预热
        PrefetchResponse prefetchResponse = BaiduCdnUtil.preload(Constants.CDN_BASE_URL
                + video.getVideoFile().getRelativePath());
        String preloadId = prefetchResponse.getId();
        String status = BaiduCdnUtil.queryPreload(preloadId).getDetails().get(0).getStatus();
        CdnPreload cdnPreload = new CdnPreload();
        cdnPreload.setPreloadId(preloadId);
        cdnPreload.setState(status);
        video.setCdnPreload(cdnPreload);
        videoDao.save(video);
    }

    /**
     * 查所有videos
     *
     * @return
     */
    public List<Video> findAllVideos() {
        return videoDao.findAll();
    }

    /**
     * 查有视频文件的videos
     *
     * @return
     */
    public List<Video> findVideosByVideoFileNotNull() {
        return videoDao.findVideosByVideoFileNotNull();
    }

}
