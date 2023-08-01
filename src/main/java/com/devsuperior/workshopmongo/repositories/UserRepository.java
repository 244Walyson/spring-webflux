package com.devsuperior.workshopmongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.devsuperior.workshopmongo.entities.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String>{

    Mono<User> findByEmail(String email);
}
