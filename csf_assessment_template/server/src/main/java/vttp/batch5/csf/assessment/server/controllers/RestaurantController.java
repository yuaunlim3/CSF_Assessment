package vttp.batch5.csf.assessment.server.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import vttp.batch5.csf.assessment.server.Model.OrderDetails;
import vttp.batch5.csf.assessment.server.services.PaymentService;
import vttp.batch5.csf.assessment.server.services.RestaurantService;

@Controller
@RequestMapping(path = "/api")
public class RestaurantController {

  @Autowired private RestaurantService restaurantService;
  @Autowired private PaymentService paymentService;


  // TODO: Task 2.2
  // You may change the method's signature
  @GetMapping(path="/menu",produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> getMenus() {
    JsonArray result = restaurantService.getMenu();
    return ResponseEntity.status(200).body(result.toString());
  }

  // TODO: Task 4
  // Do not change the method's signature
  @PostMapping(path ="/food_order")
  public ResponseEntity<String> postFoodOrder(@RequestBody String payload) {
    OrderDetails orderDetails = restaurantService.fromString(payload);
    restaurantService.checkUser(orderDetails);
    

    orderDetails.setId(UUID.randomUUID().toString().replace("-", "").substring(0, 8));
    JsonObject result = paymentService.makePayment(orderDetails);
    System.out.println(result.toString());
    return ResponseEntity.ok(result.toString());
  }
}
