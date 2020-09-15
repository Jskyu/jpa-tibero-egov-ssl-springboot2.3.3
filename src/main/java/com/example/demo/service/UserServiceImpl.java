package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.DuplicateValidateException;
import com.example.demo.repository.UserRepository;
import com.example.demo.vo.Enum.UserRole;
import com.example.demo.vo.UserVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Class UserServiceImpl
 * 설명 : UserDAO 의 메소드들을 호출할때에 필요한 로직을 구현한 Class
 */
@Service // Service Bean 주입
@Transactional(readOnly = true) // Transactional을 읽기모드로 주입
@RequiredArgsConstructor // private final 로 선언되어있는 변수들을 생성자를 이용하여 의존성을 추가해준다.
public class UserServiceImpl extends EgovAbstractServiceImpl implements UserService {

    //UserRepository 의존성 주입
    private final UserRepository userRepository;

    // ID값을 1씩 올려주기 위한 변수
    private Long generateValueId;

    /**
     * @return Long
     * @Method getGenerateValueId
     * 설명 : ID 값을 받아오지 않은 상태라면 ID 값을 받아오고, 메서드를 호출한 지점에 ID값을 1 더해서 반환해준다.
     * 목적 : ID 값을 자동으로 입력하기 위한 Method
     */
//    public Long getGenerateValueId(){
//        System.out.println("======================"+generateValueId+"======================");
//        // ID값을 받아오지 않은 상태일때 값을 받아온다.
//        if(generateValueId == null){
//            generateValueId = userRepository.findUserEntityByLastResultId();
//        }
//        System.out.println("======================"+generateValueId+"======================");
//        // ID값을 1 증가시켜 반환
//        return ++generateValueId;
//    }

    /**
     * @param username
     * @return UserDTO
     * @Method findByName
     * 설명 : UserRepository.findByUsername 메소드에 name 값을 넘겨주고 반환받은 값을 DTO 로
     *       치환하여 username, country 정보만을 메소드 호출지점에 반환해준다.
     * 목적 : 유저의 이름을 입력받아 그 이름을 가진 유저를 찾아주는 Method 이다.
     */
    @Override
    public UserDTO findByUsername(String username){
        return userRepository.findByUsername(username).stream()
                .map(u -> new UserDTO(u.getUsername(), u.getCountry()))
                .collect(Collectors.toList()).get(0);
    }

    /**
     * @return List<UserDTO>
     * @Method findAll
     * 설명 : UserRepository 의 findAll 메소드를 호출하고 반환받은 값을 DTO 로 치환하여 username, country 의 정보만을 메소드를 호출한 지점으로 반환해준다.
     * 목적 : 모든 유저를 조회하기 위한 Method 이다.
     */
    @Override
    public List<UserDTO> findAll(){
        return userRepository.findAll().stream()
                .map(u -> new UserDTO(u.getUsername(), u.getCountry()))
                .collect(Collectors.toList());
    }

    /**
     * @param id
     * @return UserDTO
     * @Method findOne
     * 설명 : UserRepository 의 findById 메소드에 id 값을 넘겨주고 반환받은 값을 DTO 로 치환하여 메소드 호출지점에 반환해준다.
     * 목적 : 유저의 아이디를 입력받아 그 아이디를 가진 유저를 찾아주는 Method 이다.
     */
    @Override
    public UserDTO findOne(Long id){
        UserVO userVO = userRepository.findById(id).orElse(null);
        return userVO.toDto();
    }

    /**
     * @param {username, country, password}
     * @return Long
     * @Method create
     * 설명 : username, password, country 값를 받아와 이미 있는 username 라면 DuplicateValidateException 을 터뜨리고, 아니라면
     * UserRepository 의 save 메소드로 전달해주고 메소드를 호출한 지점에 받은 유저객체의 ID 값을 반환해준다.
     * 목적 : 유저 정보를 생성하기 위한 Method 이다.
     */
    @Override
    @Transactional // Transaction 을 불러오는 Annotation. readOnly=true 를 입력하지 않으면 수정이 가능하다.
    public Long create(String username, String password, String country){
        duplicateValidateUser(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserVO userVO = UserVO.builder().id(9223372036854775807L).username(username)
                .password(passwordEncoder.encode(password)).country(country).build();
        return userRepository.save(userVO).getId();
    }

    /**
     * @param {id, username, country}
     * @return UserDTO
     * @Method update
     * 설명 : UserVO 객체를 받아와 기존데이터에 값이 존재하는지 확인 한 후 값이 있다면 기존의 데이터를 갱신하고, 기존데이터에 값이
     *       존재하지 않는다면 AssertionError 로 예외처리한다.
     * 목적 : 유저 정보를 업데이트하기 위한 Method 이다.
     */
    @Override
    @Transactional
    public UserDTO update(Long id, String username, String country){
        UserVO userVO = userRepository.findById(id).orElse(null);
        if(userVO == null) throw new AssertionError();
        userVO.updateUser(username, country);
        return userVO.toDto();
    }

    /**
     * @param id
     * @return void
     * @Method delete
     * 설명 : UserDAO 의 deleteById 메소드에 주입받은 id 값을 보내어 호출한다.
     * 목적 : ID 값을 받아와 그 유저 정보를 삭제시키기 위한 Method 이다.
     */
    @Override
    @Transactional
    public void delete(Long id){
        userRepository.deleteById(id);
    }

    /**
     * @param username
     * @method duplicateValidateUser
     * 설명 : id 를 받아와 UserDAO.findById 메서드를 호출하여 ID 값이 일치하는 유저가 있다면
     * DuplicateValidateException 을 터뜨리며 "이미 존재하는 ID" 라는 메세지를 출력한다.
     * 목적 : 유저의 중복 검사를 하기 위한 Method 이다.
     */
    private void duplicateValidateUser(String username){
        if(userRepository.findByUsername(username).size() != 0){
            throw new DuplicateValidateException("이미 존재하는 이름");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        List<UserVO> findUser = userRepository.findByUsername(username);

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(username.equals("admin")){
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else{
            authorities.add(new SimpleGrantedAuthority(UserRole.MEMBER.getValue()));
        }

        return new User(findUser.get(0).getUsername(), findUser.get(0).getPassword(), authorities);
    }
}