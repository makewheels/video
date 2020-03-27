package com.eg.video.video.bean.play;

import lombok.Data;

import java.util.Date;

/**
 * 播放信息
 */
@Data
public class PlayInfo {
    private String title;
    private String description;
    private int viewCount;
    private Date createTime;
    private PlaySetting playSetting;
    private String videoUrl;
}
