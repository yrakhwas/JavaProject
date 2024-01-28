package org.example;

import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)
    {
        System.out.println("Enter expression you want calculate : ");
        System.out.println("+ - Plus\n- - Minus\n* - Multyply\n/ - Dividing\n** - pow\n% - percents");
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        double result = calculateExpression(expression);
        System.out.printf("Result of your expression %s = %s", expression, result);
    }

    private static double calculateExpression(String expression)
    {
        try {
            String[] parts = expression.split("\\s+|(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");
            double operand1 = Double.parseDouble(parts[0]);
            String operator = parts[1];
            double operand2 = Double.parseDouble(parts[2]);
            switch (operator) {
                case "+":
                    return operand1 + operand2;
                case "-":
                    return operand1 - operand2;
                case "*":
                    return operand1 * operand2;
                case "/":
                    if (operand2 == 0) {
                        System.out.println("Error! Dividing by zero!");
                    } else {
                        return operand1 / operand2;
                    }
                case "**":
                    double sum = 1;
                    for (int i = 0; i < operand2; i++) {
                        sum *= operand1;
                    }
                    return sum;
                case "%":
                    return operand1 / 100 * operand2;
                default:
                    System.out.println("Unknown operation! Enter expression one more time!");
                    return Double.NaN;
            }
        }catch(Exception ex)
        {
            System.out.println("Error of calculating expression" + ex);
        }
        return Double.NaN;
    }
}