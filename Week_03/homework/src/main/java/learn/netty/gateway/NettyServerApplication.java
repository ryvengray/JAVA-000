package learn.netty.gateway;

import com.google.common.collect.Maps;
import learn.netty.gateway.inbound.InboundSingleServer;

import java.util.Map;

public class NettyServerApplication {

    public static void main(String[] args) throws Exception {
        Map<String, String> proxyMap = Maps.newHashMap();
        proxyMap.put("/", "http://localhost:8082");
        proxyMap.put("/test", "http://localhost:8083");

        InboundSingleServer inboundSingleServer = new InboundSingleServer(8888, proxyMap);
        inboundSingleServer.run();
    }
}
