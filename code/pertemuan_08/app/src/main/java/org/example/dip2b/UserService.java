package org.example.dip2b;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getUser(int id) {
        return repository.findById(id);
    }
}
