package com.yipage.leanmarketing.mapper;

import com.yipage.leanmarketing.core.Mapper;
import com.yipage.leanmarketing.model.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeMapper extends Mapper<Employee> {

    Integer countToEmployee(@Param("userId") Integer userId, @Param("companyName") String companyName);

    List<Map<String, Object>> listPagerToEmployee(@Param("limit") Integer limit,
                                                  @Param("beginIndex") Integer beginIndex,
                                                  @Param("userId") Integer userId,
                                                  @Param("companyName") String companyName);
}