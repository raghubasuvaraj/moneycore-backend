package com.moneycore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.Menu;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String>{

    boolean existsByName(String Name);
    boolean existsByMenuCode(String menuCode);
    boolean existsByMenuId(String menuId);
    boolean existsByUrl(String Url);

    @Query(value="select u.* from menu u where user_create != '0'",nativeQuery = true)
    List<Menu> findAllMenus();

}
