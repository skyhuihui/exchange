package com.zag.rest.interceptor;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zag.support.web.handler.JSONResult;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.rest.interceptor
 * @ClassName: TokenInterceptor
 * @Description: 拦截器
 * @Author: skyhuihui
 * @CreateDate: 2018/8/6 17:40
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/6 17:40
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class TokenInterceptor implements HandlerInterceptor{

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView model) throws Exception {
    }

    //拦截每个请求
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        response.setCharacterEncoding("utf-8");
        String token = request.getParameter("token");
        JSONResult jsonResult = new JSONResult();
        //token不存在
        if(null != token) {
            //Login login = Jwt.unsign(token, Login.class);
            String loginId = request.getParameter("loginId");
            //解密token后的loginId与用户传来的loginId不一致，一般都是token过期
//            if(null != loginId && null != login) {
//                if(Integer.parseInt(loginId) == login.getId()) {
//                    return true;
//                }
//                else{
//                    jsonResult = new JSONResult(500, "登录验证错误");
//                    responseMessage(response, response.getWriter(), jsonResult);
//                    return false;
//                }
//            }
//            else
//            {
//                jsonResult = new JSONResult(500, "登录验证错误");
//                responseMessage(response, response.getWriter(), jsonResult);
//                return false;
//            }
            return false;
        }
        else
        {
            jsonResult = new JSONResult(500, "登录验证错误");
            responseMessage(response, response.getWriter(), jsonResult);
            return false;
        }
    }

    //请求不通过，返回错误信息给客户端
    private void responseMessage(HttpServletResponse response, PrintWriter out, JSONResult jsonResult) {
        jsonResult = new JSONResult(500, "登录验证错误");
        response.setContentType("application/json; charset=utf-8");
        String json = JSONObject.toJSONString(jsonResult);
        out.print(json);
        out.flush();
        out.close();
    }
}