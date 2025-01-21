package bsu.labs.ArithmeticsApp.readers;


import java.io.IOException;
import java.util.List;

public interface ExpressionReader  {
    List<String> read(String fileName) throws IOException;
}
