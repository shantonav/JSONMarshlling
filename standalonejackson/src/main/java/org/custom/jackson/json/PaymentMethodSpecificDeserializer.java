package org.custom.jackson.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nl.isaac.globalcollect.fei.connect.domain.api.PaymentMethodSpecificInput;
import nl.isaac.globalcollect.fei.connect.domain.api.payment.*;

import java.io.IOException;
import java.util.List;

public class PaymentMethodSpecificDeserializer extends StdDeserializer<CreatePaymentRequest> {
    public PaymentMethodSpecificDeserializer(Class<?> vc) {
        super(vc);
    }
    public PaymentMethodSpecificDeserializer(){
        this(null);
    }

    public CreatePaymentRequest deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        long startTimeForDeserialization = System.currentTimeMillis();
        //System.out.println("Deserialization start Time "+startTimeForDeserialization + "ms");
        long startTime = System.currentTimeMillis();
        JsonNode node = jp.getCodec().readTree(jp);
        long endTime = System.currentTimeMillis();

        //System.out.println("Time taken to parse the JSON tree "+(endTime-startTime) + "ms");


        CreatePaymentRequest returnType = new CreatePaymentRequest();

        PaymentMethodSpecificInput whichGenericTypeToCreate = null;

        startTime = System.currentTimeMillis();
        JsonNode specificNode = node.get("cardPaymentMethodSpecificInput");
        endTime = System.currentTimeMillis();

        //System.out.println("Time taken to get a specific node "+(endTime-startTime) + "ms");
        if (specificNode!= null){
            startTime = System.currentTimeMillis();
            whichGenericTypeToCreate = ((ObjectMapper)jp.getCodec()).treeToValue(specificNode,CardPaymentMethodSpecificInput.class);
            endTime = System.currentTimeMillis();
            //System.out.println("Time taken to create the specific object from the specific node "+(endTime-startTime) + "ms");

            startTime = System.currentTimeMillis();
            ((ObjectNode)node).remove("cardPaymentMethodSpecificInput");
            endTime = System.currentTimeMillis();
            //System.out.println("Time taken to remove the specific node "+(endTime-startTime) + "ms");
        }

        startTime = System.currentTimeMillis();
        JsonNode fullOrderNode = node.get("order");
        endTime = System.currentTimeMillis();
        //System.out.println("Time taken get the  Order node "+(endTime-startTime) + "ms");
       /* JsonNode customerNode = node.path("order").get("customer");

        Customer customer = ((ObjectMapper)jp.getCodec()).treeToValue(customerNode,Customer.class);

        JsonNode orderRefNode = node.path("order").get("references");
        JsonNode orderItemsJsonNode = node.path("order").get("items");

        OrderReferences orderReferences = ((ObjectMapper)jp.getCodec()).treeToValue(orderRefNode,OrderReferences.class);

        List<LineItem> lineItems = ((ObjectMapper)jp.getCodec()).readerFor(new TypeReference<List<LineItem>>() {
        }).readValue(orderItemsJsonNode);


        Order order = new Order();
        order.setReferences(orderReferences);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setItems(lineItems);
        order.setShoppingCart(shoppingCart);
        */
        startTime = System.currentTimeMillis();
        Order order = ((ObjectMapper)jp.getCodec()).treeToValue(fullOrderNode,Order.class);
        endTime = System.currentTimeMillis();
        //System.out.println("Time taken get create Order object from Order node "+(endTime-startTime) + "ms");

        returnType.setOrder(order);
        returnType.setPaymentMethodSpecificInput(whichGenericTypeToCreate);

        long endTimeForDeserialization = System.currentTimeMillis();
        //System.out.println("Deserialization end Time "+endTimeForDeserialization + "ms");

        System.out.println("Time taken to deserialization " +(endTimeForDeserialization -startTimeForDeserialization) + "ms");
        return returnType;
    }
}
