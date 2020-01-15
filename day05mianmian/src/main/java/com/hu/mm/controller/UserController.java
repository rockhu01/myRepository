package com.hu.mm.controller;

import com.alibaba.fastjson.JSON;
import com.hu.mm.entity.Result;
import com.hu.mm.framework.annotation.Controller;
import com.hu.mm.framework.annotation.RequestMapping;
import com.hu.mm.pojo.User;
import com.hu.mm.service.UserService;
import com.hu.pojo.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.PublicKey;

/**
 * Project：ssm
 * Date：2019/12/31
 * Time：18:47
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
@Controller
public class UserController {
    private UserService userService = new UserService();


    @RequestMapping("/user/logout")
    public void logout(HttpServletRequest request , HttpServletResponse response) throws IOException {
        //没有参数,只需要将Session中的user信息清除掉即可
        try {
            request.getSession().removeAttribute("user");
            Result result = new Result(true,"退出成功!" );
            JsonUtils.printResult(response,result );
        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false,"退出失败!" );
            JsonUtils.printResult(response,result );
        }


    }




    /**
     * 用户登录
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/user/login")
    public void login(HttpServletRequest request , HttpServletResponse response) throws IOException {
        try {
            System.out.println("login 收到请求");
            //1.0获得参数
            User loginUser = JsonUtils.parseJSON2Object(request, User.class);
            //2.0调用业务
            User user = userService.login(loginUser);
            //System.out.println(user);
            //3.0 响应页面
            if(user != null){
                //4.0 登陆成功,将user存入 session中,用于整个会话中调用
                request.getSession().setAttribute("user",user);
                Result result = new Result(true,"loginOk" );
                JsonUtils.printResult(response,result );
            }else {
                //4.1登录失败
                Result result = new Result(false,"loginNG" );
                JsonUtils.printResult(response,result );
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false,"服务器异常" );
            JsonUtils.printResult(response,result );
        }
    }
}
