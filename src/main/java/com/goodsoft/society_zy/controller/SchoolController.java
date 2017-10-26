package com.goodsoft.society_zy.controller;

import com.goodsoft.society_zy.service.SchoolService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 学校信息数据传输接口访问入口类
 * Created by 严彬荣 on 2017/10/25.
 *
 * @version V1.0
 */
@RestController
@RequestMapping("/education")
public class SchoolController {
    @Resource
    private SchoolService service;
}
