package rxc.springcloud.wservice.servicefeign.test;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(value = "service-hi" , fallback = SchedualServiceHiService.class)
public interface  SchedualServiceHi {
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    public abstract String sayHiFromClientOne(@RequestParam(value = "name") String name);
}
