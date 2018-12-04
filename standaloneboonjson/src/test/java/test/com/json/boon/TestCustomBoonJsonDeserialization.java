package test.com.json.boon;

import com.json.boon.NonJaxbBean;
import org.boon.Str;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.junit.Test;

import java.util.Map;

public class TestCustomBoonJsonDeserialization {
    @Test
    public void testBoonJsonDeserialization(){
        String json ="{\n" +
                "  \"name\" : \"non-JAXB-bean\",\n" +
                "  \"description\" : \"Shantonav\",\n" +
                "  \"array\" : [ 1, 1, 2, 3, 5, 8, 13, 21 ],\n" +
                "  \"specificTypeOne\" : { \n" +
                "      \"propertyName\" : \"Idiotic specificTypeOne\"\n" +
                "  }\n" +
                "}";

        ObjectMapper mapper = JsonFactory.create();

        Map<String,Object> map = mapper.parser().parseMap(json);
        map.toString();

    }
}
