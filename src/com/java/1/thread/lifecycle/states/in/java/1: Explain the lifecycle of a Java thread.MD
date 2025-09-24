## 1. Thread Lifecycle States in Java
Java threads go through six main states, represented by Thread.State:

NEW → Thread is created but not yet started (new Thread(runnable)).

RUNNABLE → Thread is ready to run, waiting for CPU scheduling.

RUNNING → Thread is actively executing (a sub-state of RUNNABLE in practice).

WAITING → Thread is waiting indefinitely for another thread’s signal (e.g., Object.wait() without timeout).

TIMED_WAITING → Thread is waiting for a bounded time (e.g., sleep(ms), join(ms), wait(ms)).

BLOCKED → Thread is waiting to acquire a monitor lock (synchronized block).

TERMINATED → Thread has finished execution.


## 2. Transitions Between States

NEW → RUNNABLE: call start().

RUNNABLE ↔ RUNNING: controlled by thread scheduler.

RUNNING → WAITING/TIMED_WAITING: calling wait(), join(), or sleep().

RUNNING → BLOCKED: thread tries to enter a synchronized block but lock is held.

WAITING/TIMED_WAITING/BLOCKED → RUNNABLE: lock acquired or signal/timeout.

RUNNING → TERMINATED: run() method finishes.

## 3. Key Leadership/Architect Insight

Performance impact: Too many threads in BLOCKED → lock contention.

Deadlocks: Multiple threads permanently stuck in BLOCKED or WAITING.

Scalability: Excessive threads stuck in TIMED_WAITING (due to poor thread pool config).

Monitoring: Use jstack, VisualVM, or async profilers to inspect states.

## Diagram



```bash
stateDiagram-v2
    [*] --> NEW
    NEW --> RUNNABLE: start()
    RUNNABLE --> RUNNING: Scheduler
    RUNNING --> RUNNABLE: yield()/time slice
    RUNNING --> BLOCKED: lock contention
    BLOCKED --> RUNNABLE: lock acquired
    RUNNING --> WAITING: wait()
    RUNNING --> TIMED_WAITING: sleep()/join(timeout)/wait(timeout)
    WAITING --> RUNNABLE: notify()/notifyAll()
    TIMED_WAITING --> RUNNABLE: timeout/notify()
    RUNNING --> TERMINATED: run() ends
    TERMINATED --> [*]

```

```java
public class ThreadLifecycleDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(1000); // TIMED_WAITING
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("State before start: " + t.getState()); // NEW
        t.start();
        Thread.sleep(100); // Let it start
        System.out.println("State after start: " + t.getState()); // RUNNABLE / TIMED_WAITING
        t.join(); // Waits for termination
        System.out.println("State after termination: " + t.getState()); // TERMINATED
    }
}


```
