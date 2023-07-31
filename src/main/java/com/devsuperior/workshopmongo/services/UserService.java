package com.devsuperior.workshopmongo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.workshopmongo.dto.PostDTO;
import com.devsuperior.workshopmongo.dto.UserDTO;
import com.devsuperior.workshopmongo.entities.User;
import com.devsuperior.workshopmongo.repositories.UserRepository;
import com.devsuperior.workshopmongo.services.exceptioons.ResourceNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public Flux<UserDTO> findAll() {
		Flux<UserDTO> result = repository.findAll().map(UserDTO::new);
		return result;
	}

	public Mono<UserDTO> findById(String id) {
		return repository.findById(id).map(existingUser -> new UserDTO(existingUser))
				.switchIfEmpty(Mono.error(new ResourceNotFoundException("id not found")));
	}
//
//	@Transactional(readOnly = true)
//	public List<PostDTO> findPosts(String id) {
//		User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
//		List<PostDTO> result = user.getPosts().stream().map(x -> new PostDTO(x)).toList();
//		return result;
//	}
//
	public Mono<UserDTO> insert(UserDTO dto) {
		User entity = new User();
		copyDtoToEntity(dto, entity);
		Mono<User> result = repository.save(entity);
		return result.map(UserDTO::new);
	}
//
//	@Transactional
//	public UserDTO update(String id, UserDTO dto) {
//		User entity = repository.findById(id)
//				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
//		copyDtoToEntity(dto, entity);
//		entity = repository.save(entity);
//		return new UserDTO(entity);
//	}
//
//	@Transactional
//	public void delete(String id) {
//		User entity = repository.findById(id)
//				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
//		repository.delete(entity);
//	}
//
	private void copyDtoToEntity(UserDTO dto, User entity) {
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
	}
}
