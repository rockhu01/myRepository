package com.hu.mm.dao;

import com.hu.mm.entity.QueryPageBean;
import com.hu.mm.pojo.Course;

import java.util.List;

/**
 * Project：ssm
 * Date：2019/12/31
 * Time：23:11
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
public interface CourseDao {
    /**
     * 查询分页课程列表
     * @param pageBean
     * @return
     */
    List<Course> findCourseList(QueryPageBean pageBean);

    /**
     * 查询所有课程总数量
     * @param pageBean
     * @return
     */
    Long finTotalCourse(QueryPageBean pageBean);

    /**
     * 添加学科
     * @param course
     */
    void addCourse(Course course);

    /**
     * 根据courseId更新学科
     * @param course
     */
    void updateCourseById(Course course);

    /**
     * 根据id 删除学科
     * @param courseId
     */
    void deleteCourseById(int courseId);

    /**
     * 获取学科列表
     * @return
     */
    List<Course> findAll();

    /**
     * 获取所有学科列表,以及对应的目录和标签列表
     * @return
     */
    List<Course> finListAll();
}
