package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.serializer;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public class GenderSerializer extends ValueSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        String formatedGender = "Male".toUpperCase().equals(value.toUpperCase()) ? "M" : " F";
        gen.writeString(formatedGender);
    }
}
