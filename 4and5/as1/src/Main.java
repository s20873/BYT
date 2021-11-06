import handlers.HandlerAddition;
import handlers.HandlerDivision;
import handlers.HandlerMultiplication;
import handlers.HandlerSubtraction;
import models.Request;

import java.util.Scanner;
//chain responsibility pattern

public class Main {
    public HandlerDivision division;
    public HandlerMultiplication multiplication;
    public HandlerAddition addition;
    public HandlerSubtraction subtraction;

    Main(){
        division = new HandlerDivision();
        multiplication = new HandlerMultiplication();
        addition = new HandlerAddition();
        subtraction = new HandlerSubtraction();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Main calc = new Main();
        calc.division.setSuccessor(calc.multiplication);
        calc.multiplication.setSuccessor(calc.addition);
        calc.addition.setSuccessor(calc.subtraction);

        while (true){
            double n1 = 0, n2 = 0;
            String op = "";
            System.out.println("\t***Calculator***\nEnter first number");
            try {
                n1 = scanner.nextDouble();
                System.out.println("Enter operation: + - * /");
                op = scanner.next();
                System.out.println("Enter second number");
                n2 = scanner.nextDouble();
            }catch (Exception exception){
                System.out.println("\t--UNKNOWN RESULT");
                break;
            }

            Request request = new Request(n1, n2, op);
            calc.division.handleRequest(request);
        }
    }
}
