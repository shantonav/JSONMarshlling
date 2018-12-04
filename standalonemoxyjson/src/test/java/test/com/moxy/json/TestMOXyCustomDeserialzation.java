package test.com.moxy.json;

import nl.isaac.globalcollect.fei.connect.domain.api.payment.CardPaymentMethodSpecificInput;
import nl.isaac.globalcollect.fei.connect.domain.api.payment.CreatePaymentRequest;


import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class TestMOXyCustomDeserialzation {

    @Test
    public  void testMOXyDeserialzation() throws JAXBException, InterruptedException {
        //Thread.sleep(30000);

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


        Map<String, Object> properties = new HashMap<String, Object>(3);

        properties.put(MarshallerProperties.MEDIA_TYPE, "application/json");
        properties.put(MarshallerProperties.JSON_INCLUDE_ROOT, false);
        JAXBContext jc = JAXBContext.newInstance(new Class[]{CreatePaymentRequest.class},properties);

        Unmarshaller um = jc.createUnmarshaller();

        for (int i=0 ; i < 10000 ; i++) {
            StringReader reader = new StringReader(json);
            StreamSource source = new StreamSource(reader);
            long startTime = System.currentTimeMillis();
            CreatePaymentRequest subject = um.unmarshal(source, CreatePaymentRequest.class).getValue();
            long endTime = System.currentTimeMillis();

            System.out.println("Time taken to deserialze "+(endTime-startTime)+"ms");

            Assert.assertNotNull(subject);
            Assert.assertNotNull(subject.getOrder());
            Assert.assertNotNull(subject.getOrder().getCustomer());
            Assert.assertNotNull(subject.getOrder().getItems());
            Assert.assertNull(subject.getOrder().getShoppingCart());
            Assert.assertNotNull(subject.getOrder().getCustomer().getContactDetails().getEmailMessageType());
            Assert.assertEquals("4155018932218818",
                    ((CardPaymentMethodSpecificInput) subject.getPaymentMethodSpecificInput()).getCard().getCardNumber());
        }


    }
}
