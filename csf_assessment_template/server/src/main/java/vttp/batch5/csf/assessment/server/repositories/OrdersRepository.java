package vttp.batch5.csf.assessment.server.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch5.csf.assessment.server.Model.OrderDetails;


@Repository
public class OrdersRepository {

  @Autowired private MongoTemplate template;
  
  private final String MONGO_MENUS= "menus";
  private final String MONGO_ORDERS = "orders";

  // TODO: Task 2.2
  // You may change the method's signature
  // Write the native MongoDB query in the comment below
  //
  //  db.menus.find({})
  //
  public List<Document> getMenu() {
    List<Document> items = template.findAll(Document.class,MONGO_MENUS);
    return items;
  }

  // TODO: Task 4
  // Write the native MongoDB query for your access methods in the comment below
  //
  /*
   db.menus.insert({
    "_id":"abcd1234",
    "order_id": "abcd1234",
    "payment_id": "xyz789",
    "username":"fred",
    "total":23.10,
    "timestamp":""
    items:[
      {
        "id": "xxx", "price":12.0 , "quantity":2
      }
    ]

   })
   */

   public void saveToMongo(Document toSave){
      template.insert(toSave,MONGO_ORDERS);
   }

  
}
