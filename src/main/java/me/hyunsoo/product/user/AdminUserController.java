package me.hyunsoo.product.user;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import me.hyunsoo.product.user.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * jackson.dataformat.xml 의존성 추가시, ObjectMapper.creatXml(true)가 되기 때문에, XML도 HttpMessageConverter에서 컨버팅가능
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {
    private final UserDaoService userDaoService;

    @GetMapping(value = "/users")
    public MappingJacksonValue retrieveAllUsers(){
       List<User> users =  userDaoService.findAll();
       //filter 만들고,
       SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name", "joinDate","ssn");

       MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
       mappingJacksonValue.setFilters(new SimpleFilterProvider().addFilter("UserInfo", filter));
       return mappingJacksonValue;
    }

    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable int id){
        User user = userDaoService.findOne(id);
        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        //포함시키고자 하는 필터입니다.
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","ssn"); //얘네만 보내주세요!!

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(new SimpleFilterProvider().addFilter("UserInfo", filter));
        return mappingJacksonValue;
    }

}
