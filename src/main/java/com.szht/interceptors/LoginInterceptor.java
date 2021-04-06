package com.szht.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.szht.enums.ExceptionEnum;
import com.szht.exceptions.MyException;
import com.szht.utils.JwtUtil;
import com.szht.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private Logger log= LoggerFactory.getLogger(LoginInterceptor.class);

    @Value("${uap.jwt.expire}")
    private long expire;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 前置拦截器
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET,PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //设置跨域响应头
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token, Authorization");
        /**
         * 判断token是否正确 以及失效
         */
        String token = request.getHeader("Authorization");
        log.info("token={}",token);
        //Result data = null;
        if (StringUtils.isEmpty(token)) {
            throw new MyException(ExceptionEnum.NO_PERMISSION_ERROR);
        } else {
            try {
                jwtUtil.verify(token);
                //获取用户id
                String ygbh = jwtUtil.getUsername(token, "ygbh");
                //判断token是否和redis一样  不一样 说明用户多次登录并且使用之前的token登录  应该提示用户使用最新的token
                //如果用户的过去时间已经过去一半 将新的token放在响应头和redis中 前端刷新token
                String tokenRedis = (String) redisUtil.get("uap_ygbh" + ygbh);
                if (token.equals(tokenRedis)) {
                    //token符合要求
                    long expireUidTimeRedis = redisUtil.getExpire("uap_ygbh" + ygbh);
                    log.info("expireUidTimeRedis={}", expireUidTimeRedis);
                    if (expire / 2 > expireUidTimeRedis) {
                        //刷新token
                        HashMap<String, String> usergMap = new HashMap<>();
                        usergMap.put("ygbh", ygbh);
                        usergMap.put("ygmc", jwtUtil.getUsername(token, "ygmc"));
                        //时间少于设置时间的一般 刷新token
                        String newToken = jwtUtil.getToken(usergMap);
                        log.info("刷新token成功={}", newToken);
                        redisUtil.set("uap_ygbh" + ygbh, newToken, expire);
                        response.setHeader("token", newToken);
                        //默认浏览器只会显示部分响应头  这个是暴露响应头为token的键值
                        response.setHeader("Access-Control-Expose-Headers", "token");
                    }
                    return true;
                }
                throw new MyException(ExceptionEnum.TOKEN_NEED_REFRESH);
            } catch (SignatureVerificationException | JWTDecodeException e) {
                //无效的token
                log.info("token=" + token + "无效 错误token");
                log.info("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
                throw new MyException(ExceptionEnum.INVALID_TOKEN);
            } catch (TokenExpiredException e) {
                log.info("token=" + token + "过期");
                throw new MyException(ExceptionEnum.TOKEN_EXPIRED);
            } catch (AlgorithmMismatchException e) {
                log.info("token=" + token + "算法不一致");
                throw new MyException(ExceptionEnum.SYSTEM_INTERNAL_ERROR);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("token=" + token + "解析token异常");
                throw new MyException(ExceptionEnum.SYSTEM_INTERNAL_ERROR);
            }
        }
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
