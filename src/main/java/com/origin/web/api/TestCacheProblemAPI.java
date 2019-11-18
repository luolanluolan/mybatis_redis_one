package com.origin.web.api;

import com.origin.entity.LoginInfoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(tags = "缓存问题测试模块")
public interface TestCacheProblemAPI {
    public final String API_SWAGGERUI ="/swaggerUi";

    @GetMapping(value = API_SWAGGERUI+"/testCachePenetrate/{id}",name = "缓存穿透问题")
    @ResponseBody
    @ApiOperation(value = "缓存穿透问题",notes = "查询一个数据库一定不存在的数据。解决：将空对象也放入缓存，设定缓存过期时间较短")
    public LoginInfoDto testCachePenetrate(@PathVariable Integer id) ;

    @GetMapping(value = API_SWAGGERUI+"/testCacheSnowSlide/{id}",name = "缓存雪崩问题")
    @ResponseBody
    @ApiOperation(value = "缓存雪崩问题",notes = "某一时间段，缓存集中过期，查询落在DB，产生周期性压力风波。解决：不同分类商品，缓存周期不同，同一类加随机因子分散缓存过期时间")
    public LoginInfoDto testCacheSnowSlide(@PathVariable Integer id) ;

    @GetMapping(value = API_SWAGGERUI+"/testCachePuncture/{id}",name = "缓存击穿问题")
    @ResponseBody
    @ApiOperation(value = "缓存击穿问题",notes = "一个key非常热，扛着大并发，当key在失效瞬间，持续大并发穿破缓存，直接请求数据库（爆款）。解决：key上锁，当对同一个key大量并发，")
    public LoginInfoDto testCachePuncture(@PathVariable Integer id) ;

    @GetMapping(value = API_SWAGGERUI+"/testCausePenetrate/{id}",name = "测试造成缓存穿透")
    @ResponseBody
    @ApiOperation(value = "redis创建多个线程造成缓存穿透",notes = "redis创建多个线程造成缓存穿透")
    public void testCausePenetrate(@PathVariable Integer id) ;
}
