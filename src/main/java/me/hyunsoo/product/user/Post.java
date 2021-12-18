package me.hyunsoo.product.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    @JsonIgnore
    //User : POST -> 1 : (0 ~ N);
    //지연 로딩 방식-> POST데이터가 로딩 되는 시점에 필요한 사용자 데이터도 가져오겠다.. 이런말
    @ManyToOne(fetch = FetchType.LAZY) //현재 POST는 여러개 있을 수 있으며, User는 하나만 와야한다.
    private User user;
}
