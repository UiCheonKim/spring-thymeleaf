package hello.thymeleaf.basic;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "Hello <b>Spring</b>!");
        // thymeleaf view 템블릿에 전달할 데이터를 model 에 담는다.
        return "basic/text-basic";
    }

    @GetMapping("text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "Hello <b>Spring</b>!");
        // thymeleaf view 템블릿에 전달할 데이터를 model 에 담는다.
        return "basic/text-unescaped";
    }

    @GetMapping("variable")
    public String variable(Model model) {
        User userA = new User("userA", 10);
        User userB = new User("userB", 20);
        // thymeleaf view 템블릿에 전달할 데이터를 model 에 담는다.
        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String, User> map = new HashMap<>();
        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);
        model.addAttribute("userMap", map);
        return "basic/variable";
    }

//    @GetMapping("basic-objects")
//    public String basicObjects(HttpSession session) {
//        // HttpRequest 는 유저가 들어왔다 나가면 끝이지만
//        // HttpSession 은 유저가 웹 브라우저를 종료하지 않는 이상 계속 유지된다.
//        session.setAttribute("sessionData", "Hello Session");
//        // 세션에 접근하는걸 보여주기 위해 사용
//        return "basic/basic-objects";
//    }

    @GetMapping("/basic-objects")
    public String basicObjects(Model model, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        // HttpRequest 는 유저가 들어왔다 나가면 끝이지만
        // HttpSession 은 유저가 웹 브라우저를 종료하지 않는 이상 계속 유지된다.
        session.setAttribute("sessionData", "Hello Session");
        // 세션에 접근하는걸 보여주기 위해 사용
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", request.getServletContext());
        return "basic/basic-objects";
    }

    static class User {
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }

    @Component("helloBean") // 빈에 직접 접근하는걸 보여주기 위해 사용
    // component scan 을 통해 빈으로 등록이 된다.
    static class HelloBean {
        public String hello(String data) {
            return "Hello " + data;
        }
    }

}
