package me.hyunsoo.product.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value = {"password"}) //해당 필드는 데이터를 보내주지 않습니다.
@JsonFilter("UserInfo") //필터의 이름
public class User {
    private Integer id;
    @Size(min=2, message = "이름은 2글자 이상 입력해주세요.")
    private String name;
    @Past //미래 시간 데이터를 쓸 수 없고, 과거 시간 데이터만 쓸 수 있다.
    private Date joinDate;

    //@JsonIgnore //해당 필드는 데이터를 보내주지 않습니다.
    private String password;

    //@JsonIgnore
    private String ssn;
}
