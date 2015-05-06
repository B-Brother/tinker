# tinker
a rmi framework.


init
----------------------
最简单的例子，有了注册中心的雏形。
客户端现在可以向服务端发起方法调用。但是仅限于无参数的方法调用。


branch-methodparam_invoke
----------------------
bugfix：修复了test工程的register模块依赖不正确的问题。
bugfix: mysql的表结构中间缺少一个分号。已经补上。
bugfix: 修复了之前测试注册中心时候mysql的jar包手工导入的问题。现在已经maven依赖调整加上了5.1.26版本。