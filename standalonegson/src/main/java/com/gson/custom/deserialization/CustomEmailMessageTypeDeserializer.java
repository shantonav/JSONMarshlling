package com.gson.custom.deserialization;

import com.google.gson.*;
import nl.isaac.globalcollect.fei.connect.domain.api.EmailMessageType;

import java.lang.reflect.Type;

public class CustomEmailMessageTypeDeserializer implements JsonDeserializer<EmailMessageType> {
    public EmailMessageType deserialize(JsonElement jsonElement, Type type,
                                        JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        EmailMessageType emailMessageType = EmailMessageType.HTML;
        String jsonObject = jsonElement.getAsString();


        if (jsonObject != null){
            emailMessageType = EmailMessageType.valueOf(jsonObject);
        }
        return emailMessageType;
    }
}
