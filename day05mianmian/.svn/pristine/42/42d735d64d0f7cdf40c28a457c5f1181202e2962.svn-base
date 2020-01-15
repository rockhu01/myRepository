package com.hu.mm.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author ：yp
 * @description : 题目POJO类
 * 扩展字段为非数据库表对应字段，用于前端页面显示使用
 * @version: 1.0
 */
@Data
public class Question {
	  private Integer id;		//ID
	  private String number;  	//试题编号
	  private String subject;	//题干
	  private Integer type;		//题目类型： 1. 单选  2. 多选 5. 简答
	  private Integer difficulty;	//难度： 0 简单  1. 一般 2. 困难
	  private String analysis;		//答案解析
	  private String analysisVideo;//答案解析视频
	  private String remark;		//
	  private Integer isClassic;  //是否精选题目 0 不是, 1 是
	  private Integer status;		//题目状态（status）：  0 待发布（待审核、已拒绝）、1 已发布（已审核）、 2 已下架（已审核）
	  private Integer reviewStatus; //审核状态（review_status）：  0 待审核、1 已审核、2 已拒绝
	  private String createDate;    //创建时间
	  private Integer userId;		//用户ID
	  private Integer companyId;  //企业id
	  private Integer catalogId;   //标签ID
	  private Integer courseId;    // 学科ID

	  // 扩展字段
	  private String courseName;            // 学科名称
	  private String usedQty;               // 使用次数
	  private String creator;               // 录入人员

	  private ReviewLog reviewLog;        // 最后一条审核日志

	  private List<String> tagNameList;      // 所属标签名称列表
	  private List<Tag> tagList;            // 所属标签列表
	  private Company company;              // 所属公司
	  private Catalog catalog;              // 所属学科目录
	  private List<QuestionItem> questionItemList; // 题目选项集合

	  // 扩展字段(小程序)
	  private String title;   // 题目标题
	  private Integer grade;  // 难易度
	  private String content; // 内容
	  private String video;    // 视频
	  private String videoPoster; // 视频封面
	  private Integer isFamous;      // 是否名企
	  private Integer answerTag;	// 答案标记
	  private String  answerResult;  // 用户答案
	  private boolean answerIsRight; // 回答是否正确
	  private Integer isFinished;    // 是否完成
	  private Integer isFavorite;    // 是否收藏
	  private List<Tag> tags;              // 标签
	  private Integer memberId;		 // 会员Id
	  private List<QuestionItem> selection; // 选项

	/**
	 * 为选项按字母编号
 	 * @return
	 */
	public List<QuestionItem> getSelection(){
		Character code = 'A';
		if(selection == null) return null;
		for (QuestionItem questionItem:selection){
		  questionItem.setCode(code.toString());
		  code++;
		}
		return selection;
	  }

	/**
	 * 页面需要boolean类型返回
	 * @return
	 */
	public boolean getIsFamous() {
		return isFamous !=null && isFamous==1;
	  }

	/**
	 * 页面需要boolean类型返回
	 * @return
	 */
	  public boolean getIsFinished() {
		return isFinished !=null && isFinished==1;
	  }
	 /**
	 * 页面需要boolean类型返回
	 * @return
	 */
	  public boolean getIsFavorite() {
		return isFavorite!=null && isFavorite==1;
	  }
}
