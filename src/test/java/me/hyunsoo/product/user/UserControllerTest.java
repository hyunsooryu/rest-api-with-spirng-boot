package me.hyunsoo.product.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(value = {UserController.class, UserDaoService.class})
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Order(Ordered.HIGHEST_PRECEDENCE + 100)
    @Test
    @DisplayName("모든 User 리스트 조회")
    void findAllTest() throws Exception {
        String json = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));

        TypeReference<List<User>> typeReference = new TypeReference<List<User>>(){};

        List<User> users = objectMapper.readValue(json, typeReference);

        assertAll(
                ()->{
                   assertThat(users.get(0).getId()).isEqualTo(1);
                },
                ()->{
                    assertThat(users.get(1).getId()).isEqualTo(2);
                },
                ()->{
                    assertThat(users.get(2).getId()).isEqualTo(3);
                }
        );
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Test
    @DisplayName("User 등록")
    void createUsersTest() throws Exception{

        User newUser = new User();
        newUser.setName("minchel");
        newUser.setJoinDate(Date.from(Instant.now()));
        String json = objectMapper.writeValueAsString(newUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                                              .content(json)
                                              .contentType(MediaType.APPLICATION_JSON_VALUE)
                                              .accept(MediaType.APPLICATION_JSON_VALUE));
    }




}