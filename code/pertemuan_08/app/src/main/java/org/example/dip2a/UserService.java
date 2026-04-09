package org.example.dip2a;

public class UserService {
    private final UserRepositoryImpl repository = new UserRepositoryImpl();

    public User getUser(int id) {
        return repository.findById(id);
    }
}
