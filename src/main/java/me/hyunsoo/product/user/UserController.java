package me.hyunsoo.product.user;


import lombok.RequiredArgsConstructor;
import me.hyunsoo.product.user.exception.UserNotFoundException;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class UserController {
    private final UserDaoService userDaoService;

    @GetMapping(value = "/users")
    public List<User> retrieveAllUsers(){
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){
        User user = userDaoService.findOne(id);
        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        EntityModel<User> userEntityModel = EntityModel.of(user);

        WebMvcLinkBuilder linkBuilder = linkTo(
                methodOn(this.getClass()).retrieveAllUsers()
        );

        userEntityModel.add(linkBuilder.withRel("all-users"));

        return userEntityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){

        User savedUser = userDaoService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
        //201코드(created)와 header에 Location에 해당 리소스에 접근 가능한 url을 만들어서 보내주게 됩니다.
    }

}
