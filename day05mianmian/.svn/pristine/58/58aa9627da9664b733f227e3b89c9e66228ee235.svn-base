package com.hu.mm.dao;

import com.hu.mm.pojo.Tag;

import java.util.List;
import java.util.Map;

/**
 * Project：ssm
 * Date：2020/1/2
 * Time：19:11
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
public interface TagDao {
    List<Tag> selectByCourseId(int CourseId);

    /**
     * 根据问题id 删除对应的题目选项
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据问题id增加对应的题目选项,参数为map
     * @param map
     */
    void addTagByQuestionId(Map<String, Object> map);
}
