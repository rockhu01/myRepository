package com.hu.mm.dao;

import com.hu.mm.pojo.QuestionItem;

/**
 * Project：ssm
 * Date：2020/1/3
 * Time：16:55
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
public interface QuestionItemDao {
    /**
     * 新增题目选项
     * @param questionItem
     */
    void add(QuestionItem questionItem);

    /**
     * 更新题目选项
     * @param questionItem
     */
    void update(QuestionItem questionItem);
}
