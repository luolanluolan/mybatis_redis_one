package com.origin.service;

import com.origin.entity.LoginInfoDto;

public interface TestCacheBasicService {
    public LoginInfoDto testCacheNonTransaction(Integer id);
    public LoginInfoDto testCacheTransaction(Integer id);
}
