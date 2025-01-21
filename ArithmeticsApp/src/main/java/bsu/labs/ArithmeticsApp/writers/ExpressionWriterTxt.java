package bsu.labs.ArithmeticsApp.writers;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component("TxtWriter")
public class ExpressionWriterTxt implements ExpressionWriter{
    @Override
    public void writeResult(List<String> data, String fileName) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter("src/main/resources/" + fileName));
        for (var a : data) {
            if (!a.isEmpty()){
                out.println(a);
            }
        }
        out.flush();
    }
}