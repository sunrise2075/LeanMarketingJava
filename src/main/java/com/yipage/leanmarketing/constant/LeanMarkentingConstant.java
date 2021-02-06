package com.yipage.leanmarketing.constant;

/**
 * 项目中的常量
 */
public interface LeanMarkentingConstant {

    //域名
    String domainUrl = "https://pc.leanmarketing.cn";
    //前端域名
    String frontEndUrl = "http://www.leanmarketing.cn";
    //本地
    String localUrl = "http://localhost:8080";

    //微信参数
    String wxAppId = "wx66103085bbb95d80";   //微信appid
    String wxMchId = "1515658341";          //微信商户号
    String wxSecret = "6dfd8805233173b5ce2676b700ebbd66";   //应用秘钥
    String wxKey = "UxeIRVdqyli3N28tiTq5TKoCBjKe6kHF";   //支付密钥

    //获取用户openid
    String getOpenIdAndTokenUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wxAppId + "&secret=" + wxSecret + "&js_code=CODE&grant_type=authorization_code";
    String wxPayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";     //微信统一下单地址
    //获取access_token
    String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + wxAppId + "&secret=" + wxSecret;
    //获取二维码
    String getminiqrQrUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";

    String wxPayRefundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund"; //微信退款地址

    String apiclient_certLocation = "apiclient_cert.p12"; //退款证书位置,放在resources目录下

    //支付宝参数
    String aliAppId = "2019040263766190";
    //这里是支付宝公钥,不是应用公钥
    String aliPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC8l2d/R/Tkh8H7\n" +
            "az6NUrBG3Pvn/W/iK5h0lD0zmvfKHJiBr9uIKzTgMbgE8NWle1a3nm3/YNbqSCxH\n" +
            "ky+72OXBrBZrEPyMJ15DB6SnFPKZyKD9ysb+B7QG4j/COPlVIAxB+WEE/tIFpKs+\n" +
            "VhlqzltntKetH6YP0ozJh+wnLVRi+QtNJSCNgAnL2aTjBQH+7EGgxzcUa8/RT5qr\n" +
            "3at7MVUzC5Tx37LIl5O++OQVd2V6c9jJ3sHGaRhdE7wC3tJSJQt5zZ5hbZyjOYlV\n" +
            "fIiFCuiXE/UYg9GT5UNLytfqoiFm6AGE1dFskLM6Hiz4ZtyE4XR5ICJ4daoQlBOY\n" +
            "9dfMuiYTAgMBAAECggEBALoPIZPIWQpSCYZTcv4Yc0MmZ/yj5Asd+aYiThMos4ia\n" +
            "F7fy2MKJ5fu98ZAmTpGZkp9LVbhbQQ0YsV7YDoLkZSBi+CcfNhnMCUpWMxKILySb\n" +
            "dIGO2i3rSah09HRYo1xhZtZ8A0TbZjcdE7lHVoIyHqm86KR7kekQT8fQKqMoLJYK\n" +
            "7o7racFukyspF/dHaC9MNJupSYdqHVyFyhvb5nf2M+Mgf88JJ0LPLm2ufHXe2zzL\n" +
            "3Q/5FCSEX1CktZX5ZySgS/4Wzb2qTgynM5fKwA4FQhrwtGuouiqy+6YLXaOVF+m5\n" +
            "lD23XfEe8RJETdAoBKzGsJWwS5prTPJhm4Yu4K2dAyECgYEA3iwaIilIVbhSlT51\n" +
            "KIemmOrD/QQwTQWXBdgnJ3HZWHV37YAQ7nBoyLLMQdCy2nzgIG/DAfaWOd5iaNu5\n" +
            "Dyc72maD6kFQVZpW7rwstbpAr7SWLR96A4NXmx1ve56KyhWm+BQ19uTct7pwj2eA\n" +
            "C1I8AoDCOICmT82qUs2dOKRDbZECgYEA2U5grvCRRwllAZXsNiLCFQYMI3j2d/Td\n" +
            "tKpimpbrbianDh/VVpxwuCyBvFTmnuRBrcwHxRhecejWw3kgQSNqAsTnxgsrUIR8\n" +
            "wKLse9qB4FpPYUoZ/tSRiy2/QJoMR9hPRVkHOc9eKqZeFWfsSCE3Oy3bs5RY70v1\n" +
            "QWITxOKC12MCgYA/gj/eMArbZhtFSltXQJ+g6ssnkoD0I3ruUsGuht7oaHvlykwM\n" +
            "vNlXNbUpwyy9RCEVZJ1L/F8pH6bl7plR+ZckcVWYKx95uOrMx8HIGPvQvkCHmY3Z\n" +
            "Xzw1vQ5XOhTWe6YFGFHsx7NIWeU/OQjjM5iv5nqeiDgCa/oaPWiJ/nq/4QKBgQCj\n" +
            "THvpWszBhjTKQUC12d7hb571lTQaJv/ogVYB98HKD+/f9FoccQlfzcpcnayE4ODe\n" +
            "WwHHwi1PCntBf6eWa6eBlzjZIZGWO0mIdaai2CxbTWJzqPzp/BG3dlRPlrP91XaU\n" +
            "lXeRoNyRzl3LVkJAXf2kUizUwXknsnwGe06B2PocIwKBgEyjzdjp3eUJ3kCMUp9l\n" +
            "fiZcIGkAM8YnLp9JqsGDsMUlfQh1P32Azt12+LYeNrlJiUsD3k67kn3pOZVudk18\n" +
            "qkXkZvA5qoxc6yC5RynkLaW81W1Wp13GoAsHe/JbcgJrUKqkBCJhxiVZp9ar6uxV\n" +
            "TpHvD4Um7vlJS9jT7yWIrJN4";
    String aliPayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm9MZ4Z6SUtqSj2DNYjscaNQBmgLzriXNKUjvfQiQNG46l9B9cS9HXlJdoA2fylnaIGze5dZovKC9a6BTZ+Ce6aUWkOyJBa7bLlG4vN6UOQdMgnlOFbvW0DCfpI5d4mm06EpKTa1rpTViZxrdQPYRAKNrW4jFrWdXiwBGeIl6FdI8QdFRqiZCLV1t6VvmZllbFG1GmY/dnPjFeiUzyMLY6NXq2H+yzvdxrhiNinGoauDALaHtIjzZFRhG2xO+iDkJgm2XyaMzG2aZW084aGVTg+c4I752ft8Ksj0CrT4DHMx+iP4I6b+zuHcEWyRbRPIskra5auE70wAfw9CrFruRKQIDAQAB";
    String aliPayUrl = "https://openapi.alipay.com/gateway.do";
    String charset = "UTF-8";
    String format = "json";
    String signType = "RSA2";

    //------------------试题参数------------------//
    //微信试题支付回调url
    String examWeiXiNotifiUrl = domainUrl + "/exam/order/notifyUrl";
    //支付宝试题支付回调
    String examAliNotifyUrl = domainUrl + "/exam/order/aliNotifyUrl";
    //支付宝试题返回页面地址
    String examAliReturnUrl = domainUrl + "/exam/order/aliPayResult";
    //支付宝回调返回页面
    String examAliReturnPage = frontEndUrl + "/online-examIng.html?";


    //------------------文库参数------------------//
    //微信文库支付回调url
    String libraryWeiXiNotifiUrl = domainUrl + "/library/order/notifyUrl";
    //支付宝文库支付回调
    String libraryAliNotifyUrl = domainUrl + "/library/order/aliNotifyUrl";
    //支付宝文库返回页面地址
    String libraryAliReturnUrl = domainUrl + "/library/order/aliPayResult";
    //支付宝回调返回页面
    String libraryAliReturnPage = frontEndUrl + "/library.html?url=";


    //------------------商品参数------------------//
    //微信文库支付回调url
    String productWeiXiNotifiUrl = domainUrl + "/product/order/notifyUrl";
    //支付宝文库支付回调
    String productAliNotifyUrl = domainUrl + "/product/order/aliNotifyUrl";
    //支付宝文库返回页面地址
    String productAliReturnUrl = domainUrl + "/product/order/aliPayResult";
    //支付宝回调返回页面
    String productAliReturnPage = frontEndUrl + "/shop-paid.html?";

    //------------------视频参数------------------//
    //微信视频支付回调url
    String videoWeiXiNotifiUrl = domainUrl + "/video/order/notifyUrl";
    //支付宝文库支付回调
    String videoAliNotifyUrl = domainUrl + "/video/order/aliNotifyUrl";
    //支付宝文库返回页面地址
    String videoAliReturnUrl = domainUrl + "/video/order/aliPayResult";
    //支付宝回调返回页面
    String videoAliReturnPage = frontEndUrl + "/online-video-single.html?videoId=";


    //------------------会员充值参数参数------------------//
    //微信会员支付回调url
    String userWeiXiNotifiUrl = domainUrl + "/user/recharge/order/notifyUrl";
    //支付宝文库支付回调
    String userAliNotifyUrl = domainUrl + "/user/recharge/order/aliNotifyUrl";
    //支付宝文库返回页面地址
    String userAliReturnUrl = domainUrl + "/user/recharge/order/aliPayResult";
    //支付宝回调返回页面
    String userAliReturnPage = frontEndUrl + "/my.html?";

    //------------------拼团微信参数------------------//
    String lessonCollageWeiXiNotifiUrl = domainUrl + "/lesson/collage/order/notifyUrl";

    //------------------文件上传相关参数------------------//
    //七牛云上传参数

    String ACCESS_KEY = "NHdBBcd-j6w-czrZm8pUKNgqoVur3cwR7lkRoHdj";
    String SECRET_KEY = "jlM7dQCc47qvrwyGQooR3GOWAvMDvAucfNDCBXpH";

    //要上传的空间
    String bucketname = "lea-marketing";
    //临时的本地文件
    String localTemporaryPath = "/upload/";
    //上传的地址
//  String uploadAddress = "https://qiniu.yipage.cn/";

    String uploadAddress = "https://qiniu.leanmarketing.cn/";

    //------------------短信相关参数------------------//
    // 产品名称:云通信短信API产品,开发者无需替换
    String product = "Dysmsapi";
    // 产品域名,开发者无需替换
    String domain = "dysmsapi.aliyuncs.com";

    String accessKeyId = "LTAIrYrpLuTyk3Ak";
    String accessKeySecret = "dJ2Gu9CLgtKznOEVroLWbbdSPlPzgR";
    String signName = "精益营销学苑";    //短信签名
    String registTempleteCode = "SMS_155371142";  //注册的短信验证模板
    String teamSuccessTempleteCode = "SMS_165117207";   //拼团成功的短信模板
    String teamFailTempleteCode = "SMS_165117201";    //拼团失败的短信模板

    //------------------物流相关参数------------------//
    String logisticsAppKey = "fe45e55aea1b99781742f1e31b8e4579";
    String logisticsAppCode = "1105391d1ee643bf9280baa57d95ff24";
    String host = "https://api09.aliyun.venuscn.com";
    String path = "/express/trace/query";
    String method = "GET";

    /**
     * 用户默认头像
     */
    String headImg = "https://qiniu.yipage.cn/1547522489648.png";


}
