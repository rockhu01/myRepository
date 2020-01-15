package com.hu.mm.controller;

import com.alibaba.fastjson.JSON;
import com.hu.mm.entity.PageResult;
import com.hu.mm.entity.QueryPageBean;
import com.hu.mm.entity.Result;
import com.hu.mm.framework.annotation.Controller;
import com.hu.mm.framework.annotation.RequestMapping;
import com.hu.mm.pojo.Question;
import com.hu.mm.pojo.User;
import com.hu.mm.service.QuestionService;
import com.hu.pojo.utils.JsonUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Project：ssm
 * Date：2020/1/1
 * Time：17:33
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
@Controller
public class QuestionControl {
    private QuestionService questionService = new QuestionService();
    /**
     * 分页查询基础题库
     * @param request
     * @param response
     */
    @RequestMapping("/question/findListByPage")
   public void findListByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("分页查询基础题库收到请求....");
        try {
            //1.0 获得请求参数
            QueryPageBean pageBean = JsonUtils.parseJSON2Object(request, QueryPageBean.class);
            System.out.println("pageBean:"+JSON.toJSONString(pageBean));
            if (pageBean == null){
                pageBean.setCurrentPage(1);
                pageBean.setPageSize(10);
                pageBean.setOffset(0);
            }
            //2.0调用业务
            PageResult pageResult = questionService.findListByPage(pageBean);
            if (pageResult != null) {
                //3.1 成功的话响应页面
                System.out.println("pageResult:"+JSON.toJSONString(pageResult));

                Result result = new Result(true,"分页查询题库成功",pageResult );
                JsonUtils.printResult(response,result);
            }else{
                //3.2 失败的话响应页面
                Result result = new Result(false,"分页查询题库失败" );
                JsonUtils.printResult(response,result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //3.2 失败的话响应页面
            Result result = new Result(false,"服务器异常" );
            JsonUtils.printResult(response,result);
        }
    }

    /**
     * 提交或者更新题目信息
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/question/addOrUpdate")
    public void addOrUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            Question question = JsonUtils.parseJSON2Object(request, Question.class);
            //获得对应的登录用户信息
            User user = (User) request.getSession().getAttribute("user");
            if (user != null){
                question.setUserId(user.getId());
            }
            //调用业务
            questionService.addOrUpdate(question);
            //响应
            Result result = new Result(true, "新增或更新题目成功!!");
            JsonUtils.printResult(response,result );
        } catch (IOException e) {
            e.printStackTrace();
            Result result = new Result(false, "服务器错误");
            JsonUtils.printResult(response,result );
        }
    }
}
