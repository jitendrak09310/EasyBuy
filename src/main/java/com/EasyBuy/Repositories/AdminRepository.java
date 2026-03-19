package com.EasyBuy.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EasyBuy.Entity.User;

public interface AdminRepository extends JpaRepository<User, Long> {

}
