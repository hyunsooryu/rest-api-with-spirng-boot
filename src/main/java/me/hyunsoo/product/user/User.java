package me.hyunsoo.product.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
//@JsonIgnoreProperties(value = {"password"}) //해당 필드는 데이터를 보내주지 않습니다.
//@JsonFilter("UserInfo") //필터의 이름
@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(notes = "사용자 등록일을 입력해주세요.")
    @Past //미래 시간 데이터를 쓸 수 없고, 과거 시간 데이터만 쓸 수 있다.
    private Date joinDate;

    @ApiModelProperty(notes = "사용자 이름을 입력해주세요.")
    @Size(min=2, message = "이름은 2글자 이상 입력해주세요.")
    private String name;

    @ApiModelProperty(notes = "사용자 비밀번호를 입력해주세요.")
    //@JsonIgnore //해당 필드는 데이터를 보내주지 않습니다.
    private String password;

    //@JsonIgnore
    private String ssn;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;


    public User(Integer id, @Past Date joinDate, @Size(min = 2, message = "이름은 2글자 이상 입력해주세요.") String name, String password, String ssn) {
        this.id = id;
        this.joinDate = joinDate;
        this.name = name;
        this.password = password;
        this.ssn = ssn;
    }
}
