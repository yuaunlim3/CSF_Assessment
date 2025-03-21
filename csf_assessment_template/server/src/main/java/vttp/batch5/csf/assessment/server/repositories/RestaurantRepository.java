package vttp.batch5.csf.assessment.server.repositories;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.batch5.csf.assessment.server.Model.OrderDetails;
import vttp.batch5.csf.assessment.server.Model.Exception.FailedException;
import vttp.batch5.csf.assessment.server.Model.Exception.OrderFailedException;

// Use the following class for MySQL database
@Repository
public class RestaurantRepository {

    @Autowired
    private JdbcTemplate template;

    private final String FIND_USER = """
            SELECT count(*) as count FROM customers WHERE username = ?
            """;
    private final String CHECK_PASSWORD = """
            SELECT password FROM customers WHERE username = ?
            """;

    private final String INSERT = """
            INSERT INTO place_orders(order_id,payment_id,order_date,total,username) VALUES(?,?,?,?,?)
            """;

    public boolean checkUsername(String name) {
        SqlRowSet rs = template.queryForRowSet(FIND_USER, name);
        while (rs.next()) {
            int checker = rs.getInt("count");
            if (checker == 0) {
                throw new FailedException("Invalid username and/or password");
            }
        }
        return true;
    }

    
    public boolean checkPassword(String password,String name) {
        SqlRowSet rs = template.queryForRowSet(CHECK_PASSWORD,name);
        while (rs.next()) {
            String checker = rs.getString("password");
            if (!checker.equals(password)) {
                throw new FailedException("Invalid username and/or password");
            }
        }
        return true;
    }


    public void saveToSQL(OrderDetails orderDetails, String paymentId, double total){
        int checker = template.update(INSERT, orderDetails.getId(),paymentId,new Date(),total,orderDetails.getUsername());

        if(checker < 1){
            throw new OrderFailedException("Failed to save to mySQL");
        }
    }
}
