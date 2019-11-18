package com.origin.dao;

import com.origin.entity.LoginInfoDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestCacheBasicDao {
    public LoginInfoDto testCacheNonTransaction(Integer id);
    public LoginInfoDto testCacheTransaction(Integer id);
}
