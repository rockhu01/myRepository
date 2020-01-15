package com.hu.mm.service;

import com.alibaba.fastjson.JSON;
import com.hu.mm.dao.CompanyDao;
import com.hu.mm.pojo.Company;
import com.hu.pojo.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

/**
 * Project：ssm
 * Date：2020/1/2
 * Time：22:13
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
public class CompanyService {
    public List<Company> findListAll() throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CompanyDao companyDao =sqlSession.getMapper(CompanyDao.class);

        List<Company> companyList = companyDao.findAllList();

        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        
        return companyList;
    }
}
