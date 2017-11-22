//package com.mileworks.config.datasource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import cn.jeefast.datasources.annotation.DataSource;
//import cn.jeefast.modules.api.entity.TbUser;
//import cn.jeefast.modules.api.service.TbUserService;
//
///**
//* 测试用例
//* @author long-laptop
//* 2017.11.6
//*/
//@Service
//public class DataSourceTestService {
//    @Autowired
//    private TbUserService userService;
//
//    public TbUser queryObject(Long userId){
//        return userService.selectById(userId);
//    }
//
//    @DataSource(name = DataSourceNames.SECOND)
//    public TbUser queryObject2(Long userId){
//        return userService.selectById(userId);
//    }
//}
