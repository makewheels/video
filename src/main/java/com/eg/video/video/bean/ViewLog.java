package com.eg.video.video.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 观看记录
 *
 * @time 2020-03-28 19:00
 */
@Data
@Document
public class ViewLog {
    @Id
    private String _id;
    private String videoKey;
    private Date viewTime;
    private String userAgent;
    private String ip;
}
