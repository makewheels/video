package com.eg.video.video;

import com.eg.video.video.bean.ViewLog;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @time 2020-03-28 19:09
 */
public interface ViewLogDao extends MongoRepository<ViewLog, String> {
}
