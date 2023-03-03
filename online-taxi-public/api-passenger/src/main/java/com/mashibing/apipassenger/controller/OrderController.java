package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.remote.ServiceOrderClient;
import com.mashibing.apipassenger.service.OrderService;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.internalcommon.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    /**
     * 创建订单/下单
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest){
        System.out.println(orderRequest);
        return orderService.add(orderRequest);
    }

    /**
     * 乘客取消订单
     * @param orderId
     * @return
     */
    @PostMapping("/cancel")
    public ResponseResult cancel(@RequestParam Long orderId){
        return orderService.cancel(orderId);
    }

    @GetMapping("/detail")
    public ResponseResult<OrderInfo> detail(Long orderId){
        return orderService.detail(orderId);
    }

    @GetMapping("/current")
    public ResponseResult<OrderInfo> currentOrder(HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        TokenResult tokenResult = JwtUtils.parseToken(authorization);
        String identity = tokenResult.getIdentity();
        if (!identity.equals(IdentityConstants.PASSENGER_IDENTITY)){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        String phone = tokenResult.getPhone();

        return orderService.currentOrder(phone,IdentityConstants.PASSENGER_IDENTITY);
    }
}
