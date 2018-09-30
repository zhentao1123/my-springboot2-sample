package com.example.demo.config.redis;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.example.demo.util.json.JsonUtil;

/**
 * 自定义序列化
 * @author zhangzhengtao
 *
 * @param <T>
 */
public class RedisJsonSerializer<T> implements RedisSerializer<T> {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;

    public RedisJsonSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }
	
	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
            return new byte[0];
        }
        return JsonUtil.obj2json(t).getBytes(DEFAULT_CHARSET);
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return JsonUtil.json2obj(str, clazz);
	}

}
