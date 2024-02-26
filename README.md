# 🐞这是一个快捷的代码业务，可以快速帮助你实现CRUD



### 🔥🔥🔥主要基于 [SpringBoot](https://spring.io/)  和 [Mybatis-plus](https://baomidou.com/) 结合快速实现CRUD的功能





#### 🚀🚀🚀maven的依赖

```pom.xml
<dependency>
    <groupId>io.github.pwxpwxtop</groupId>
    <artifactId>fastservice</artifactId>
    <version>1.0.0</version>
</dependency>
```



### 🤖🤖🤖视频讲解：[点击跳转](https://www.bilibili.com/video/BV1ut421877s/)



### 🌈🌈🌈fastservice的使用

#### **1.** **首先创建一个MyUser.java**

```java
package com.xingble.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.pwxpwxtop.fastservice.animation.Bo;
import io.github.pwxpwxtop.fastservice.animation.Vo;
import lombok.Data;

@Data
@TableName("my_user")
public class MyUser {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private Long age;

    private String sex;
}
```



#### **2.再创建一个MyUserMapper.java**

```java
package com.xingble.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xingble.model.MyUser;

public interface MyUserMapper extends BaseMapper<MyUser> {
}

```



#### **3.再次创建一个MyUserController.java**

#### 这里注意一下，需要去继承我们的 --> FastService 然后去写两个泛型 MyUser和MyUserMapper

```java
package com.xingble.controller;

import com.xingble.mapper.MyUserMapper;
import com.xingble.model.MyUser;
import io.github.pwxpwxtop.fastservice.service.core.FastService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class MyUserController extends FastService<MyUser, MyUserMapper> {

}

```



#### **4. api接口调用**

为什么继承了FastService之后api接口就可以去调用我们的api接口,因为实现了FastService这个类

默认4个api接口调用

```
GET  /api/data
POST /api/insert
POST /api/update
POST /api/delete
```



##### 用你的浏览器输入地址 --> http://127.0.0.1:8080/api/data，得到结果

```json
GET   http://127.0.0.1:8080/api/data
{
    "code": "OK",
    "msg": "搜索成功",
    "data": [],
    "total": 1,
    "count": null
}
```



##### 你也可以使用相关工具Postman，ApiFox去测试其他接口



##### 创建数据接口 

```json
POST  http://127.0.0.1:8080/api/insert
{
    "name":"张三",
    "age":12,
    "sex":"男"
}
```



##### 更新接口，根据id去跟新数据

```json
POST http://127.0.0.1:8080/api/update
{
    "name":"李白",
    "age":22,
    "sex":"男",
    "id":"1"
}
```



##### 删除接口，根据id去跟新数据

```json
POST http://127.0.0.1:8080/api/delete
{
    "id":"1"
}
```



### 注解@Vo的使用

。。。待更新

### 注解@Bo的使用

。。。待更新

### 进阶使用

。。。待更新