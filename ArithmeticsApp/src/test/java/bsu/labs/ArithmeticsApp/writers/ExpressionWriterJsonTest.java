package bsu.labs.ArithmeticsApp.writers;

import bsu.labs.ArithmeticsApp.dto.JSONResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ExpressionWriterJsonTest {
    private static final String TEST_FILE = "tests/writeTest.json";
    private ExpressionWriterJson writer;

    @BeforeEach
    public void setup() {
        writer = new ExpressionWriterJson();
    }

    @AfterEach
    public void cleanup() throws IOException {
        File file = new File("src/main/resources/" + TEST_FILE);
        file.delete();
    }

    @Test
    void testWriteResult() throws IOException {
        List<String> result = Arrays.asList("3.0", "12.0", "5.0");
        JSONResponse response = new JSONResponse(result);
        writer.writeResult(result, TEST_FILE);
        File file = new File("src/main/resources/" + TEST_FILE);
        assertTrue(file.exists());
        String jsonContent = Files.readString(Path.of("src/main/resources/" + TEST_FILE));
        ObjectMapper objectMapper = new ObjectMapper();
        JSONResponse readResponse = objectMapper.readValue(jsonContent, JSONResponse.class);
        assertNotNull(readResponse);
        List<String> readResult = readResponse.getExpressions();
        assertEquals(3, readResult.size());
        assertEquals("3.0", readResult.get(0));
        assertEquals("12.0", readResult.get(1));
        assertEquals("5.0", readResult.get(2));
    }
}