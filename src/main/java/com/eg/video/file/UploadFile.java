package com.eg.video.file;

import lombok.Data;

import java.util.Date;

@Data
public class UploadFile {
    private String originalFilename;
    private Date createTime;
    private long fileSize;
    private String format;//文件格式，也就是拓展名
    private String saveType;//保存类型
}
