package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.Employee;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/19.
 */
public interface EmployeeService extends Service<Employee> {

    Integer selectCount(Employee employee);

    Map listPagerToEmployee(Integer page, Integer limit, Integer userId, String companyName);

    List<Employee> select(Employee employee);
}
