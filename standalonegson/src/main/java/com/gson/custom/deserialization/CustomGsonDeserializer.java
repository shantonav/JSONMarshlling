package com.gson.custom.deserialization;

import com.google.gson.*;
import nl.isaac.globalcollect.fei.connect.domain.api.PaymentMethodSpecificInput;
import nl.isaac.globalcollect.fei.connect.domain.api.payment.CardPaymentMethodSpecificInput;
import nl.isaac.globalcollect.fei.connect.domain.api.payment.CreatePaymentRequest;
import nl.isaac.globalcollect.fei.connect.domain.api.payment.Order;
import nl.isaac.globalcollect.fei.connect.domain.api.payment.ShoppingCart;

import java.lang.reflect.Type;

public class CustomGsonDeserializer implements JsonDeserializer<CreatePaymentRequest> {
    public CreatePaymentRequest deserialize(JsonElement jsonElement, Type type,
                                            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        long startTimeForDeserialization = System.currentTimeMillis();
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        JsonElement paymentMethodSpecificInput = jsonObject.get("cardPaymentMethodSpecificInput");
        CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest();

        if (paymentMethodSpecificInput != null){
            PaymentMethodSpecificInput genericPMSpecificInut =
                    jsonDeserializationContext.deserialize(paymentMethodSpecificInput, CardPaymentMethodSpecificInput.class);
            createPaymentRequest.setPaymentMethodSpecificInput(genericPMSpecificInut);
        }
        JsonElement orderElememt = jsonObject.get("order");
        Order newOrder = jsonDeserializationContext.deserialize(orderElememt, Order.class);

        createPaymentRequest.setOrder(newOrder);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setItems(newOrder.getItems());
        newOrder.setShoppingCart(shoppingCart);
        long endTimeForDeserialization = System.currentTimeMillis();
        System.out.println("Time taken to deserialization " +(endTimeForDeserialization -startTimeForDeserialization) + "ms");
        return createPaymentRequest;
    }
}
