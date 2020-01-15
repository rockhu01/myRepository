package com.hu.mm.dao;

import com.hu.mm.pojo.Catalog;

import java.util.List;

/**
 * Project：ssm
 * Date：2020/1/2
 * Time：18:22
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
public interface CatalogDao {


    /**
     * 查询目录列表
     * @param courseId
     * @return
     */
    List<Catalog> selectByCourseId(int courseId);
}
