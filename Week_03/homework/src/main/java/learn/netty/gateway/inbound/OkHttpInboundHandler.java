package learn.netty.gateway.inbound;

import com.google.common.collect.Maps;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import learn.netty.gateway.outbound.BaseHttpOutboundHandler;
import learn.netty.gateway.outbound.okhttp.OkHttpOutboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author gray
 */
public class OkHttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(OkHttpInboundHandler.class);

    private final Map<String, String> proxyServerMap;

    private BaseHttpOutboundHandler httpOutboundHandler;

    public OkHttpInboundHandler(Map<String, String> proxyServerMap) {
        this.proxyServerMap = proxyServerMap;
        this.afterConstruct();
    }

    private void afterConstruct() {
        this.httpOutboundHandler = new OkHttpOutboundHandler(this.proxyServerMap);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("Channel read");
        FullHttpRequest request = (FullHttpRequest) msg;
        this.httpOutboundHandler.handle(ctx, request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Exception caught", cause);
        super.exceptionCaught(ctx, cause);
    }
}
