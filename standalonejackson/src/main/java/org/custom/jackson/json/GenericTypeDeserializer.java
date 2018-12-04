package org.custom.jackson.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;



public class GenericTypeDeserializer extends StdDeserializer<NonJaxbBean> {
    public GenericTypeDeserializer(Class<?> vc) {
        super(vc);
    }

    public GenericTypeDeserializer(){
        this(null);
    }


    @Override
    public NonJaxbBean deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);


        NonJaxbBean returnType = null;

        GenericType whichGenericTypeToCreate = null;
        if (node.has("specificTypeOne")){
            JsonNode specificNode = node.get("specificTypeOne");
            String propertyName = specificNode.get("propertyName").asText();

            whichGenericTypeToCreate = new SpecificTypeOne();
            ((SpecificTypeOne)whichGenericTypeToCreate).specifBehaviour();
            ((SpecificTypeOne)whichGenericTypeToCreate).setPropertyName(propertyName);
            ((ObjectNode)node).remove("specificTypeOne");
        }else if (node.has("specificTypeTwo") ){
            whichGenericTypeToCreate = new SpecificTypeTwo();
            ((ObjectNode)node).remove("specificTypeTwo");
        }

        JsonNode nameNode = node.get("name");
        String name = nameNode.asText();

        JsonNode descriptionNode = node.get("description");
        String description = descriptionNode.asText();
        JsonNode arrayNode = node.get("array");
        int[] array = ((ObjectMapper)jp.getCodec()).treeToValue(arrayNode,int[].class);

        returnType = new NonJaxbBean();
        returnType.setName(name);
        returnType.setDescription(description);
        returnType.setArray(array);
        returnType.setActualType(whichGenericTypeToCreate);


       return returnType;


    }
}
