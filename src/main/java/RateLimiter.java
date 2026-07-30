public class RateLimiter {
    private final int maxRequestsPerSecond;
    private long currentSecond;
    private int currentCount;

    public RateLimiter(int maxRequestsPerSecond) {
        this.maxRequestsPerSecond = maxRequestsPerSecond;
        this.currentSecond = System.currentTimeMillis() / 1000;
        this.currentCount = 0;
    }

    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis() / 1000;
        
        if (now > currentSecond) {
            currentSecond = now;
            currentCount = 0;
        }

        if (currentCount < maxRequestsPerSecond) {
            currentCount++;
            return true;
        }

        return false;
    }
}