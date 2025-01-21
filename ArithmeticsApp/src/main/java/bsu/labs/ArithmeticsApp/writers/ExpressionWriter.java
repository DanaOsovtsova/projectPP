package bsu.labs.ArithmeticsApp.writers;


import java.io.IOException;
import java.util.List;

public interface ExpressionWriter {
    void writeResult(List<String> data, String fileName) throws IOException;
}
