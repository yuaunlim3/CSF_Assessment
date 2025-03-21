package vttp.batch5.csf.assessment.server.Model.Exception;


public class OrderFailedException extends RuntimeException {
    public OrderFailedException(){
        super();
    }

    public OrderFailedException(String msg){
        super(msg);
    }

    public OrderFailedException(String msg,Throwable t){
        super(msg,t);
    }
}