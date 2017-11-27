package com.goodsoft.society_zy.util;

import com.google.gson.JsonObject;
import org.codehaus.xfire.client.Client;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 龙宏 on 2017/11/20.
 */
public class UrlUtil {
    public static String url(String url,String code,String startTime,String endTime) throws  Exception{
        Object[]  results =null;
        String result="";
//        try {
//
//           // String endpoint = "http://localhost:3000/hy/gw/queryConsignOrder?wsdl";
//            Service service = new Service();
//            Call call = (Call) service.createCall();
//            call.setTargetEndpointAddress(url);
//            // WSDL里面描述的接口名称(要调用的方法)
//            call.setOperationName("Run");
//            call.setSOAPActionURI("http://www.wdweb.com/Run");
//            // 接口方法的参数名, 参数类型,参数模式  IN(输入), OUT(输出) or INOUT(输入输出)
//            call.addParameter(new QName("RunSoapIn","json"), XMLType.XSD_STRING, ParameterMode.IN);
//            // 设置被调用方法的返回值类型
//            call.setReturnType(XMLType.XSD_STRING);
//            //设置方法中参数的值
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("UserName","Admin");
//            jsonObject.addProperty("Password","Admin");
//            jsonObject.addProperty("ComCode",code);
//            jsonObject.addProperty("StartDate",startTime);
//            jsonObject.addProperty("EndDate",endTime);
//            String json = Base64Util.encode(jsonObject.toString());
//            System.out.println(json);
//          //  String[] str = new  String[]{json};
//            Object[] paramValues = new Object[]{json};
//            // 给方法传递参数，并且调用方法
//            System.out.println(paramValues);
//             result = (String) call.invoke(paramValues);
//            System.out.println("result is " + result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("UserName","Admin");
            jsonObject.addProperty("Password","Admin");
            jsonObject.addProperty("ComCode",code);
            jsonObject.addProperty("StartDate",startTime);
            jsonObject.addProperty("EndDate",endTime);
            String json = Base64Util.encode(jsonObject.toString());
            Client client = new Client(
                    new URL(url));
            client.setEndpointUri("http://www.wdweb.com/Run");
            results = client.invoke("Run", new String[] { json});
             result = results[0].toString();
            System.out.println(results[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
