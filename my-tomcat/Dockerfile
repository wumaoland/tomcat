# 生成镜像：docker build -f Dockerfile -t cxk/centos .
FROM java:8
MAINTAINER lmmarise.j@gmail.com
# 生成镜像自动挂载目录，这两个卷会与外部有同步的目录
# macOS使用进入宿主机卷：docker run -it --privileged --pid=host debian nsenter -t 1 -m -u -n -i sh
# 父子镜像： docker run -it --name cxk-centos-02 --volumes-from cxk-centos-01 cxk/centos:latest  # cxk-centos-01 已启动的父容器
COPY target/*.jar my-tomcat.jar
EXPOSE 8080 8080
ENTRYPOINT ["java", "-jar", "my-tomcat.jar"]

