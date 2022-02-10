package com.picpay.wallet.exception;

import javax.persistence.EntityExistsException;

public class UserAlreadyExistsException extends EntityExistsException {
	
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(String message) {
		super(message);
	}
	
	public UserAlreadyExistsException(Long id, String cpf) {
		this(String.format("Existe um cliente cadastrado com o cpf %s.", cpf));
	}
}
