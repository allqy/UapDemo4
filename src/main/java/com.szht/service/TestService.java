package com.szht.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class TestService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JSONObject test(JSONObject jsonParam){
        JSONObject result = new JSONObject();
        String zmkid=jsonParam.getString("zmkid");
        String mkpx=jsonParam.getString("mkpx");
        //查询
        String selSql="select count(1) from tb_name where mkpx=? and zmkid!=?";
        Long count = jdbcTemplate.queryForObject(selSql, Long.class, mkpx, zmkid);
        if(count>0){ //证明已存在
            result.put("flg","error");
            result.put("error","mkpx已存在，修改失败！");
            return result;
        }
        //修改
        String updateSql="update tb_name set zmkmc=?,zmktb=?,zmkdz=?,zkmzt=?,xxid=?,mkid=?,mkpx=? where zmkid=?";
        int updateRet = jdbcTemplate.update(updateSql, jsonParam.getString("zmkmc"), jsonParam.getString("zmktb"),
                jsonParam.getString("zmkdz"), jsonParam.getString("zkmzt"), jsonParam.getString("xxid"),
                jsonParam.getString("mkid"), jsonParam.getString("mkpx"), jsonParam.getString("zmkid"));
        if(updateRet == 0){
            result.put("flg","error");
            result.put("error","修改失败！");
            return result;
        }
        result.put("flg","success");
        return result;
    }
}
