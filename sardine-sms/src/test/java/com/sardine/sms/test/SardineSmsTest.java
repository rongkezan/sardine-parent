package com.sardine.sms.test;

import com.sardine.sms.prop.AliyunSmsProperties;
import com.sardine.sms.service.SmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author keith
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SardineSmsTest {

    @Autowired
    private AliyunSmsProperties aliyunSmsProperties;

    @Test
    public void test(){
        System.out.println(aliyunSmsProperties.getAccessKeyId());
    }
}
