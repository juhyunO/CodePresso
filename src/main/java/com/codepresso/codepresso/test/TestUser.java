
package com.codepresso.codepresso.test;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TestUser {
    private Long id;
    private String name;
    private String email;


    public String toString() {
        Long var10000 = this.getId();
        return "TestUser(id=" + var10000 + ", name=" + this.getName() + ", email=" + this.getEmail() + ")";
    }
}
