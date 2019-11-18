package com.origin.web.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import com.origin.entity.LoginInfoDto;

@Api(tags = "缓存简单测试模块")
public interface TestCacheBasicAPI {
    public final String API_SWAGGERUI ="/swaggerUi";

    @GetMapping(value = API_SWAGGERUI+"/testCacheNonTransaction/{id}",name = "通过id查询")
    @ResponseBody
    @ApiOperation(value = "redis无事务正常查询",notes = "是否走缓存，缓存命中情况")
    public LoginInfoDto testCacheNonTransaction(@PathVariable Integer id) ;

    @GetMapping(value = API_SWAGGERUI+"/testCacheTransaction/{id}",name = "通过id查询")
    @ResponseBody
    @ApiOperation(value = "redis有事务查询",notes = "有事务，查询效率比对")
    public LoginInfoDto testCacheTransaction(@PathVariable Integer id) ;

    @GetMapping(value = API_SWAGGERUI+"/testRedisCache/{id}",name = "通过id查询")
    @ResponseBody
    @ApiOperation(value = "使用redis缓存",notes = "使用redis作缓存")
    public LoginInfoDto testRedisCache(@PathVariable Integer id) ;


}
