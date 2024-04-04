# 🐞这是一个可以简化代码，可以快速帮助你实现CRUD



### 🕹️Fastservice是什么？

```
1.一个可以快速实现 创建数据，查询数据，修改数据，删除数据，导入excle，导出excel的加强工具
2.可以提高你写代码的速度，简化代码量
```



觉得不错的话，来个star⭐吧



### 🔥🔥🔥1.2.0 更新说明

```
1.2.0版本更新说明.
新内容：
1. 自动创建表格sql语句，SqlUtils.getCreateTable()
2. 生成代码工具类:GenCodeUtils.genCode()
3. 更多注解使用 @Comment， @Char, @Dates , @Index等等。。。
```



### 🐞1.2.* bug修复

```
1.2.*bug修复
暂无
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
    <version>1.2.0</version>
</dependency>
```



### 🤖🤖🤖视频讲解：[点击跳转1](https://www.bilibili.com/video/BV1ut421877s/)





------





### 现在开始

#### 🍭第一步：去拉取现有springboot的项目进行尝试一下 或者 在你现有的项目引入依赖

```
git clone https://github.com/pwxpwxtop/springboot-singleton
```



#### 👻第二部：执行sql语句

```sql
CREATE TABLE `my_user` (
  `id` bigint NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```



#### ☃️第三部：创建实体类MyUser.java

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



#### **☃️第四部：创建一个java映射类MyUserMapper.java，并继承**BaseMapper&lt;MyUser&gt;

```java
package com.xingble.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xingble.model.MyUser;

public interface MyUserMapper extends BaseMapper<MyUser> {
}

```



#### 🚀第五部：创建MyUserController.java,并继承FastService<MyUser, MyUserMapper>

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





#### 🏯好了，我们去启动下我们的服务，然后就有了下面的接口

| 接口      |       说明        | 请求方式 |
| :-------- | :---------------: | :------: |
| /data     |   查询数据接口    |   GET    |
| /insert   |   添加数据接口    |   POST   |
| /update   |   更新数据接口    |   POST   |
| /delete   |   删除数据接口    |   POST   |
| /deletes  |   批量删除数据    |   POST   |
| /impExcel | 导入excel数据接口 |   POST   |
| /expExcel | 导出excel数据接口 |   POST   |



### 你也可以用工具Postman，ApiFox去测试其他接口

##### 或者用你的浏览器输入地址 --> http://127.0.0.1:8080/api/data，得到结果

```json
GET   http://127.0.0.1:8080/api/data
parame:
{
    "code": "OK",
    "msg": "搜索成功",
    "data": [],
    "total": 1,
    "count": null
}
```



##### 创建数据接口 

```json
POST  http://127.0.0.1:8080/api/insert
json:
{
    "name":"张三",
    "age":12,
    "sex":"男"
}
```



##### 更新接口，根据id去跟新数据

```json
POST http://127.0.0.1:8080/api/update
json:
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
json:
{
    "id":"1"
}
```



##### 批量删除，根据主键id去删除数据

```
POST http://127.0.0.1:8080/api/delete
json:
[
    "1",
    "2",
    "3"
]
```



##### 导入Excel表格数据

```
POST http://127.0.0.1:8080/api/impExcel
param: 
{
	isBo:true //true:开启导入表格验证，false:关闭验证
}
form-data:
{
	file:你的文件
}
```



##### 导出Excel表格数据

```
POST http://127.0.0.1:8080/api/expExcel
json:
{
    "idName":"id", 		
    "isSubTable":false, 
    "isAll": false,
    "tableSize":10,
    "fileName":"我的测试文件",
    "ids":[
        "1",
        "2",
        "3",
        "4",
        "5"
    ],
    "columns":[
        "id",
        "name"
    ]
}
```



| json数据   | 说明                                                         |
| :--------- | ------------------------------------------------------------ |
| idName     | 主键id名称                                                   |
| isSubTable | 是否开启分表导入数据,开启分表查询返回 *.zip的文件，不开启返回 *.xlsx文件 |
| isAll      | 是否导出全部数据                                             |
| tableSize  | 每张表格数据大小，isSubTable:true 时有效                     |
| fileName   | 文件名称                                                     |
| ids        | 查询指定数据，根据idName主键查询，isAll:false时有效          |
| columns    | 要查询的列                                                   |





------



### 👻注解@Vo的使用

##### @Vo注解的内部参数

| 名称  |       说明       |
| :---: | :--------------: |
| exist | 表示是否支持查询 |
| type  | 表示查询匹配规则 |
| regex |   正则匹配规则   |
|  msg  | 查询返回提示消息 |



##### 在MyUser.java中添加@Vo注解

**1  exist**：如果**@Vo(exist = false)**表示该字段不参与查询，不填写**@Vo**注解的话或者**@Vo(exist = true)**表示参与查询

**2  type**：查询数据库方式

```
EQ, //相等查询
NE, //不等于
LIKE,//全模糊查询
LIKE_LEFT,//左模糊查询
LIKE_RIGHT,//右模糊查询
NOT_LIKE,//反向模糊匹配
IN,//包含
NOT_IN,//不包含
EXISTS,//存在
NOT_EXISTS,//不存在
LT, //小于查询
LE, //小于等于查询
GT, //大于查询
GE,  //大于等于查询
NOT_NULL,//不能为空
NULL,//为空
```

**3  regex**:  正则匹配，匹配如何参数是否符合要求

**4  msg**：提示消息，返回给前端的提示消息

##### 5 代码示例，在你的MyUser.java文件下尝试一下

```java
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

##### 5 代码示例，在你的MyUser文件下尝试一下

```java
@Data
@TableName("my_user")
public class MyUser {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Bo( type = {BoType.REPEAT, BoType.FILTER}, exist = false)
    private String name;

    private Long age;

    private String sex;

    @Bo( type = {BoType.NOT_NULL_STR}, regex = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", msg = "手机号码不正确")
    private Long phone;
}

```





### 🐕注解@ExcelProperty的使用

##### @ExcelProperty作用于excel导入和导出



### 🕹️注解@ExcelIgnore的使用

##### 作为表格导入和导出时@ExcelIgnore表示略这个字段



##### 示例代码

```java
@Data
@TableName("my_user")
public class MyUser extends BaseModel{

    @ExcelProperty(value = "姓名")
    private String name;

    @ExcelProperty(value = "年龄")
    private Integer age;

    @ExcelProperty("性别")
    private String sex;

    @ExcelProperty(value = "手机号码")
    private Long phone;

    @ExcelIgnore//忽略这个字段
    private Integer deleteState;

}
```



### [更多关于注解详情请访问easyexcel](https://easyexcel.opensource.alibaba.com/docs/current/)





### 🛹自定义sql语句Dao的使用

#### 第一：配置

```java
package com.xingble.conf;

import io.github.pwxpwxtop.fastservice.bean.FastServiceBeans;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FastServiceConf extends FastServiceBeans {

}
```



#### 代码示例

```java
@RestController
@RequestMapping("/api")
public class MyUserController extends FastService<MyUser, MyUserMapper> {

    @Resource
    private Dao dao;

    @GetMapping("/test")
    public R data(){
        List<Map<String, Object>> list = dao.sql("select id, name,age from my_user");
        return R.ok(list);
    }
}
```



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
    
    @Override
    @PostMapping("/import")
    public R<MyUser> impExcel(MultipartFile file, MyUser myUser, ImpPro<MyUser> impPro) {
        return super.impExcel(file, myUser, impPro);
    }

    @Override
    @PostMapping("/export")
    public void expExcel(ExpPro expPro, HttpServletResponse response, MyUser myUser) {
        super.expExcel(expPro, response, myUser);
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
/api/import
/api/export
```



### 工具类SqlUtils.getCreateTable()

##### 代码示例

```java
@Test
public void test(){
    //获取指定数据库创建表格
    String sql1 = SqlUtils.getCreateTable(MyUser.class, DatabaseType.MYSQL);
    System.out.println(sql1);

    //获取mysql数据库sql创建语句
    String sql2 = SqlUtils.getCreateTableMySql(MyUser.class);
    System.out.println(sql2);

    //获取postgresql数据库sql创建语句
    String sql3= SqlUtils.getCreateTablePostgreSql(MyUser.class);
    System.out.println(sql3);
}
```

##### 返回结果

```sql
create table if not exists  `my_user`(  `id` bigint not null  comment '唯一id' ,  `name` varchar(10) comment '姓名' ,  `phone` bigint comment '手机号码' ,  `sex` varchar(255) comment '性别' ,  `age` int not null  comment '年龄' ,  `delete_state` int ,  `create_time` datetime ,  `create_by` varchar(255) ,  `update_time` datetime ,  `update_by` varchar(255) , primary key (id) );

create table if not exists  `my_user`(  `id` bigint not null  comment '唯一id' ,  `name` varchar(10) comment '姓名' ,  `phone` bigint comment '手机号码' ,  `sex` varchar(255) comment '性别' ,  `age` int not null  comment '年龄' ,  `delete_state` int ,  `create_time` datetime ,  `create_by` varchar(255) ,  `update_time` datetime ,  `update_by` varchar(255) , primary key (id) );

create table if not exists my_user( id bigint not null  , name varchar(10) , phone bigint , sex varchar(255) , age integer not null  , delete_state integer , create_time timestamp , create_by varchar(255) , update_time timestamp , update_by varchar(255) , primary key (id) );comment on column my_user.id is '唯一id';comment on column my_user.name is '姓名';comment on column 
```



#### 可以配合注解，匹配数据类型

|     注解      |                             说明                             |
| :-----------: | :----------------------------------------------------------: |
|     @Char     |                      对应数据库char类型                      |
|    @Times     |                           时间类型                           |
|    @Dates     |                           日期类型                           |
|  @Datetimes   |                          日期和时间                          |
|   @Decimal    |         用于计算金钱的数据类型,例如 @Decimal(10, 2)          |
|    @Float4    |                         4位浮点类型                          |
|    @Float8    |                         8位浮点类型                          |
|    @Index     |     数据库表格排序循序，@Index(100)，值越大，排在越前面      |
|     @Int2     |                           2位整形                            |
|     @Int4     |                           4位整形                            |
|     @Int8     |                           8位整形                            |
|   @Varchar    |                 可变字符类型，@Varchar(255)                  |
|     @Text     |                         文本数据类型                         |
|   @Default    |                  数据库默认值 @Default("0")                  |
|    @Length    | 可以指定数据的长度，比如@Varchar(255)等同于@Varchar @Length(255)<br />再比如@Decimal(10, 2)等同于@Decimal @Length(10) @DecimalPoint(2) |
| @DecimalPoint |                  可以指定数据库的浮点后长度                  |
|   @NotNull    |                       指定不能位空数据                       |
|   @TableId    |                           指定主键                           |



#### 实体类示例代码

```java
@Data
@TableName("my_user")
public class MyUser {

    @TableId(type = IdType.ASSIGN_ID)
    @ExcelProperty("唯一id")
    @Index(10)
    @Int8 @NotNull
    private Long id;


    @Bo(type = { BoType.NOT_NULL_STR, BoType.FILTER, BoType.FILTER})
    @Vo(type = {VoType.LIKE})
    @ExcelProperty(value = "姓名")
    @Index(9) @Varchar(10)
    private String name;

    @Bo(regex = "([1-9][0-9]{0,1}|100|0)", msg = "不在年龄范围内")
    @ExcelProperty(value = "年龄")
    @Index(4) @NotNull
    private Integer age;

    @ExcelProperty("性别")
    @Index(5)
    private String sex;


    @ExcelProperty(value = "手机号码")
    @Index(8)
    private Long phone;

    @Vo(exist = false)
    @TableLogic(value = "0" , delval = "1")
    @TableField(fill = FieldFill.INSERT)
    @ExcelIgnore
    private Integer deleteState;


    //创建时间
    @Bo(exist = false)
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    //保存更新人的id或者更新人的姓名
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

}
```



### 代码生成工具GenCodeUtils.genCode()

```
@Test
public void test04(){
	JdbcMysql jdbc = new JdbcMysql(); //数据库配置
	GenCodeUtils.genCode(MyUser.class,//生成表格实体类
	jdbc,							  //传递的参数
	"D:\\file\\com\\example",		  //生成文件的位置	
	"com.example");					  //生成的包名	
}
```



### 生成代码文件

com.example

  - controller

    - MyUserController

  - mapper

    - MyUserMapper

  - service

    - MyUserService

    

#### 目前生成代码支持MySql，Postgresql数据库，[更多详情可以查看视频](https://www.bilibili.com/video/BV1ut421877s/)





### 觉得不错的话，点个star⭐