package com.eg.video.video;

import lombok.Data;

/**
 * 播放设置
 */
@Data
public class PlaySetting {
    private int volume = 66;//默认音量
    private boolean autoFullscreen = false;//是否自动全屏
    private boolean autoPlay = false;//是否自动播放
}
