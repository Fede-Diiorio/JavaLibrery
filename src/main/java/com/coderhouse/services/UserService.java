package com.coderhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.dtos.UserDTO;
import com.coderhouse.interfaces.DAOInterface;
import com.coderhouse.models.User;
import com.coderhouse.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService implements DAOInterface<User, UserDTO> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserDTO> getAll() {
		List<User> users = userRepository.findAll();
		return users.stream().map(this::convertToDTO).toList();
	}

	@Override
	public UserDTO getById(Long id) {
		User user = getUserById(id);
		return convertToDTO(user);
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));
	}

	@Override
	@Transactional
	public UserDTO save(User object) {
		User savedUser = userRepository.save(object);
		validateMandatoryFields(savedUser);
		return convertToDTO(savedUser);
	}

	@Override
	@Transactional
	public UserDTO update(Long id, User object) throws Exception {
	    User user = getUserById(id);

	    if (object.getEmail() != null && !object.getEmail().isEmpty()) {
	        user.setEmail(object.getEmail());
	    }
	    if (object.getName() != null && !object.getName().isEmpty()) {
	        user.setName(object.getName());
	    }
	    if (object.getPhone() != null && !object.getPhone().isEmpty()) {
	        user.setPhone(object.getPhone());
	    }

	    User updatedUser = userRepository.save(user);

	    return convertToDTO(updatedUser);
	}


	@Override
	@Transactional
	public void delete(Long id) {
		if (!userRepository.existsById(id)) {
			throw new IllegalArgumentException("Usuario no encontrado.");

		}
		userRepository.deleteById(id);

	}

	private void validateMandatoryFields(User user) {
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			throw new IllegalArgumentException("El email del usuario es olbigatorio.");
		}
		if (user.getName() == null || user.getName().isEmpty()) {
			throw new IllegalArgumentException("El nombre del usuario es olbigatorio.");
		}
		if (user.getPhone() == null || user.getPhone().isEmpty()) {
			throw new IllegalArgumentException("El teléfono del usuario es olbigatorio.");
		}
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
