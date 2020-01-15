package com.hu.mm.controller;

import com.hu.mm.entity.Result;
import com.hu.mm.security.PreAuthorize;
import com.hu.mm.framework.annotation.Controller;
import com.hu.mm.framework.annotation.RequestMapping;
import com.hu.mm.pojo.ReviewLog;
import com.hu.mm.pojo.User;
import com.hu.mm.service.ReviewService;
import com.hu.pojo.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Project：ssm
 * Date：2020/1/3
 * Time：19:36
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
@Controller
public class ReviewController {
    private ReviewService reviewService = new ReviewService();
    /**
     * 在精选试题中审核题目
     * @param request
     * @param response
     */
    @PreAuthorize("QUESTION_REVIEW_UPDATE")
    @RequestMapping("/review/add")
    public void add(HttpServletRequest request , HttpServletResponse response) throws IOException {

        try {
            //获得请求参数
            ReviewLog reviewLog = JsonUtils.parseJSON2Object(request, ReviewLog.class);
            //补全参数
            User user = (User) request.getSession().getAttribute("user");
            if (user != null){
                reviewLog.setId(user.getId());
            }
            //调用业务
            reviewService.add(reviewLog);
            //响应业务
            Result result = new Result(true,"审核成功" );
            JsonUtils.printResult(response,result );
        } catch (IOException e) {
            e.printStackTrace();
            Result result = new Result(false,"审核失败" );
            JsonUtils.printResult(response,result );
        }
    }
}
