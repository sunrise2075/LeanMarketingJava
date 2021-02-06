package com.yipage.leanmarketing.mapper;

import com.yipage.leanmarketing.core.Mapper;
import com.yipage.leanmarketing.model.ConsumeRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ConsumeRecordMapper extends Mapper<ConsumeRecord> {

    BigDecimal getTotalRevenue(@Param("address") String address);

    Integer getTotalNumber(@Param("address") String address);

    Integer getListCount(@Param("year") Integer year, @Param("month") Integer month,
                         @Param("day") Integer day, @Param("type") Integer type,
                         @Param("address") String address);

    List<ConsumeRecord> getList(@Param("beginIndex") Integer beginIndex, @Param("limit") Integer limit,
                                @Param("year") Integer year, @Param("month") Integer month,
                                @Param("day") Integer day, @Param("type") Integer type,
                                @Param("address") String address);

    BigDecimal getRevenue(@Param("year") Integer year, @Param("month") Integer month,
                          @Param("day") Integer day, @Param("type") Integer type,
                          @Param("address") String address);

    Integer getNumber(@Param("year") Integer year, @Param("month") Integer month,
                      @Param("day") Integer day, @Param("type") Integer type,
                      @Param("address") String address);


//    BigDecimal dealerRevenueStreams(@Param("superiorId")Integer superiorId, @Param("year") Integer year,@Param("month") Integer month,
//                                    @Param("day") Integer day, @Param("type")Integer type);

    BigDecimal dealerRevenueStreams(@Param("superiorId") Integer superiorId);

    Integer getListDealerRevenueCount(@Param("year") Integer year, @Param("month") Integer month,
                                      @Param("day") Integer day, @Param("type") Integer type,
                                      @Param("superiorId") Integer superiorId,
                                      @Param("address") String address);

    List<ConsumeRecord> getListDealerRevenue(@Param("beginIndex") Integer beginIndex, @Param("limit") Integer limit,
                                             @Param("year") Integer year, @Param("month") Integer month,
                                             @Param("day") Integer day, @Param("type") Integer type,
                                             @Param("superiorId") Integer superiorId, @Param("address") String address);

    BigDecimal getDealerRevenue(@Param("year") Integer year, @Param("month") Integer month,
                                @Param("day") Integer day, @Param("type") Integer type,
                                @Param("superiorId") Integer superiorId);

    Integer getDealerNumber(@Param("year") Integer year, @Param("month") Integer month,
                            @Param("day") Integer day, @Param("type") Integer type,
                            @Param("superiorId") Integer superiorId);

    Integer getConsumeRecordCount(@Param("superiorId") Integer superiorId, @Param("address") String address, @Param("type") Integer type);

    List<ConsumeRecord> getConsumeRecordList(@Param("beginIndex") Integer beginIndex, @Param("limit") Integer limit, @Param("superiorId") Integer superiorId, @Param("address") String address, @Param("type") Integer type);
}