package vttp.batch5.csf.assessment.server.services;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.batch5.csf.assessment.server.Model.OrderDetails;
import vttp.batch5.csf.assessment.server.Model.OrderItems;
import vttp.batch5.csf.assessment.server.Model.Exception.OrderFailedException;
import vttp.batch5.csf.assessment.server.repositories.OrdersRepository;
import vttp.batch5.csf.assessment.server.repositories.RestaurantRepository;

@Service
public class PaymentService {

    private final String PAYMENT_URL = "https://payment-service-production-a75a.up.railway.app/api/payment";
    @Autowired private OrdersRepository ordersRepository;
    @Autowired private RestaurantRepository restaurantRepository;

    @Value("${payee}")
    String payee;

    public JsonObject makePayment(OrderDetails orderDetails){
        System.out.println("Making payment");
        double total= 0.0;
        List<OrderItems> itemsList = orderDetails.getItems();
        for(OrderItems item:itemsList){
            total += item.getTotal();
        }
        JsonObject json = Json.createObjectBuilder()
                        .add("order_id",orderDetails.getId())
                        .add("payer",orderDetails.getUsername())
                        .add("payee",payee)
                        .add("payment",total)
                        .build();


        System.out.println(json.toString());
        RestTemplate restTemplate = new RestTemplate();
        try{
            RequestEntity<String> req = RequestEntity
                        .post(PAYMENT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Authenticate",orderDetails.getUsername())
                        .body(json.toString());

            ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
            String payload = resp.getBody();
            //{"payment_id":"01JPVGMG6FQPTEBGFM60Q4E2DE",
            //"order_id":"b0710169","timestamp":1742532658095,"total":15.399999618530273}

            JsonObject result = Json.createReader(new StringReader(payload)).readObject();

            String payment_id = result.getString("payment_id");
            restaurantRepository.saveToSQL(orderDetails, payment_id, total);

            long milliSec = result.getInt("timestamp");
            DateFormat simple = new SimpleDateFormat(
                "dd MMM yyyy HH:mm:ss:SSS Z");
    
            Date date = new Date(milliSec);

            Document order = new Document();
            order.append("_id",orderDetails.getId());
            order.append("order_id",orderDetails.getId());
            order.append("username",orderDetails.getUsername());
            order.append("total", total);
            order.append("timestamp",simple.format(date));
            
            List<OrderItems> orderItems = orderDetails.getItems();
            List<Document> items = new LinkedList<>();
            for(OrderItems item:orderItems){
                Document document = new Document();
                document.append("id",item.getId());
                document.append("price",item.getPrice());
                document.append("quantity",item.getQuantity());

                items.add(document);

            }

            order.append("items", items);

            ordersRepository.saveToMongo(order);

            return Json.createObjectBuilder()
                    .add("orderId",orderDetails.getId())
                    .add("paymentId", payment_id)
                    .add("total", total)
                    .add("timestamp",date.toString())
                    .build();


        }
        catch(Exception ex){
            throw new OrderFailedException(ex.getMessage());
        }
    }
}
