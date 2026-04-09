package org.example.dip2b;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public User findById(int id) {
        return new User(id, "User " + id);
    }
}
