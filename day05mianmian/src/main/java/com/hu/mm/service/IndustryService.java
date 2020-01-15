package com.hu.mm.service;

import com.hu.mm.dao.IndustryDao;
import com.hu.mm.pojo.Industry;
import com.hu.pojo.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

/**
 * Project：ssm
 * Date：2020/1/2
 * Time：19:46
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
public class IndustryService {
    /**
     * 获得行业方向列表
     * @return
     */
    public List<Industry> findListAll() throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        IndustryDao industryDao = sqlSession.getMapper(IndustryDao.class);
        List<Industry> industryList = industryDao.findListAll();
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return industryList;
    }
}
