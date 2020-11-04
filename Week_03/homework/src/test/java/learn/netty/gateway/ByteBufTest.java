package learn.netty.gateway;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;

public class ByteBufTest {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer(10);
        ByteBuf srcBuf = Unpooled.buffer(10);
        print(byteBuf);

        byte[] bytes = {1, 2, 3, 4, 5};
        byteBuf.writeBytes(bytes);
        srcBuf.writeBytes(bytes);
        print(byteBuf);

        byte b1 = byteBuf.readByte();
        byte b2 = byteBuf.readByte();
        System.out.println(b1 + "," + b2);
        print(byteBuf);

        byteBuf.discardReadBytes();
        print(byteBuf);

        byteBuf.readByte();
        byteBuf.readByte();

        print(byteBuf);
        byteBuf.writeInt(10000);
        print(byteBuf);

        byteBuf.clear();
        print(byteBuf);
        byteBuf.writeBytes(srcBuf);
        print(byteBuf);
    }

    private static void print(ByteBuf buf) {
        System.out.println(buf.toString());
        System.out.println(Arrays.toString(buf.array()));
    }

}
