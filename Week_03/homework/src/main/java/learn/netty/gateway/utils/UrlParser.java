package learn.netty.gateway.utils;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.SocketAddress;
import java.net.URL;

public class UrlParser {

    public static SocketAddress addressFromUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            return new InetSocketAddress(url.getHost(), url.getPort() == -1 ? 80: url.getPort());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("错误的url: " + urlString);
        }
    }

}
