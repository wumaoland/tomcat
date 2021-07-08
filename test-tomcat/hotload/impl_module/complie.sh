# 模拟类变化
# 1.执行本脚本文件
#   会编译javapath下所有的java文件，编译后的.class放入classpath
# 2.运行 Main 方法
# 3.修改 MyManager.java 源码
# 4.观察 Main 方法控制台输出的变化

source_path=$(pwd)/source
target_path=$(pwd)/target
package='org/lmmarise/tomcat/loader'

javac8 -d $target_path $source_path/$package/*.java