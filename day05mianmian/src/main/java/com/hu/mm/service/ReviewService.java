package com.hu.mm.service;

import com.hu.mm.constants.Constants;
import com.hu.mm.dao.QuestionDao;
import com.hu.mm.dao.ReviewDao;
import com.hu.mm.pojo.Question;
import com.hu.mm.pojo.ReviewLog;
import com.hu.pojo.utils.DateUtils;
import com.hu.pojo.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Project：ssm
 * Date：2020/1/3
 * Time：19:49
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
public class ReviewService {
    /**
     * 审核题目
     * @param reviewLog
     */
    public void add(ReviewLog reviewLog) throws IOException {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        ReviewDao reviewDao = sqlSession.getMapper(ReviewDao.class);
        reviewLog.setCreateDate(DateUtils.parseDate2String(new Date()));

        //调用业务添加对应的信息
        reviewDao.add(reviewLog);

        //添加完成之后需要更新处理关联的表的数据状态
        QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);
        //1.0更新对应的题目状态和审核状态
        if (reviewLog.getStatus()== Constants.QUESTION_REVIEWED){
            //审核通过 , 将题目的状态更改为 已审核 status=1 审核状态更改为 reviewStatus= 1
            Map map = new HashMap();
            map.put("status", Constants.QUESTION_PUBLISHED);
            map.put("reviewStatus",Constants.QUESTION_REVIEWED);
            map.put("id",reviewLog.getQuestionId());
            questionDao.updateByReviewedStatus(map);
        }else if (reviewLog.getStatus()== Constants.QUESTION_REJECT_REVIEW) {
            //审核拒绝 ,将题目的状态更改为 已审核 status=0 审核状态更改为 reviewStatus= 2
            Map map = new HashMap();
            map.put("status", Constants.QUESTION_PRE_PUBLISH);
            map.put("reviewStatus",Constants.QUESTION_REJECT_REVIEW);
            map.put("id",reviewLog.getQuestionId());
            questionDao.updateByReviewedStatus(map);
        }
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
    }
}
