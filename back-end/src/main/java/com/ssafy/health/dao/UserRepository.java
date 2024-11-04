
package com.ssafy.health.dao;

import com.ssafy.health.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserRepository {
    User findUserById(@Param("userId") Long userId);

    void insertUser(User user);

    void updateUser(User user);

    void deleteUser(@Param("userId") Long userId);

    User selectUserByName(String username);

}
