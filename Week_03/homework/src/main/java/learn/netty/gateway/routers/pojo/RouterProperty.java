package learn.netty.gateway.routers.pojo;

import java.util.List;

public class RouterProperty {

    private String url;

    private List<RouterServer> servers;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<RouterServer> getServers() {
        return servers;
    }

    public void setServers(List<RouterServer> servers) {
        this.servers = servers;
    }
}
