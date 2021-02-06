package com.yipage.leanmarketing.mapper;

import com.yipage.leanmarketing.core.Mapper;
import com.yipage.leanmarketing.model.ConsumeRecord;
import com.yipage.leanmarketing.model.VideoChapter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoChapterMapper extends Mapper<VideoChapter> {

    Integer countToVideoChapter(@Param("videoChapterName") String videoChapterName,
                                @Param("videoName") String videoName,
                                @Param("videoId") Integer videoId);

    List<ConsumeRecord> listPagerToVideoChapter(@Param("beginIndex") Integer beginIndex,
                                                @Param("limit") Integer limit,
                                                @Param("videoName") String videoName,
                                                @Param("videoChapterName") String videoChapterName,
                                                @Param("videoId") Integer videoId);
}