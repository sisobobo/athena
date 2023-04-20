package io.github.sisobobo.athena.serializer;

public interface Serializer {

    /**
     * 序列化
     * @param data
     * @param clazz
     * @return
     * @param <T>
     * @param <S>
     */
    <T , S>byte[] serialize(T data , Class<S> clazz);

    /**
     * 反序列化
     * @param data
     * @param clazz
     * @param sClazz
     * @return
     * @param <T>
     * @param <S>
     */
    <T , S> T deserializer(byte[] data , Class<T> clazz , Class<S> sClazz);
}
