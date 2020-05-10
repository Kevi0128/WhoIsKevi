package com.kevi.code.interceptor;

import com.kevi.code.utils.KeviTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
public class CustomInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(com.kevi.code.interceptor.CustomInterceptor.class);

    //请求执行前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //返回true才会继续执行（未被拦截）
        String ip = KeviTool.getIpAddress(request);
        String url = request.getRequestURL().toString();
        logger.trace("【拦截器拦截】用户IP：{}，请求：{}", ip, url);
        return true;


//        //返回拦截提示
//        JSONObject result = new JSONObject();
//        result.put("error", "拦截器拦截");
//        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html;charset=UTF-8");//这句话是解决乱码的
//        response.getWriter().write(result.toString());KeviTool
//        response.getWriter().close();
//        return false;
    }

}
