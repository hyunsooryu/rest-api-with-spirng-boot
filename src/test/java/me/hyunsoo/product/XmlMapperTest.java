package me.hyunsoo.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import me.hyunsoo.product.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.Instant;

@SpringBootTest
public class XmlMapperTest {

    @Autowired
    XmlMapper xmlMapper;

    @Test
    void xmlMappingTest() throws JsonProcessingException {
        User user = new User();
        user.setName("김민준");
        user.setJoinDate(Date.from(Instant.now()));
        user.setId(4);
        String xml = xmlMapper.writeValueAsString(user);
        System.out.println(xml);
    }
}
