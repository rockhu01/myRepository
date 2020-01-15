package com.hu.pojo.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description: MyBatis工具类
 * 分析: 1,2,3抽取一个方法获得SqlSession , 6抽取一个方法 close()
 * @Author: yp
 */
public class SqlSessionFactoryUtils {

    private static SqlSessionFactoryBuilder sqlSessionFactoryBuilder;
    private static SqlSessionFactory sqlSessionFactory;

    //1.2步定义在静态代码块里面(配置文件不需要读取一次, 全局只需要一个SqlSessionFactory)
    static {
        //1.读取SqlMapConfig.xml
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream("SqlMapConfig.xml");
            //2.根据SqlMapConfig.xml创建SqlSessionFactory(相当于连接池)
            sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获得SqlSession
     *
     * @return
     * @throws IOException
     */
    public static SqlSession openSqlSession() throws IOException {
        //3.打开SqlSession(相当于Connection)
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }




    /**
     * 提交释放资源
     * @param sqlSession
     */
    public static void commitAndClose(SqlSession sqlSession) {
        if (sqlSession != null) {
            sqlSession.commit();
            sqlSession.close();
        }
    }

    /**
     * 回滚释放资源
     * @param sqlSession
     */
    public static void rollbackAndClose(SqlSession sqlSession) {
        if (sqlSession != null) {
            sqlSession.rollback();
            sqlSession.close();
        }
    }



}
