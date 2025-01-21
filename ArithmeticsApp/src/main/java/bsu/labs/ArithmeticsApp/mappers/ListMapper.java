package bsu.labs.ArithmeticsApp.mappers;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ListMapper {
    public static List<String> map(String data) {
        String[] split = data.split("\n");
        return Arrays.stream(split)
                .map(String::trim)
                .toList();
    }
}
