package test.org.custom.jackson.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import nl.isaac.globalcollect.fei.connect.domain.api.payment.CreatePaymentRequest;
import org.custom.jackson.json.GenericType;
import org.custom.jackson.json.NonJaxbBean;
import org.custom.jackson.json.PaymentMethodSpecificDeserializer;
import org.custom.jackson.json.SpecificTypeOne;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class TestJacksonCustomDeserialization {
    private  ObjectMapper mapper;

    @Before
    public  void setUp() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CreatePaymentRequest.class, new PaymentMethodSpecificDeserializer());

        mapper.registerModule(module);


    }

    @Test
    public void testNonJaxbJSONDeserializationWithUnmatchedStructure() throws IOException {
        String json ="{\n" +
                "  \"name\" : \"non-JAXB-bean\",\n" +
                "  \"description\" : \"Shantonav\",\n" +
                "  \"array\" : [ 1, 1, 2, 3, 5, 8, 13, 21 ],\n" +
                "  \"specificTypeOne\" : { \n" +
                "      \"propertyName\" : \"Idiotic specificTypeOne\"\n" +
                "  }\n" +
                "}";

        NonJaxbBean enrichedBean = mapper.readValue(json,NonJaxbBean.class);
        Assert.assertNotNull(enrichedBean);
        Assert.assertEquals("Shantonav",enrichedBean.getDescription());
        Assert.assertEquals("non-JAXB-bean",enrichedBean.getName());
        Assert.assertNotNull(enrichedBean.getActualType());
        Assert.assertEquals(SpecificTypeOne.class,enrichedBean.getActualType().getClass());
        Assert.assertEquals("Idiotic specificTypeOne",enrichedBean.getActualType().getPropertyName());
    }

    @Test
    public void testCreatePaymentRequestJSONDeserializationWithUnmatchedStructure() throws IOException, InterruptedException {
        Thread.sleep(30000);
        String json ="{\n" +
                "              \"order\": {\n" +
                "                \"amountOfMoney\": {\n" +
                "                  \"currencyCode\": \"EUR\",\n" +
                "                  \"amount\": 2980\n" +
                "                },\n" +
                "                \"customer\": {\n" +
                "                  \"merchantCustomerId\": \"1234\",\n" +
                "                  \"personalInformation\": {\n" +
                "                    \"name\": {\n" +
                "                      \"title\": \"Mr.\",\n" +
                "                      \"firstName\": \"Wile\",\n" +
                "                      \"surnamePrefix\": \"E.\",\n" +
                "                      \"surname\": \"Coyote\"\n" +
                "                    },\n" +
                "                    \n" +
                "                    \"dateOfBirth\": \"19490917\"\n" +
                "                  },\n" +
                "                  \"companyInformation\": {\n" +
                "                    \"name\": \"Acme Labs\"\n" +
                "                  },\n" +

                "                  \"billingAddress\": {\n" +
                "                    \"street\": \"Desertroad\",\n" +
                "                    \"houseNumber\": \"13\",\n" +
                "                    \"additionalInfo\": \"b\",\n" +
                "                    \"zip\": \"84536\",\n" +
                "                    \"city\": \"Monument Valley\",\n" +
                "                    \"state\": \"Utah\",\n" +
                "                    \"countryCode\": \"NL\"\n" +
                "                  },\n" +
                "                  \"shippingAddress\": {\n" +
                "                    \"name\": {\n" +
                "                      \"title\": \"Miss\",\n" +
                "                      \"firstName\": \"Road\",\n" +
                "                      \"surname\": \"Runner\"\n" +
                "                    },\n" +
                "                    \"street\": \"Desertroad\",\n" +
                "                    \"houseNumber\": \"1\",\n" +
                "                    \"additionalInfo\": \"Suite II\",\n" +
                "                    \"zip\": \"84536\",\n" +
                "                    \"city\": \"Monument Valley\",\n" +
                "                    \"state\": \"Utah\",\n" +
                "                    \"countryCode\": \"NL\"\n" +
                "                  },\n" +
                "                  \"contactDetails\": {\n" +
                "                    \"emailAddress\": \"wile.e.coyote@acmelabs.com\",\n" +
                "                    \"phoneNumber\": \"+1234567890\",\n" +
                "                    \"faxNumber\": \"+1234567891\",\n" +
                "                    \"emailMessageType\": \"html\"\n" +
                "                  },\n" +
                "                  \"vatNumber\": \"1234AB5678CD\"\n" +
                "                },\n" +
                "                \"references\": {\n" +
                "                  \"merchantOrderId\": \"123456\",\n" +
                "                  \"merchantReference\": \"AcmeOrder0001\",\n" +
                "                  \"invoiceData\": {\n" +
                "                    \"invoiceNumber\": \"000000123\",\n" +
                "                    \"invoiceDate\": \"20140306191500\"\n" +
                "                  },\n" +
                "                  \"descriptor\": \"Fast and Furry-ous\"\n" +
                "                },\n" +
                "                \"items\": [\n" +
                "                    {\n" +
                "                      \"amountOfMoney\": {\n" +
                "                        \"currencyCode\": \"EUR\",\n" +
                "                        \"amount\": 2500\n" +
                "                      },\n" +
                "                      \"invoiceData\": {\n" +
                "                        \"nrOfItems\": \"1\",\n" +
                "                        \"pricePerItem\": 2500,\n" +
                "                        \"description\": \"ACME Super Outfit\"\n" +
                "                      }\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"amountOfMoney\": {\n" +
                "                        \"currencyCode\": \"EUR\",\n" +
                "                        \"amount\": 480\n" +
                "                      },\n" +
                "                      \"invoiceData\": {\n" +
                "                        \"nrOfItems\": \"12\",\n" +
                "                        \"pricePerItem\": 40,\n" +
                "                        \"description\": \"Asperin\"\n" +
                "                      }\n" +
                "                    }\n" +
                "                  ]\n" +
                "              },\n" +
                "              \"cardPaymentMethodSpecificInput\": {\n" +
                "                \"paymentProductId\": 1,\n" +
                "                \"skipAuthentication\": false,\n" +
                "                \"card\": {\n" +
                "                  \"cvv\": \"223\",\n" +
                "                  \"cardNumber\":\"4155018932218818\",\n" +
                "                  \"expiryDate\": \"1220\",\n" +
                "                  \"cardholderName\": \"Wile E. Coyote\"\n" +
                "                }\n" +
                "              }\n" +
                "            }\n";
        for(int i=0 ; i<10000; i++) {
            CreatePaymentRequest enrichedBean = mapper.readValue(json, CreatePaymentRequest.class);
            Assert.assertNotNull(enrichedBean);

            Assert.assertNotNull(enrichedBean.getOrder());
            Assert.assertNotNull(enrichedBean.getOrder().getReferences());
            Assert.assertNotNull(enrichedBean.getOrder().getItems());
        }


    }
}
