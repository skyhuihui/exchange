package com.zag.core.spring.properties;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ByteArrayResource;

import com.zag.core.exception.SystemException;

public class PropertiesResource extends ByteArrayResource {

    public PropertiesResource(Properties properties) {
        super(prop2bytes(properties));
    }

    private static byte[] prop2bytes(Properties properties) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            properties.store(os, null);
        } catch (IOException e) {
            throw new SystemException("properties convert to bytes failed.", e);
        }
        return os.toByteArray();
    }

}
