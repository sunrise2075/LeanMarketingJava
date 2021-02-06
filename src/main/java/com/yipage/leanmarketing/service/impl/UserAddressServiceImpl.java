package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.UserAddressMapper;
import com.yipage.leanmarketing.model.UserAddress;
import com.yipage.leanmarketing.service.UserAddressService;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/14.
 */
@Service
@Transactional
public class UserAddressServiceImpl extends AbstractService<UserAddress> implements UserAddressService {
    @Resource
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> select(UserAddress userAddress) {
        return userAddressMapper.select(userAddress);
    }

    /**
     * 修改用户的默认地址
     *
     * @param id
     * @param openid
     * @return
     */
    @Override
    public Integer modifyDefaultAddress(Integer id, String openid) {

        UserAddress address = new UserAddress();
        address.setIsDefaultAddress(UserAddress.IS_DEFAULT_ADDRESS);
        address.setOpenid(openid);
        List<UserAddress> userAddressList = userAddressMapper.select(address);
        if (!CollectionUtils.isEmpty(userAddressList)) {
            //更新为不是默认地址
            UserAddress model = new UserAddress();
            model.setId(userAddressList.get(0).getId());
            model.setIsDefaultAddress(UserAddress.NO_DEFAULT_ADDRESS);
            userAddressMapper.updateByPrimaryKeySelective(model);
        }
        //将原来的默认地址修改掉
        UserAddress userAddress = new UserAddress();
        userAddress.setId(id);
        userAddress.setIsDefaultAddress(UserAddress.IS_DEFAULT_ADDRESS);

        return userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }
}
