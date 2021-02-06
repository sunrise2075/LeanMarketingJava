package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.Banner;
import com.yipage.leanmarketing.model.Product;
import com.yipage.leanmarketing.model.Video;
import com.yipage.leanmarketing.service.BannerService;
import com.yipage.leanmarketing.service.ProductService;
import com.yipage.leanmarketing.service.VideoService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by CodeGenerator on 2018/12/11.
 */
@RestController
@RequestMapping("/banner")
public class BannerController {
    @Resource
    private BannerService bannerService;
    @Resource
    private ProductService productService;
    @Resource
    private VideoService videoService;

    @PostMapping("add")
    public Result add(Banner banner) {


        banner.setCreateTime(new Date());
        banner.setUpdateTime(new Date());
        bannerService.save(banner);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(Banner banner) {
        banner.setUpdateTime(new Date());
        bannerService.update(banner);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {

        Banner banner = bannerService.findById(id);
        return ResultGenerator.genSuccessResult(banner);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(required = false) Integer type) {

        PageHelper.startPage(page, limit, "create_time desc");
        Banner queryBanner = new Banner();
        queryBanner.setType(type);
        queryBanner.setState(1);
        List<Banner> list = bannerService.select(queryBanner);
        List<Map<String, Object>> list2 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {

            for (Banner banner : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("createTime", banner.getCreateTime());
                map.put("updateTime", banner.getUpdateTime());
                map.put("id", banner.getId());
                map.put("img", banner.getImg());
                map.put("typeId", banner.getTypeId());
                map.put("state", banner.getState());
                map.put("title", banner.getTitle());
                map.put("imgpc", banner.getImgpc());

                int type2 = banner.getType();
                if (type2 == 1) {  //是商品轮播

                    Product product = productService.findById(banner.getTypeId());

                    if (product != null) {
                        map.put("typeName", product.getName());

                    }
                } else {

                    Video video = videoService.findById(banner.getTypeId());
                    if (video != null) {
                        map.put("typeName", video.getTitle());
                    }
                }
                list2.add(map);
            }

        }
        PageInfo pageInfo = new PageInfo(list2);
        return MapUtil.PageResult(pageInfo);
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        bannerService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 查询所有商品轮播图
     *
     * @return
     */
    @GetMapping("select")
    public Result select() {

        Banner banner = new Banner();
        banner.setState(Banner.IS_USE);

        List<Banner> list = bannerService.select(banner);

        return ResultGenerator.genSuccessResult(list);

    }
}
