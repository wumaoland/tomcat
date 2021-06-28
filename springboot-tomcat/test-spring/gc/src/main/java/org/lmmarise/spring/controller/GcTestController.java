package org.lmmarise.spring.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author lmmarise.j@gmail.com
 * @date 2021/6/26 10:59 上午
 */
@RestController
public class GcTestController {

    private final Queue<Greeting> objCache = new ConcurrentLinkedDeque<>();

    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;

    @RequestMapping("/greeting")
    public Greeting greeting() {
        Greeting greeting = new Greeting("Hello World!");
        if (objCache.size() >= 200000) {
            objCache.clear();
        } else {
            objCache.add(greeting);
        }
        return greeting;
    }

}

@Data
@AllArgsConstructor
class Greeting {
    private String message;
}
