package handlers;
import models.Request;

public class HandlerSubtraction extends Handler{
    @Override
    public void handleRequest(Request request) {
        if(request.getOperation().equals("-")){
            System.out.println(request.result());
        }
        else{
            if(this.getSuccessor() != null){
                this.getSuccessor().handleRequest(request);
            }
            else{
                System.out.println("Result is unknown");
            }
        }
    }
}
