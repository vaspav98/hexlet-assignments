package exercise.service;

import exercise.model.User;
import exercise.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    // BEGIN
    public Mono<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<User> create(User data) {
        return userRepository.save(data);
    }

    public Mono<User> update(Long id, User data) {
        data.setId(id);
        return userRepository.save(data);
    }

    public Mono<Void> delete(Long id) {
        return userRepository.deleteById(id);
    }
    // END
}
