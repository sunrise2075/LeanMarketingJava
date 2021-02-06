package com.yipage.leanmarketing.controller;

import com.yipage.leanmarketing.model.Agent;
import com.yipage.leanmarketing.model.ProductOrder;
import com.yipage.leanmarketing.model.User;
import com.yipage.leanmarketing.service.AgentService;
import com.yipage.leanmarketing.service.LessonCollageRecordService;
import com.yipage.leanmarketing.service.ProductOrderService;
import com.yipage.leanmarketing.service.UserService;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 定时器控制类
 */
@Controller
public class ScheduledController {

    @Resource
    private UserService userService;

    @Resource
    private AgentService agentService;

    @Resource
    private ProductOrderService productOrderService;

    @Resource
    private LessonCollageRecordService lessonCollageRecordService;


    /**
     * 每天触发用户会员是否过期
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void timer() {


        User query = new User();
        query.setIsExpiration(1);
        List<User> userList = userService.select(query);
        if (!CollectionUtils.isEmpty(userList)) {
            for (User user : userList) {
                if (user.getExpirationTime() != null) {
                    if (new Date().getTime() > user.getExpirationTime().getTime() && user.getIsExpiration() == 1) {
                        userService.deleteByIds(user.getId() + "");
                    }
                }
            }
        }
    }

    /**
     * 每个一小时触发代理商是否过期
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void agentOutTimer() {
        List<Agent> agentList = agentService.findAll();
        if (!CollectionUtils.isEmpty(agentList)) {
            for (Agent agent : agentList) {
                if (agent.getExpirationTime() != null) {
                    if (agent.getExpirationTime().getTime() < System.currentTimeMillis()) { //说明过期了

                        //直接删除这个代理商
                        agentService.deleteByIds(agent.getId() + "");

                    }
                }
            }
        }
    }

    /**
     * 定时任务商品自动收货
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void productAutoReceive() {

        //获取待收货的商品
        ProductOrder order = new ProductOrder();
        order.setPayState(ProductOrder.NEEDRECEIVED);
        List<ProductOrder> list = productOrderService.select(order);

        if (!CollectionUtils.isEmpty(list)) {

            for (ProductOrder productOrder : list) {
                if (productOrder.getAutoReceiveTime().getTime() < System.currentTimeMillis()) {
                    productOrder.setId(productOrder.getId());
                    productOrder.setUpdateTime(new Date());
                    productOrder.setReceiveTime(new Date());
                    productOrder.setPayState(ProductOrder.NOEVALUATE);
                    productOrderService.update(productOrder);
                }
            }
        }
    }

    /**
     * 定时任务处理拼课失败
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void OverdueTimeLessonCollage() {

        lessonCollageRecordService.OverdueTimeLessonCollage();

    }
}
