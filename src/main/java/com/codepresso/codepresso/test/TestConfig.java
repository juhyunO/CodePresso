

package com.codepresso.codepresso.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class TestConfig {
//    @Autowired
//    private CodepressoMapper codepressoMapper;

    private final CodepressoMapper codepressoMapper;

//    @Autowired
//    public void setterMapper(CodepressoMapper codepressoMapper){
//        this.codepressoMapper = codepressoMapper;
//    }

//
//    public TestConfig(CodepressoMapper codepressoMapper){
//        this.codepressoMapper = codepressoMapper;
//    }


    @Bean
    public MybatisService mybatisService() {
        return new MybatisService(this.mybatisTestRepository());
    }

    @Bean
    public MybatisTestRepository mybatisTestRepository() {
        return new MybatisTestRepository(this.codepressoMapper);
    }

}
