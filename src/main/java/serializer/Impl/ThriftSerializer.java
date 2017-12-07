package serializer.Impl;

import org.apache.thrift.TBase;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import serializer.ISerializer;

/**
 * fhrift序列化/反序列化
 *
 * @author bfy
 * @version 1.0.0
 */
public class ThriftSerializer implements ISerializer{

    @Override
    public <T> byte[] serialize(T obj) {
        try {
            if (!(obj instanceof TBase)) {
                throw new UnsupportedOperationException("not supported obj type");
            }
            TSerializer serializer = new TSerializer(new TBinaryProtocol.Factory());
            return serializer.serialize((TBase) obj);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> tClass) {
        try {
            if (!TBase.class.isAssignableFrom(tClass)) {
                throw new UnsupportedOperationException("not supported obj type");
            }
            TBase o = (TBase) tClass.newInstance();
            TDeserializer tDeserializer = new TDeserializer(new TBinaryProtocol.Factory());
            tDeserializer.deserialize(o, data);
            return (T) o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
