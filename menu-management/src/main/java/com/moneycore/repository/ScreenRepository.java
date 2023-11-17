package com.moneycore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.Screen;

import java.util.List;

@Repository("ScreenRepository")
public interface ScreenRepository extends JpaRepository<Screen, String> {

    boolean existsByName(String Name);

    boolean existsByScreenId(String screenId);

    boolean existsByScreenCode(String screenCode);

    boolean existsByUrl(String url);

    @Query(value = "select u.* from screen u where user_create != '0'",nativeQuery = true)
    List<Screen> findAllScreens();

    @Query(value = "select i.* from screen i where screen_code=:screenCode",nativeQuery = true)
    List<Screen> findByScreenBYCode(@Param("screenCode") String screenCode);
}
