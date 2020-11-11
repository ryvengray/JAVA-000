package learn.netty.gateway.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import learn.netty.gateway.filter.HttpRequestFilter;
import learn.netty.gateway.filter.impl.HeaderFilter;
import learn.netty.gateway.outbound.BaseHttpOutboundHandler;
import learn.netty.gateway.outbound.netty.NettyOutboundHandler;
import learn.netty.gateway.routers.pojo.RouterProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author gray
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(HttpInboundHandler.class);

    private final Map<String, RouterProperty> proxyServerMap;

    private BaseHttpOutboundHandler httpOutboundHandler;

    private HttpRequestFilter requestFilter = new HeaderFilter();

    public HttpInboundHandler(Map<String, RouterProperty> proxyServerMap) {
        this.proxyServerMap = proxyServerMap;
        this.afterConstruct();
    }

    private void afterConstruct() {
        this.httpOutboundHandler = new NettyOutboundHandler(this.proxyServerMap);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;

        // request filter
        requestFilter.filter(request, ctx);

        this.httpOutboundHandler.handle(ctx, request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Exception caught", cause);
//        super.exceptionCaught(ctx, cause);
    }
}
