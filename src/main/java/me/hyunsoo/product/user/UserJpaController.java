package me.hyunsoo.product.user;

import lombok.RequiredArgsConstructor;
import me.hyunsoo.product.user.exception.UserNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jpa")
public class UserJpaController {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        List<User> users = userRepository.findAll();

        return users;
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable(name = "id") int id){

       User user =  userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(String.format("ID[%s] not found", id)));

       EntityModel<User> userEntityModel = EntityModel.of(user);

        Link allUsersLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers()
        ).withRel("all-users");

        Link nonJpaRetrieveUserLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class).retrieveUser(id)
        ).withRel("user-without-jpa");

        userEntityModel.add(allUsersLink);
        userEntityModel.add(nonJpaRetrieveUserLink);

        return userEntityModel;
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @PostMapping(path = "/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id){
        Optional<User> optUser = userRepository.findById(id);
        User user = optUser.orElseThrow(()->new UserNotFoundException(String.format("ID[%s] not found", id)));
        return user.getPosts();
    }


    @PostMapping(path = "/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post){

        Optional<User> optUser = userRepository.findById(id);
        User user = optUser.orElseThrow(()->new UserNotFoundException(String.format("ID[%s] not found", id)));
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/users/{id}/posts/{post_id}")
    public Post retrievePostByUserIdAndPostId(@PathVariable int id, @PathVariable int post_id){
        Optional<User> optUser = userRepository.findById(id);
        User user = optUser.orElseThrow(()->new UserNotFoundException(String.format("ID[%s] not found", id)));
        Optional<Post> optPost = postRepository.findById(post_id);
        return optPost.orElse(new Post());
    }

    @DeleteMapping(path="/users/{id}/posts/{post_id}")
    public void deletePost(@PathVariable int id, @PathVariable int post_id){
        Optional<User> optUser = userRepository.findById(id);
        User user = optUser.orElseThrow(()->new UserNotFoundException(String.format("ID[%s] not found", id)));
        postRepository.deleteById(post_id);
    }

}
