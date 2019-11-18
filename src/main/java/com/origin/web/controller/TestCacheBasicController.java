package com.origin.web.controller;

import com.origin.entity.LoginInfoDto;
import com.origin.service.TestCacheBasicService;
import com.origin.service.UseRedisCacheService;
import com.origin.web.api.TestCacheBasicAPI;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
public class TestCacheBasicController implements TestCacheBasicAPI {

    @Resource
    private TestCacheBasicService testCacheBasicService;
    @Resource
    private UseRedisCacheService useRedisCacheService;

    private  final String  nullResult= "null_dbNoResult";


    public LoginInfoDto testCacheNonTransaction(Integer id){
        LoginInfoDto loginInfoDto = testCacheBasicService.testCacheNonTransaction(id);
        return loginInfoDto;
    }

    public LoginInfoDto testCacheTransaction(Integer id){
        LoginInfoDto loginInfoDto = testCacheBasicService.testCacheNonTransaction(id);
        return loginInfoDto;
    }

    @Override
    public LoginInfoDto testRedisCache(Integer id) {
        //查询缓存
        Object resultObject =  useRedisCacheService.get(id+"");
        System.out.println("id = [" + id + "]"+"::::"+resultObject);
        if(resultObject==null){
            //缓存未命中，查询DB
            resultObject = testCacheBasicService.testCacheNonTransaction(id);
            if(resultObject==null){ //查询DB结果为空，放入缓存，设置缓存时间少
                useRedisCacheService.put(String.valueOf(id),nullResult, 1);
            }else{
                resultObject = (LoginInfoDto)resultObject;
                useRedisCacheService.put(String.valueOf(id),resultObject.getClass().getName(), 20);
            }

        }

        return (LoginInfoDto)resultObject;
    }


}
