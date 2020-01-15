package com.hu.mm.dao;

import com.hu.mm.entity.QueryPageBean;
import com.hu.mm.pojo.Question;

import java.util.List;
import java.util.Map;

/**
 * Project：ssm
 * Date：2020/1/1
 * Time：18:48
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
public interface QuestionDao {
    /**
     * 查询所有问题列表集合
     * @param pageBean
     * @return
     */
    List<Question> findQuestionList(QueryPageBean pageBean);

    /**
     * 查询问题总数
     * @param pageBean
     * @return
     */
    Long findTotalQuestion(QueryPageBean pageBean);

    /**
     * 新增题目
     * @param question
     */
    void add(Question question);

    /**
     * 更新题目
     * @param question
     */
    void update(Question question);

    /**
     * 根据审核状态跟新题目对应信息
     * @param map
     */
    void updateByReviewedStatus(Map map);
}
