package bsu.labs.ArithmeticsApp.readers;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component("TxtReader")
public class ExpressionReaderTxt implements ExpressionReader {
    @Override
    public List<String> read(String filename) throws IOException {
        File file = new File("src/main/resources/" + filename);
        if(!file.exists()) {
            return null;
        }
        Scanner in = new Scanner(file);
        List<String> inputList = new ArrayList<>();
        while (in.hasNext()) {
            String line = in.nextLine();
            if (line != null){
                inputList.add(line);
            }
        }
        return inputList;
    }
}