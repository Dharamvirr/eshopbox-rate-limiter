import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiterTest {
    public static void main(String[] args) throws InterruptedException {
        int maxRequests = 10;
        RateLimiter rateLimiter = new RateLimiter(maxRequests);
        int totalThreads = 100;
        
        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);
        CountDownLatch startGate = new CountDownLatch(1);
        AtomicInteger successfulRequests = new AtomicInteger(0);

        for (int i = 0; i < totalThreads; i++) {
            executor.submit(() -> {
                try {
                    startGate.await();
                    if (rateLimiter.allowRequest()) {
                        successfulRequests.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        startGate.countDown();
        executor.shutdown();
        
        while (!executor.isTerminated()) {
            Thread.sleep(10);
        }

        System.out.println("Allowed Limit: " + maxRequests);
        System.out.println("Actual Successful Requests: " + successfulRequests.get());
    }
}

