package vttp.batch5.csf.assessment.server.Model.Exception;

public class FailedException extends RuntimeException {
    public FailedException(){
        super();
    }

    public FailedException(String msg){
        super(msg);
    }

    public FailedException(String msg,Throwable t){
        super(msg,t);
    }
}

