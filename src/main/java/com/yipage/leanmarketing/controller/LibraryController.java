package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.annotation.NeedLoginAnnotation;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.*;
import com.yipage.leanmarketing.service.*;
import com.yipage.leanmarketing.utils.FileDownloadUtil;
import com.yipage.leanmarketing.utils.MapUtil;
import com.yipage.leanmarketing.utils.TokenManagerUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by CodeGenerator on 2018/12/13.
 */
@RestController
@RequestMapping("/library")
public class LibraryController {
    @Resource
    private LibraryService libraryService;
    @Resource
    private LibraryCategoryService libraryCategoryService;

    @Resource
    private UserService userService;
    @Resource
    private LibraryDownloadRecordService libraryDownloadRecordService;
    @Resource
    private LibraryOrderService libraryOrderService;

    @Resource
    private BannerService bannerService;


    @PostMapping("add")
    public Result add(Library library) {
        library.setCreateTime(new Date());
        library.setUpdateTime(new Date());
        LibraryCategory libraryCategory = libraryCategoryService.findById(library.getCategoryId());
        if (libraryCategory != null) {
            library.setCategoryName(libraryCategory.getCategoryName());
        }
        libraryService.save(library);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        libraryService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(Library library) {
        library.setUpdateTime(new Date());
        libraryService.update(library);
        return ResultGenerator.genSuccessResult();
    }

    @NeedLoginAnnotation
    @GetMapping("/{id}/{openid}")
    public Result detail(@PathVariable Integer id,
                         @PathVariable String openid) {
        Map<String, Object> map = new HashMap<>();
        Library library = libraryService.findById(id);
        User user = null;
        if (StringUtil.isNotEmpty(openid)) {
            user = userService.findBy("wxid", openid);
        }
        if (library != null) {
            map.put("id", library.getId());
            map.put("codes", library.getCodes());
            map.put("title", library.getTitle());
            map.put("fileType", library.getFileType());
            map.put("fileSize", library.getFileSize());
            map.put("url", library.getUrl());
            map.put("categoryId", library.getCategoryId());
            map.put("categoryName", library.getCategoryName());
            map.put("content", library.getContent());
            map.put("createTime", library.getCreateTime());
            map.put("updateTime", library.getUpdateTime());
            map.put("introduce", library.getIntroduce());
            map.put("isFree", library.getIsFree());
            map.put("isHide", library.getIsHide());
            map.put("price", library.getPrice());
            map.put("labels", library.getLabels());
            map.put("librarys", library.getLibrarys());
            if (library.getIsFree() == Library.IS_FREE) {
                map.put("isBuy", 1);
            } else {
                //是否已经购买
                LibraryOrder libraryOrder = new LibraryOrder();
                libraryOrder.setOpenid(openid);
                libraryOrder.setLibraryId(library.getId());
                libraryOrder.setPayState(LibraryOrder.ISPAY);
                List<LibraryOrder> libraryOrders = libraryOrderService.select(libraryOrder);
                if (!CollectionUtils.isEmpty(libraryOrders)) {
                    map.put("isBuy", 1);
                } else {
                    map.put("isBuy", 2);
                }
            }
            if (StringUtils.isNotEmpty(library.getCodes())) {
                if (user != null) {
                    int menberLevel = user.getMemberLevel();
                    if (menberLevel != 1) {
                        if (menberLevel == 10) {  //添加了一个经销商,前端页面只有0-9
                            menberLevel = 1;
                        }
                        if (library.getCodes().contains(menberLevel + "")) {
                            map.put("isFree", Library.IS_FREE);
                        }
                    }

                }
            }
        }
        return ResultGenerator.genSuccessResult(map);
    }

    /**
     * 条件查询并分页文库
     *
     * @param page
     * @param limit
     * @param categoryId
     * @param fileType
     * @return
     */
    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "categoryId", required = false) Integer categoryId,
                    @RequestParam(value = "fileType", required = false) String fileType,
                    @RequestParam(value = "isFree", required = false) Integer isFree,
                    @RequestParam(value = "isHide", required = false) Integer isHide,
                    @RequestParam(value = "openid", required = false) String openid,
                    @RequestParam(value = "title", required = false) String title,
                    @RequestParam(value = "isAppleDevice", required = false, defaultValue = "false") boolean isAppleDevice
                    ) {

        PageHelper.startPage(page, limit);
        Condition condition = new Condition(Library.class);
        Example.Criteria criteria = condition.createCriteria();
        condition.setOrderByClause("create_time desc");
        if (isHide != null) {
            criteria.andCondition("is_hide =" + isHide);
        }
        if (StringUtils.isNotEmpty(fileType)) {
            criteria.andCondition("file_type = '" + fileType + "'");
        }

        //未登录用户或者是从苹果设备访问小程序，只查询免费文档
        if (StringUtils.isEmpty(openid) || isAppleDevice){
            criteria.andCondition("is_free =" + 1);
        }else if (isFree != null) {
            criteria.andCondition("is_free =" + isFree);
        }
        if (categoryId != null) {
            criteria.andCondition("category_id =" + categoryId);
        }
        if (title != null) {
            criteria.andCondition("title like '%" + title + "%'");
        }
        List<Library> list = libraryService.findByCondition(condition);
        PageInfo pageInfo = new PageInfo(list);
        //封装

        User user = null;
        if (StringUtils.isNotEmpty(openid)){
            user = userService.findBy("wxid", openid);
        }
        List<Map<String, Object>> list2 = packageLibrary(list, user);
        pageInfo.setList(list2);
        return MapUtil.PageResult(pageInfo);
    }


    /**
     * 搜索关键字
     */
    @GetMapping("findByKeyWord")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "title") String title,
                    @RequestParam(value = "fileType", required = false) String fileType,
                    @RequestParam(value = "openid", required = false) String openid,
                    @RequestParam(value = "categoryId", required = false) Integer categoryId) {
        PageHelper.startPage(page, limit);
        List<Library> list = libraryService.findByKeyWord(title, fileType, categoryId);
        PageInfo pageInfo = new PageInfo(list);
        //封装
        User user = null;
        if (StringUtils.isNotEmpty(openid)){
            user = userService.findBy("wxid", openid);
        }
        List<Map<String, Object>> list2 = packageLibrary(list, user);
        pageInfo.setList(list2);
        return MapUtil.PageResult(pageInfo);
    }


    /**
     * pc文库首页
     */
    @GetMapping("/index")
    public Map index(@RequestParam(value = "type", required = false) Integer type) {

        Map<String, Object> map = new HashMap<>();

        //查找分类
        Condition condition = new Condition(LibraryCategory.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andCondition("is_hide = 1"); //只查询显示的
        condition.setOrderByClause("sort");
        List<LibraryCategory> libraryCategoryList = libraryCategoryService.findByCondition(condition);
        map.put("libraryCategoryList", libraryCategoryList);

        Condition bannerCondition = new Condition(Banner.class);
        Example.Criteria bannerCriteria = bannerCondition.createCriteria();
        bannerCriteria.andCondition("state =" + Banner.IS_USE);
        bannerCriteria.andCondition("type=" + 3);
        List<Banner> libraryBannerList = bannerService.findByCondition(bannerCondition);
        if (!CollectionUtils.isEmpty(libraryBannerList)) {

            if (type != null && type == 2) {  //来自pc端

                for (Banner banner : libraryBannerList) {
                    banner.setImg(banner.getImgpc());
                }
            }
        }
        map.put("libraryBannerList", libraryBannerList);

        return map;
    }

    /**
     * 文件下载
     */
    @RequestMapping(value = "/download")
    public void download(HttpServletResponse response, HttpServletRequest request,
                         @RequestParam(value = "libraryId") Integer libraryId) {
        try {
            String token = request.getHeader("token");
            Integer userId = TokenManagerUtil.getUserId(token);

            //获取文库信息
            Library library = libraryService.findById(libraryId);
            if (library != null) {
                String url = library.getUrl();
                String filename = library.getTitle() + url.substring(url.lastIndexOf("."));
                FileDownloadUtil.download(url, filename, response);
            }
            LibraryDownloadRecord libraryDownloadRecord = new LibraryDownloadRecord();

            libraryDownloadRecord.setUserId(userId);
            libraryDownloadRecord.setLibraryId(library.getId());

            libraryDownloadRecord.setLibraryTitle(library.getTitle());
            libraryDownloadRecord.setFileSize(library.getFileSize());
            libraryDownloadRecord.setFileType(library.getFileType());
            libraryDownloadRecord.setCreateTime(new Date());
            libraryDownloadRecord.setUpdateTime(new Date());
            libraryDownloadRecord.setOpenid(userService.findById(userId).getWxid());
            libraryDownloadRecord.setUrl(library.getUrl());

            //生成下载记录
            libraryDownloadRecordService.save(libraryDownloadRecord);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 封装商品信息
     *
     * @param list
     * @param user
     * @return
     */
    private List<Map<String, Object>> packageLibrary(List<Library> list, User user) {

        //查用户身份
        List<Map<String, Object>> list2 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Library library : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", library.getId());
                map.put("codes", library.getCodes());
                map.put("title", library.getTitle());
                map.put("fileType", library.getFileType());
                map.put("isShare", library.getIsShare());
                map.put("fileSize", library.getFileSize());
                map.put("url", library.getUrl());
                map.put("labels", library.getLabels());
                map.put("categoryId", library.getCategoryId());
                map.put("categoryName", library.getCategoryName());
                map.put("content", library.getContent());
                map.put("createTime", library.getCreateTime());
                map.put("updateTime", library.getUpdateTime());
                map.put("introduce", library.getIntroduce());
                map.put("isFree", library.getIsFree());
                map.put("labels", library.getLabels());
                map.put("librarys", library.getLibrarys());
                if (library.getIsFree() == Library.IS_FREE) {
                    map.put("isBuy", 1);
                } else {
                    map.put("isBuy", 2);
                }
                map.put("isHide", library.getIsHide());
                map.put("price", library.getPrice());
                if (user != null) {
                    if (library.getIsFree() == Library.IS_FREE) {
                        map.put("isBuy", 1);
                    } else {
                        if (StringUtils.isNotEmpty(library.getCodes())) {
                            if (user != null) {
                                int menberLevel = user.getMemberLevel();
                                if (menberLevel == 1) {

                                    //是否已经购买
                                    LibraryOrder libraryOrder = new LibraryOrder();
                                    libraryOrder.setOpenid(user.getWxid());
                                    libraryOrder.setLibraryId(library.getId());
                                    libraryOrder.setPayState(LibraryOrder.ISPAY);
                                    List<LibraryOrder> libraryOrders = libraryOrderService.select(libraryOrder);
                                    if (!CollectionUtils.isEmpty(libraryOrders)) {
                                        map.put("isBuy", 1);
                                    } else {
                                        map.put("isBuy", 2);
                                    }

                                } else {

                                    if (menberLevel == 10) {  //添加了一个经销商,前端页面只有0-9
                                        menberLevel = 1;
                                    }
                                    if (library.getCodes().contains(menberLevel + "")) {
                                        map.put("isFree", Library.IS_FREE);
                                        map.put("isBuy", 1);
                                    } else {
                                        //是否已经购买
                                        LibraryOrder libraryOrder = new LibraryOrder();
                                        libraryOrder.setOpenid(user.getWxid());
                                        libraryOrder.setLibraryId(library.getId());
                                        libraryOrder.setPayState(LibraryOrder.ISPAY);
                                        List<LibraryOrder> libraryOrders = libraryOrderService.select(libraryOrder);
                                        if (!CollectionUtils.isEmpty(libraryOrders)) {
                                            map.put("isBuy", 1);
                                        } else {
                                            map.put("isBuy", 2);
                                        }
                                    }

                                }

                            }
                        }
                    }
                }
                //下载数量
                LibraryDownloadRecord libraryDownloadRecord = new LibraryDownloadRecord();
                libraryDownloadRecord.setLibraryId(library.getId());
                Integer count = libraryDownloadRecordService.selectCount(libraryDownloadRecord);
                map.put("downloadNum", count);
                list2.add(map);
            }
        }
        return list2;
    }

    /**
     * 按条件查找文库
     */
    @GetMapping("select")
    public Result list(@RequestParam(value = "categoryId", required = false) Integer categoryId) {

        Library library = new Library();
        if (categoryId != null) {
            library.setCategoryId(categoryId);
        }
        List<Library> list = libraryService.select(library);

        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 推荐文库(根据用户的标签记录)
     */
    @GetMapping("recommendedLibraries")
    public Map recommendedLibraries(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                    @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                    @RequestParam(value = "userId", required = false) Integer userId,
                                    @RequestParam(value = "fileType", required = false) String fileType,
                                    @RequestParam(value = "isAppleDevice", required = false, defaultValue = "false") boolean isAppleDevice) {

        User user = userService.findById(userId);
        Integer isFree = null;
        if (user == null || user.getIsBind().equals(User.IS_BIND_PHONE) || isAppleDevice) {
            isFree = 1;
        }

        Map<String, Object> map = libraryService.recommendedLibrarys(page, limit, userId, fileType, isFree);
        List<Library> data = (List<Library>) map.get("data");
        List<Map<String, Object>> list = packageLibrary(data, user);
        map.put("data", list);
        return map;
    }

    /**
     * 根据标签查找对应的文库
     */
    @GetMapping("getByLabels")
    public Map getByLabels(String labels) {

        return libraryService.getByLabels(labels);
    }
}
