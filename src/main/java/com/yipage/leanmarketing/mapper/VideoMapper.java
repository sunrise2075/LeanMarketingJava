package com.yipage.leanmarketing.mapper;

import com.yipage.leanmarketing.core.Mapper;
import com.yipage.leanmarketing.model.Video;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoMapper extends Mapper<Video> {

    List<Video> orderBySalesNum();

    List<Video> findVideo(@Param("categoryId") Integer categoryId,
                          @Param("isFree") Integer isFree,
                          @Param("isShow") Integer isShow,
                          @Param("limit") Integer limit);

    Long recommendedVideosCount(@Param("userId") Integer userId,
                                @Param("isFree") Integer isFree);

    List<Video> recommendedVideos(@Param("startIndex") Integer startIndex,
                                  @Param("limit") Integer limit,
                                  @Param("userId") Integer userId,
                                  @Param("isFree") Integer isFree);

    List<Video> getByLabels(String[] array);

    Long getByWatchRecordCount(Integer isFree);

    List<Video> getByWatchRecord(@Param("startIndex") Integer startIndex,
                                 @Param("limit") Integer limit,
                                 @Param("isFree") Integer isFree);

    List<Video> getByLabel(String s);
}