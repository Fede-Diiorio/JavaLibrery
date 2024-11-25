package com.coderhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.coderhouse.dtos.UserDTO;
import com.coderhouse.interfaces.DAOInterface;
import com.coderhouse.models.User;
import com.coderhouse.repositories.UserRepository;

public class UserService implements DAOInterface<User, UserDTO> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserDTO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO getById(Long id) {
		User user = getModelById(id);
		return convertToDTO(user);
	}

	@Override
	public User getModelById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));
	}

	@Override
	public UserDTO save(User object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO update(Long id, User object) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}
	
	private UserDTO convertToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		
		userDTO.setEmail(user.getEmail());
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setPhone(user.getPhone());
		
		return userDTO;
		
	}

}
