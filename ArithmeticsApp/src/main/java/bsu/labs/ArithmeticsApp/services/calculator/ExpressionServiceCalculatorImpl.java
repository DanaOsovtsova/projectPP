package bsu.labs.ArithmeticsApp.services.calculator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExpressionServiceCalculatorImpl implements ExpressionServiceCalculator{
    @Override
    public List<String> calculateExpression(List<String> data) {
        List<Double> resultList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            data.set(i, data.get(i).replaceAll("\\s+", ""));
            Pattern pattern = Pattern.compile("(\\d+\\.?\\d*)|[-+*/()]");
            Matcher matcher = pattern.matcher(data.get(i));
            StringBuilder postfix = new StringBuilder();
            Stack<String> operatorStack = new Stack<>();

            while (matcher.find()) {
                String token = matcher.group();
                if (token.matches("\\d+\\.?\\d*")) {
                    postfix.append(token).append(" ");
                } else {
                    if (operatorStack.isEmpty() || token.equals("(")) {
                        operatorStack.push(token);
                    } else if (token.equals(")")) {
                        while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                            postfix.append(operatorStack.pop()).append(" ");
                        }
                        if (!operatorStack.isEmpty() && operatorStack.peek().equals("(")) {
                            operatorStack.pop();
                        }
                    } else {
                        while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(") && hasHigherPrecedence(operatorStack.peek(), token)) {
                            postfix.append(operatorStack.pop()).append(" ");
                        }
                        operatorStack.push(token);
                    }
                }
            }

            while (!operatorStack.isEmpty()) {
                postfix.append(operatorStack.pop()).append(" ");
            }

            Stack<Double> operandStack = new Stack<>();
            String[] tokens = postfix.toString().trim().split("\\s+");
            for (String token : tokens) {
                if (token.matches("\\d+\\.?\\d*")) {
                    operandStack.push(Double.parseDouble(token));
                } else {
                    double operand2 = operandStack.pop();
                    double operand1 = operandStack.pop();
                    double result;
                    switch (token) {
                        case "+":
                            result = operand1 + operand2;
                            break;
                        case "-":
                            result = operand1 - operand2;
                            break;
                        case "*":
                            result = operand1 * operand2;
                            break;
                        case "/":
                            result = operand1 / operand2;
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid operator: " + token);
                    }
                    operandStack.push(result);
                }
            }
            resultList.add(operandStack.pop());
        }

        return resultList.stream().map(String::valueOf).toList();
    }
    public static boolean hasHigherPrecedence(String operator1, String operator2) {
        return (operator1.equals("*") || operator1.equals("/")) && (operator2.equals("+") || operator2.equals("-"));
    }
}
