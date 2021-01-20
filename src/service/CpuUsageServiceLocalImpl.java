package service;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CpuUsageServiceLocalImpl implements CpuUsageService {

    public static final int MAX_COORDINATES_AMOUNT = 60;

    private final LinkedList<Double> cpuData;
    private final OperatingSystemMXBean mxBean;

    public CpuUsageServiceLocalImpl() {
        this.mxBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();

        this.cpuData = Stream.generate(() -> 0.0)
                .limit(MAX_COORDINATES_AMOUNT)
                .collect(Collectors.toCollection(LinkedList::new));

    }

    @Override
    public LinkedList<Double> loadAndGetCpuData() {
        this.loadCoordinate();
        return this.getCpuData();
    }

    private LinkedList<Double> getCpuData() {
        return cpuData;
    }

    private void loadCoordinate() {
        cpuData.addLast(mxBean.getSystemCpuLoad());
        if (cpuData.size() > MAX_COORDINATES_AMOUNT) {
            cpuData.removeFirst();
        }
    }

}
