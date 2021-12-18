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
        users.add(new User(1, new Date(), "hyunsoo", "pass1", "111-111"));
        users.add(new User(2, new Date(),"jungsoo","pass2", "111-112"));
        users.add(new User(3, new Date(),"minseok", "pass3", "111-113"));
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
