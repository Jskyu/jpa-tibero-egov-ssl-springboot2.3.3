package com.example.demo.repository;

import com.example.demo.vo.UserVO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Interface UserRepository
 * 설명 : CrudRepository 를 UserEntity, Long(EntityClassName, IdKeyType)로 받아와 사용하는 Interface 이다.
 *       CrudRepository 특성상 따로 구현체 없이 메소드의 사용이 가능하다.
 *       UserEntity 를 다루기 위한 Repository 이다.
 */
@Repository
public interface UserRepository extends CrudRepository<UserVO, Long> {

    List<UserVO> findAll();

    /**
     * @param username
     * @return Optional<UserEntity>
     * @method findByName
     * 설명 : name 값을 받아와 UserEntity class 의 Name 필드와 값이 일치하는 정보를 받아온다.
     * 목적 : 유저 name 정보로 유저 전체의 정보를 조회하기 위한 Method 이다.
     */
    List<UserVO> findByUsername(String username);

    /**
     * @return Long
     * @method findUserEntityByLastResultId
     * 설명 : UserEntity 테이블에서 ID 순으로 정렬하였을때 마지막 데이터의 ID 값을 가져온다.
     * 목적 : 가장 마지막 데이터의 ID 값을 가져오기 위한 Method 이다.
     */
//    @Query(value = "select u.id from UserEntity u" +
//            " where rownum = (select count(*) from UserEntity)" +
//            " order by u.id"
//            , nativeQuery = true)
//    Long findUserEntityByLastResultId();

}