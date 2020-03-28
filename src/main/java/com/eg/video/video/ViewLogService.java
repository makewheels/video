package com.eg.video.video;

import com.eg.video.video.bean.ViewLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @time 2020-03-28 19:10
 */
@Service
@Transactional
public class ViewLogService {
    @Autowired
    private ViewLogDao viewLogDao;

    /**
     * 保存
     *
     * @param viewLog
     * @return
     */
    public ViewLog save(ViewLog viewLog) {
        return viewLogDao.save(viewLog);
    }
}
