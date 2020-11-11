package learn.netty.gateway.outbound.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import learn.netty.gateway.outbound.BaseHttpOutboundHandler;
import learn.netty.gateway.routers.pojo.RouterProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class NettyOutboundHandler extends BaseHttpOutboundHandler {

    private static final Logger log = LoggerFactory.getLogger(NettyOutboundHandler.class);

    public NettyOutboundHandler(Map<String, RouterProperty> backendUrlMap) {
        super(backendUrlMap);
    }

    @Override
    public void handle(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws IOException {
        String backendUrl = transferUrl(fullHttpRequest.uri());

        NettyHttpClient nettyHttpClient = new NettyHttpClient(ctx, fullHttpRequest);
        nettyHttpClient.connect(backendUrl);
    }

}
