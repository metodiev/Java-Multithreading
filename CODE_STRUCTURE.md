# Code Structure — `src/`

The Java sources are organised by **topic package** under `com.java.*`. This document maps each package to the tutorial sections it supports and flags what is still missing.

## Package map

| Package (`src/com/java/...`) | Purpose | Feeds section(s) |
| --- | --- | --- |
| `multithreading.interview.questions` | Curated Q&A demos aligned to the README questions | 1–3 |
| `javathread` | Thread creation, naming, sleep, stoppable, daemon (`ThreadExample1–10`) | 1 |
| `startingthreads` | `extends Thread` basic demo | 1 |
| `runnable` | `implements Runnable` demo | 1 |
| `anonymousclass` | Anonymous-class `Runnable` | 1 |
| `javamemorymodel` | Shared vs separate objects (race demo), `MyRunnable` | 3 |
| `javasynchronize` | `synchronized`, monitors, reentrance, static vs instance locks | 2 |
| `synchronizekeyword` | Intrinsic lock / counter demo | 2 |
| `synchronization` | `volatile`-based graceful shutdown | 2, 3 |
| `multiplelocks.synchronizedcodeblocks` | Fine-grained locking vs coarse locking (contention demo) | 2, 8 |
| `threadpools` | `ExecutorService` fixed pool | 4 |
| `countdownlatches` | `CountDownLatch` + pooled tasks | 4 |
| `producerconsumer` | `BlockingQueue` and `wait/notify` producer-consumer | 4, 7 |

## The Q&A demo package (`multithreading.interview.questions`)

| Class | Topic | Tutorial |
| --- | --- | --- |
| `ThreadLifecycleDemo` | NEW → RUNNABLE → TERMINATED | 2, 3 |
| `ThreadStatesDemo` | BLOCKED / WAITING / TIMED_WAITING | 1 |
| `ThreadRunnableCallableDemo` | Thread / Runnable / Callable | 12, 13, 14 |
| `ThreadGroupExample` | Thread groups | 9, 10 |
| `DaemonExample`, `DaemonThreadDemo` | Daemon vs user threads | 6, 7, 8 |
| `InterruptDemo` | Interrupt | 17, 18 |
| `SynchronizedDemo` | synchronized method & block | 21, 23 |
| `NonStaticSyncDemo`, `StaticSyncDemo` | instance vs class monitors | 23, 40 |
| `ReentrantLockDemo` | ReentrantLock + fairness | 25, 26 |
| `ReadWriteStampedLockDemo` | ReadWriteLock & StampedLock | 28–31 |
| `SpuriousWakeupDemo` | Condition + wait loop | 33, 34, 35 |
| `WaitVsSleepDemo` | wait() vs sleep() | 36 |

## Gaps (candidate new packages)

The following sections have **no example code yet**. Proposed packages:

| Proposed package | Section | Example classes to add |
| --- | --- | --- |
| `com.java.atomic` | 5 | `CasDemo`, `AtomicCounterVsLongAdder`, `AbaDemo` |
| `com.java.collections` | 6 | `ConcurrentMapDemo`, `CopyOnWriteDemo`, `SkipListDemo` |
| `com.java.patterns` | 7 | `BlockingQueueProducerConsumer`, `ActorDemo`, `DisruptorLite` |
| `com.java.performance` | 8 | `DeadlockDemo`, `LockContentionDemo`, `ThreadDumpDemo` |
| `com.java.modern` | 9 | `VirtualThreadsDemo`, `StructuredConcurrencyDemo`, `CompletableFutureDemo` |
| `com.java.systemdesign` | 10 | `PipelineDemo`, `RateLimiterDemo`, `CircuitBreakerDemo` |

## Build

- Target: **Java 1.8** (see [`.classpath`](.classpath)); some demos target newer APIs and would need a newer source level.
- No build tool (Maven/Gradle) is present — compilation is IDE-driven (Eclipse/IntelliJ project files are checked in).
