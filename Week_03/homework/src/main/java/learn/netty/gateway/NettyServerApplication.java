package learn.netty.gateway;

import learn.netty.gateway.inbound.InboundSingleServer;
import learn.netty.gateway.routers.PropertyRead;
import learn.netty.gateway.routers.pojo.RouterProperty;

import java.util.Map;

public class NettyServerApplication {

    public static void main(String[] args) throws Exception {
        Map<String, RouterProperty> proxyMap = PropertyRead.routerPropetiesMap();
        InboundSingleServer inboundSingleServer = new InboundSingleServer(8888, proxyMap);
        inboundSingleServer.run();
    }
}
