package com.yipage.leanmarketing.mapper;

import com.yipage.leanmarketing.core.Mapper;
import com.yipage.leanmarketing.model.Administrator;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorMapper extends Mapper<Administrator> {

    Administrator getAdministratorByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}