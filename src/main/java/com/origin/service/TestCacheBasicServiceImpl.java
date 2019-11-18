package com.origin.service;

import com.origin.dao.TestCacheBasicDao;
import com.origin.entity.LoginInfoDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class TestCacheBasicServiceImpl implements TestCacheBasicService{


    @Resource
    private TestCacheBasicDao testCacheBasicDao;

    @Override
    public LoginInfoDto testCacheNonTransaction(Integer id){
        LoginInfoDto loginInfoDto = testCacheBasicDao.testCacheNonTransaction(id);
        return loginInfoDto;
    }

    @Override
    @Transactional
    public LoginInfoDto testCacheTransaction(Integer id){
        long beginTime = System.currentTimeMillis();
        LoginInfoDto loginInfoDto = testCacheBasicDao.testCacheNonTransaction(id);
        System.out.println(System.currentTimeMillis()-beginTime);
        /*beginTime = System.currentTimeMillis();
        LoginInfoDto loginInfoDto2 = testCacheBasicDao.testCacheNonTransaction(id);
        System.out.println(System.currentTimeMillis()-beginTime);*/
        return loginInfoDto;
    }

}
