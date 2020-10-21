```java
package rw.geek.learn.week01;

import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author gray
 */
public class HelloClassLoader extends ClassLoader {

    private String resourcePath;

    HelloClassLoader(String resourcePath) {
        super();
        this.resourcePath = resourcePath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = readFromResource();
        return defineClass(name, data, 0, data.length);
    }

    private byte[] readFromResource() {
        InputStream resourceAsStream = this.getClass().getResourceAsStream(resourcePath);
        Assert.notNull(resourceAsStream, "Resource not found");
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int buffer = 256;
            byte[] bs = new byte[buffer];
            int rc;
            while ((rc = resourceAsStream.read(bs, 0, buffer)) > 0) {
                baos.write(bs, 0, rc);
            }
            byte[] source = baos.toByteArray();
            byte[] result = new byte[source.length];
            for (int i = 0; i < source.length; i++) {
                result[i] = (byte) (255 - source[i]);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("解析失败");
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        HelloClassLoader helloClassLoader = new HelloClassLoader("/files/Hello.xlass");
        Class<?> hello = helloClassLoader.findClass("Hello");
        Method helloMethod = hello.getDeclaredMethod("hello");
        helloMethod.invoke(hello.newInstance());
    }

}
```