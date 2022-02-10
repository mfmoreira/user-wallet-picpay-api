package com.picpay.wallet.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.picpay.wallet.model.UserModel;
import com.picpay.wallet.request.UserRequest;
import com.picpay.wallet.entities.User;

@Component
public class UserConverter {

	@Autowired
	private ModelMapper modelMapper;

	public UserModel toModel(User user) {
		return modelMapper.map(user, UserModel.class);
	}

	public List<UserModel> toCollectionModel(List<User> users) {
		return users.stream()
				.map(user -> this.toModel(user))
				.collect(Collectors.toList());
	}

	public User toDomainObject(UserRequest userRequest) {
		return modelMapper.map(userRequest, User.class);
	}
}
