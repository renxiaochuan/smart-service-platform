package rxc.springcloud.wservice.eurekaclient;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rxc.springcloud.wservice.mapper.UserMapper;
import rxc.springcloud.wservice.model.User;

@EnableEurekaClient
@RestController
@SpringBootApplication
@MapperScan("rxc.springcloud.wservice.mapper")//将项目中对应的mapper类的路径加进来就可以了
public class SmartServiceEurekaClientApplication {
    @Value("${server.port}")
    String port;
//
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/hi")
    public String home(@RequestParam String name) {
        User user = userMapper.selectByPrimaryKey(Integer.parseInt(name));
        return "hi "+user.getUserName()+"  phone:"+ user.getPhone()+",i am from port:" +port;
//        return null;
    }
    public static void main(String[] args) {
        SpringApplication.run(SmartServiceEurekaClientApplication.class, args);
    }
}
