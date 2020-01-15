package com.hu.mm.controller;

import com.alibaba.fastjson.JSON;
import com.hu.mm.entity.Result;
import com.hu.mm.framework.annotation.Controller;
import com.hu.mm.framework.annotation.RequestMapping;
import com.hu.mm.pojo.Dict;
import com.hu.mm.service.DictService;
import com.hu.pojo.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Project：ssm
 * Date：2020/1/2
 * Time：20:20
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
@Controller
public class DictController {

    private DictService dictService = new DictService();

    @RequestMapping("/dict/findListByType")
    public void findListByType(HttpServletRequest request , HttpServletResponse response) throws IOException {

        try {
            //调用业务
            List<Dict> dictList = dictService.findListByType();
            if (dictList!=null&&dictList.size()>0){
                Result result = new Result(true,"获取成功",dictList );

                JsonUtils.printResult(response,result );
            } else {
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
