package learn.netty.gateway.outbound.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyHttpClient {

    private final static Logger log = LoggerFactory.getLogger(NettyHttpClient.class);

    private ChannelHandlerContext outCtx;

    private FullHttpRequest outRequest;

    private FullHttpResponse fullHttpResponse;

    public NettyHttpClient(ChannelHandlerContext outCtx, FullHttpRequest outRequest) {
        this.outCtx = outCtx;
        this.outRequest = outRequest;
    }

    public void connect(String url) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpResponseDecoder());
                            pipeline.addLast(new HttpRequestEncoder());
                            pipeline.addLast(new ChannelInboundHandlerAdapter() {

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    super.channelActive(ctx);
                                    fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    if (msg instanceof HttpResponse) {
                                        log.info("Http response");
                                        HttpResponse response = (HttpResponse)msg;
                                        log.info("Response: {}", response);
                                        // 赋值Headers
                                        fullHttpResponse.headers().set(response.headers());
                                    }
                                    if (msg instanceof HttpContent) {
                                        log.info("----------- Http content");
                                        try {
                                            HttpContent httpContent = (HttpContent)msg;
                                            ByteBuf buf = httpContent.content();

                                            ByteBuf content = fullHttpResponse.content();
                                            content.writeBytes(buf);

                                            fullHttpResponse.replace(Unpooled.wrappedBuffer(buf));
                                            log.info("Content: {}", buf.toString(CharsetUtil.UTF_8));
                                        } finally {
                                            log.info("Out context write: {}, response: {}", outCtx, fullHttpResponse);
                                            outCtx.write(fullHttpResponse);
                                            outCtx.flush();
                                            outCtx.close();
                                        }
                                    }
                                }

                                @Override
                                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                    super.channelReadComplete(ctx);
                                    log.info("Complete complete");
                                    ctx.close();
                                }
                            });
                        }
                    });
            ChannelFuture sync = b.connect("localhost", 8082).sync();
            sync.channel().write(outRequest);
            sync.channel().flush();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
