package fr.meritis.prsample.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Duration;

public class DurationDeserializer extends StdDeserializer<Duration> {

    public DurationDeserializer() {
        this(null);
    }

    public DurationDeserializer(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Duration deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        return Duration.parse(node.asText());

    }
}
