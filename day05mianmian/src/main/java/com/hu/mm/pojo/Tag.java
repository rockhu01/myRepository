package com.hu.mm.pojo;

import lombok.Data;

/**
 * @author ：yp
 * @description : 标签POJO类
 * 扩展字段为非数据库表对应字段，用于前端页面显示使用
 * @version: 1.0
 */
@Data
public class Tag {
  private Integer id;
  private String name;        // 标签名
  private String createDate;
  private Integer status;       // 0 启动 1 禁用
  private Integer userId;
  private Integer courseId;
  private Integer orderNo;
  // 扩展字段，用于前台显示
  private String creator;   // 创建者
  private Integer questionQty;   // 面试题数量
  private String title;
}
