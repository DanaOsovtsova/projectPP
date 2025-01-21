package bsu.labs.ArithmeticsApp.writers;

import bsu.labs.ArithmeticsApp.dto.JSONRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component("JsonWriter")
public class ExpressionWriterJson implements ExpressionWriter{
    @Override
    public void writeResult(List<String> data, String filePath) throws IOException {
        File file = new File("src/main/resources/" + filePath);
        ObjectMapper objectMapper = new ObjectMapper();
        JSONRequest jsonRequest = new JSONRequest(data);
        objectMapper.writeValue(file, jsonRequest);
    }
}