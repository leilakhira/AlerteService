package com.example.alerte_service.Service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.IOException;
import java.util.ArrayList;
public class PageDeserializer extends StdDeserializer<Page<?>> {

    public PageDeserializer() {
        this(null);
    }
    @Override
    public Page<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return null;
    }

    public PageDeserializer(Class<?> vc) {
        super(vc);
    }
    public Page<?> deserialize(JsonNode jsonNode, DeserializationContext deserializationContext) throws IOException {
        // Extract necessary information from the JSON node and create a PageImpl
        // This example assumes that the structure of the JSON matches the Page structure
        // Adjust this based on the actual structure of your JSON response
        return new PageImpl<>(new ArrayList<>(), null, jsonNode.get("totalElements").asLong());
    }
}

