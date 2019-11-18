package com.origin.web.controller;

import com.origin.entity.LoginInfoDto;
import com.origin.service.TestCacheBasicService;
import com.origin.service.UseRedisCacheService;
import com.origin.web.api.TestCacheProblemAPI;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class TestCacheProblemController implements TestCacheProblemAPI{

    @Resource
    private TestCacheBasicService testCacheBasicService;
    @Resource
    private UseRedisCacheService useRedisCacheService;

    private  final String  nullResult= "null_dbNoResult";

    @Override
    public LoginInfoDto testCachePenetrate(Integer id) {
        Object resultObject = useRedisCacheService.get(id + "");
        //先查询缓存，缓存返回对象为空
        if(resultObject==null) {
            //查询DB，对象为空，空值放入缓存，设缓存过期时间较短
            resultObject = testCacheBasicService.testCacheNonTransaction(id);
            if(resultObject==null){
                useRedisCacheService.put(String.valueOf(id), nullResult, 5);
            }else{
                useRedisCacheService.put(String.valueOf(id), resultObject, 20);
            }
        }
        return (LoginInfoDto)resultObject;
    }

    @Override
    public LoginInfoDto testCacheSnowSlide(Integer id) {
        Object resultObject = useRedisCacheService.get(id + "");
        //先查询缓存，缓存返回对象为空
        if(resultObject==null) {
            //查询DB，对象为空，空值放入缓存，设缓存过期时间较短
            resultObject = testCacheBasicService.testCacheNonTransaction(id);
            if(resultObject==null){
                useRedisCacheService.put(String.valueOf(id), nullResult, 5);
            }else{
                //查看属于哪一类商品，设置一定的缓存有效时间，加上随机因子，分散缓存过期时间
                int randomNum = (int)(Math.random()*3+1);
                if("张三".equals(resultObject.getClass().getName())){
                    randomNum = randomNum + 2;
                }else if("李四".equals(resultObject.getClass().getName())){
                    randomNum = randomNum + 6;
                }
                useRedisCacheService.put(String.valueOf(id), resultObject, randomNum);
            }
        }
        return (LoginInfoDto)resultObject;
    }

    @Override
    public LoginInfoDto testCachePuncture(Integer id) {
        //热点key，将key分散在每个分布缓存
        //判断当前key是否在本地缓存，如果是返回查询结果
        //如果当前key不在本地缓存，通过hash散列算法，随机访问查询其中的一个分布缓存

        return null;
    }


    @Override
    public void testCausePenetrate(Integer id) {
        final int idNo = id;
        try {
            while (true){
                new Thread() {
                    @Override
                    public void run() {
                        Object resultObject = useRedisCacheService.get(idNo + "");
                        if(resultObject==null) {
                            //查询DB，对象为空，空值放入缓存，设缓存过期时间较短
                            resultObject = testCacheBasicService.testCacheNonTransaction(idNo);
                            if(resultObject==null){
                                useRedisCacheService.put(String.valueOf(idNo), nullResult, 1);
                            }else{
                                useRedisCacheService.put(String.valueOf(idNo), resultObject, 20);
                            }
                        }
                    }
                }.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
