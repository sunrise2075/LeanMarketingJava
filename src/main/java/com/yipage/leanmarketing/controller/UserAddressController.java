package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.annotation.NeedLoginAnnotation;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.User;
import com.yipage.leanmarketing.model.UserAddress;
import com.yipage.leanmarketing.service.UserAddressService;
import com.yipage.leanmarketing.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by CodeGenerator on 2018/12/14.
 */
@NeedLoginAnnotation
@RestController
@RequestMapping("/user/address")
public class UserAddressController {
    @Resource
    private UserAddressService userAddressService;

    @Resource
    private UserService userService;

    @PostMapping("add")
    public Result add(@RequestBody UserAddress userAddress) {
        User user = userService.findBy("wxid", userAddress.getOpenid());
        if (user != null) {
            userAddress.setUserId(user.getId());
        }
        userAddress.setCreateTime(new Date());
        userAddress.setUpdateTime(new Date());
        userAddressService.save(userAddress);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userAddressService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    //pc端用的delete方法会报403
    @GetMapping("/delete/{id}")
    public Result delete2(@PathVariable Integer id) {
        userAddressService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(@RequestBody UserAddress userAddress) {
        userAddress.setUpdateTime(new Date());
        userAddressService.update(userAddress);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        UserAddress userAddress = userAddressService.findById(id);
        return ResultGenerator.genSuccessResult(userAddress);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit) {
        PageHelper.startPage(page, limit);
        List<UserAddress> list = userAddressService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/select")
    public Result listAll(@RequestParam(value = "openid") String openid) {
        UserAddress userAddress = new UserAddress();
        userAddress.setOpenid(openid);
        return ResultGenerator.genSuccessResult(userAddressService.select(userAddress));
    }


    /**
     * 修改用户的默认地址
     */
    @GetMapping("modifyDefaultAddress/{id}/{openid}")
    public Result modifyDefaultAddress(@PathVariable(value = "id") Integer id,
                                       @PathVariable(value = "openid") String openid) {
        return ResultGenerator.genSuccessResult(userAddressService.modifyDefaultAddress(id, openid));
    }

}
