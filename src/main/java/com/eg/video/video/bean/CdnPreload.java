package com.eg.video.video.bean;

import lombok.Data;

import java.util.Date;

/**
 * cdn预热
 *
 * @time 2020-03-28 19:49
 */
@Data
public class CdnPreload {
    private String state;//cdn预热状态：loading，ready
    private String preloadId;//cdn预热id
    private Date finishTime;//cdn预热完成时间
    private boolean isPreloadFinished = false;//cdn预热是否完成
}
