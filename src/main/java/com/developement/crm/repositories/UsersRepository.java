package com.developement.crm.repositories;

import com.developement.crm.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UserModel, String> {
    UserDetails findByLogin(String login);
    Optional<UserModel> findUserModelByLogin(String login);

    Optional<UserModel> findUserModelByEmail(String email);


}
