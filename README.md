# Rate Limiter Test

This repository contains a thread-safe Rate Limiter implementation in Java.

## Concurrency Strategy
I implemented a Fixed Window Counter algorithm. To ensure thread safety when multiple threads call the method simultaneously, I used the `synchronized` keyword on the `allowRequest()` method. 

This prevents race conditions by ensuring only one thread can read the timestamp and update the counter at any given millisecond. 

## How to run the test
1. Compile the code: `javac src/main/java/*.java`
2. Run the test: `java -cp src/main/java RateLimiterTest`

The test uses an ExecutorService and a CountDownLatch to release 100 threads at the exact same moment to prove the rate limiter never exceeds the maximum configured limit.
