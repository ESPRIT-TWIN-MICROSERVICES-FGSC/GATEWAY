package esprit.fgsc.gateway.controllers;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@RestController
public class GatewayInfoController{
    @Autowired private EurekaDiscoveryClient eurekaDiscoveryClient;
    @Autowired private EurekaClient eurekaClient;
    @Autowired private RouteLocator routeLocator;
    @GetMapping("/services")
    public List<String> GateWayServices(){
        return eurekaDiscoveryClient.getServices();
    }
    @GetMapping("/info")
    public InstanceInfo InstanceInfo(){
        return eurekaClient.getApplicationInfoManager().getInfo();
    }
    @GetMapping("/routes")
    public List<Route> ZuulGatewayRoutes(){
        return routeLocator.getRoutes();
    }
}
