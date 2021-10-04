package esprit.fgsc.gateway;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    private EurekaDiscoveryClient dsc;
    @RequestMapping("/eureka/instances/{id}")
    public List serviceInstancesByApplicationName(@PathVariable String id) {
        return this.discoveryClient.getInstancesById(id);
    }
    @RequestMapping("/eureka/applications")
    public Applications serviceInstances() {
        return this.discoveryClient.getApplications();
    }
    @RequestMapping("/eureka/info")
    public ApplicationInfoManager applicationInfoManager(){
        return discoveryClient.getApplicationInfoManager();
    }
    @RequestMapping("/eureka/services")
    public List<String> ZOOMER(){
        return dsc.getServices();
    }
    @RequestMapping("/eureka/service-homepage/{serviceName}")
    public InstanceInfo serviceUrl(@RequestParam String serviceName) {
        InstanceInfo instanceInfo = discoveryClient.getNextServerFromEureka(serviceName, false);
        return instanceInfo;
    }
}
