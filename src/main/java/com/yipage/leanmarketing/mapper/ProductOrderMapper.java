package com.yipage.leanmarketing.mapper;

import com.yipage.leanmarketing.core.Mapper;
import com.yipage.leanmarketing.model.ProductOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductOrderMapper extends Mapper<ProductOrder> {

    Map<String, Object> selectAllOrderStateNum(@Param("openid") String openid);

    Integer count(@Param("payState") Integer payState,
                  @Param("startTime") String startTime,
                  @Param("endTime") String endTime,
                  @Param("payNumber") String payNumber,
                  @Param("userName") String userName,
                  @Param("productName") String productName,
                  @Param("openid") String openid);

    List<Map<String, Object>> listPager(@Param("beginIndex") int beginIndex,
                                        @Param("limit") Integer limit,
                                        @Param("payState") Integer payState,
                                        @Param("startTime") String startTime,
                                        @Param("endTime") String endTime,
                                        @Param("payNumber") String payNumber,
                                        @Param("userName") String userName,
                                        @Param("productName") String productName,
                                        @Param("openid") String openid);

    List<Map<String, Object>> exportExcel(@Param("payState") Integer payState,
                                          @Param("startTime") String startTime,
                                          @Param("endTime") String endTime,
                                          @Param("payNumber") String payNumber,
                                          @Param("userName") String userName,
                                          @Param("productName") String productName);
}