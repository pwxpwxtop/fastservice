# 🐞这是一个可以简化代码，可以快速帮助你实现CRUD



### 🕹️Fastservice是什么？

```
1.一个可以快速实现 创建数据，查询数据，修改数据，删除数据，导入excle，导出excel的加强工具
2.可以提高你写代码的速度，简化代码量
```



### ☃️相关说明：

##### 需要用到spingboot依赖： [SpringBoot](https://spring.io/) 

##### 依赖Mybatis-plus工具： [Mybatis-plus](https://baomidou.com/)

##### Excel导入导出依赖项工具：[easyexcel](https://github.com/alibaba/easyexcel)





### 🚀fastservice的 maven的依赖

```pom.xml
<dependency>
    <groupId>io.github.pwxpwxtop</groupId>
    <artifactId>fastservice</artifactId>
    <version>1.1.0</version>
</dependency>
```





### 🤖🤖🤖视频讲解：[点击跳转1](https://www.bilibili.com/video/BV1ut421877s/)

### 现在开始

##### 第一步：去拉取现有springboot的项目进行测试，cmd创建

```
git clone https://gitee.com/xingble/springboot-singleton.git
```



### 默认api接口

| 接口      |       说明        |
| :-------- | :---------------: |
| /data     |   查询数据接口    |
| /insert   |   添加数据接口    |
| /update   |   更新数据接口    |
| /delete   |   删除数据接口    |
| /deletes  |   批量删除数据    |
| /impExcel | 导入excel数据接口 |
| /expExcel | 导出excel数据接口 |



### 



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
    
    private String phone;
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





------



### 👻注解@Vo的使用



#### 🤖🤖🤖视频讲解：[待发布。。。]()

vo一般用来对数据库查询操作做的条件限制

##### @Vo注解的内部参数

| 名称  |       说明       |
| :---: | :--------------: |
| exist | 表示是否支持查询 |
| type  | 表示查询匹配规则 |
| regex |   正则匹配规则   |
|  msg  | 查询返回提示消息 |



##### 

##### 在MyUser.java中添加@Vo注解

**1  exist**：如果**@Vo(exist = false)**表示该字段不参与查询，不填写**@Vo**注解的话或者**@Vo(exist = true)**表示参与查询

**2  type**：查询数据库方式

```
 VoType.EQ（默认查询）
 EQ//相等查询
 NE//不等于
 LIKE//全模糊查询
 LIKE_LEFT//左模糊查询
 LIKE_RIGHT//右模糊查询
 LT//小于查询
 LE //小于等于查询
 GT //大于查询
 GE //大于等于查询
 NOT_NULL//不能为空
 NULL//为空
```

**3  regex**:  正则匹配，匹配如何参数是否符合要求

**4  msg**：提示消息，返回给前端的提示消息

```
package com.xingble.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.pwxpwxtop.fastservice.animation.Vo;
import io.github.pwxpwxtop.fastservice.enums.VoType;
import lombok.Data;

@Data
@TableName("my_user")
public class MyUser {
	
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    
    //查询的时候进行模糊查询，且查询name参数值不能为空
    @Vo(type = {VoType.LIKE, VoType.NOT_NULL})
    private String name;
    
    //正则匹配1-99年龄范围
    @Vo(regex = "(0?[1-9]|[1-9][0-9])", msg = "超过年龄范围")
    private Long age;

	//不参与查询
    @Vo(exist = false)
    private String sex;

	//正则匹配手机号码
    @Vo(regex = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", msg = "手机号码不正确")
    private Long phone;
}

```



------



### 🐼注解@Bo的使用

#### 🤖🤖🤖视频讲解：[待发布。。。]()

bo一般对数据创建和修改限制条件

##### @Bo注解的内部参数说明

| 名称  |            说明            |
| :---: | :------------------------: |
| exist |      表示参与匹配字段      |
| type  |          限制条件          |
| regex |        正则匹配规则        |
|  msg  | 添加或者查询返回提示的消息 |



##### 在MyUser.java中添加@Vo注解

**1  exist**：如果**@Bo(exist = false)**表示该字段不参与创建或更新，不填写**@Bo**注解的话或者**@Vo(exist = true)**表示参与创建或更新

**2  type**：查询数据库方式
    

```
NOT_STR//STR不能为空字符
REPEAT//防止数据库字段重复
FILTER//字段过虑，对特殊字传过来的参数进行过滤。比如传了个 name="$!hello&"，会将字段进行过滤为 name="hello"，将特殊字符给过虑掉
```



**3  regex**:  正则匹配，匹配如何传参数是否符合要求，正则匹配规则

**4  msg**：正则匹配将不符合条件的消息返回给前端





### 🌏进阶使用

##### 如果想在接口追加新注解，你不想用默认的接口(/data, /insert, /update, /delete)名称,可以自定义,只需要您实现接口MapperService，重新实现接口之后，

##### 在idea开发工具中，ctrl + O 键，去追加方法。下面是代码示例。

```java
package com.xingble.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xingble.mapper.MyUserMapper;
import com.xingble.model.MyUser;
import io.github.pwxpwxtop.fastservice.r.R;
import io.github.pwxpwxtop.fastservice.service.core.MapperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class MyUserController extends MapperService<MyUser, MyUserMapper> {

    @Override
    @GetMapping("/getData")
    public R<MyUser> data(MyUser myUser, Page<MyUser> page) {
        return super.data(myUser, page);
    }

    @Override
    @PostMapping("/add")
    public R<MyUser> insert(MyUser myUser) {
        return super.insert(myUser);
    }

    @Override
    @PostMapping("/edit")
    public R<MyUser> update(MyUser myUser) {
        return super.update(myUser);
    }

    @Override
    @PostMapping("/remove")
    public R<MyUser> delete(MyUser myUser) {
        return super.delete(myUser);
    }
}

```



#### 这样我们就实现了自定义接口

```
http://127.0.0.1:8080
/api/getData
/api/add
/api/edit
/api/remove
```





内容待更新。。。



### 觉得不错的话，点个star⭐