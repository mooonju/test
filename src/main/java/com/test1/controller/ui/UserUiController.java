package com.test1.controller.ui;

import com.test1.domain.dto.user.UserJoinRequest;
import com.test1.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/users")
@RequiredArgsConstructor
@Slf4j
public class UserUiController {

    private final UserService userService;

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("userJoinRequest", new UserJoinRequest());
        return "join";
    }

}
