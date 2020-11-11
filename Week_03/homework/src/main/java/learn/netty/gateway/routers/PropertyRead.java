package learn.netty.gateway.routers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import learn.netty.gateway.routers.pojo.RouterProperty;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 解析Yaml文件，获取配置的路由contextPath和后台server的关系
 */
public class PropertyRead {

    public static Map<String, RouterProperty> routerPropetiesMap() throws IOException {
        ClassPathResource resource = new ClassPathResource("servers.yaml");
        Yaml y = new Yaml();
        Object load = y.load(resource.getInputStream());
        Gson gson = new Gson();
        String jsonString = gson.toJson(load);
        List<RouterProperty> properties = gson.fromJson(jsonString, new TypeToken<List<RouterProperty>>(){}.getType());
        return properties.stream().collect(Collectors.toMap(RouterProperty::getUrl, Function.identity()));
    }
}
