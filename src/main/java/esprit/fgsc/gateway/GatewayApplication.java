package esprit.fgsc.gateway;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableEurekaServer
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
@RestController
class ServiceInstanceRestController {

    @Autowired
    private EurekaClient discoveryClient;

    @RequestMapping("/instances/{id}")
    public List serviceInstancesByApplicationName(@PathVariable String id) {
        return this.discoveryClient.getInstancesById(id);
    }
    @RequestMapping("/services")
    public InstanceInfo.InstanceStatus serviceInstances() {
        return this.discoveryClient.getInstanceRemoteStatus();
    }
    @RequestMapping("/test")
    public InstanceInfo servicesUrls(){
        return discoveryClient.getApplicationInfoManager().getInfo();
    }
    @RequestMapping("/services-homepage")
    public String serviceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("FGSC_USERS_MICROSERVICE", false);
        return instance.getHomePageUrl();
    }
}
