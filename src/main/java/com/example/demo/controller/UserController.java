package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @Class UserController
 * 설명 : UserServiceImpl 에서 구현한 메서드를 사용하여 도출된 데이터를 보여주는 Class 이다.
 */
@RestController //데이터자체를 내보내는 Controller 주입
@RequiredArgsConstructor // private final 로 선언되어있는 변수들을 생성자를 이용하여 의존성을 추가해준다.
public class UserController {

    //UserService 객체 userService 의존성 주입
    private final UserService userService;

    /**
     * @return List<UserDTO>
     * @Method findAll
     * 설명 : UserService.findAll 메서드를 호출하여 받은 값을 ArrayList 로 치환하여 반환해준다.
     * 목적 : 유저의 정보를 조회하기 위한 Method 이다.
     */
    @GetMapping("/findAll")
    public List<UserDTO> findAll(){
        return userService.findAll();
    }

    /**
     * @param id
     * @return UserDTO
     * @Method findOne
     * 설명 : UserService.findOne 메서드를 호출하여 받은 값을 UserDTO 로 치환하여 반환해준다. 만약 값이 없다면 null 값을 반환한다.
     */
    @GetMapping("/findOne")
    public UserDTO findOne(@RequestParam("id") Long id){
        return userService.findOne(id);
    }


    /**
     * @param username
     * @return UserDTO
     * @Method findByName
     * 설명 : UserService.findByName 메서드를 호출하여 받은 값을 UserDTO 로 치환하여 반환해준다. 만약 값이 없다면 null 값을 반환한다.
     * 목적 : 유저 이름으로 유저 정보를 찾기 위한 Method 이다.
     */
    @GetMapping("/findByName")
    public UserDTO findByName(@RequestParam("name") String username){
        return userService.findByUsername(username);
    }

    /**
     * @param {name, password, country}
     * @return Long
     * @Method create
     * 설명 : name, password, country 값을 Parameter 로 받아오고, ID 값을 GenerateValue 로 설정하여
     * 유저 정보를 생성하여 저장하고, 저장한 유저의 정보를 반환해준다.
     * <p>
     * 목적 : 새로운 유저 정보를 생성하기 위한 Method 이다.
     */
    @GetMapping("/createUser")
    public Long create(@RequestParam("name") String name,
                       @RequestParam("password") String password,
                       @RequestParam("country") String country){
        return userService.create(name, password, country);
    }

    /**
     * @param {id, name, country}
     * @return UserDTO
     * @method update
     * 설명 : id, name, country 값을 받아와 UserService.findOne 메서드를 호출하여 id 를 통해 수정할 유저의 정보를 가져오고,
     *       만약 가져온 유저의 정보가 없다면 NullPointerException 을 터뜨리고 정보가 있다면 업데이트한 후 유저의 정보를 반환한다.
     * 목적 : 유저 정보를 업데이트 하기 위한 Method 이다.
     */
    @GetMapping("/updateUser")
    public UserDTO update(@RequestParam("id") Long id,
                          @RequestParam("name") String name,
                          @RequestParam("country") String country){
        return userService.update(id, name, country);
    }

    /**
     * @param id
     * @return String
     * @method remove
     * 설명 : id 값을 받아와 UserService.findOne 메서드를 사용하여 id 가 일치하는 유저의 정보를 받아와서
     *       만약 가져온 유저의 정보가 없다면 NullPointerException 을 터뜨리고 정보가 있다면 유저의 이름을 저장하고,
     *       해당 유저의 정보를 삭제 한 후 삭제된 유저의 이름과 삭제 성공여부를 출력한다.
     * 목적 : 유저 정보를 삭제하기 위한 Method 이다.
     */
    @GetMapping("/deleteUser")
    public String remove(@RequestParam("id") Long id) throws NullPointerException{
        String name = Objects.requireNonNull(userService.findOne(id)).getUsername();
        userService.delete(id);
        String isDelete;
        if(true){
            isDelete = " delete COMPLETE";
        }
        else{
            isDelete = " delete Fail";
        }
        return name + isDelete;
    }
}