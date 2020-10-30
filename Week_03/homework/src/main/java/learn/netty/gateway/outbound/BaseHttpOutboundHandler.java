package learn.netty.gateway.outbound;

import com.google.common.collect.Maps;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 转发的基类
 *
 * @author gray
 */
public abstract class BaseHttpOutboundHandler {

    protected final Map<String, String> backendUrlMap;

    private final Map<String, String> specialUrlMap = new HashMap<String, String>() {{
        put("/favicon.ico", "https://avatars1.githubusercontent.com/u/807508?s=48&v=4");
    }};

    protected BaseHttpOutboundHandler() {
        this.backendUrlMap = Maps.newHashMap();
    }

    protected BaseHttpOutboundHandler(Map<String, String> backendUrlMap) {
        this.backendUrlMap = backendUrlMap;
    }

    /**
     * 转换url为后端backend的url
     * 找到下一个/的地方，确定context-path
     *
     * @param originUrl start with '/', request.uri
     */
    protected String transferUrl(String originUrl) {
        // 特殊url
        String s = specialUrl(originUrl);
        if (s != null) {
            return s;
        }

        int sepIndex = originUrl.indexOf('/', 1);
        String contextPath = originUrl.substring(0, sepIndex == -1 ? originUrl.length() : sepIndex);

        return backendUrlMap.getOrDefault(contextPath, "http://localhost") + originUrl;
    }

    private String specialUrl(String originUrl) {
        return specialUrlMap.get(originUrl);
    }

    protected FullHttpResponse errorResponse() {
        String msg = "{\"msg\": \"系统异常\"}";
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(msg.getBytes(Charset.defaultCharset())));
        response.headers().set("Content-Type", "application/json");
        response.headers().setInt("Content-Length", msg.getBytes(Charset.defaultCharset()).length);

        return response;
    }

    public abstract void handle(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws IOException;
}
