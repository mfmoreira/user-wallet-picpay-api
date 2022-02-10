package com.picpay.wallet.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.picpay.wallet.entities.User;
import com.picpay.wallet.exception.UserAlreadyExistsException;
import com.picpay.wallet.exception.UserNotFoundException;
import com.picpay.wallet.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAll(String name) {
		if (name == null) {
			return userRepository.findAll();
		} else {
			return userRepository.findByName(name);
		}
	}

	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	public User findByCpf(String cpf) {
		return userRepository.findByCpf(cpf).orElse(null);
	}

	@Transactional
	public User create(User user) {
		User userDb = this.findByCpf(user.getCpf());
		if (userDb != null) {
			throw new UserAlreadyExistsException(user.getId(), user.getCpf());
		}
		return userRepository.save(user);
	}

	@Transactional
	public User update(Long id, User user) {
		User dbUser = this.findById(id);
		if (dbUser != null) {
			BeanUtils.copyProperties(user, dbUser, "id");
			return userRepository.save(dbUser);
		} else {
			throw new UserNotFoundException(id);
		}
	}

	@Transactional
	public void delete(Long id) {
		User dbUser = this.findById(id);
		if (dbUser != null) {
			userRepository.delete(dbUser);
		} else {
			throw new UserNotFoundException(id);
		}
	}

}
