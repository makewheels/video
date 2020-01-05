package com.eg.video.file;

import lombok.Data;

import java.util.Date;

@Data
public class UploadFile {
    private String originalFilename;
    private Date createTime;
    private long fileSize;
    private String extension;//文件格式，也就是拓展名

    private String relativePath;//相对路径：对象存储有相对路径，本地存储也有相对路径
    private String url;//网址
    private String provider;//提供者，例如：七牛，GCP
}
