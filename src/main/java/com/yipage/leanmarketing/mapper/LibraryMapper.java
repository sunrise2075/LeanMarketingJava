package com.yipage.leanmarketing.mapper;

import com.yipage.leanmarketing.core.Mapper;
import com.yipage.leanmarketing.model.Library;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryMapper extends Mapper<Library> {

    List<Library> findByKeyWord(@Param("title") String title, @Param("fileType") String fileType, @Param("categoryId") Integer categoryId);

    Long recommendedLibrarysCount(@Param("userId") Integer userId, @Param("fileType") String fileType, @Param("isFree") Integer isFree);

    List<Library> recommendedLibrarys(@Param("startIndex") Integer startIndex,
                                      @Param("limit") Integer limit,
                                      @Param("userId") Integer userId, @Param("fileType") String fileType, @Param("isFree") Integer isFree);

    List<Library> getByLabels(String[] array);

    Long getByDownLoadRecordCount(@Param("fileType") String fileType, @Param("isFree") Integer isFree);

    List<Library> getByDownLoadRecord(@Param("startIndex") Integer startIndex,
                                      @Param("limit") Integer limit,
                                      @Param("fileType") String fileType, @Param("isFree") Integer isFree);

    List<Library> getByLabel(String label);
}