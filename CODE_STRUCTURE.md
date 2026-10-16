# Code Structure — `src/`

The Java sources are organised by **topic package** under `com.java.*`. This document maps focused runnable examples to the tutorial set and documents topics that necessarily need another runtime or service.

## Package map

| Package (`src/com/java/...`) | Purpose | Feeds section(s) |
| --- | --- | --- |
| `multithreading.interview.questions` | Curated Q&A demos aligned to the README questions | 1–3 |
| `javathread` | Thread creation, naming, sleep, stoppable, daemon (`ThreadExample`, `ThreadExample2–10`) | 1 |
| `startingthreads` | `extends Thread` basic demo | 1 |
| `runnable` | `implements Runnable` demo | 1 |
| `anonymousclass` | Anonymous-class `Runnable` | 1 |
| `javamemorymodel` | Shared vs separate objects (race demo), `MyRunnable` | 3 |
| `javasynchronize` | `synchronized`, monitors, reentrance, static vs instance locks | 2 |
| `synchronizekeyword` | Intrinsic lock / counter demo | 2 |
| `synchronization` | `volatile`-based graceful shutdown | 2, 3 |
| `multiplelocks.synchronizedcodeblocks` | Fine-grained locking: separate stage locks allow independent work to overlap | 2, 8 |
| `threadpools` | `ExecutorService` fixed pool | 4 |
| `countdownlatches` | `CountDownLatch` + pooled tasks | 4 |
| `producerconsumer` | `BlockingQueue` and `wait/notify` producer-consumer | 4, 7 |
| `atomic` | CAS-safe counters, `LongAdder`, stamped-reference ABA detection | 5 |
| `collections` | Concurrent map updates, copy-on-write snapshots, skip-list ranges, bounded queues | 6 |
| `concurrency` | `CyclicBarrier`, `Phaser`, and scheduled executor coordination | 4 |
| `patterns` | Bounded-mailbox, single-owner actor-style message passing | 7 |
| `performance` | Reproducible lock-order deadlock and `ThreadMXBean` detection | 8 |
| `modern` | Explicit-executor `CompletableFuture` pipeline (Java 8) | 4, 9 |
| `systemdesign` | Bounded pipeline, retry/timeout/token-bucket samples, and circuit breaker | 10 |

## Added runnable examples

These are compact demonstrations of core ideas, not production frameworks. Each is independently runnable from the IDE or with `java` after compilation:

| Source | What to observe | Tutorial topics |
| --- | --- | --- |
| [`AtomicOperationsDemo.java`](src/com/java/atomic/AtomicOperationsDemo.java) | Exact `AtomicInteger` updates, aggregate `LongAdder`, stamped ABA rejection | 81–89, 92–93 |
| [`ConcurrentCollectionsDemo.java`](src/com/java/collections/ConcurrentCollectionsDemo.java) | Atomic map merge, snapshot iterator, sorted range, bounded handoff | 101–120 |
| [`CoordinationUtilitiesDemo.java`](src/com/java/concurrency/CoordinationUtilitiesDemo.java) | Reusable barrier, dynamic phaser, scheduled task lifecycle | 77–80 |
| [`ActorMessagePassingDemo.java`](src/com/java/patterns/ActorMessagePassingDemo.java) | Immutable messages, bounded mailbox, single-thread state ownership, orderly stop | 121–129, 133, 138–139 |
| [`DeadlockDetectionDemo.java`](src/com/java/performance/DeadlockDetectionDemo.java) | Intentional daemon-thread lock cycle found using `ThreadMXBean` | 141–142, 148–149, 157 |
| [`CompletableFuturePipelineDemo.java`](src/com/java/modern/CompletableFuturePipelineDemo.java) | Async stages, explicit executor, independent-task combination and shutdown | 69–71, 161–162, 166 |
| [`BoundedPipelineDemo.java`](src/com/java/systemdesign/BoundedPipelineDemo.java) | Fixed workers, bounded queue, caller-runs feedback under overload | 121–122, 129, 137, 181, 183 |
| [`CircuitBreakerDemo.java`](src/com/java/systemdesign/CircuitBreakerDemo.java) | Closed/open/half-open transitions and one permitted recovery probe | 186–188 |
| [`ResiliencePatternsDemo.java`](src/com/java/systemdesign/ResiliencePatternsDemo.java) | Bounded retry, timeout-triggered cancellation, refilling token-bucket rate limit | 186–188 |

The deadlock demo **intentionally leaves two daemon workers deadlocked** so the JVM can exit after detection. Run it only as a teaching example. The resilience demo uses short artificial delays to keep execution fast; it does not contact a remote dependency.

## Newer JDK example source set

[`src-modern/com/java/modern/VirtualThreadsDemo.java`](src-modern/com/java/modern/VirtualThreadsDemo.java) uses the standard virtual-thread-per-task executor available in **JDK 21+**. It is deliberately outside the Java 8 Eclipse source root (`src/`); compile it separately with a JDK 21 or later:

```powershell
javac --release 21 -d out-modern src-modern/com/java/modern/VirtualThreadsDemo.java
java -cp out-modern com.java.modern.VirtualThreadsDemo
```

The project remains Java 8 compatible by default. Java 9+ `Flow`, Java 21 virtual threads, and release-dependent structured-concurrency APIs must not be mixed into that baseline source root.

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

## Topics that need explicit environment boundaries

Not every question needs a runnable program in the default source set. The following are documented or discussed in tutorials but should not be represented as a misleading Java 8-only demo:

| Topic | Why it is not in the Java 8 runnable set | Appropriate next form |
| --- | --- | --- |
| Reactor / RxJava | External libraries and dependency/version choices; no Maven/Gradle build is present | Add an optional dependency-managed module with its own tests |
| Reactive Streams `Flow` API | JDK 9+ API | Add to a newer-JDK source set if desired |
| Virtual threads | Standardized in JDK 21, above the configured Java 8 baseline | Example is isolated in `src-modern/` |
| Structured concurrency | API/stability depends on the selected JDK release | Pin a JDK and API status before adding a dedicated example |
| Redis / ZooKeeper coordination | Requires a running service and client dependency; in-memory mock cannot prove distributed guarantees | Add an integration-test module and service-backed tests |
| Disruptor | Third-party dependency and workload-specific value | Add only with dependency management and a benchmark-backed use case |
| Leadership/review guidance | Best taught through review scenarios, checklists, and ADR examples rather than application code | Keep as tutorial and team-process material |
| Flame graphs / production thread dumps | Requires profiling a real workload/JVM process | Follow the operational steps in Section 8; do not fake production evidence |

These boundaries are intentional. The source examples cover repeatable core JDK behavior; service-specific and framework-specific examples need explicit dependencies, setup, and validation rather than snippets that appear runnable but are not.

## Build

- Default source root target: **Java 1.8** (see [`.classpath`](.classpath)); newer-JDK examples are kept outside `src/`.
- No external dependencies are required for the JDK examples.
- Run `powershell -ExecutionPolicy Bypass -File scripts\verify-examples.ps1` from the repository root to compile every Java 8 source and execute selected finite demos. The JDK 21 virtual-thread demo is compiled and run automatically when JDK 21+ is installed; otherwise the script reports that it was skipped.
- The script is a compile-and-smoke-test harness, not a full unit, stress, benchmark, or distributed integration-test suite.
- Compile manually with `javac --release 8 -d out <all .java files under src>`; compile `src-modern/` separately with `javac --release 21`.
