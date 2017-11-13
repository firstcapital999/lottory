package com.thinkive;

import com.thinkive.common.authority.entity.Role;
import com.thinkive.common.authority.entity.User;
import com.thinkive.common.entity.Result;
import com.thinkive.lottery.constant.RedisConstant;
import com.thinkive.lottery.constant.UserConstant;
import com.thinkive.lottery.dao.UserRepository;
import com.thinkive.lottery.service.ILotteryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ILotteryService lotteryService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/findAll")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
    }

    @Test
    public void whenUploadSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file")
                .file(new MockMultipartFile("file","test.txt","multipart/form-data","hello upload".getBytes("UTF-8"))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testRedis() throws Exception{
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testRedis2() throws  Exception{
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testSaveUser() throws Exception{
        User user  = new User();
        user.setEnabled("1");
        user.setUserName("13456544323");
        user.setPassword("12345678");
        user.setRegistrationTime(new Date());
        Set<Role> roles = new HashSet<Role>();
        Role role = new Role();
        role.setRoleCode(UserConstant.ADMIN_CODE);
        role.setRoleName(UserConstant.ADMIN_NAME);
        role.setUser(user);
        roles.add(role);
        user.setRoles(roles);
        user = this.userRepository.save(user);
    }


    @Test
    public void testRedisIncrement(){
        this.redisTemplate.opsForHash().increment(RedisConstant.AWARD_POOL_NUM_PREFIX_KEY+"1","1",1 );
    }

    @Test
    public void testLottery(){

        Result result = this.lotteryService.lotteryMain("18670014155","1");
        System.out.print(result.getCode());
    }

    @Test
    public void testQueue(){
        Result result = this.lotteryService.getLatestAwardList("1",0,4);
        System.out.println(result.getCode());
    }
}
