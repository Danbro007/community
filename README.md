社区
====
运行
====

git clone https://github.com/Danbro007/community.git


资料
====
[目标网站](https://elasticsearch.cn/)

[spring文档](https://spring.io/guides)

[spring mvc文档](https://spring.io/guides/gs/serving-web-content/)

[bootstrap文档](https://v3.bootcss.com/getting-started/)

[thymeleaf文档](https://www.thymeleaf.org/documentation.html)

[pagehelper分页器文档](https://pagehelper.github.io/)

[springboot文档](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/)

[Ucloud云存储 SDK](https://github.com/ucloud/ufile-sdk-java)

[springboot schedule task 定时任务](https://spring.io/guides/gs/scheduling-tasks/)



工具
====
[Git](https://git-scm.com/)

[lombok](https://projectlombok.org/setup/maven)

[Markdown 编辑器](https://github.com/pandao/editor.md)

脚本
====
[修改配置文件]

修改pom.xml和application-prod.properties文件里的数据库地址

[MybatisGenerator初始化脚本]

mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

[打包并迁移数据库]

mvn clean compile package fly:migrate

[部署]

java -jar target/community-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

