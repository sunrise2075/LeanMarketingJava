package com.yipage.leanmarketing.utils;

import com.google.common.reflect.TypeToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 工具类
 *
 * @author cyy
 */
public class Tools {

    public static Type IntegerListType = new TypeToken<List<Integer>>() {
    }.getType();
    public static Type StringListType = new TypeToken<List<String>>() {
    }.getType();

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat format2 = new SimpleDateFormat("yy/MM/dd");
    public static SimpleDateFormat format3 = new SimpleDateFormat("yyy-MM-dd HH:mm");
    public static SimpleDateFormat format4 = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat format5 = new SimpleDateFormat("yyyyMMddhhmmss");
    /**
     * 根据token插入验证码信息
     */
    public static Map<String, String> CodeMap;

    /**
     * 保留小数点后两位
     */
    public static double SaveTwoPoint(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(value));
    }

    //获得授权地址
    public static String getAddress(String state) {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb8fe9657eb40e99f&redirect_uri=http%3a%2f%2fxm-fzw.com%2fUserController%2fwechat_LoginRegist.do&response_type=code&scope=snsapi_userinfo&state=" + state + "#wechat_redirect";//首页
        return url;
    }

    //无需授权获得openid
    public static String getAddress2(String state) {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb8fe9657eb40e99f&redirect_uri=http%3a%2f%2fxm-fzw.com%2fUserController%2fwechat_LoginRegist.do&response_type=code&scope=snsapi_base&state=" + state + "#wechat_redirect";//首页
        return url;
    }

    /**
     * MD5加密
     */
    public static String md5(String password) {
        try {
            StringBuffer buffer = new StringBuffer();
            char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'A', 'B', 'C', 'D', 'E', 'F'};
            byte[] bytes = password.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] targ = md.digest(bytes);
            for (byte b : targ) {
                buffer.append(chars[(b >> 4) & 0x0F]);
                buffer.append(chars[b & 0x0F]);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成唯一的订单号
     */
    public static String creatOrderNumber() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String number = format5.format(timestamp) + (int) (Math.random() * 10) + (int) (Math.random() * 10) + (int) (Math.random() * 10) + (int) (Math.random() * 10) + (int) (Math.random() * 10) + (int) (Math.random() * 10);//时间+N位随机数
        return number;
    }

    public static void main(String[] args) {


    }

    /**
     * @param parameters 请求参数
     * @return
     * @author
     * @date 2016-4-22
     * @Description：将请求参数转换为xml格式的string
     */
    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    public static String getUUID32() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }

    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return
     * @throws Exception
     */
//	public static Map<String, Object> parseXml(HttpServletRequest request)
//			throws Exception {
//
//		// 将解析结果存储在HashMap中
//		Map<String, Object> map = new HashMap<String, Object>();
//
//		// 从request中取得输入流
//		InputStream inputStream = request.getInputStream();
//		// 读取输入流
//		SAXReader reader = new SAXReader();
//		Document document = reader.read(inputStream);
//		// 得到xml根元素
//		Element root = document.getRootElement();
//		// 得到根元素的所有子节点
//		List<Element> elementList = root.elements();
//		// 遍历所有子节点
//		for (Element e : elementList)
//			map.put(e.getName(), e.getText());
//
//		// 释放资源
//		inputStream.close();
//		inputStream = null;
//		return map;
//	}


    //生成uuid
    public static String GetToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    //返回post请求参数列表
    public static String getPostParma(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String[]> params = request.getParameterMap();

            String queryString = "";
            for (String key : params.keySet()) {
                String[] values = params.get(key);
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    queryString += key + "=" + value + "&";
                }
            }


            if (queryString.length() == 0) {
                return null;
            }
            queryString = queryString.substring(0, queryString.length() - 1);

            return queryString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void SetCodeNum(String token, String code) {
        CodeMap = new HashMap<>();
        CodeMap.put(token, code);
    }


    /**
     * 根据token获取验证码
     *
     * @param token
     * @return
     */
    public static String GetCodeNum(String token) {
        //开始拿值
        return CodeMap.get(token);
    }


    /**
     * 判断特殊字符
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     * @Title : FilterStr
     * @Type : FilterString
     * @date : 2014年2月28日 下午11:01:21
     * @Description : 判断特殊字符
     */
    public static String FilterStr(String str) throws PatternSyntaxException {
        /**
         * 特殊字符
         */
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？_]";

        /**
         * Pattern p = Pattern.compile("a*b");
         * Matcher m = p.matcher("aaaaab");
         * boolean b = m.matches();
         */
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);

        /**
         * 返回替换结果
         */
        return mat.replaceAll("").trim();
    }


    //返回返现层数(num表示现有数据长度 i表示返现即n返1中的n)
    public static int GetLayers(int num, int i) {
        if (i == 1) {
            return num;
        } else {
            int copy = num;//  复制需要计算的值
            int j = 1;// 层数
            for (; copy > i; j++) {
                copy = copy / i;
                System.out.println("====" + j + "===" + copy);
            }
            return j + 1;
        }
    }


}




