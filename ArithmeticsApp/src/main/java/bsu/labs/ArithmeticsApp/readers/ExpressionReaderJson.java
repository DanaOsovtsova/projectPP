package bsu.labs.ArithmeticsApp.readers;

import bsu.labs.ArithmeticsApp.dto.JSONResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component("JsonReader")
public class ExpressionReaderJson implements ExpressionReader{
    public List<String> read(String filePath) throws IOException {
        File file = new File("src/main/resources/" + filePath);
        if (!file.exists()) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JSONResponse jsonResponse = objectMapper.readValue(file, JSONResponse.class);
        return jsonResponse.getExpressions();
    }
}