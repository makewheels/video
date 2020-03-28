package com.eg.video.video.bean;

import lombok.Data;

import java.util.Date;

@Data
public class UploadFile {
    private Date createTime;
    private long fileSize;
    private String extension;

    private String originalFilename;    //原始文件名
    private String diskFilename;        //本地磁盘保存文件文件名
    private String relativePath;        //本地磁盘相对路径
}
