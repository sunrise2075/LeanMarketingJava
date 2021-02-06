package com.yipage.leanmarketing.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.annotation.NeedLoginAnnotation;
import com.yipage.leanmarketing.constant.LeanMarkentingConstant;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.*;
import com.yipage.leanmarketing.service.*;
import com.yipage.leanmarketing.utils.EmojiFilterUtil;
import com.yipage.leanmarketing.utils.MapUtil;
import com.yipage.leanmarketing.utils.MoblieMessageUtil;
import com.yipage.leanmarketing.utils.TokenManagerUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.*;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/10.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final Map<String, Object> messageMap = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;
    @Resource
    private UserSubService userSubService;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private UserBenefitService userBenefitService;
    @Resource
    private AgentService agentService;
    @Resource
    private EmployeeService employeeService;
    @Resource
    private VideoCategoryService videoCategoryService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private ProductOrderService productOrderService;
    @Resource
    private BlackService blackService;
    @Resource
    private ProductService productService;
    @Resource
    private ExamCategoryService examCategoryService;
    @Resource
    private AdministratorService administratorService;
    @Resource
    private NewsService newsService;
    @Resource
    private LeanMarketingService leanMarketingService;
    @Resource
    private MarketingConsultantService marketingConsultantService;
    @Resource
    private ExampleService exampleService;
    @Resource
    private DealerService dealerService;
    @Resource
    private CompanyinfoService companyinfoService;

    /**
     * 自定义方法绑定请求参数的Date类型
     *
     * @param request
     * @param binder
     * @throws Exception
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, true);//true表示允许为空，false反之
        binder.registerCustomEditor(Date.class, editor);
    }

    @PostMapping("add")
    public Result add(User user) {
        userService.save(user);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(@RequestBody User user) {
        User u = userService.findBy("wxid", user.getWxid());
        if (u != null) {
            user.setId(u.getId());
            user.setNickname(EmojiFilterUtil.filterEmoji(user.getNickname()));
            userService.update(user);
        }
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("updateUserDelete/{id}")
    public Result updateUserDelete(@PathVariable Integer id) {

        userService.updateUserDelete(id);

        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        User user = userService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }

    @GetMapping("select")
    public Result select(User user) {
        return ResultGenerator.genSuccessResult(userService.select(user));
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "isBind", required = false) Integer isBind,
                    @RequestParam(defaultValue = "1") Integer isDelete,
                    @RequestParam(value = "identity", required = false) Integer identity,
                    @RequestParam(value = "memberLevel", required = false) Integer memberLevel,
                    @RequestParam(value = "nickname", required = false) String nickname,
                    @RequestParam(value = "phone", required = false) String phone,
                    @RequestParam(value = "province", required = false) String province,
                    @RequestParam(value = "registeredAddress", required = false) String registeredAddress,
                    @RequestParam(value = "superiorId", required = false) Integer superiorId,
                    @RequestParam(value = "dealerId", required = false) Integer dealerId,
                    @RequestParam(value = "year", required = false) Integer year,
                    @RequestParam(value = "month", required = false) Integer month,
                    @RequestParam(value = "day", required = false) Integer day) {

        Condition condition = new Condition(User.class);
        Example.Criteria criteria = condition.createCriteria();

        if (dealerId != null) {
            Dealer dealer = dealerService.findById(dealerId);
            User user = userService.findBy("phone", dealer.getPhone());
            if (user != null) {
                criteria.andCondition("superior_id= '" + user.getId() + "'");
            } else {
                criteria.andCondition("superior_id= -1");
            }
        }
        criteria.andCondition("is_delete != '" + isDelete + "'");
        if (identity != null) {
            criteria.andCondition("identity = '" + identity + "'");
        }
        if (isBind != null) {
            criteria.andCondition("is_bind = '" + isBind + "'");
        }
        if (memberLevel != null) {
            criteria.andCondition("member_level = '" + memberLevel + "'");
        }
        if (StringUtils.isNotEmpty(registeredAddress)) {
            criteria.andCondition("registered_address like '%" + registeredAddress + "%'");
        }
        if (StringUtils.isNotEmpty(phone)) {
            criteria.andCondition("phone like '%" + phone + "%'");
        }
        if (StringUtils.isNotEmpty(province)) {
            criteria.andCondition("registered_address like '%" + province + "%'");
        }
        if (StringUtils.isNotEmpty(nickname)) {
            criteria.andCondition("nickname like '%" + nickname + "%'");
        }
        if (superiorId != null) {
            criteria.andCondition("superior_id= '" + superiorId + "'");
        }
        if (year != null) {
            criteria.andCondition("YEAR(create_time) = '" + year + "'");
        }
        if (month != null) {
            criteria.andCondition("MONTH(create_time) = '" + month + "'");
        }
        if (day != null) {
            criteria.andCondition("DAY(create_time) = '" + day + "'");
        }
        String orderBy = "create_time DESC";
        PageHelper.startPage(page, limit, orderBy);
        List<User> list = userService.findByCondition(condition);
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }

    /**
     * 用户微信授权登录
     */
    @GetMapping("login")
    public Result login(HttpServletRequest request) {

        String code = request.getParameter("code");
        String headimgurl = request.getParameter("headimgurl");
        String city = request.getParameter("city");
        //String nickname = request.getParameter("nickname");
        String nickname = EmojiFilterUtil.filterEmoji(request.getParameter("nickname"));
        String province = request.getParameter("province");
        Integer superiorId = null;
        if (StringUtils.isNotEmpty(request.getParameter("superiorId"))) {
            Integer.parseInt(request.getParameter("superiorId"));
        }
        if (StringUtils.isEmpty(code)) {
            return ResultGenerator.genFailResult("code为空");
        }
        User user = userService.weixinAuthority(code, headimgurl, nickname, province, city, superiorId);

        return ResultGenerator.genSuccessResult(user);
    }

    /**
     * 发送手机验证号
     */
    @GetMapping("sendMessage")
    @Deprecated
    public Result sendMessage(@RequestParam(value = "phone") String phone) {
        JSONObject json = null;
        String code = MoblieMessageUtil.sendRegisterCode(phone);
        if (code != null) {
            json = new JSONObject();

            json.put("verifyNumber", code);
            json.put("createTime", System.currentTimeMillis());
            json.put("sessionId", request.getSession().getId());
            //将其存入session中
            //request.getSession().setAttribute("messageInfo",json);
            messageMap.put(phone + "verifyNumber", json);
            MoblieMessageUtil.removeAttrbute(messageMap, phone + "verifyNumber");
            //5分钟后删除
            return ResultGenerator.genSuccessResult(json);
        }
        return ResultGenerator.genFailResult("验证码获取失败,请重新发送");
    }

    /**
     * 解密并且获取用户手机号码
     *
     * @param encrypdata
     * @param ivdata
     * @param sessionkey
     * @return
     * @throws Exception
     */
    private String decipheringPhoneNo(String encrypdata, String ivdata, String sessionkey) {

        byte[] encrypData = Base64.decode(encrypdata);
        byte[] ivData = Base64.decode(ivdata);
        byte[] sessionKey = Base64.decode(sessionkey);
        try {
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivData);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(sessionKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            //解析解密后的字符串
            String result = new String(cipher.doFinal(encrypData), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject.containsKey("phoneNumber")){
                return jsonObject.getString("phoneNumber");
            }else if (jsonObject.containsKey("purePhoneNumber")){
                return jsonObject.getString("purePhoneNumber");
            }else {
                return StringUtils.EMPTY;
            }
        } catch (Exception e) {
            logger.error("deciphering phone number error", e);
            return StringUtils.EMPTY;
        }

    }

    /**
     * 绑定手机号
     */
    @GetMapping("bindPhone")
    public Result bindPhone(@RequestParam("openId") String openId,
                            @RequestParam("encrypdata") String  encrypdata,
                            @RequestParam("ivdata") String ivdata,
                            @RequestParam("sessionkey") String sessionkey) {
        //验证是否以绑定手机
        User user = userService.findBy("wxid", openId);

        String phoneNo = decipheringPhoneNo(encrypdata, ivdata, sessionkey);
        logger.info("解析得到手机号码是:{}", phoneNo);
        Black black = blackService.findBy("phone", phoneNo);
        if (black != null) {
            return ResultGenerator.genBlackFailResult("该手机号存在于黑名单中");
        }
        //绑定手机
        if (user.getIsBind() == User.IS_BIND_PHONE) {
            //已绑定手机
            return ResultGenerator.genFailResult("已绑定手机");
        }

        //进行绑定手机的操作(更新手机号)
        User model = new User();
        model.setId(user.getId());
        model.setUpdateTime(new Date());
        model.setPhone(phoneNo);
        model.setIsBind(User.IS_BIND_PHONE);

        User user2 = userService.findBy("phone", phoneNo);

        if (user2 != null) {

            if (user2.getSource() == 2) {
                //来自pc端
                //user2.setSource(1);//将其改成小程序端,主要是因为pc端要使用这个openid,并要与小程序同步
                user2.setWxid(openId);

                user2.setNickname(user.getNickname());
                user2.setHeadPortrait(user.getHeadPortrait());

                userService.update(user2);

                //删除原来的数据
                userService.deleteById(user.getId());
            }
            //更新子用户
            UserSub userSub = userSubService.findBy("phone", phoneNo);

            if (userSub != null) {
                UserSub sub = new UserSub();
                sub.setOpenid(user.getWxid());
                sub.setId(userSub.getId());
                userSubService.update(sub);
            } else {

                //添加一个子用户
                UserSub userSub3 = new UserSub();
                userSub3.setOpenid(user.getWxid());
                userSub3.setMemberLevel(1);
                userSub3.setPhone(phoneNo);
                userSub3.setIdentity(User.USER_MENNBER_IDENTITY);
                userSub3.setCreateTime(new Date());
                userSub3.setUpdateTime(new Date());
                userSubService.save(userSub3);
            }
            userService.update(model);
            return ResultGenerator.genSuccessResult();
        }
        //查看子用户中是否存在
        UserSub userSub = userSubService.findBy("phone", phoneNo);
        if (userSub != null) {
            //切换身份
            model.setMemberLevel(userSub.getMemberLevel());
            model.setExpirationTime(userSub.getExpirationTime());
            model.setUserBenefitId(userSub.getBenfitId());
            model.setAgentProvince(userSub.getAgentProvince());
            model.setCompanyName(userSub.getCompanyName());
            model.setName(userSub.getApplyName());

        } else {  //不存在就添加
            //是否是代理商
            Agent agent = agentService.findBy("phone", phoneNo);
            Employee employee = employeeService.findBy("phone", phoneNo);
            Dealer dealer = dealerService.findBy("phone", phoneNo);
            if (agent != null) { //代理商

                if (agent.getExpirationTime().getTime() < System.currentTimeMillis()) { //已过期
                    return ResultGenerator.genFailResult("此代理商已过期");
                } else {  //没有过期
                    model.setIdentity(User.USER_ANGET_IDENTITY);
                    model.setExpirationTime(agent.getExpirationTime());
                    model.setAgentProvince(agent.getProvince());
                    model.setMemberLevel(8);
                    //添加一个子用户
                    UserSub userSub1 = new UserSub();
                    userSub1.setOpenid(user.getWxid());
                    userSub1.setPhone(phoneNo);
                    userSub1.setIdentity(User.USER_ANGET_IDENTITY);
                    userSub1.setCreateTime(new Date());
                    userSub1.setUpdateTime(new Date());
                    userSub1.setExpirationTime(agent.getExpirationTime());
                    userSub1.setMemberLevel(8);
                    userSub1.setAgentProvince(agent.getProvince());
                    userSubService.save(userSub1);
                }

            } else if (dealer != null) {

                //更新管理员
                Administrator conditionAdministrator = new Administrator();
                conditionAdministrator.setIdentityId(dealer.getId());
                conditionAdministrator.setRoleCode(Administrator.Dealer_ROLE);
                List<Administrator> administratorList = administratorService.select(conditionAdministrator);
                if (CollectionUtils.isEmpty(administratorList)) {
                    return ResultGenerator.genFailResult("该经销商的不存在后面管理员身份");
                }
                Administrator administrator = new Administrator();
                administrator.setUserId(user.getId());
                administrator.setId(administratorList.get(0).getId());
                administrator.setUpdateTime(new Date());
                administratorService.update2(administrator);

                model.setIdentity(User.USER_DEALER_IDENTITY);
                model.setMemberLevel(10);
                //添加一个子用户
                UserSub userSub1 = new UserSub();
                userSub1.setOpenid(user.getWxid());
                userSub1.setPhone(phoneNo);
                userSub1.setIdentity(User.USER_DEALER_IDENTITY);
                userSub1.setCreateTime(new Date());
                userSub1.setUpdateTime(new Date());
                userSub1.setMemberLevel(10);
                userSubService.save(userSub1);

            } else if (employee != null) {
                //找到用户信息
                User user1 = userService.findById(employee.getUserId());
                if (user1.getExpirationTime().getTime() < System.currentTimeMillis()) {
                    return ResultGenerator.genFailResult("此企业已过期");
                } else {
                    model.setIdentity(User.USER_COMPANY_IDENTITY);
                    model.setExpirationTime(user1.getExpirationTime());

                    //添加一个子用户
                    UserSub userSub2 = new UserSub();
                    userSub2.setOpenid(user.getWxid());
                    userSub2.setPhone(phoneNo);
                    userSub2.setIdentity(User.USER_COMPANY_IDENTITY);
                    userSub2.setCreateTime(new Date());
                    userSub2.setUpdateTime(new Date());
                    userSub2.setExpirationTime(user1.getExpirationTime());
                    if (employee.getIdentity() == 1) {
                        userSub2.setMemberLevel(9);
                        model.setMemberLevel(9);
                    } else {
                        userSub2.setMemberLevel(0);
                        model.setMemberLevel(0);
                    }
                    userSubService.save(userSub2);
                }
            } else {

                //普通用户
                model.setMemberLevel(1);
                model.setIdentity(User.USER_MENNBER_IDENTITY);
                //添加一个子用户
                UserSub userSub3 = new UserSub();
                userSub3.setOpenid(user.getWxid());
                userSub3.setMemberLevel(1);
                userSub3.setPhone(phoneNo);
                userSub3.setIdentity(User.USER_MENNBER_IDENTITY);
                userSub3.setCreateTime(new Date());
                userSub3.setUpdateTime(new Date());
                userSubService.save(userSub3);
            }
        }
        return ResultGenerator.genSuccessResult(userService.update(model));
    }

    /**
     * 解绑手机号
     */
    @GetMapping("untying")
    public Result untying(@RequestParam(value = "openid") String openid) {

        User user = userService.findBy("wxid", openid);
        if (user == null) {
            return ResultGenerator.genFailResult("查找用户信息发生错误");
        }
        //进行解绑手机的操作(更新手机号)
        User model = new User();
        model.setId(user.getId());
        model.setUpdateTime(new Date());
        model.setPhone("");
        model.setIsBind(User.NO_BIND_PHONE);
        model.setMemberLevel(1);
        model.setUserBenefitId(null);
        Integer result = userService.update(model);
        if (result == 0) {
            return ResultGenerator.genFailResult("更新用户信息发生错误");
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 获取用户信息和默认地址信息
     */
    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestParam(value = "openid") String openid) {

        Map<String, Object> resMap = new HashMap<>();
        User user = userService.findBy("wxid", openid);
        if (user != null) {
            resMap.put("user", user);
            //获取默认地址信息
            UserAddress userAddress = new UserAddress();
            userAddress.setUserId(user.getId());
            userAddress.setIsDefaultAddress(UserAddress.IS_DEFAULT_ADDRESS);
            List<UserAddress> list = userAddressService.select(userAddress);
            if (!CollectionUtils.isEmpty(list)) {
                resMap.put("userAddress", list.get(0));
            } else {
                resMap.put("userAddress", "");
            }
        }
        return ResultGenerator.genSuccessResult(resMap);
    }

    /**
     * 获取用户会员的权益信息
     */
    @GetMapping("getUserBenefitInfo")
    public Result getUserBenefitInfo(@RequestParam(value = "openid") String openid) {
        Map<String, Object> resMap = new HashMap<>();
        User user = userService.findBy("wxid", openid);
        if (user != null) {
            UserBenefit userBenefit = userBenefitService.findById(user.getUserBenefitId());
            resMap.put("userBenefit", userBenefit);
            resMap.put("expirationTime", user.getExpirationTime());
        }
        return ResultGenerator.genSuccessResult(resMap);
    }

    /**
     * 获取所有的会员权益
     *
     * @return
     */
    @GetMapping("getAllBenefitInfo")
    public Result getAllBenefitInfo() {

        Map<String, Object> resMap = new HashMap<>();

        Condition condition = new Condition(UserBenefit.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("type", 1);
        List<UserBenefit> menberBenefitlist = userBenefitService.findByCondition(condition);
        resMap.put("menberBenefitlist", menberBenefitlist);

        Condition condition2 = new Condition(UserBenefit.class);

        Example.Criteria criteria2 = condition2.createCriteria();
        criteria2.andEqualTo("type", 2);
        List<UserBenefit> companyBenefitlist = userBenefitService.findByCondition(condition2);
        resMap.put("companyBenefitlist", companyBenefitlist);

        return ResultGenerator.genSuccessResult(resMap);
    }

    /**
     * 验证手机号验证码是否正确
     */
    @GetMapping("verifyPhone")
    @NeedLoginAnnotation
    public Result verifyPhone(@RequestParam(value = "verifyCode") String verifyCode,
                              @RequestParam(value = "phone") String phone) {
        //获取用户的id
        Integer userId = TokenManagerUtil.getUserId(request.getHeader("token"));

        //检查验证码是否正确
        JSONObject json = (JSONObject) messageMap.get(phone + "verifyNumber");
        String verifyNumber = json.getString("verifyNumber");
        if (StringUtils.isEmpty(verifyNumber)) {
            return ResultGenerator.genFailResult("验证码发送失败啦，请重试");
        }
        if (System.currentTimeMillis() - json.getLong("createTime") > 1000 * 60 * 5) {
            return ResultGenerator.genFailResult("验证码已过期");
        }
        if (!verifyNumber.equals(verifyCode)) {
            return ResultGenerator.genFailResult("验证码不正确");
        }

        Black black = blackService.findBy("phone", phone);
        if (black != null) {
            return ResultGenerator.genFailResult("该手机号存在于黑名单中");
        }
        User user = userService.findById(userId);
        if (user != null) {
            //跟手机号进行对比
            if (!phone.equals(user.getPhone())) {
                User user2 = userService.findBy("phone", phone);
                if (user2 != null) {
                    return ResultGenerator.genFailResult("该手机号已被注册");
                }
            }
        } else {
            return ResultGenerator.genFailResult("获取用户信息失败");
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 绑定企业信息
     */
    @PostMapping("bindCompanyInfo")
    public Result bindCompanyInfo(@RequestBody Map<String, String> map) {

        User user = userService.findBy("wxid", map.get("openid"));
        //绑定手机
//        if(user.getIsBind() == User.IS_BIND_PHONE){
//            //已绑定手机
//            return ResultGenerator.genFailResult("已绑定手机");
//        }
        User user2 = userService.findBy("phone", map.get("phone"));
        if (user2 != null) {
            return ResultGenerator.genFailResult("该手机号已被绑定");
        }
        //检查验证码是否正确

        //JSONObject json= (JSONObject) request.getSession().getAttribute("messageInfo");
        JSONObject json = (JSONObject) messageMap.get(map.get("phone") + "verifyNumber");
        String verifyNumber = json.getString("verifyNumber");
        if (StringUtils.isEmpty(verifyNumber)) {
            return ResultGenerator.genFailResult("验证码发送失败啦，请重试");
        }
        if (System.currentTimeMillis() - json.getLong("createTime") > 1000 * 60 * 5) {
            return ResultGenerator.genFailResult("验证码已过期");
        }
        if (!verifyNumber.equals(map.get("verifyCode"))) {
            return ResultGenerator.genFailResult("验证码不正确");
        }
        //进行绑定手机的操作(更新手机号)
        User model = new User();
        model.setId(user.getId());
        model.setUpdateTime(new Date());
        model.setPhone(map.get("phone"));
        model.setName(map.get("name"));
        model.setCompanyName(map.get("companyName"));
        model.setIsBind(User.IS_BIND_PHONE);
        model.setIdentity(User.USER_COMPANY_IDENTITY);
        //增加一个子用户
        UserSub userSub = new UserSub();
        userSub.setOpenid(user.getWxid());
        userSub.setPhone(map.get("phone"));
        userSub.setIdentity(User.USER_COMPANY_IDENTITY);
        userSub.setCreateTime(new Date());
        userSub.setUpdateTime(new Date());
        userSubService.save(userSub);

        Integer result = userService.update(model);
        if (result == 0) {
            return ResultGenerator.genFailResult("更新用户信息发生错误");
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 进入我的页面
     *
     * @return
     */
    @GetMapping("myIndex")
    public Result myIndex(@RequestParam(value = "openid") String openid) {
        Map<String, Object> resMap = new HashMap<>();
        User user = userService.findBy("wxid", openid);
        if (user != null) {
            User model = userService.findById(user.getId());
            if (model != null) {
                resMap.put("user", model);
                //如果用户是企业身份
                if (model.getIdentity() == User.USER_COMPANY_IDENTITY) {
                    //查询已启用的账号数量
                    Employee employee = new Employee();
                    employee.setUserId(user.getId());
                    employee.setIdentity(Employee.EMPLOYEE_DIRECTOR_IDENTITY);
                    Integer directorNum = employeeService.selectCount(employee);
                    resMap.put("directorNum", directorNum);
                    employee.setIdentity(Employee.EMPLOYEE_CLERK_IDENTITY);
                    Integer clerkNum = employeeService.selectCount(employee);
                    resMap.put("clerkNum", clerkNum);
                }
            }
        }
        return ResultGenerator.genSuccessResult(resMap);
    }

    /**
     * 导出用户
     */
    @GetMapping("exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //第一步查找数据
        Integer identity = null;
        if (StringUtils.isNotEmpty(request.getParameter("identity"))) {
            identity = Integer.parseInt(request.getParameter("identity"));
        }
        Integer memberLevel = null;
        if (StringUtils.isNotEmpty(request.getParameter("memberLevel"))) {
            memberLevel = Integer.parseInt(request.getParameter("memberLevel"));
        }
        Integer superiorId = null;
        if (StringUtils.isNotEmpty(request.getParameter("superiorId"))) {
            superiorId = Integer.parseInt(request.getParameter("superiorId"));
        }
        Integer dealerId = null;
        if (StringUtils.isNotEmpty(request.getParameter("dealerId"))) {
            dealerId = Integer.parseInt(request.getParameter("dealerId"));
            Dealer dealer = dealerService.findById(dealerId);
            User user = userService.findBy("phone", dealer.getPhone());
            if (user != null) {
                superiorId = user.getId();
            } else {
                superiorId = -1;
            }
        }
        String nickname = request.getParameter("nickname");
        String phone = request.getParameter("phone");
        String province = request.getParameter("province");
        String registeredAddress = request.getParameter("registeredAddress");

        List<User> list = (List<User>) this.list(0, 0, 1, 1, identity, memberLevel, nickname, phone, province, registeredAddress, superiorId, dealerId, null, null, null).get("data");

        //第二步数据转成excel

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 第一步：定义一个新的工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步：创建一个Sheet页
        XSSFSheet sheet = wb.createSheet("用户信息");

        sheet.setDefaultRowHeight((short) (2 * 256));//设置行高

        for (int i = 0; i <= 15; i++) {
            sheet.setColumnWidth(i, 4000);//设置列宽
        }

        XSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);

        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("用户id ");
        cell = row.createCell(1);
        cell.setCellValue("微信号 ");
        cell = row.createCell(2);
        cell.setCellValue("是否绑定手机");
        cell = row.createCell(3);
        cell.setCellValue("用户身份");
        cell = row.createCell(4);
        cell.setCellValue("用户昵称");
        cell = row.createCell(5);
        cell.setCellValue("用户头像 ");
        cell = row.createCell(6);
        cell.setCellValue("注册地址 ");
        cell = row.createCell(7);
        cell.setCellValue("手机号 ");
        cell = row.createCell(8);
        cell.setCellValue("会员级别 ");
        cell = row.createCell(9);
        cell.setCellValue("用户会员权益id");
        cell = row.createCell(10);
        cell.setCellValue("到期时间");
        cell = row.createCell(11);
        cell.setCellValue("姓名 ");
        cell = row.createCell(12);
        cell.setCellValue("企业名称");
        cell = row.createCell(13);
        cell.setCellValue("代理商");

        cell = row.createCell(14);
        cell.setCellValue("注册时间");
        cell = row.createCell(15);
        cell.setCellValue("更新时间");

        XSSFRow rows;
        XSSFCell cells;
        for (int i = 0; i < list.size(); i++) {
            // 第三步：在这个sheet页里创建一行
            rows = sheet.createRow(i + 1);
            // 第四步：在该行创建一个单元格
            cells = rows.createCell(0);
            // 第五步：在该单元格里设置值
            cells.setCellValue(list.get(i).getId());
            cells = rows.createCell(1);
            cells.setCellValue(list.get(i).getWxid());
            cells = rows.createCell(2);
            if (list.get(i).getIsBind() == 1) {
                cells.setCellValue("是");
            } else {
                cells.setCellValue("否");
            }
            cells = rows.createCell(3);
            if (list.get(i).getIdentity() == 1) {
                cells.setCellValue("普通会员");
            } else if (list.get(i).getIdentity() == 2) {
                cells.setCellValue("企业会员");
            } else {
                cells.setCellValue("代理商");
            }
            cells = rows.createCell(4);
            cells.setCellValue(list.get(i).getNickname());
            cells = rows.createCell(5);
            cells.setCellValue(list.get(i).getHeadPortrait());
            cells = rows.createCell(6);
            cells.setCellValue(list.get(i).getPhone());
            cells = rows.createCell(7);
            if (list.get(i).getMemberLevel() == 1) {
                cells.setCellValue("普通会员");
            } else if (list.get(i).getMemberLevel() == 2) {
                cells.setCellValue("初级会员");
            } else if (list.get(i).getMemberLevel() == 3) {
                cells.setCellValue("中级会员");
            } else if (list.get(i).getMemberLevel() == 4) {
                cells.setCellValue("高级会员");
            } else if (list.get(i).getMemberLevel() == 5) {
                cells.setCellValue("企业初级会员");
            } else if (list.get(i).getMemberLevel() == 6) {
                cells.setCellValue("企业中级会员");
            } else {
                cells.setCellValue("企业高级会员");
            }
            cells = rows.createCell(8);
            cells.setCellValue(list.get(i).getRegisteredAddress());
            cells = rows.createCell(9);
            if (list.get(i).getUserBenefitId() == null) {
                cells.setCellValue("");
            } else {
                cells.setCellValue(list.get(i).getUserBenefitId());
            }
            cells = rows.createCell(10);

            if (list.get(i).getExpirationTime() != null) {
                cells.setCellValue(simpleDateFormat.format(list.get(i).getExpirationTime()));
            } else {
                cells.setCellValue("");
            }

            cells = rows.createCell(11);
            cells.setCellValue(list.get(i).getName());

            cells = rows.createCell(12);
            cells.setCellValue(list.get(i).getCompanyName());

            cells = rows.createCell(13);
            cells.setCellValue(list.get(i).getAgentProvince());

            cells = rows.createCell(14);
            cells.setCellValue(simpleDateFormat.format(list.get(i).getCreateTime()));
            cells = rows.createCell(15);
            cells.setCellValue(simpleDateFormat.format(list.get(i).getUpdateTime()));
        }

        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            String fileName = "用户信息";
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

    /**
     * pc端登录
     */
    @PostMapping("/pc/login")
    public Result pcLogin(@RequestBody Map<String, String> map) {

        String phone = map.get("phone");
        String verifyCode = map.get("verifyCode");

        Black black = blackService.findBy("phone", phone);
        if (black != null) {
            return ResultGenerator.genFailResult("该手机号存在于黑名单中");
        }
        //检查验证码是否正确
        JSONObject json = (JSONObject) messageMap.get(phone + "verifyNumber");

        if (json != null) {
            String verifyNumber = json.getString("verifyNumber");
            if (StringUtils.isEmpty(verifyNumber)) {
                return ResultGenerator.genFailResult("验证码为空，请重试");
            }
            if (System.currentTimeMillis() - json.getLong("createTime") > 1000 * 60 * 5) {
                return ResultGenerator.genFailResult("验证码已过期");
            }
            if (!verifyNumber.equals(verifyCode)) {
                return ResultGenerator.genFailResult("验证码不正确");
            }
        } else {
            return ResultGenerator.genFailResult("验证码发送失败，请重试");
        }
        //二:查询用户
        User user = userService.findBy("phone", phone);
        if (user == null) {
            //看一下子用户里面有没有
            UserSub userSub = userSubService.findBy("phone", phone);
            user = new User();
            if (userSub == null) {
                //没有就添加一个用户
                //是否是代理商
                Agent agent = agentService.findBy("phone", phone);
                Employee employee = employeeService.findBy("phone", phone);
                Dealer dealer = dealerService.findBy("phone", phone);
                if (agent != null) { //代理商

                    if (agent.getExpirationTime().getTime() < System.currentTimeMillis()) { //已过期
                        return ResultGenerator.genFailResult("此代理商已过期");
                    } else {  //没有过期
                        user.setWxid(System.currentTimeMillis() + "");
                        user.setIsBind(User.IS_BIND_PHONE);
                        user.setNickname(phone);
                        user.setPhone(phone);
                        user.setSource(2);
                        user.setHeadPortrait(LeanMarkentingConstant.headImg);
                        user.setIdentity(User.USER_ANGET_IDENTITY);
                        user.setExpirationTime(agent.getExpirationTime());
                        user.setAgentProvince(agent.getProvince());
                        user.setMemberLevel(8);
                        user.setCreateTime(new Date());
                        user.setAgentProvince(agent.getProvince());
                        user.setIsExpiration(1);
                        user.setExpirationTime(agent.getExpirationTime());

                        userService.save(user);
                        //添加一个子用户
                        UserSub userSub1 = new UserSub();
                        userSub1.setOpenid(user.getWxid());
                        userSub1.setPhone(phone);
                        userSub1.setIdentity(User.USER_ANGET_IDENTITY);
                        userSub1.setCreateTime(new Date());
                        userSub1.setUpdateTime(new Date());
                        userSub1.setExpirationTime(agent.getExpirationTime());
                        userSub1.setMemberLevel(8);
                        userSub1.setAgentProvince(agent.getProvince());
                        userSubService.save(userSub1);
                    }

                } else if (dealer != null) {

                    user.setWxid(System.currentTimeMillis() + "");
                    user.setIsBind(User.IS_BIND_PHONE);
                    user.setNickname(phone);
                    user.setPhone(phone);
                    user.setSource(2);
                    user.setHeadPortrait(LeanMarkentingConstant.headImg);
                    user.setIdentity(User.USER_DEALER_IDENTITY);
                    user.setMemberLevel(10);
                    user.setCreateTime(new Date());

                    userService.save(user);
                    //添加一个子用户
                    UserSub userSub1 = new UserSub();
                    userSub1.setOpenid(user.getWxid());
                    userSub1.setPhone(phone);
                    userSub1.setIdentity(User.USER_DEALER_IDENTITY);
                    userSub1.setCreateTime(new Date());
                    userSub1.setUpdateTime(new Date());
                    userSub1.setMemberLevel(10);
                    userSubService.save(userSub1);

                    //更新管理员
                    Administrator conditionAdministrator = new Administrator();
                    conditionAdministrator.setIdentityId(dealer.getId());
                    conditionAdministrator.setRoleCode(Administrator.Dealer_ROLE);
                    List<Administrator> administratorList = administratorService.select(conditionAdministrator);
                    if (CollectionUtils.isEmpty(administratorList)) {
                        return ResultGenerator.genFailResult("该经销商的不存在后面管理员身份");
                    }
                    Administrator administrator = new Administrator();
                    administrator.setUserId(user.getId());
                    administrator.setId(administratorList.get(0).getId());
                    administrator.setUpdateTime(new Date());
                    administratorService.update(administrator);

                } else if (employee != null) {
                    //找到用户信息
                    User user1 = userService.findById(employee.getUserId());
                    if (user1.getExpirationTime().getTime() < System.currentTimeMillis()) {
                        return ResultGenerator.genFailResult("此企业账号已过期");
                    } else {
                        user.setWxid(System.currentTimeMillis() + "");
                        user.setIsBind(User.IS_BIND_PHONE);
                        user.setNickname(phone);
                        user.setPhone(phone);
                        user.setSource(2);
                        user.setHeadPortrait(LeanMarkentingConstant.headImg);
                        user.setExpirationTime(agent.getExpirationTime());
                        user.setCreateTime(new Date());
                        user.setIsExpiration(1);
                        user.setIdentity(User.USER_COMPANY_IDENTITY);
                        user.setExpirationTime(user1.getExpirationTime());

                        //添加一个子用户
                        UserSub userSub2 = new UserSub();
                        userSub2.setOpenid(user.getWxid());
                        userSub2.setPhone(phone);
                        userSub2.setIdentity(User.USER_COMPANY_IDENTITY);
                        userSub2.setCreateTime(new Date());
                        userSub2.setUpdateTime(new Date());
                        userSub2.setExpirationTime(user1.getExpirationTime());
                        if (employee.getIdentity() == 1) {
                            userSub2.setMemberLevel(9);
                            user.setMemberLevel(9);
                        } else {
                            userSub2.setMemberLevel(0);
                            user.setMemberLevel(0);
                        }
                        userService.save(user);
                        userSubService.save(userSub2);
                    }
                } else {  //普通用户
                    user.setWxid(System.currentTimeMillis() + "");
                    user.setMemberLevel(1);
                    user.setIsBind(User.IS_BIND_PHONE);
                    user.setNickname(phone);
                    user.setPhone(phone);
                    user.setSource(2);
                    user.setHeadPortrait(LeanMarkentingConstant.headImg);
                    user.setCreateTime(new Date());
                    user.setUpdateTime(new Date());
                    user.setIdentity(User.USER_MENNBER_IDENTITY);
                    //添加一个子用户
                    UserSub userSub3 = new UserSub();
                    userSub3.setOpenid(user.getWxid());
                    userSub3.setMemberLevel(1);
                    userSub3.setPhone(phone);
                    userSub3.setIdentity(User.USER_MENNBER_IDENTITY);
                    userSub3.setCreateTime(new Date());
                    userSub3.setUpdateTime(new Date());
                    userService.save(user);
                    userSubService.save(userSub3);
                }

            } else {
                user = userService.findBy("wxid", userSub.getOpenid());
                //有就切换身份
                user.setPhone(phone);
                user.setMemberLevel(userSub.getMemberLevel());
                user.setExpirationTime(userSub.getExpirationTime());
                user.setUserBenefitId(userSub.getBenfitId());
                user.setAgentProvince(userSub.getAgentProvince());
                user.setCompanyName(userSub.getCompanyName());
                user.setName(userSub.getApplyName());

                userService.update(user);
            }
        }
        request.getSession().setAttribute(User.LOGIN_USER_SESSION, user);
        user.setToken(TokenManagerUtil.createToken(user.getId()));
        return ResultGenerator.genSuccessResult(user);
    }

    /**
     * pc端退出登录
     */
    @PostMapping("/pc/loginOut")
    public Result pcLoginOut() {
        request.getSession().removeAttribute(User.LOGIN_USER_SESSION);
        TokenManagerUtil.clearToken(request.getHeader("token"));
        return ResultGenerator.genSuccessResult();
    }

    /**
     * pc首页
     */
    @GetMapping("/pc/index")
    public Result pcIndex() {

        Integer userId = TokenManagerUtil.getUserId(request.getHeader("token"));
        User user = userService.findById(userId);
        Map<String, Object> map = new HashMap<>();

        //用户订单信息
        if (user != null) {
            //查找用户订单信息
            Map<String, Object> userOrderInfo = productOrderService.selectAllOrderStateNum(user.getWxid());
            if (userOrderInfo != null && userOrderInfo.size() > 0) {
                map.put("userOrderInfo", userOrderInfo);
            }
        }
        //视频分类
        Condition c = new Condition(VideoCategory.class);
        Example.Criteria cri = c.createCriteria();
        c.setOrderByClause("sort");
        cri.andCondition("is_hide = " + VideoCategory.IS_SHOW);
        List<VideoCategory> videoCategoryList = videoCategoryService.findByCondition(c);
        map.put("videoCategoryList", videoCategoryList);
        //map.put("videoList", "");
//        map.put("videoContents", "");
//        Integer limit = 6;
//        //查找第一个视频分类里面的视频
//        if(!CollectionUtils.isEmpty(videoCategoryList)){
//            List<Video> videoList = videoService.findVideo(videoCategoryList.get(0).getId(),null,Video.IS_SHOW,limit);
//            //查找视频目录下面的第一个目录下面的第一个视频
//            if(!CollectionUtils.isEmpty(videoList)){
//                map.put("videoList", videoList);
//                Condition condition = new Condition(VideoChapter.class);
//                Example.Criteria criteria = condition.createCriteria();
//                condition.setOrderByClause("sort");
//                criteria.andCondition("video_id="+videoList.get(0).getId());
//                List<VideoChapter> videoChapterList = videoChapterService.findByCondition(condition);
//
//                if (!CollectionUtils.isEmpty(videoChapterList)) {
//                    Condition condition2 = new Condition(VideoChapter.class);
//                    Example.Criteria criteria2 = condition2.createCriteria();
//                    condition2.setOrderByClause("sort");
//                    criteria2.andCondition("chapter_id="+videoChapterList.get(0).getId());
//                    List<VideoContents> videoContentsList = videoContentsService.findByCondition(condition2);
//                    if (!CollectionUtils.isEmpty(videoContentsList)) {
//                        map.put("videoContents", videoContentsList.get(0));
//                    }
//                }
//            }
//        }

        //课程推荐(默认免费)
//        limit = 8;
//        List<Video> videoList2 = videoService.findVideo(null,Video.IS_FREE,Video.IS_SHOW,limit);
//        List<Map<String,Object>> videoList3 = new ArrayList<>();
//        if(!CollectionUtils.isEmpty(videoList2)){
//            for (Video video : videoList2) {
//                Map<String,Object> m = new HashMap<>();
//                m.put("id",video.getId());
//                m.put("authorName",video.getAuthorName());
//                m.put("authorImg",video.getAuthorImg());
//                m.put("description",video.getDescription());
//                m.put("salesNum",video.getSalesNum());
//                m.put("authorIntroduce",video.getAuthorIntroduce());
//                m.put("categoryId",video.getCategoryId());
//                m.put("categoryName",video.getCategoryName());
//                m.put("isHide",video.getIsHide());
//                m.put("createTime",video.getCreateTime());
//                m.put("codes",video.getCodes());
//                m.put("updateTime",video.getUpdateTime());
//                m.put("title",video.getTitle());
//                m.put("coverImg",video.getCoverImg());
//                m.put("isFree",video.getIsFree());
//                m.put("price",video.getPrice());
//                m.put("content",video.getContent());
//
//                if(user!=null){
//                    if(video.getIsFree() == Video.IS_FREE){
//                        m.put("isBuy",1);
//                        //查找观看记录
//                        WatchRecord watchRecord = new WatchRecord();
//                        watchRecord.setVideoId(video.getId());
//                        Integer watchNum =  watchRecordService.selectCount(watchRecord);
//                        m.put("watchNum",watchNum);
//
//                    }else{
//                        //查看购买数量
//                        VideoOrder videoOrder = new VideoOrder();
//                        videoOrder.setVideoId(video.getId());
//                        videoOrder.setPayState(VideoOrder.ISPAY);
//                        Integer buyNum = videoOrderService.selectCount(videoOrder);
//                        m.put("buyNum",buyNum);
//
//                        if(StringUtils.isNotEmpty(video.getCodes())){
//                            if(user!=null){
//                                if(video.getCodes().contains(user.getMemberLevel()+"")){
//                                    m.put("isFree",Library.IS_FREE);
//                                    m.put("isBuy",1);
//                                }else{
//
//                                    //查找是否已经购买该视频
//                                    VideoOrder order = new VideoOrder();
//                                    order.setOpenid(user.getWxid());
//                                    order.setPayState(VideoOrder.ISPAY);
//                                    order.setVideoId(video.getId());
//                                    List<VideoOrder> orderList = videoOrderService.select(order);
//                                    if(!CollectionUtils.isEmpty(orderList)){
//                                        m.put("isBuy",1);
//                                    }else{
//                                        m.put("isBuy",2);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    //评价数量
//                    VideoEvaluate videoEvaluate = new VideoEvaluate();
//                    videoEvaluate.setVideoId(video.getId());
//                    Integer evaluateNum = videoEvaluateService.selectCount(videoEvaluate);
//                    m.put("evaluateNum",evaluateNum);
//                }
//                videoList3.add(m);
//            }
//            for (Video video : videoList2) {
//                Map<String,Object> m = new HashMap<>();
//                m.put("coverImg",video.getCoverImg());
//                m.put("title",video.getTitle());
//
//                if(user!=null){
//                    if(video.getIsFree() == Video.IS_FREE){
//                        m.put("isBuy",1);
//                        //查找观看记录
//                        WatchRecord watchRecord = new WatchRecord();
//                        watchRecord.setVideoId(video.getId());
//                        Integer watchNum =  watchRecordService.selectCount(watchRecord);
//                        m.put("watchNum",watchNum);
//
//                    }else{
//                        //查看购买数量
//                        VideoOrder videoOrder = new VideoOrder();
//                        videoOrder.setPayState(VideoOrder.ISPAY);
//                        Integer buyNum = videoOrderService.selectCount(videoOrder);
//                        map.put("buyNum",buyNum);
//
//                        if(StringUtils.isNotEmpty(video.getCodes())){
//
//                            if(video.getCodes().contains(user.getMemberLevel()+"")){
//                                map.put("isFree",Library.IS_FREE);
//                                map.put("isBuy",1);
//                            }else{
//
//                                //查找是否已经购买该视频
//                                VideoOrder order = new VideoOrder();
//                                order.setOpenid(user.getWxid());
//                                order.setPayState(VideoOrder.ISPAY);
//                                order.setVideoId(video.getId());
//                                List<VideoOrder> orderList = videoOrderService.select(order);
//                                if(!CollectionUtils.isEmpty(orderList)){
//                                    map.put("isBuy",1);
//                                }else{
//                                    map.put("isBuy",2);
//                                }
//                            }
//                        }
//                    }
//                    //评价数量
//                    VideoEvaluate videoEvaluate = new VideoEvaluate();
//                    videoEvaluate.setVideoId(video.getId());
//                    Integer evaluateNum = videoEvaluateService.selectCount(videoEvaluate);
//                    map.put("evaluateNum",evaluateNum);
//                }
//                videoList3.add(m);
//            }
        //       }
//        map.put("videoList2",videoList3);

//        LibraryCategory libraryCategory = new LibraryCategory();
//        libraryCategory.setIsHide(LibraryCategory.IS_SHOW);
//        List<LibraryCategory> libraryCategoryList =  libraryCategoryService.select(libraryCategory);
//        map.put("libraryCategoryList",libraryCategoryList);

        //营销理论考试(免费,收费)
        ExamCategory examCategory = new ExamCategory();
        examCategory.setIsHide(ExamCategory.IS_SHOW);
        List<ExamCategory> examCategorieList = examCategoryService.select(examCategory);
        map.put("examCategorieList", examCategorieList);

        //好书推荐
        int limit = 4;
        List<Product> productList = productService.query(null, null, limit);
        if (!CollectionUtils.isEmpty(productList)) {

            map.put("productList", productList);
        }

        //新闻资讯
        PageHelper.startPage(1, 5, "update_time desc");
        List<News> newsList = newsService.select(null);

        if (!CollectionUtils.isEmpty(newsList)) {
            map.put("newsList", newsList);
        }

        //lean marketing 的数据(4条)
        PageHelper.startPage(1, 4, "create_time desc");
        List<LeanMarketing> leanMarketingList = leanMarketingService.select(null);
        if (!CollectionUtils.isEmpty(leanMarketingList)) {
            map.put("leanMarketingList", leanMarketingList);
        }
        //营销顾问(2条)
        PageHelper.startPage(1, 2, "create_time desc");
        List<MarketingConsultant> marketingConsultantList = marketingConsultantService.select(null);
        if (!CollectionUtils.isEmpty(marketingConsultantList)) {
            map.put("marketingConsultantList", marketingConsultantList);
        }

        //案例展示(4条)
        PageHelper.startPage(1, 4, "create_time desc");
        List<com.yipage.leanmarketing.model.Example> exampleList = exampleService.select(null);
        if (!CollectionUtils.isEmpty(exampleList)) {
            map.put("exampleList", exampleList);
        }
        //公司信息
        Companyinfo companyinfo = companyinfoService.findById(1);
        map.put("companyinfo", companyinfo);
        return ResultGenerator.genSuccessResult(map);

    }

    /**
     * pc首页(未登录)
     */
//    @GetMapping("/pc/index2")
//    public Result pcIndex2() {
//
//        Map<String, Object> map = new HashMap<>();
//
//        //视频分类
//        Condition c = new Condition(VideoCategory.class);
//        Example.Criteria cri = c.createCriteria();
//        c.setOrderByClause("sort desc");
//        cri.andCondition("is_hide = " + VideoCategory.IS_SHOW);
//        List<VideoCategory> videoCategoryList = videoCategoryService.findByCondition(c);
//        map.put("videoCategoryList", videoCategoryList);
//
//        Integer limit = 6;
//        //查找第一个视频分类里面的视频
//        if (!CollectionUtils.isEmpty(videoCategoryList)) {
//            List<Video> videoList = videoService.findVideo(videoCategoryList.get(0).getId(), null, Video.IS_SHOW, limit);
//            //查找视频目录下面的第一个目录下面的第一个视频
//            if (!CollectionUtils.isEmpty(videoList)) {
//                map.put("videoList", videoList);
//                Condition condition = new Condition(VideoChapter.class);
//                Example.Criteria criteria = condition.createCriteria();
//                condition.setOrderByClause("sort");
//                criteria.andCondition("video_id=" + videoList.get(0).getId());
//                List<VideoChapter> videoChapterList = videoChapterService.findByCondition(condition);
//
//                if (!CollectionUtils.isEmpty(videoChapterList)) {
//                    Condition condition2 = new Condition(VideoChapter.class);
//                    Example.Criteria criteria2 = condition2.createCriteria();
//                    condition2.setOrderByClause("sort");
//                    criteria2.andCondition("chapter_id=" + videoChapterList.get(0).getId());
//                    List<VideoContents> videoContentsList = videoContentsService.findByCondition(condition2);
//                    if (!CollectionUtils.isEmpty(videoContentsList)) {
//                        map.put("videoContents", videoContentsList.get(0));
//                    }
//                }
//            }
//        }
//
//        //lean marketing 的数据(4条)
//        PageHelper.startPage(1,4,"create_time desc");
//        List<LeanMarketing> leanMarketingList = leanMarketingService.select(null);
//        if(!CollectionUtils.isEmpty(leanMarketingList)){
//            map.put("leanMarketingList",leanMarketingList);
//        }
//        //营销顾问(2条)
//        PageHelper.startPage(1,2,"create_time desc");
//        List<MarketingConsultant> marketingConsultantList = marketingConsultantService.select(null);
//        if(!CollectionUtils.isEmpty(marketingConsultantList)){
//            map.put("marketingConsultantList",marketingConsultantList);
//        }
//
//        //案例展示(4条)
//        PageHelper.startPage(1,4,"create_time desc");
//        List<com.yipage.leanmarketing.model.Example> exampleList =exampleService.select(null);
//        if(!CollectionUtils.isEmpty(exampleList)){
//            map.put("exampleList",exampleList);
//        }
//        //公司信息
//        Companyinfo companyinfo = companyinfoService.findById(1);
//        map.put("companyinfo",companyinfo);
//
//        //新闻资讯
//        PageHelper.startPage(1,5,"create_time desc");
//        List<News> newsList = newsService.select(null);
//
//        if(!CollectionUtils.isEmpty(newsList)){
//            map.put("newsList",newsList);
//        }
//        return ResultGenerator.genSuccessResult(map);
//    }
    @PostMapping("/updateUserLevel")
    public Result updateUserLevel(User user) {

        return userService.updateUserLevel(user);

    }

    @GetMapping("/test")
    public void test() {


    }

    @GetMapping("/getData")
    public Result getData() {

        Map<String, Object> map = new HashMap<>();

        Companyinfo companyinfo = companyinfoService.findById(1);

        if (companyinfo != null) {
            map.put("loginBgImg", companyinfo.getLoginBgImg());
        }
        return ResultGenerator.genSuccessResult(map);

    }


}
