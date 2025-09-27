# Happens-Before Relationship

- Defines a partial ordering of actions in threads.
- Guarantees that memory writes by one action are visible to another that happens after it.
- Forms the foundation of visibility, ordering, and atomicity in Java concurrency.
  

### Examples of happens-before:


- Thread.start() → run(): All actions before start() are visible to the new thread.
- Thread.join() → subsequent actions: All actions in the joined thread are visible after join returns.
- synchronized blocks: Unlocking a monitor happens-before another thread locks the same monitor.
- volatile write → volatile read: Ensures visibility of the volatile value and all preceding writes.

## synchronized and Visibility

- Acquiring and releasing a monitor creates a happens-before relationship.
- Unlock (exit synchronized) happens-before subsequent lock (enter synchronized) on the same object.
- Ensures mutual exclusion + visibility, preventing instruction reordering across the synchronized boundaries.

```java
class Counter {
    private int count = 0;
    public synchronized void increment() { count++; }
    public synchronized int get() { return count; }
}

```

- increment() guarantees visibility of changes to other threads calling get().

## volatile and Visibility

- Declaring a variable volatile ensures:
  - Visibility: Writes by one thread are visible to others immediately.
  - Ordering: Prevents certain compiler/CPU reordering (memory fences).

### Insufficient for atomic increments because count++ is read-modify-write — still a race.

Example:

```java
volatile int counter = 0;
counter++; // NOT atomic; use AtomicInteger

```


## Thread Start / Join

- Thread.start(): All prior writes by the creating thread are visible to the new thread.
- Thread.join(): All actions in the joined thread are visible to the joining thread after join returns.

## Atomicity, Visibility, Ordering

- Atomicity: Operation completes as a single indivisible step (int read/write, long pre-Java 5 NOT atomic).
- Visibility: Other threads can see the result of a write.
- Ordering: Instructions execute in the order expected from happens-before; prevents reordering issues.

## Safe Publication

Ensuring an object reference and its fields are visible to other threads safely.
<br>
Techniques:

- Initialize and store in a final field.
- Use volatile reference.
- Publish via synchronized block.

Out-of-thin-air problem:

- Without proper JMM guarantees, a thread might see a value that was never written by any thread due to aggressive reordering.


## Instruction Reordering & Memory Fences

- Compilers/CPUs may reorder instructions for optimization.
- Java inserts memory fences at key points (volatile write/read, monitor enter/exit) to preserve happens-before semantics.
- Double-checked locking fails without volatile because the write to the object reference may appear before the constructor completes.

## final Fields

Treated specially by JMM:

Writing to a final field in a constructor ensures safe publication of the object.
Once constructor finishes, other threads cannot see a partially constructed object through that final field.

<br>
Summary Table: Memory Guarantees

| Feature           | Atomicity              | Visibility             | Ordering                           |
| ----------------- | ---------------------- | ---------------------- | ---------------------------------- |
| volatile          | Single read/write only | Yes                    | Yes (prevents certain reorderings) |
| synchronized      | Compound actions       | Yes                    | Yes (monitor unlock → lock)        |
| final fields      | N/A                    | Safe after constructor | Prevents out-of-thin-air reads     |
| Thread.start/join | N/A                    | Yes                    | Happens-before relationships       |


## Diagram

![Diagram](images/Java%20Memory%20model.svg)

