package com.szht.interceptors;

import com.auth0.jwt.interfaces.DecodedJWT;
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
                DecodedJWT verify = jwtUtil.verify(token);
                //获取用户id
                String userId = jwtUtil.getUsername(token, "id");
                //判断token是否和redis一样  不一样 说明用户多次登录并且使用之前的token登录  应该提示用户使用最新的token
                //如果用户的过去时间已经过去一半 将新的token放在响应头和redis中 前端刷新token
                String tokenRedis = (String) redisUtil.get("uid" + userId);
                if (token.equals(tokenRedis)) {
                    //token符合要求
                    long expireUidTimeRedis = redisUtil.getExpire("uid" + userId);
                    log.info("expireUidTimeRedis={}", expireUidTimeRedis);
                    if (expire / 2 > expireUidTimeRedis) {
                        //刷新token
                        HashMap<String, String> usergMap = new HashMap<>();
                        usergMap.put("id", userId);
                        usergMap.put("nickname", jwtUtil.getUsername(token, "nickname"));
                        usergMap.put("username", jwtUtil.getUsername(token, "username"));
                        //时间少于设置时间的一般 刷新token
                        String newToken = jwtUtil.getToken(usergMap);
                        log.info("刷新token成功={}", newToken);
                        redisUtil.set("uid" + userId, newToken, expire);
                        response.setHeader("token", newToken);
                        //默认浏览器只会显示部分响应头  这个是暴露响应头为token的键值
                        response.setHeader("Access-Control-Expose-Headers", "token");
                    }
                    return true;
                }

                //和redis的token不一致 说明用户使用了旧的并且没有过期的token 提示使用最新的
                data = Result.error(StaticConstant.CODE_400, "无效凭证，请退出重试或者使用最新的凭证").put("data", null);
            } catch (SignatureVerificationException | JWTDecodeException e) {
                //无效的token
                log.info("token=" + token + "无效 错误token");
                log.info("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
                data = Result.error(StaticConstant.CODE_400, "无效凭证，请重新登录").put("data", null);
            } catch (TokenExpiredException e) {
                log.info("token=" + token + "过期");
                data = Result.error(StaticConstant.CODE_400, "登录以过期，请重新登录").put("data", null);
            } catch (AlgorithmMismatchException e) {
                log.info("token=" + token + "算法不一致");
                data = Result.error(StaticConstant.CODE_500, "服务器异常,请退出重试").put("data", null);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("token=" + token + "解析token异常");
                data = Result.error(StaticConstant.CODE_500, "服务器异常,请退出重试").put("data", null);
            }
        }
        response.setContentType("application/json;charset=utf-8");
        String msgJson = new ObjectMapper().writeValueAsString(data);
        response.getWriter().write(msgJson);
        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
