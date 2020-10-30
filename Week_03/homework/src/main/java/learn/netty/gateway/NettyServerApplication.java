package learn.netty.gateway;

import com.google.common.collect.Maps;
import learn.netty.gateway.inbound.InboundSingleServer;

import java.util.Map;

public class NettyServerApplication {

    /**
     * text/html 类型的返回需要ico
     */
    public static final String FAVICON_ICO = "";

    public static void main(String[] args) throws Exception {
        Map<String, String> proxyMap = Maps.newHashMap();
        proxyMap.put("/", "http://localhost:8082");

        InboundSingleServer inboundSingleServer = new InboundSingleServer(8888, proxyMap);
        inboundSingleServer.run();
    }
}
