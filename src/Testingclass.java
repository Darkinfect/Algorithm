import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class Testingclass {
    private final MemoryMXBean memoryBean;
    private long startTime;
    private long startMemory;

    public Testingclass() {
        this.memoryBean = ManagementFactory.getMemoryMXBean();
    }

    public void start() {
        System.gc();
        try { Thread.sleep(100); } catch (InterruptedException e) {}

        this.startTime = System.nanoTime();
        this.startMemory = getUsedMemory();
    }

    public MeasurementResult stop() {
        long endTime = System.nanoTime();
        long endMemory = getUsedMemory();

        return new MeasurementResult(
                endTime - startTime,
                endMemory - startMemory
        );
    }

    private long getUsedMemory() {
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeapUsage = memoryBean.getNonHeapMemoryUsage();
        return heapUsage.getUsed() + nonHeapUsage.getUsed();
    }

    public static class MeasurementResult {
        public final long timeNanos;
        public final long memoryBytes;

        public MeasurementResult(long timeNanos, long memoryBytes) {
            this.timeNanos = timeNanos;
            this.memoryBytes = memoryBytes;
        }

        public double getTimeMillis() {
            return timeNanos / 1_000_000.0;
        }

        public double getMemoryMB() {
            return memoryBytes / (1024.0 * 1024.0);
        }

        @Override
        public String toString() {
            return String.format("Time: %.3f ms | Memory: %.2f MB",
                    getTimeMillis(), getMemoryMB());
        }
    }
}
