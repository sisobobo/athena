package io.github.sisobobo.athena.serializer;


public class FlatBufferSerializer implements Serializer{

    @Override
    public <T, S> byte[] serialize(T data, Class<S> clazz) {
        return null ;
    }

    @Override
    public <T, S> T deserializer(byte[] data, Class<T> clazz, Class<S> sClazz) {
        return null ;
    }


}
