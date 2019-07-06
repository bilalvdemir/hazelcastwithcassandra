package com.example.bilal.model.util.json;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Helps to trim leading and trailing whitespace for each and every JSON value
 * whose type is String.class.
 * This is a global deserializer (applies to every JSON deserialization).
 *
 */
@Component
public class StringTrimModule extends SimpleModule {

    private static final long serialVersionUID = -3717124477651229767L;

    public StringTrimModule() {
        addDeserializer(String.class, new StdScalarDeserializer<String>(String.class) {

            private static final long serialVersionUID = 2371110630440702630L;

            @Override
            public String deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException, JsonProcessingException {
                return jsonParser.getValueAsString().trim();
            }
        });
    }

}
