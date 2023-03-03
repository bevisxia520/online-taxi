package com.mashibing.apiDriver.remote;

import com.mashibing.internalcommon.request.PushRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-sse-push")
public interface ServiceSsePushClient {


    @RequestMapping(method = RequestMethod.POST,value = "/push")
    public String push(@RequestBody PushRequest pushRequest);
}
