package com.coderhouse.mappers;

import org.springframework.stereotype.Component;

import com.coderhouse.dtos.UserDTO;
import com.coderhouse.models.User;

@Component
public class UserMapper {

	public UserDTO toDTO(User user) {
		return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone());
	}
}
