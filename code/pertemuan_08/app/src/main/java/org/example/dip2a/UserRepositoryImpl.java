package org.example.dip2a;

public class UserRepositoryImpl {
    public User findById(int id) {
        return new User(id, "User " + id);
    }
}
