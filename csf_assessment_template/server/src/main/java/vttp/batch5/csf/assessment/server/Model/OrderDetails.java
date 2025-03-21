package vttp.batch5.csf.assessment.server.Model;

import java.util.List;

public class OrderDetails {
    private String username;
    private String password;
    private List<OrderItems> items;
    private String id;
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<OrderItems> getItems() {
        return items;
    }
    public void setItems(List<OrderItems> items) {
        this.items = items;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    
}
