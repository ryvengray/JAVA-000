package learn.netty.gateway.outbound.okhttp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import learn.netty.gateway.inbound.OkHttpInboundHandler;
import learn.netty.gateway.outbound.BaseHttpOutboundHandler;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @author gray
 */
public class OkHttpOutboundHandler extends BaseHttpOutboundHandler {

    private static final Logger log = LoggerFactory.getLogger(OkHttpInboundHandler.class);

    public OkHttpOutboundHandler(Map<String, String> backendUrlMap) {
        super(backendUrlMap);
    }

    @Override
    public void handle(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws IOException {
        String backendUrl = transferUrl(fullHttpRequest.uri());

        FullHttpResponse fullHttpResponse = null;

        OkHttpClient httpClient = new OkHttpClient();

        Request request = new Request.Builder().url(backendUrl)
                .get().build();

        try (Response response = httpClient.newCall(request).execute()) {
            ResponseBody body = response.body();
            assert body != null;
            if (response.isSuccessful()) {
                byte[] bodyBytes = body.bytes();
                fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(bodyBytes));
                fullHttpResponse.headers().setInt("Content-Length", Integer.parseInt(response.header("Content-Length", "0")));
                fullHttpResponse.headers().set("Content-Type", response.header("Content-Type"));
            } else {
                log.error("Error: " + response.code() + "." + body.string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fullHttpResponse == null) {
                fullHttpResponse = errorResponse();
            }
            ctx.write(fullHttpResponse);
            ctx.flush();
        }
    }
}