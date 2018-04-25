package rxc.springcloud.wservice.eurekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * Spring could EurekaServer程序主入口
 *
 * @author Administrator
 *
 */

@EnableEurekaServer
@SpringBootApplication
public class EurekaserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaserviceApplication.class, args);
    }
}