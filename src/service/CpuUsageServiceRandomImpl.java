package service;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CpuUsageServiceRandomImpl implements CpuUsageService {

    public static final int MAX_COORDINATES_AMOUNT = 60;

    private final LinkedList<Double> data;

    public CpuUsageServiceRandomImpl() {
        data = Stream.generate(() -> 0.0)
                .limit(MAX_COORDINATES_AMOUNT)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public LinkedList<Double> loadAndGetCpuData() {
        data.addLast(Math.floor(Math.random() * 100));
        if (data.size() > MAX_COORDINATES_AMOUNT) {
            data.removeFirst();
        }
        return data;
    }

}
