package app.theblu.cashflow.cs.batteries.common;

import java.util.List;

public class BatchUtil {
    public static <T> void process(List<T> source, int batchSize, BatchConsumer<T> consumer) {
        List<List<T>> batchUpdates = ListUtil.INSTANCE.splitIntoBatches(source, batchSize);
        batchUpdates.stream().forEach(consumer::consume);
    }

    public static <T> void processParallel(List<T> source, int batchSize, BatchConsumer<T> consumer) {
        List<List<T>> batchUpdates = ListUtil.INSTANCE.splitIntoBatches(source, batchSize);
        batchUpdates.parallelStream().forEach(consumer::consume);
    }

    public interface BatchConsumer<T> {
        public void consume(List<T> batch);
    }
}
