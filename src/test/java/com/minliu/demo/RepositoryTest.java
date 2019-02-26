package com.minliu.demo;

import com.minliu.demo.pojo.User;
import com.minliu.demo.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryTest.class);

    @Resource
    private UserRepository userRepository;

    @Test
    public void insertTest(){
        User user = new User();
        user.setUsername("root");
        user.setPassword("123456");
        user.setEmail("root@qq.com");
        user.setPhone("14712349876");
        user.setSalt("");
        userRepository.save(user);
    }

    @Test
    public void queryTest(){
        User root = userRepository.findUserByUsername("root");
        logger.info(root.getEmail());
    }

}
