package com.yipage.leanmarketing.mapper;

import com.yipage.leanmarketing.core.Mapper;
import com.yipage.leanmarketing.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends Mapper<User> {

    List<User> exportExcel(@Param("identity") String identity,
                           @Param("memberLevel") String memberLevel,
                           @Param("address") String address,
                           @Param("superiorId") String superiorId);

    void updateUserDelete(@Param("ids") String ids);
}