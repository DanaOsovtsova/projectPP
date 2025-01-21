package bsu.labs.ArithmeticsApp.services;

import bsu.labs.ArithmeticsApp.services.calculator.ExpressionServiceCalculator;
import bsu.labs.ArithmeticsApp.services.calculator.ExpressionServiceCalculatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
class ExpressionServiceCalculatorImplTest {

    private ExpressionServiceCalculator calculator = new ExpressionServiceCalculatorImpl();
    @Test
    void calculateFirstExpression() {
        List<String> expression = Arrays.asList("1 + 1", "2 * 4.3 + 5", "7 / (1 - 4.5)");
        List<String> actualResults = calculator.calculateExpression(expression);
        List<String> expectedResults = Arrays.asList("2.0","13.6","-2.0");
        Assertions.assertEquals(expectedResults, actualResults);
    }
    @Test
    void calculateSecondExpression() {

        List<String> expression = Arrays.asList("2 + 3 * 4", "(5 + 3) * 2", "10 - 4 / 2");
        List<String> actualResults = calculator.calculateExpression(expression);
        List<String> expectedResults = Arrays.asList("14.0", "16.0", "8.0");
        Assertions.assertEquals(expectedResults, actualResults);
    }
    @Test
    void calculateEmptyExpression () {
        List<String> expression = Arrays.asList();
        List<String> actualResults =  calculator.calculateExpression(expression);;
        List<String> expectedResults = Arrays.asList();
        Assertions.assertEquals(expectedResults, actualResults);
    }
}