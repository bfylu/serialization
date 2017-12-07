package serializer.Impl;

import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import serializer.ISerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Avro序列化/反序列化
 */
public class AvroSerializer implements ISerializer{


    @Override
    public <T> byte[] serialize(T obj) {
        try {
            if (!(obj instanceof SpecificRecordBase)) {
                throw new UnsupportedOperationException("not supported obj ytpe");
            }
            DatumWriter userDatumWriter = new SpecificDatumWriter(obj.getClass());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BinaryEncoder binaryEncoder = EncoderFactory.get().directBinaryEncoder(outputStream, null);
            userDatumWriter.write(obj, binaryEncoder);
            return outputStream.toByteArray();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> tClass) {
        try {
            if (!SpecificRecordBase.class.isAssignableFrom(tClass)) {
                throw new UnsupportedOperationException("not supported tClass type");
            }
            DatumReader userDatumReader = new SpecificDatumReader(tClass);
            BinaryDecoder binaryDecoder = DecoderFactory.get().directBinaryDecoder(new ByteArrayInputStream(data), null);
            return (T) userDatumReader.read(tClass.newInstance(), binaryDecoder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
