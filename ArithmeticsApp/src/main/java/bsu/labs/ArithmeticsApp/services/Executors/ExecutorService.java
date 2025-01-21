package bsu.labs.ArithmeticsApp.services.Executors;

import java.io.IOException;
import java.util.List;

public interface ExecutorService {
    List<String> execute(List<String> data) throws IOException;
}
