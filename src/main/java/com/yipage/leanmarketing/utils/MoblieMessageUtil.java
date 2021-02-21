package com.yipage.leanmarketing.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.yipage.leanmarketing.constant.LeanMarkentingConstant;

import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MoblieMessageUtil {

    // 产品名称:云通信短信API产品,开发者无需替换
//    private static final String product = "Dysmsapi";
//    // 产品域名,开发者无需替换
//    private static final String domain = "dysmsapi.aliyuncs.com";
//
//    // 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
//    private static String accessKeyId = "LTAIGkMohKW5cLtN";
//    private static String accessKeySecret = "ghAk7j6iFRaL3lwJBD2ykUX3z9WXyr";
//
//    private static String signName = "晨光七夕寻宝";    //短信签名
//    //private static String identifyingTempleteCode = "ghAk7j6iFRaL3lwJBD2ykUX3z9WXyr";
//    private static String registTempleteCode = "SMS_140210001";  //注册的短信验证模板

//    public static void init(String accessKeyId, String accessKeySecret, String signName, String identifyingTempleteCode,
//                            String registTempleteCode) {
//        MoblieMessageUtil.accessKeyId = accessKeyId;
//        MoblieMessageUtil.accessKeySecret = accessKeySecret;
//        MoblieMessageUtil.signName = signName;
//        MoblieMessageUtil.identifyingTempleteCode = identifyingTempleteCode;
//        MoblieMessageUtil.registTempleteCode = registTempleteCode;
//    }


    public static String getRandNum() {
        String randNum = new Random().nextInt(1000000) + "";
        if (randNum.length() != 6) {   //如果生成的不是6位数随机数则返回该方法继续生成
            return getRandNum();
        }
        return randNum;
    }

    public static SendSmsResponse sendSms(String mobile, String templateParam, String templateCode)
            throws ClientException {

        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", LeanMarkentingConstant.accessKeyId, LeanMarkentingConstant.accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", LeanMarkentingConstant.product, LeanMarkentingConstant.domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();

        // 必填:待发送手机号
        request.setPhoneNumbers(mobile);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(LeanMarkentingConstant.signName);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);

        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);

        // 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");

        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        // hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

    /**
     * 拼团成功通知
     *
     * @param phone
     * @param name
     * @param lessonName
     * @param beginTime
     * @param address
     * @return
     */
    public static SendSmsResponse sendTeamSuccessNotice(String phone, String name, String lessonName, String beginTime, String address) {
        try {
            return sendSms(phone, "{\"name\":\"" + name + "\", \"lessonName\":\"" + lessonName + "\",\"beginTime\":\"" + beginTime + "\",\"address\":\"" + address + "\"}",
                    LeanMarkentingConstant.teamSuccessTempleteCode);
        } catch (ClientException e) {
            try {
                throw new ClientException(e.getMessage());
            } catch (ClientException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 拼团失败
     *
     * @param phone
     * @param name
     * @param lessonName
     * @return
     */
    public static SendSmsResponse sendTeamFailNotice(String phone, String name, String lessonName) {
        try {
            return sendSms(phone, "{\"name\":\"" + name + "\", \"lessonName\":\"" + lessonName + "\"}",
                    LeanMarkentingConstant.teamSuccessTempleteCode);
        } catch (ClientException e) {
            try {
                throw new ClientException(e.getMessage());
            } catch (ClientException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 注册的短信验证码
     *
     * @param mobile
     * @param
     * @return
     */
    public static String sendRegisterCode(String mobile) {
        try {
            String code = getRandNum();
            SendSmsResponse sendSmsResponse = sendSms(mobile, "{\"code\":\"" + code + "\"}", LeanMarkentingConstant.registTempleteCode);
            if ("OK".equals(sendSmsResponse.getCode())) {
                return code;
            }
        } catch (ClientException e) {
            try {
                throw new ClientException(e.getMessage());
            } catch (ClientException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        // 发短信
        //String code = MoblieMessageUtil.sendRegisterCode("18879736158");
        //System.out.println(code);
        //sendTeamSuccessNotice("18879736158","ghs","测试","2019-09-10 08:00:00","中央西谷大厦");
    }

    /**
     * 设置5分钟后删除session中的验证码
     *
     * @param attrName
     */
    public static void removeAttrbute(Map<String, Object> map, final String attrName) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                map.remove(attrName);//清空全局静态map中对应的验证码
                timer.cancel();
            }
        }, 5 * 60 * 1000);
    }


}
