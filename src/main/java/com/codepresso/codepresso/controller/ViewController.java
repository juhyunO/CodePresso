package com.codepresso.codepresso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 뷰 컨트롤러.
 * - JSP를 렌더링하는 엔드포인트를 제공.
 */
@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        // /WEB-INF/views/index.jsp
        return "index";
    }

    @GetMapping("/auth/signup")
    public String signupPage() {
        // /WEB-INF/views/auth/signup.jsp
        return "auth/signup";
    }
}
