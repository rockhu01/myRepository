package com.hu.mm.controller;

import com.hu.mm.entity.Result;
import com.hu.mm.framework.annotation.Controller;
import com.hu.mm.framework.annotation.RequestMapping;
import com.hu.mm.pojo.Industry;
import com.hu.mm.service.IndustryService;
import com.hu.pojo.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Project：ssm
 * Date：2020/1/2
 * Time：19:44
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
@Controller
public class IndustryCntroller {
    private IndustryService industryService = new IndustryService();
    @RequestMapping("/industry/findListAll")
    public void findListAll(HttpServletRequest request , HttpServletResponse response) throws IOException {
        try {
            //调用业务
            List<Industry> industryList = industryService.findListAll();
            if (industryList != null && industryList.size()>0){
                //响应
                Result result = new Result(true,"方向查询成功...",industryList );
                JsonUtils.printResult(response,result );
            }else {
                Result result = new Result(false,"方向查询失败..." );
                JsonUtils.printResult(response,result );
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false,"服务器错误..." );
            JsonUtils.printResult(response,result );
        }


    }

}
