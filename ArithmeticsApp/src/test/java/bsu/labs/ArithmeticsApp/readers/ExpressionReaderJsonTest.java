package bsu.labs.ArithmeticsApp.readers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ExpressionReaderJsonTest {
    private static final String TEST_FILE = "tests/test.json";
    private ExpressionReaderJson reader;
    private PrintWriter printWriter;

    @BeforeEach
    public void setup() throws IOException {
        printWriter = new PrintWriter(new FileWriter("src/main/resources/" + TEST_FILE));
        reader = new ExpressionReaderJson();
    }

    @AfterEach
    public void cleanup() {
        File file = new File("src/main/resources/" + TEST_FILE);
        file.delete();
    }

    @Test
    void testRead() throws IOException {
        printWriter.println("{\"expressions\": [\"1 + 2\", \"3 * 4\"]}");
        printWriter.close();
        List<String> request = reader.read(TEST_FILE);
        assertNotNull(request);
        assertEquals(2, request.size());
        assertEquals("1 + 2", request.get(0));
        assertEquals("3 * 4", request.get(1));
    }
    @Test
    void testReadWithInvalidFile() throws IOException {
        String invalidFilePath = "nonexistent.json";
        List<String> request = reader.read(invalidFilePath);
        assertNull(request);
    }
}