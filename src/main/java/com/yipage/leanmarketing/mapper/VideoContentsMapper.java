package com.yipage.leanmarketing.mapper;

import com.yipage.leanmarketing.core.Mapper;
import com.yipage.leanmarketing.model.ConsumeRecord;
import com.yipage.leanmarketing.model.VideoContents;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoContentsMapper extends Mapper<VideoContents> {

    Integer countToVideoContents(@Param("videoChapterName") String videoChapterName,
                                 @Param("videoName") String videoName);

    List<ConsumeRecord> listPagerToVideoContents(@Param("beginIndex") Integer beginIndex,
                                                 @Param("limit") Integer limit,
                                                 @Param("videoChapterName") String videoChapterName,
                                                 @Param("videoName") String videoName);
}