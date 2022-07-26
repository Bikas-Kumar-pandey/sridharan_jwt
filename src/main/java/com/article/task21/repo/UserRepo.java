package com.article.task21.repo;


import com.article.task21.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    boolean existsByMail(String username);

    boolean existsByMobile(String username);

    User findByMail(String username);

    User findByMobile(String username);
}
