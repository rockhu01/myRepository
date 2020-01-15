package com.hu.mm.filter;

import com.hu.mm.framework.annotation.Controller;
import com.hu.mm.framework.annotation.RequestMapping;
import com.hu.mm.framework.utils.ClassScannerUtils;
import com.hu.mm.pojo.User;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Project：ssm
 * Date：2020/1/3
 * Time：23:00
 * Description：权限过滤
 *
 * @author huxiongjun
 * @version 1.0
 */
public class SecurityFilter implements Filter {

    Map<String,String> accessMap = new HashMap<>();

    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //强转两个参数
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //获得当前用户的访问路径
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String accessPath = requestURI.substring(contextPath.length());
        if (accessPath.endsWith(".do")){
            accessPath = accessPath.replace(".do","" );
        }

        //====================================
        System.out.println("访问的路径:"+accessPath);


        Set<Map.Entry<String, String>> entries = accessMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println("路径:"+entry.getKey()+"   权限:"+entry.getValue());
        }
        //===========================================


        //判断访问的路径是否在map里面
        String hasPrivilege = accessMap.get(accessPath);

        System.out.println("访问路径从map中查找出来的权限:"+hasPrivilege);

        if (hasPrivilege==null){
            //不在控制的访问权限里面,直接放行
            chain.doFilter(request, response);
            return;
        }
        //判断用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        System.out.println("用户名:"+user.getUsername());
        if (user==null){
            //重定向到登录页面
            response.sendRedirect(request.getContextPath()+"/login.html");
            return;
        }
        //判断用户是否有访问权限
        List<String> authorityList = user.getAuthorityList();
        if (authorityList==null||authorityList.size()==0){
            //没有登录权限
            response.getWriter().print("当前用户权限不足，请切换用户！");
        }

        System.out.println("=========================");
        for (String s : authorityList) {
            System.out.println("用户具有的权限"+s);
        }
        System.out.println("=========================");

        String[] split = hasPrivilege.split(",");
        boolean authFlag = false;
        for (String path : split) {
            if (authorityList.contains(path)){
                authFlag = true;
                break;
            }
        }
        if (authFlag){
            chain.doFilter(request,response );
        } else {
            response.getWriter().print("当前用户权限不足，请切换用户！");
        }
    }

    /*
     * 配置文件:校验页面访问权限      注解:校验操作权限
     * @param config
     * @throws ServletException
     */
    public void init(FilterConfig config) throws ServletException {
        InputStream is = null;
        //在初始化方法中解析配置文件
        try {
            String configLocation = config.getInitParameter("configLocation");
            is = SecurityFilter.class.getClassLoader().getResourceAsStream(configLocation);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(is);
            //获取xml配置文件中的路径以及权限   key:访问路径 value 用户角色
            List<Element> selectNodes = document.selectNodes("//security");
            if (selectNodes!=null&&selectNodes.size()>0){
                for (Element selectNode : selectNodes) {
                    String pattern = selectNode.attributeValue("pattern");
                    String has_role = selectNode.attributeValue("has_role");
                    accessMap.put(pattern, has_role);
                }
            }
            //注解方式实现controller包下面的权限过滤
            Element scanFile = (Element) document.selectSingleNode("/beans/scan");
            String packageName = scanFile.attributeValue("package");
            List<Class<?>> classList = ClassScannerUtils.getClasssFromPackage(packageName);
            if (classList!=null && classList.size()>0){
                for (Class<?> clazz : classList) {
                   if (clazz.isAnnotationPresent(Controller.class)){
                       Method[] methods = clazz.getDeclaredMethods();
                       if (methods!=null&&methods.length>0){
                           for (Method method : methods) {
                               PreAuthorize preAuthorize = method.getAnnotation(PreAuthorize.class);
                               if (preAuthorize!=null){
                                   String requestMappingValue = method.getAnnotation(RequestMapping.class).value();
                                   String preAuthorizeValue = preAuthorize.value();
                                   //将对应的访问路径 和需要有的权限添加到map集合中 key:访问路径 value: 操作权限
                                   accessMap.put(requestMappingValue,preAuthorizeValue );
                               }
                           }
                       }
                   }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
