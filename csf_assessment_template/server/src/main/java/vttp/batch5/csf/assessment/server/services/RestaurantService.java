package vttp.batch5.csf.assessment.server.services;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.batch5.csf.assessment.server.Model.OrderDetails;
import vttp.batch5.csf.assessment.server.Model.OrderItems;
import vttp.batch5.csf.assessment.server.Model.Exception.FailedException;
import vttp.batch5.csf.assessment.server.repositories.OrdersRepository;
import vttp.batch5.csf.assessment.server.repositories.RestaurantRepository;

@Service
public class RestaurantService {

  @Autowired
  private OrdersRepository ordersRepository;
  @Autowired
  private RestaurantRepository restaurantRepository;

  // TODO: Task 2.2
  // You may change the method's signature
  public JsonArray getMenu() {
    List<Document> items = ordersRepository.getMenu();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    for (Document doc : items) {
      // Document{{_id=08635ecb, name=Tacos De alambre, price=4.6, description=Chicken
      // cooked with bacon, topped with Oaxaca cheese}}
      JsonObject jsonObject = Json.createObjectBuilder()
          .add("id", doc.getString("_id"))
          .add("name", doc.getString("name"))
          .add("description", doc.getString("description"))
          .add("price", doc.getDouble("price"))
          .build();
      arrayBuilder.add(jsonObject);
    }

    return arrayBuilder.build();
  }

  // TODO: Task 4

  public OrderDetails fromString(String payload) {
    //{"username":"fred","password":"b280f6be0fb6617394fd0db9a5ed7861820e27b816d141872f8dca69",
    //"items":[{"id":"ef78c865","price":7.7,"quantity":1,"total":7.7}]}
    JsonObject jPayload = Json.createReader(new StringReader(payload)).readObject();

    OrderDetails orderDetails = new OrderDetails();
    orderDetails.setUsername(jPayload.getString("username"));
    orderDetails.setPassword(jPayload.getString("password"));
    List<OrderItems> itemList = new LinkedList<>();

    JsonArray items = jPayload.getJsonArray("items");
    for (int i = 0; i < items.size(); i++) {
      JsonObject item = items.getJsonObject(i);
      OrderItems orderItems = new OrderItems();
      orderItems.setId(item.getString("id"));
      orderItems.setPrice(item.getJsonNumber("price").doubleValue());
      orderItems.setQuantity(item.getInt("quantity"));
      orderItems.setTotal(item.getJsonNumber("total").doubleValue());

      itemList.add(orderItems);
    }

    orderDetails.setItems(itemList);

    return orderDetails;
  }

  public void checkUser(OrderDetails orderDetails) {

    boolean userName = restaurantRepository.checkUsername(orderDetails.getUsername());
    if(userName){
      restaurantRepository.checkPassword(orderDetails.getPassword(),orderDetails.getUsername());
    }

  }

}
