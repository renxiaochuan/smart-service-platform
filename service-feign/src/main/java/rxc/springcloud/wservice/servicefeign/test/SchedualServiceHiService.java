package rxc.springcloud.wservice.servicefeign.test;

import org.springframework.stereotype.Component;

@Component
public class SchedualServiceHiService implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return name + "hi error";
    }
}
