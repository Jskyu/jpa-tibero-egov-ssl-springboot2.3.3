package com.example.demo.vo;

import com.example.demo.dto.UserDTO;
import com.example.demo.vo.Enum.UserRole;
import lombok.*;

import javax.persistence.*;

@Getter // Getter 생성
@Setter // Setter 생성
@Entity // Entity 명시
@Table(name = "USER_ENTITY")
public class UserVO {

    @Id @GeneratedValue// Primary Key ID 명시
    private Long id;

    @Column(length = 20, nullable = false, unique = true)// Column의 길이를 20으로, null을 허용하지 않는다.
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)// Column의 길이를 20으로, null을 허용하지 않는다.
    private String country;

/*    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private UserRole role;*/

    @Builder //UserVO Constructor Builder 주입
    public UserVO(Long id, String username, String password, String country){
        this.id = id;       //field data id 변수에 주입받은 id 값을 넣어준다. 이하 동일
        this.username = username;
        this.password = password;
        this.country = country;

    }

    public static UserVO createUser(String username, String password, String country){
        return new UserVO(null, username, password, country);
    }

    public void updateUser(String username, String country){
        this.username = username;
        this.country = country;
    }

    public UserDTO toDto(){
        return UserDTO.builder()
                .username(username)
                .country(country)
                .build();
    }

    protected UserVO(){} // JPA 가 읽을 수 있도록 아무런 값도 받지 않는 Constructor 생성


}
