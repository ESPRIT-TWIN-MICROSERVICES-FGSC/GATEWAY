package esprit.fgsc.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
//@RestController
//class ServiceInstanceRestController {
//
//    @Autowired
//    private EurekaClient discoveryClient;
//    @Autowired
//    private EurekaDiscoveryClient dsc;
//    @RequestMapping("/a/{id}")
//    public List serviceInstancesByApplicationName(@PathVariable String id) {
//        return this.discoveryClient.getInstancesById(id);
//    }
//    @RequestMapping("/b/applications")
//    public Applications serviceInstances() {
//        return this.discoveryClient.getApplications();
//    }
//    @RequestMapping("/c/info")
//    public ApplicationInfoManager applicationInfoManager(){
//        // TODO : Doesnt work
//        return discoveryClient.getApplicationInfoManager();
//    }
//    @RequestMapping("/d/services")
//    public List<String> ZOOMER(){
//        return dsc.getServices();
//    }
//    @RequestMapping("/e/service-homepage/{serviceName}")
//    public InstanceInfo serviceUrl(@RequestParam String serviceName) {
//        InstanceInfo instanceInfo = discoveryClient.getNextServerFromEureka(serviceName, false);
//        return instanceInfo;
//    }
//}
