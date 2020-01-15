package com.hu.mm.security;

import com.hu.mm.framework.annotation.Controller;
import com.hu.mm.framework.annotation.RequestMapping;
import com.hu.mm.framework.utils.ClassScannerUtils;
import com.hu.mm.pojo.User;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project：ssm
 * Date：2020/1/4
 * Time：12:34
 * Description：权限过滤器
 *
 * @author huxiongjun
 * @version 1.0
 */

public class MmSecurityFilter implements Filter {
    private Map<String,String> accessMap = new HashMap();
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //强转两个参数
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //处理权限 1.0 获得请求路径
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        //获得访问路径
        String accessPath = requestURI.substring(contextPath.length());
        if (accessPath.endsWith(".do")){
            accessPath = accessPath.replace(".do","" );
        }
        //判断访问路径是否有权限限制
        String hasLimited = accessMap.get(accessPath);
        if (hasLimited==null){
            //没有限制,直接放行
            chain.doFilter(request, response);
            return;
        }
        User user = (User) request.getSession().getAttribute("user");
        //用户为空,切换到登录页面
        if (user==null){
           response.sendRedirect("login.html");
           return;
        }
        List<String> authorityList = user.getAuthorityList();
        //用户权限列表为空,权限不足
        if (authorityList==null || authorityList.size()==0){
            response.getWriter().print("权限不足,请切换用户");
            return;
        }
        boolean authFalg = false;
        String[] split = hasLimited.split(",");
        for (String s : split) {
            if (authorityList.contains(s)){
                authFalg = true;
                break;
            }
        }
        if (authFalg){
            chain.doFilter(request,response );
        }
        else {
            response.getWriter().print("权限不足,请切换用户");
        }
    }

    public void init(FilterConfig config) throws ServletException {
        InputStream is = null;
        try {
            //在初始化方法中读取配置文件
            String configLocate = config.getInitParameter("configLocate");
            is = MmSecurityFilter.class.getClassLoader().getResourceAsStream(configLocate);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(is);
            List<Element> elements = document.selectNodes("//security");
            if (elements!= null &&elements.size()>0){
                for (Element element : elements) {
                    //将配置文件中的 路径  对应账号 添加到map 中
                    String pattern = element.attributeValue("pattern");
                    String has_role = element.attributeValue("has_role");
                    accessMap.put(pattern,has_role );
                }
            }
            //使用注解方式获得controller包下面路径对应的权限
            Element singleNode = (Element) document.selectSingleNode("/beans/scan");
            String packageName = singleNode.attributeValue("package");
            //获得controller 包下所有字节码对象
            List<Class<?>> classList = ClassScannerUtils.getClasssFromPackage(packageName);
            if (classList!=null && classList.size()>0){
                for (Class<?> clazz : classList) {
                    if (clazz.isAnnotationPresent(Controller.class)){
                        Method[] methods = clazz.getDeclaredMethods();
                        if (methods!=null&&methods.length>0){
                            for (Method method : methods) {
                                PreAuthorize preAuthorize = method.getAnnotation(PreAuthorize.class);
                                //不为空,则说明权限需要受到控制
                                if (preAuthorize!=null){
                                    String requestMappingValue = method.getAnnotation(RequestMapping.class).value();
                                    String preAuthorizeValue = preAuthorize.value();
                                    accessMap.put(requestMappingValue,preAuthorizeValue );
                                }
                            }
                        }
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
