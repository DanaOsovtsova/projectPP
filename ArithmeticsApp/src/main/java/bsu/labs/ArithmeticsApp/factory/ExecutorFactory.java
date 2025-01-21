package bsu.labs.ArithmeticsApp.factory;

import bsu.labs.ArithmeticsApp.services.Executors.ExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class ExecutorFactory {
    private final Map<String, ExecutorService> executors;

    @Autowired
    public ExecutorFactory(Map<String, ExecutorService> executors) {
        this.executors = executors;
    }

    public ExecutorService getExecutor(String dataType) {
        log.info(executors.toString());
        ExecutorService executor = executors.get(dataType.toLowerCase());
        if (executor == null) {
            throw new IllegalArgumentException("Unsupported data type: " + dataType);
        }
        return executor;
    }
}
