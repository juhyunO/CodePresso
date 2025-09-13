
package com.codepresso.codepresso.test;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MybatisService {
    private final MybatisTestRepository mybatisTestRepository;

    public List<TestUser> getAllUsers() {
        return this.mybatisTestRepository.findAllUsers();
    }

    public TestUser getUserById(Long id) {
        TestUser user = this.mybatisTestRepository.findUserById(id);
        if (user == null) {
            throw new RuntimeException("User not found with id: " + id);
        } else {
            return user;
        }
    }

    public void createUser(String name, String email) {
        TestUser user = new TestUser();
        user.setName(name);
        user.setEmail(email);
        this.mybatisTestRepository.insertUser(user);
    }

}
