package bsu.labs.ArithmeticsApp.services;

import bsu.labs.ArithmeticsApp.factory.ExecutorFactory;
import bsu.labs.ArithmeticsApp.mappers.ListMapper;
import bsu.labs.ArithmeticsApp.services.Executors.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ArithmeticService {
    private final ExecutorFactory executorFactory;

    @Autowired
    public ArithmeticService(ExecutorFactory executorFactory) {
        this.executorFactory = executorFactory;
    }

    public List<String> start(String data, String dataType) throws IOException  {
        ExecutorService executorService = executorFactory.getExecutor(dataType);
        return executorService.execute(ListMapper.map(data));
    }
}
