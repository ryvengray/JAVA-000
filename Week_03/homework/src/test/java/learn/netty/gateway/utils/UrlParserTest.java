package learn.netty.gateway.utils;

import org.junit.Test;

public class UrlParserTest {

    @Test
    public void testAddressFromUrl() {

        System.out.println(UrlParser.addressFromUrl("http://localhost:8080/test"));
        System.out.println(UrlParser.addressFromUrl("http://localhost:8080"));

    }
}
