package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.BannerMapper;
import com.yipage.leanmarketing.model.Banner;
import com.yipage.leanmarketing.service.BannerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/11.
 */
@Service
@Transactional
public class BannerServiceImpl extends AbstractService<Banner> implements BannerService {
    @Resource
    private BannerMapper bannerMapper;

    @Override
    public List<Banner> select(Banner banner) {
        return bannerMapper.select(banner);
    }
}
