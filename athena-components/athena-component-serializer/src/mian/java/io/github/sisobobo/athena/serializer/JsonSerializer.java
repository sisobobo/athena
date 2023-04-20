package io.github.sisobobo.athena.serializer;

import com.alibaba.fastjson2.JSON;

/**
 * JSON序列化
 */
public class JsonSerializer implements Serializer {

    @Override
    public <T, S> byte[] serialize(T data, Class<S> clazz) {
        return JSON.toJSONBytes(data);
    }

    @Override
    public <T, S> T deserializer(byte[] data, Class<T> clazz, Class<S> sClazz) {
        return JSON.parseObject(data, clazz);
    }
}
