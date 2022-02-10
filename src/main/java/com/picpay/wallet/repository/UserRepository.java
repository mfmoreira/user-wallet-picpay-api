package com.picpay.wallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.picpay.wallet.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByCpf(String cpf);
	public List<User> findByName(String name);
	
}
