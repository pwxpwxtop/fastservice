package com.example.controller;

import com.example.mapper.MyUserMapper;
import com.example.model.MyUser;
import io.github.pwxpwxtop.fastservice.service.core.FastService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: PWX
 * @CreateDate: 2024/2/23 11:44
 * @UpdateUser: IntelliJ IDEA
 * @UpdateDate: 2024/2/23 11:44
 * @Version: 1.0
 */

@RestController
@RequestMapping("/api")
public class MyUserController extends FastService<MyUser, MyUserMapper> {

}
