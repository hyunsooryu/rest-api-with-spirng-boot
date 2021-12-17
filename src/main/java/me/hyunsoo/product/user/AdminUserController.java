package me.hyunsoo.product.user;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import me.hyunsoo.product.hateoas.SampleBean;
import me.hyunsoo.product.user.exception.UserNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * jackson.dataformat.xml 의존성 추가시, ObjectMapper.creatXml(true)가 되기 때문에, XML도 HttpMessageConverter에서 컨버팅가능
 *
 *
 * 1. URI 를 이용한 버전처리
 * 2. Request Parameter와 Header를 활용한 API Version 관리
 * 3. Header
 * 4. application.vnd.company.appv1+json
 *
 * Versioning
 * URI Versioning = twitter
 * Request Parameter Versioning - Amazon
 * -> 일반 브라우저에서 실행이 가능
 *
 * Media type Versioning - GitHub
 * (Custom) headers versioning - MicroSoft능
 * -> 일반 브라우저에서는 실행이 불가
 *
 * 중요한 점
 * Factors
 * URI Pollution, Misuse of Http Headers, Caching, Can we excute the request on the browser?, Api Documentation
 *
 *
 * HATEOAS ? -> Hypermedia as the engine of Application State
 * 현재 리소스와 연관된(호출 가능한) 자원 상태 정보를 제공
 *
 * LEVEL 3 Hypermedia Controls -> Glory of REST
 *
 * Spring 2.1.8.RELEASE 일 경우
 * Resource, ControllerLinkBuilder
 *
 * Spring 2.2 이상 일 경우
 * Resource -> EntityModel
 * ControllerLinkeBuilder -> WebMvcLinkBuilder
 *
 *
 *
 *
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

    //@GetMapping("/v1/users/{id}")
    //@GetMapping(value = "/users/{id}", params = {"version=1"})
    //@GetMapping(path = "/users/{id}", headers = "X-API-VERSION=1")
    @GetMapping(path = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id){
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

    //version 처리 V2

    //@GetMapping("/v2/users/{id}") //URI로 버전처리
    //@GetMapping(path = "/users/{id}", params = {"version=2"}) //request param으로 버전처리
    //@GetMapping(path="/users/{id}", headers = "X-API-VERSION=2")
    @GetMapping(path = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id){
        User user = userDaoService.findOne(id);
        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        //User -> User2 v2 업그레이드

        UserV2 userV2 = new UserV2();
        //빈 유틸즈를 사용해서, 값을 복사해서 넣어줍니다.
        BeanUtils.copyProperties(user, userV2); //id, name, joinDate, password, ssn
        userV2.setGrade("VIP");

        //포함시키고자 하는 필터입니다.
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate", "ssn", "password", "grade"); //얘네만 보내주세요!!

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(new SimpleFilterProvider().addFilter("UserInfoV2", filter));
        return mappingJacksonValue;
    }

    //HATEOAS
    @GetMapping("/hateoas/users/{id}")
    public EntityModel<SampleBean> getHateoasUser(@PathVariable int id){
        SampleBean sampleBean = new SampleBean();
        sampleBean.setId(id);
        sampleBean.setName("sample");
        sampleBean.setLevel("VIP");
        EntityModel<SampleBean> model = EntityModel.of(sampleBean);
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        model.add(linkTo.withRel("all-users"));
        return model;
    }

}
