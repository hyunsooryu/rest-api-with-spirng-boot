package me.hyunsoo.product.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int userCount = 3;

    static {
        users.add(new User(1, "hyunsoo", new Date()));
        users.add(new User(2, "jungsoo", new Date()));
        users.add(new User(3, "minseok", new Date()));
    }

    public List<User> findAll(){
        return users;
    }

    public User findOne(final int id){
        for(User user : users){
            if(user.getId() != null && user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public User save(User user){
        if(user.getId() == null){
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }


}
