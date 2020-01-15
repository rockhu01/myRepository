package com.hu.mm.service;

import com.alibaba.fastjson.JSON;
import com.hu.mm.dao.CourseDao;
import com.hu.mm.entity.PageResult;
import com.hu.mm.entity.QueryPageBean;
import com.hu.mm.framework.annotation.RequestMapping;
import com.hu.mm.pojo.Course;
import com.hu.pojo.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

/**
 * Project：ssm
 * Date：2019/12/31
 * Time：23:02
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
public class CourseService {


    /**
     * 分页查询列表
     * @param pageBean
     * @return
     * @throws IOException
     */
    public PageResult findListByPage(QueryPageBean pageBean) throws IOException {
        //调用dao
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        //获得课程总数量
        Long totalCourse =courseDao.finTotalCourse(pageBean);
        System.out.println(totalCourse);
        //获得课程列表
        List<Course> courseList = courseDao.findCourseList(pageBean);

        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        //将查询到的结果封装成 PageResult 对象
        return new PageResult(totalCourse, courseList);
    }

    /**
     * 添加学科
     * @param course
     * @throws IOException
     */
    public void add(Course course) throws IOException {
        //调用dao
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        //获得代理对象后执行响应的添加语句
        
        courseDao.addCourse(course);

        SqlSessionFactoryUtils.commitAndClose(sqlSession);

    }

    /**
     * 更新学科根据id
     * @param course
     * @throws IOException
     */
    public void updateCourse(Course course) throws IOException {
        //调用dao
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        //获得代理对象后执行响应的添加语句

        courseDao.updateCourseById(course);

        SqlSessionFactoryUtils.commitAndClose(sqlSession);


    }

    /**
     * 根据id 删除课程
     * @param courseId
     * @throws IOException
     */
    public void deleteCourse(int courseId) throws IOException {
        //调用dao
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        //获得代理对象后执行响应的添加语句
        courseDao.deleteCourseById(courseId);

        SqlSessionFactoryUtils.commitAndClose(sqlSession);

    }

    /**
     * 获取学科列表
     * @return
     * @throws IOException
     */
    public List<Course> findAll() throws IOException {
        //调用dao
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        //获得代理对象后执行响应的查询语句
        List<Course> courseList = courseDao.findAll();

        SqlSessionFactoryUtils.commitAndClose(sqlSession);

        return courseList;

    }

    /**
     * 获取所有学科列表,以及对应的目录和标签列表
     * @return
     */
    public List<Course> finListAll() throws IOException {
        //调用dao
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        //获得代理对象后执行响应的查询语句
        List<Course> courseList = courseDao.finListAll();
        SqlSessionFactoryUtils.commitAndClose(sqlSession);

        return courseList;
    }
}
