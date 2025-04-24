package com.board.member.controller.auth;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.board.member.Infrastructure.auth.JwtTokenProvider;
import com.board.member.controller.auth.dto.request.LoginRequest;
import com.board.member.controller.auth.dto.request.SignUpRequest;
import com.board.member.controller.auth.dto.response.SignUpResponse;
import com.board.member.service.auth.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthService authService;

    private SignUpResponse signUpResponse;

    @BeforeEach
    void set() {
        signUpResponse = new SignUpResponse(1L, "token");
    }

    @Test
    @DisplayName("회원가입을 진행한다.")
    void signUp() throws Exception {
        // given
        SignUpRequest request = new SignUpRequest("신짱구", "짱구", "aaa", "password123");
        given(authService.signUp(request)).willReturn(signUpResponse);
        given(jwtTokenProvider.create(1L)).willReturn("token");

        // when & then
        mockMvc.perform(post("/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/signUp/1"))
                .andExpect(jsonPath("$.memberId").value(1L))
                .andExpect(jsonPath("$.token").value("token"));
    }

    @Test
    @DisplayName("로그인을 진행한다.")
    void login() throws Exception {
        // given
        LoginRequest request = new LoginRequest("aaa", "111");
        String token = "token";
        given(authService.login(request)).willReturn(token);
        given(jwtTokenProvider.create(1L)).willReturn(token);

        // when & then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Authorization=Bearer+token"));
    }
}
