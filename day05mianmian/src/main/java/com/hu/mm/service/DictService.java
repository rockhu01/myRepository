package com.hu.mm.service;

import com.alibaba.fastjson.JSON;
import com.hu.mm.dao.DictDao;
import com.hu.mm.framework.annotation.Controller;
import com.hu.mm.pojo.Dict;
import com.hu.pojo.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

/**
 * Project：ssm
 * Date：2020/1/2
 * Time：20:33
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */

public class DictService {
    /**
     * 获取城市列表
     * @return
     */
    public List<Dict> findListByType() throws IOException {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();

        DictDao dictDao = sqlSession.getMapper(DictDao.class);


        List<Dict> dictList = dictDao.findListByType();

        SqlSessionFactoryUtils.commitAndClose(sqlSession);

        return dictList;

    }
}
