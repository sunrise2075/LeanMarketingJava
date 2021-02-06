package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.*;
import com.yipage.leanmarketing.service.*;
import com.yipage.leanmarketing.utils.MapUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.*;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/10.
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    private ProductService productService;
    @Resource
    private ProductCategoryService productCategoryService;
    @Resource
    private BannerService bannerService;
    @Resource
    private ProductEvaluateService productEvaluateService;
    @Resource
    private UserCollectionProductService userCollectionProductService;

    @PostMapping("add")
    public Result add(Product product) {
        productService.save(product);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        productService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("update")
    public Result update(Product product) {
        productService.update(product);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam(value = "id") Integer id,
                         @RequestParam(value = "openid", required = false) String openid) {
        Map<String, Object> resMap = new HashMap<>();

        Product product = productService.findById(id);
        resMap.put("product", product);

        if (openid != null) {
            //查找是否已经收藏该商品
            UserCollectionProduct userCollectionProduct = new UserCollectionProduct();
            userCollectionProduct.setOpenid(openid);
            userCollectionProduct.setProductId(id);
            List<UserCollectionProduct> collectionList = userCollectionProductService.select(userCollectionProduct);
            if (!CollectionUtils.isEmpty(collectionList)) {
                resMap.put("isCollection", 1);
                resMap.put("userCollectionProduct", collectionList.get(0));
            } else {
                resMap.put("isCollection", 2);
            }
        } else {
            //查商品分类详情
            ProductCategory productCategory = productCategoryService.findById(product.getCategoryId());
            if (productCategory != null) {
                resMap.put("productCategory", productCategory);
            }
        }
        Condition condition = new Condition(ProductEvaluate.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andCondition("product_id=" + product.getId());
        List<ProductEvaluate> evaluates = productEvaluateService.findByCondition(condition);

        criteria.andCondition("star_num > 3");
        List<ProductEvaluate> evaluateList = productEvaluateService.findByCondition(condition);

        Integer rate = 0;
        if (evaluates.size() != 0) {
            rate = (evaluateList.size() * 100 / evaluates.size());
        } else {
            rate = 100;
        }
        //好评率
        resMap.put("rate", rate);

        //获取商品的评价数量
        ProductEvaluate productEvaluate = new ProductEvaluate();
        productEvaluate.setProductId(id);
        Integer count = productEvaluateService.selectCount(productEvaluate);
        resMap.put("evaluateCount", count);
        //获取评价的前两条(按星的等级)
        List<Map<String, Object>> list = productEvaluateService.orderByStarNumLimit2(id);
        resMap.put("evaluateList", list);
        return ResultGenerator.genSuccessResult(resMap);
    }

    /**
     * 分页查询商品列表
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "state", required = false) Integer state,
                    @RequestParam(value = "isHide", required = false) Integer isHide,
                    @RequestParam(value = "isFree", required = false) Integer isFree,
                    @RequestParam(value = "categoryId", required = false) Integer categoryId,
                    @RequestParam(value = "authorName", required = false) String authorName,
                    @RequestParam(value = "name", required = false) String name) {

        Condition condition = new Condition(Video.class);
        Example.Criteria criteria = condition.createCriteria();

        String orderBy = "create_time DESC";
        PageHelper.startPage(page, limit, orderBy);
        if (state != null) {
            criteria.andCondition("state = " + state);
        }
        if (isHide != null) {
            criteria.andCondition("is_hide = " + isHide);
        }
        if (categoryId != null) {
            criteria.andCondition("category_id = " + categoryId);
        }
        if (isFree != null) {
            criteria.andCondition("is_free = " + isFree);
        }
        if (StringUtils.isNotEmpty(authorName)) {
            criteria.andCondition("author_name like  '%" + authorName + "%'");
        }
        if (StringUtils.isNotEmpty(name)) {
            criteria.andCondition("name like  '%" + name + "%'");
        }
        List<Product> list = productService.findByCondition(condition);

        List<Map<String, Object>> list2 = packageProduct(list);

        PageInfo pageInfo = new PageInfo(list2);
        return MapUtil.PageResult(pageInfo);
    }

    /**
     * 按条件查找商品
     */
    @GetMapping("select")
    public Result list(@RequestParam(value = "categoryId", required = false) Integer categoryId) {

        Product product = new Product();
        if (product != null) {
            product.setCategoryId(categoryId);
        }
        List<Product> list = productService.select(product);
        List<Map<String, Object>> list2 = packageProduct(list);
        return ResultGenerator.genSuccessResult(list2);
    }

    private List<Map<String, Object>> packageProduct(List<Product> list) {
        List<Map<String, Object>> list2 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Product product : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", product.getId());
                map.put("salesNum", product.getSalesNum());
                map.put("introduction", product.getIntroduction());
                map.put("suit", product.getSuit());
                map.put("wordNumber", product.getWordNumber());
                map.put("bookNumber", product.getBookNumber());
                map.put("publishingHouse", product.getPublishingHouse());
                map.put("authorIntroduce", product.getAuthorIntroduce());
                map.put("authorImg", product.getAuthorImg());
                map.put("authorName", product.getAuthorName());
                map.put("description", product.getDescription());
                map.put("price", product.getPrice());
                map.put("address", product.getAddress());
                map.put("bannerImg", product.getBannerImg());
                map.put("recommendImg", product.getRecommendImg());
                map.put("coverImg", product.getCoverImg());
                map.put("categoryName", product.getCategoryName());
                map.put("title", product.getTitle());
                map.put("name", product.getName());
                map.put("categoryId", product.getCategoryId());
                map.put("createTime", product.getCreateTime());
                map.put("isHide", product.getIsHide());
                map.put("pageNumber", product.getPageNumber());
                map.put("publishTime", product.getPublishTime());
                map.put("state", product.getState());
                map.put("updateTime", product.getUpdateTime());
                map.put("updateTime", product.getUpdateTime());
                map.put("isFree", product.getIsFree());

                Condition condition = new Condition(ProductEvaluate.class);
                Example.Criteria criteria = condition.createCriteria();
                criteria.andCondition("product_id=" + product.getId());
                List<ProductEvaluate> evaluates = productEvaluateService.findByCondition(condition);

                criteria.andCondition("star_num > 3");
                List<ProductEvaluate> evaluateList = productEvaluateService.findByCondition(condition);

                Integer rate = 0;
                if (evaluates.size() != 0) {
                    rate = evaluateList.size() / evaluates.size() * 100;
                } else {
                    rate = 100;
                }
                //好评率
                map.put("rate", rate);
                list2.add(map);
            }

        }
        return list2;
    }

    /**
     * 商品首页
     */
    @GetMapping("mallIndex")
    public Map mallIndex(@RequestParam(defaultValue = "12") Integer limit,
                         @RequestParam(required = false) Integer type) {

        Map<String, Object> resMap = new HashMap<>();

        //轮播图
        Banner banner = new Banner();
        banner.setState(Banner.IS_USE);
        banner.setType(1);
        List<Banner> bannerList = bannerService.select(banner);
        if (!CollectionUtils.isEmpty(bannerList)) {
            if (type != null && type == 2) {
                for (Banner banner1 : bannerList) {
                    banner1.setImg(banner1.getImgpc());
                }
            }
        }
        resMap.put("bannerList", bannerList);

        //查出所有分类
        ProductCategory productCategory = new ProductCategory();
        productCategory.setIsHide(ProductCategory.IS_SHOW);
        List<ProductCategory> ProductCategoryList = productCategoryService.select(productCategory);
        List<Map<String, Object>> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(ProductCategoryList)) {

            for (ProductCategory category : ProductCategoryList) {
                Map<String, Object> map = new HashMap<>();
                map.put("categoryId", category.getId());
                map.put("categoryName", category.getName());
                //默认查12条
                List<Product> productList = productService.query(category.getId(), null, limit);
                List<Map<String, Object>> list2 = packageProduct(productList);
                map.put("productList", list2);
                list.add(map);
            }
        }
        resMap.put("list", list);
        return resMap;
    }

    /**
     * 随机推荐
     *
     * @return
     */
    @GetMapping("randomRecommend")
    public Result randomRecommend(@RequestParam(defaultValue = "3") Integer limit) {

        List<Product> randomRecommendList = productService.randomRecommend(limit);
        List<Map<String, Object>> list2 = packageProduct(randomRecommendList);
        return ResultGenerator.genSuccessResult(list2);
    }

    /**
     * 导出商品信息
     */
    @GetMapping("exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Condition condition = new Condition(Video.class);
        Example.Criteria criteria = condition.createCriteria();
        //第一步查找数据
        String authorName = request.getParameter("authorName");
        String isHide = request.getParameter("isHide");
        String state = request.getParameter("state");
        String categoryId = request.getParameter("categoryId");
        condition.setOrderByClause("update_time desc");
        if (StringUtils.isNotEmpty(isHide)) {
            criteria.andCondition("is_hide = " + isHide);
        }
        if (StringUtils.isNotEmpty(state)) {
            criteria.andCondition("state = " + state);
        }
        if (StringUtils.isNotEmpty(categoryId)) {
            criteria.andCondition("category_id = " + categoryId);
        }
        if (StringUtils.isNotEmpty(authorName)) {
            criteria.andCondition("author_name like  '%" + authorName + "%'");
        }
        List<Product> list = productService.findByCondition(condition);

        //第二步数据转成excel
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 第一步：定义一个新的工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步：创建一个Sheet页
        XSSFSheet sheet = wb.createSheet("商品信息");

        sheet.setDefaultRowHeight((short) (2 * 256));//设置行高

        for (int i = 0; i <= 22; i++) {
            sheet.setColumnWidth(i, 4000);//设置列宽
        }

        XSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);

        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("商品id ");
        cell = row.createCell(1);
        cell.setCellValue("商品名称 ");
        cell = row.createCell(2);
        cell.setCellValue("商品标题");
        cell = row.createCell(3);
        cell.setCellValue("分类名称");
        cell = row.createCell(4);
        cell.setCellValue("封面图链接");
        cell = row.createCell(5);
        cell.setCellValue("商品详情图链接");
        cell = row.createCell(6);
        cell.setCellValue("地址 ");
        cell = row.createCell(7);
        cell.setCellValue("价格");
        cell = row.createCell(8);
        cell.setCellValue("商品描述 ");
        cell = row.createCell(9);
        cell.setCellValue("作者名称");
        cell = row.createCell(10);
        cell.setCellValue("作者头像链接");
        cell = row.createCell(11);
        cell.setCellValue("作者简介");
        cell = row.createCell(12);
        cell.setCellValue("出版社");
        cell = row.createCell(13);
        cell.setCellValue("国际标准书号");
        cell = row.createCell(14);
        cell.setCellValue("页数");
        cell = row.createCell(15);
        cell.setCellValue("字数(万)");

        cell = row.createCell(16);
        cell.setCellValue("是否套装");
        cell = row.createCell(17);
        cell.setCellValue("销售数量");
        cell = row.createCell(18);
        cell.setCellValue("是否隐藏");
        cell = row.createCell(19);
        cell.setCellValue("商品状态");
        cell = row.createCell(20);
        cell.setCellValue("出版时间");
        cell = row.createCell(21);
        cell.setCellValue("创建时间");
        cell = row.createCell(22);
        cell.setCellValue("更新时间");

        XSSFRow rows;
        XSSFCell cells;
        for (int i = 0; i < list.size(); i++) {
            Product product = list.get(i);
            // 第三步：在这个sheet页里创建一行
            rows = sheet.createRow(i + 1);
            // 第四步：在该行创建一个单元格
            cells = rows.createCell(0);
            // 第五步：在该单元格里设置值
            cells.setCellValue(list.get(i).getId());
            cells = rows.createCell(1);
            cells.setCellValue(product.getName());
            cells = rows.createCell(2);
            cells.setCellValue(product.getTitle());

            cells = rows.createCell(3);
            cells.setCellValue(product.getCategoryName());

            cells = rows.createCell(4);
            cells.setCellValue(product.getCoverImg());
            cells = rows.createCell(5);
            cells.setCellValue(product.getBannerImg());
            cells = rows.createCell(6);
            cells.setCellValue(product.getAddress());
            cells = rows.createCell(7);
            cells.setCellValue(product.getPrice() + "");
            cells = rows.createCell(8);
            cells.setCellValue(product.getDescription());
            cells = rows.createCell(9);
            cells.setCellValue(product.getAuthorName());
            cells = rows.createCell(10);
            cells.setCellValue(product.getAuthorImg());
            cells = rows.createCell(11);
            cells.setCellValue(product.getAuthorIntroduce());
            cells = rows.createCell(12);
            cells.setCellValue(product.getPublishingHouse());
            cells = rows.createCell(13);
            cells.setCellValue(product.getBookNumber());

            cells = rows.createCell(14);
            cells.setCellValue(product.getPageNumber());
            cells = rows.createCell(15);
            cells.setCellValue(product.getWordNumber());
            cells = rows.createCell(16);
            if (product.getSuit() == 1) {
                cells.setCellValue("是");
            } else {
                cells.setCellValue("否");
            }
            cells = rows.createCell(17);
            cells.setCellValue(product.getSalesNum());
            cells = rows.createCell(18);
            if (product.getIsHide() == Product.IS_SHOW) {
                cells.setCellValue("显示");
            } else {
                cells.setCellValue("隐藏");
            }
            cells = rows.createCell(19);
            if (product.getState() == Product.STATE_UP) {
                cells.setCellValue("上架");
            } else {
                cells.setCellValue("下架");
            }
            cells = rows.createCell(20);
            cells.setCellValue(product.getPublishTime());
            cells = rows.createCell(21);
            cells.setCellValue(simpleDateFormat.format(product.getCreateTime()));
            cells = rows.createCell(22);
            cells.setCellValue(simpleDateFormat.format(product.getUpdateTime()));
        }

        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            String fileName = "商品信息";
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes("gb2312"), StandardCharsets.ISO_8859_1) + ".xlsx"); //支持中文文件名
            response.setContentType("multipart/form-data");
            OutputStream ouputStream = response.getOutputStream();
            wb.write(ouputStream);

            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
