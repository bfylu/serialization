package serializer.Impl;

import org.jboss.marshalling.*;
import serializer.ISerializer;
import sun.reflect.annotation.ExceptionProxy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * JBoss Marshalling序列化框架
 *
 */
public class MarshallingSerializer implements ISerializer{

    final static MarshallingConfiguration configuration = new MarshallingConfiguration();

    //获取序列化工厂对象，参数serial标识创建的是Java序列化工厂对象
    final static MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");

    static {
        configuration.setVersion(5);
    }

    public <T> byte[] serialize(T obj) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            final Marshaller marshaller = marshallerFactory.createMarshaller(configuration);
            marshaller.start(Marshalling.createByteOutput(byteArrayOutputStream));
            marshaller.writeObject(obj);
            marshaller.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }


    public <T> T deserialize(byte[] data, Class<T> tClass) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            final Unmarshaller unmarshaller = marshallerFactory.createUnmarshaller(configuration);
            unmarshaller.start(Marshalling.createByteInput(byteArrayInputStream));
            Object object = unmarshaller.readObject();
            unmarshaller.finish();
            return (T) object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
