package com.test1.controller.rest;

import com.test1.domain.dto.Response;
import com.test1.domain.dto.token.TokenInfo;
import com.test1.domain.dto.user.UserJoinRequest;
import com.test1.domain.dto.user.UserJoinResponse;
import com.test1.domain.dto.user.UserLoginRequest;
import com.test1.domain.dto.user.UserLoginResponse;
import com.test1.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserRestController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        UserJoinResponse userJoinResponse = userService.join(userJoinRequest);
        return com.test1.domain.dto.Response.success(userJoinResponse);
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest,
                                             HttpServletRequest httpServletRequest) {
        System.out.println("로그인 컨트롤러 시작");

        TokenInfo token = userService.login(userLoginRequest);
        String accessToken = token.getAccessToken();


        System.out.println("컨트롤러: " + userLoginRequest.getUserId());

        String userIdhttp = httpServletRequest.getParameter("userId");
        System.out.println("http: " + userIdhttp);

        if (accessToken != null) {
            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute("userId", userLoginRequest.getUserId());
        }

        return Response.success(new UserLoginResponse(accessToken));

    }
}
