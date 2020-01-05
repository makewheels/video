package com.eg.video.video;

import com.eg.video.file.UploadFile;
import lombok.Data;

import java.util.Date;

@Data
public class Video {
    private String key;//视频id
    private String title;//标题
    private Date createTime;//创建时间
    private String description;//描述
    private int viewCount;//播放量
    private PlaySetting playSetting;//播放设置

    private UploadFile videoFile;
    private UploadFile coverFile;

}
