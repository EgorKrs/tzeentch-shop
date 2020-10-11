package com.loneliness.util.json_parser;



import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.loneliness.entity.Role;

import java.io.IOException;

public class RoleSerializer extends JsonSerializer<Role>
{

    @Override
    public void serialize(Role role, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(role.getAuthority());
    }
}