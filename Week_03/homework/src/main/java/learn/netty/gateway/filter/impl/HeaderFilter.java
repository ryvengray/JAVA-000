package learn.netty.gateway.filter.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import learn.netty.gateway.filter.HttpRequestFilter;

public class HeaderFilter implements HttpRequestFilter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        // set header
        fullRequest.headers().set("nio", "gray");
    }
}
