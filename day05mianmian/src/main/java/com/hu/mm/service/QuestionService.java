package com.hu.mm.service;

import com.hu.mm.constants.Constants;
import com.hu.mm.dao.QuestionDao;
import com.hu.mm.dao.QuestionItemDao;
import com.hu.mm.dao.TagDao;
import com.hu.mm.entity.PageResult;
import com.hu.mm.entity.QueryPageBean;
import com.hu.mm.pojo.Question;
import com.hu.mm.pojo.QuestionItem;
import com.hu.mm.pojo.Tag;
import com.hu.pojo.utils.DateUtils;
import com.hu.pojo.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project：ssm
 * Date：2020/1/1
 * Time：18:42
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
public class QuestionService {
    /**
     * 分页查询基础题库
     * @param pageBean
     * @return
     * @throws IOException
     */
    public PageResult findListByPage(QueryPageBean pageBean) throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);
        //获得基础题库列表
        List<Question> questionList = questionDao.findQuestionList(pageBean);
        System.out.println("查询出来的基础题库列表大小:"+questionList.size());
        //获得基础题库总数量
        Long total = questionDao.findTotalQuestion(pageBean);
        //将查询结构封装成pageResult对象
        PageResult pageResult = new PageResult(total,questionList);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return pageResult;
    }

    /**
     * 新增或者更新题目
     * @param question
     */
    public void addOrUpdate(Question question) throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        //1.0保存或者更新题目
        //处理业务,根据题目id 来决定是更新还是新增
        QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);
        
        if (question.getId()== null || question.getId()==0){
            //新增题目  补全数据
            question.setCreateDate(DateUtils.parseDate2String(new Date()));
            question.setStatus(Constants.QUESTION_PRE_PUBLISH);
            question.setReviewStatus(Constants.QUESTION_PRE_REVIEW);
            //调用dao
            questionDao.add(question);
        }else {
            //更新题目
            questionDao.update(question);
        }

        //2.0 保存或者更新题目选项  ======================
        List<QuestionItem> questionItemList = question.getQuestionItemList();

        QuestionItemDao questionItemDao = sqlSession.getMapper(QuestionItemDao.class);
        if (questionItemList!=null && questionItemList.size()>0){
            //遍历题目选项
            for (QuestionItem questionItem : questionItemList) {
                //如果选项为空,跳过
                if (questionItem==null || "".equals(question.getContent())){
                   continue;
                }
                //获得题目id,并保存在对应的题目中
                questionItem.setQuestionId(question.getId());
                //判断是保存还是更新
                if (question.getId()==null || question.getId()==0){
                    //保存
                    questionItemDao.add(questionItem);
                }else {
                    //更新
                    questionItemDao.update(questionItem);
                }
            }
        }
        //3.0保存更新标签信息  ===========================
        TagDao tagDao = sqlSession.getMapper(TagDao.class);
        //3.1删除之前的标签对应的题目id 信息,在保存现在的对应关系
        tagDao.deleteById(question.getId());
        //3.2 新建关系    多对多的关系
        List<Tag> tagList = question.getTagList();
        if (tagList!= null && tagList.size()>0){
            for (Tag tag : tagList) {
                Map<String,Object> map = new HashMap<>();
                map.put("questionId",question.getId());
                map.put("tagId",tag.getId() );
                tagDao.addTagByQuestionId(map);
            }
        }
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
    }
}
