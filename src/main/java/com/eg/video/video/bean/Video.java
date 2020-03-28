package com.eg.video.video.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class Video {
    @Id
    private String _id;
    private String key;             //视频id

    private String title;           //标题
    private Date createTime;        //创建时间
    private String description;     //描述
    private int viewCount;          //播放量

    private PlaySetting playSetting;//播放设置
    private UploadFile videoFile;   //视频文件
    private CdnPreload cdnPreload;  //cdn预热

}
