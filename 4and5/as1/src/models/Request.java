package models;

public class Request {
    private double num1;
    private double num2;
    private String operation;

    public Request(double num1, double num2, String operation){
        this.num1 = num1;
        this.num2 = num2;
        this.operation = operation;
    }

    public double getNum1() {
        return num1;
    }

    public double getNum2() {
        return num2;
    }

    public String getOperation() {
        return operation;
    }

    public String result(){
        double result = 0;
        switch (operation){
            case "+": result = num1 + num2; break;
            case "-": result = num1 - num2; break;
            case "*": result = num1 * num2; break;
            case "/": result = num1 / num2; break;
        }
        return "\t" + num1 + operation + num2 + " = " + result;
    }
}
