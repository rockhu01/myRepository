package com.hu.mm.controller;

import com.hu.mm.entity.Result;
import com.hu.mm.framework.annotation.Controller;
import com.hu.mm.framework.annotation.RequestMapping;
import com.hu.mm.pojo.Company;
import com.hu.mm.service.CompanyService;
import com.hu.pojo.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Project：ssm
 * Date：2020/1/2
 * Time：22:01
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
@Controller
public class CompanyController {
    private CompanyService companyService = new CompanyService();

    /**
     * 查询公司行业方向
     * @param request
     * @param response
     * @throws IOException
     */
    
    @RequestMapping("/company/findListAll")
    public void findListAll(HttpServletRequest request , HttpServletResponse response) throws IOException {
        try {
            //调用业务
            List<Company> companyList = companyService.findListAll();

            if (companyList!=null && companyList.size()>0){
                //响应
                Result result = new Result(true,"获取成功",companyList );
                JsonUtils.printResult(response,result );
            }else {
                Result result = new Result(false,"获取失败" );
                JsonUtils.printResult(response,result );
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false,"服务器错误" );
            JsonUtils.printResult(response,result );
        }
    }
}
