package com.picpay.wallet.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.picpay.wallet.converter.UserConverter;
import com.picpay.wallet.model.UserModel;
import com.picpay.wallet.request.UserRequest;
import com.picpay.wallet.entities.User;
import com.picpay.wallet.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired 
	private UserConverter userConverter;

	@GetMapping
	public List<UserModel> findAll(@RequestParam(required = false) String name) {
		return userConverter.toCollectionModel(userService.findAll(name));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserModel> findById(@PathVariable Long id) {
		User user = userService.findById(id);
		if (user != null) {
			return ResponseEntity.ok(userConverter.toModel(user));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<UserModel> findByCpf(@PathVariable String cpf) {
		User user = userService.findByCpf(cpf);
		if (user != null) {
			return ResponseEntity.ok(userConverter.toModel(user));
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@Transactional
	public ResponseEntity<UserModel> create(@RequestBody @Valid UserRequest userRequest,
			UriComponentsBuilder uriBuilder) {
		User cliente = userConverter.toDomainObject(userRequest);
		userService.create(cliente);
		URI uri = uriBuilder.path("/user/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).body(userConverter.toModel(cliente));
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<UserModel> update(@PathVariable Long id, @RequestBody @Valid UserRequest userRequest) {
		User cliente = userConverter.toDomainObject(userRequest);
		return ResponseEntity.ok(userConverter.toModel(userService.update(id, cliente)));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
