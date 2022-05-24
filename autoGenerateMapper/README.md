思路：该项目通过对MybatisGenerator进行一点扩展实现，能够一键生成mybatis代码。  

实现方式： 
   
两种使用方式：
- 简易一键生成mybatis代码（StartSimpleMGB），  MybatisGenerator原有功能上对model层对象新增了注解功能（根据数据库的注释对model对象进行注释）
    1. generatorConfig.xml进行数据库相关配置
    2. 运行StartSimpleMGB类
- 模板替换版一键生成mybatis代码（StartReplaceModelsMGB），
 先使用MybatisGenerator生成model层和mapper层代码，然后service层等则替换模板内容来生成。（模板是自定义的）
    1. resources/MGB_Replace_Models/ 下自定义模板
    2. StartReplaceModelsMGB的static{}中定义替换规则、数据库配置
    3. 运行StartReplaceModelsMGB类