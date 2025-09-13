
package com.codepresso.codepresso.test;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisTestRepository {
    private final CodepressoMapper codepressoMapper;

    public List<TestUser> findAllUsers() {
        return this.codepressoMapper.findAllUsers();
    }

    public TestUser findUserById(Long id) {
        return this.codepressoMapper.findUserById(id);
    }

    public void insertUser(TestUser user) {
        this.codepressoMapper.insertUser(user);
    }

}
