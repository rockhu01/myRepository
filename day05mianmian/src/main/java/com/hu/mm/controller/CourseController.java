package com.hu.mm.controller;

import com.alibaba.fastjson.JSON;
import com.hu.mm.entity.PageResult;
import com.hu.mm.entity.QueryPageBean;
import com.hu.mm.entity.Result;
import com.hu.mm.framework.annotation.Controller;
import com.hu.mm.framework.annotation.RequestMapping;
import com.hu.mm.pojo.Course;
import com.hu.mm.pojo.User;
import com.hu.mm.service.CourseService;
import com.hu.pojo.utils.DateUtils;
import com.hu.pojo.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Project：ssm
 * Date：2019/12/31
 * Time：22:44
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
@Controller
public class CourseController {

    private CourseService courseService = new CourseService();

    /**
     * 分页查询列表
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/course/findListByPage")
    public void findListByPage(HttpServletRequest request , HttpServletResponse response) throws IOException {
        //1.0获得请求参数
        try {
            QueryPageBean pageBean = JsonUtils.parseJSON2Object(request, QueryPageBean.class);
            if (pageBean!=null){
                //2.0调用业务
                PageResult pageResult  = courseService.findListByPage(pageBean);
                //3.0 响应
                if (pageResult != null ){

                    Result result = new Result(true,"查询成功",pageResult );
                    JsonUtils.printResult(response,result );
                }else {
                    Result result = new Result(false,"查询失败" );
                    JsonUtils.printResult(response,result );
                }
            }else {
                Result result = new Result(false,"前端页面错误..." );
                JsonUtils.printResult(response,result );
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false,"服务器错误..." );
            JsonUtils.printResult(response,result );
        }
    }

    /**
     * 添加学科
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/course/add")
    public void add(HttpServletRequest request , HttpServletResponse response) throws IOException {
        System.out.println("新增学科收到请求...");
        try {
            //1.0 获得请求参数
            Course course = JsonUtils.parseJSON2Object(request, Course.class);
            User user = (User) request.getSession().getAttribute("user");
            System.out.println(JSON.toJSONString(course));
            if (course!=null&&course.getName()!=null && course.getName().trim().length()>0 && course.getIsShow()!=null){
                course.setUserId(user.getId());  //补全添加课程的人员
                String date2String = DateUtils.parseDate2String(new Date());
                course.setCreateDate(date2String);   //补全添加课程的时间
                //2.0 调用业务
                courseService.add(course);

                //3.0 响应
                Result result = new Result(true, "添加成功");
                JsonUtils.printResult(response,result);
            }else {
                Result result = new Result(false,"添加失败" );
                JsonUtils.printResult(response,result );
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false,"服务器错误!" );
            JsonUtils.printResult(response,result );
        }
    }

    /**
     * 修改学科
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/course/update")
    public void update(HttpServletRequest request , HttpServletResponse response) throws IOException {
        System.out.println("更新学科收到请求...");
        try {
            // 1.0 获得请求参数 课程id 课程名字name 是否展示isShow
            Course course = JsonUtils.parseJSON2Object(request, Course.class);
            //1.1 参数是否合理判断
            if(course != null && course.getName()!= null && course.getName().trim().length()>0){
                // 1.2补全course 参数
                User user = (User) request.getSession().getAttribute("user");
                course.setUserId(user.getId());  //更新时对应的修改人需要存进去
                //2.0调用业务
                courseService.updateCourse(course);
                //3.0响应页面
                Result result = new Result(true,"更新成功" );
                JsonUtils.printResult(response, result);
            } else {
                Result result = new Result(false,"前端数据错误" );
                JsonUtils.printResult(response, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false,"服务器错误..." );
            JsonUtils.printResult(response, result);
        }
    }

    /**
     * 删除课程根据id
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/course/delete")
    public void delete(HttpServletRequest request , HttpServletResponse response) throws IOException {
        System.out.println("删除学科收到请求....");
        try {
            //1.0 获得参数
            String id = request.getParameter("id");
            int courseId = Integer.parseInt(id);
            System.out.println(id);
            //2.0 调用业务
            courseService.deleteCourse(courseId);
            //3.0  响应
            Result result = new Result(true,"删除成功" );
            JsonUtils.printResult(response,result);
        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(true,"服务器错误...." );
            JsonUtils.printResult(response,result);
        }
    }

    /**
     * 查询学科列表    /course/findListAll.do
     * @param request
     * @param response
     * @throws IOException
     */

    @RequestMapping("/course/findAll")
    public void findAll(HttpServletRequest request , HttpServletResponse response) throws IOException {

        System.out.println("查询学科列表收到请求....");

        try {
            // 调用业务
            List<Course> courseList = courseService.findAll();

            // 响应
            Result result = new Result(true,"获取列表成功",courseList);
            JsonUtils.printResult(response,result);
        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false,"服务器错误..." );
            JsonUtils.printResult(response,result);
        }
    }

    /**
     * 获取所有学科列表,以及对应的目录和标签列表
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/course/findListAll")
    public void findListAll(HttpServletRequest request , HttpServletResponse response) throws IOException {
        try {
            //调用业务
            List<Course> courseList = courseService.finListAll();
            if (courseList != null && courseList.size() >0){
                //响应
                Result result = new Result(true,"查询学科列表成功",courseList );
                JsonUtils.printResult(response,result );
            }else {
                Result result = new Result(false,"查询学科列表失败" );
                JsonUtils.printResult(response,result );
            }
        } catch (IOException e) {
            e.printStackTrace();
            Result result = new Result(false,"服务器异常...." );
            JsonUtils.printResult(response,result );
        }

    }
}
