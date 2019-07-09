package com.beemall.sms.service.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ：bee
 * @date ：Created in 2019/7/9 14:06
 * @description：
 * @modified By：
 */
@Component
public class SmsUtil {

    @Value("${aliSms.regionId}")
    private String regionId;

    @Value("${aliSms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliSms.accessSecret}")
    private String accessSecret;


    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    static final String version = "2017-05-25";



    public void sendRegisterMsg(String mobile,String template_code,String sign_name,String param){
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction("SendSms");//3种:QuerySendDetails SendBatchSms SendSms
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", sign_name);
        request.putQueryParameter("TemplateCode", template_code);
        request.putQueryParameter("TemplateParam", param);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
