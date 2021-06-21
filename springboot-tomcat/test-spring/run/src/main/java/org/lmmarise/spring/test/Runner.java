package org.lmmarise.spring.test;

import org.springframework.boot.loader.tools.JavaExecutable;
import org.springframework.boot.loader.tools.MainClassFinder;
import org.springframework.boot.loader.tools.RunProcess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tangshengbo@honezhi.com
 * @date 2021/6/21
 * @description 演示SpringBoot是怎么找到编译后的@SpringBootApplication注解所在类的main方法并执行
 */
public class Runner {

    public static void main(String[] args) throws IOException {
        String SPRING_BOOT_APPLICATION_CLASS_NAME = "org.springframework.boot.autoconfigure.SpringBootApplication";
        File classesDirectory = new File("D:\\IdeaProjects\\abx-sso\\uisp-sso-server\\target");
        String mainClass = MainClassFinder.findSingleMainClass(classesDirectory, SPRING_BOOT_APPLICATION_CLASS_NAME);
        RunProcess runProcess = new RunProcess(classesDirectory, new JavaExecutable().toString());
        Runtime.getRuntime().addShutdownHook(new Thread(new RunProcessKiller(runProcess)));
        List<String> params = new ArrayList<>();
        params.add("-cp");
        // 相关库路径
        params.add("D:\\IdeaProjects\\abx-sso\\uisp-sso-server\\target\\lib");
        params.add(mainClass);
        Map<String, String> environmentVariables = new HashMap<>();
        runProcess.run(true, params, environmentVariables);
    }

    private static final class RunProcessKiller implements Runnable {

        private final RunProcess runProcess;

        private RunProcessKiller(RunProcess runProcess) {
            this.runProcess = runProcess;
        }

        @Override
        public void run() {
            this.runProcess.kill();
        }

    }

}