# Java Multithreading Tutorials — Master Index

This folder contains **one standalone tutorial for every question** — all **200 questions across 11 sections** in the [project README](../README.md) — written for **Architects & Technical Leads**. Section guides provide additional context, cross-cutting diagrams, and review checklists. Runnable examples are linked where available under [`src/`](../src). Mermaid diagrams are included throughout.

## Sections

| # | Section | Questions | Section guide and standalone tutorials | Status |
| --- | --- | --- | --- | --- |
| 1 | Thread Fundamentals | 1–20 | [Individual tutorials](1.%20Thread%20Fundamentals/) | **Complete** |
| 2 | Synchronization and Locks | 21–40 | [Section guide](2.%20Synchronization%20and%20Locks/README.md) · [Tutorials](2.%20Synchronization%20and%20Locks/) | **Complete** |
| 3 | Java Memory Model | 41–60 | [Section guide](3.%20Java%20Memory%20Model/README.md) · [Tutorials](3.%20Java%20Memory%20Model/) | **Complete** |
| 4 | Concurrency Utilities | 61–80 | [Section guide](4.%20Concurrency%20Utilities/README.md) · [Tutorials](4.%20Concurrency%20Utilities/) | **Complete** |
| 5 | Atomic Operations and Lock-Free | 81–100 | [Section guide](5.%20Atomic%20Operations%20and%20Lock-Free/README.md) · [Tutorials](5.%20Atomic%20Operations%20and%20Lock-Free/) | **Complete** |
| 6 | Concurrent Collections | 101–120 | [Section guide](6.%20Concurrent%20Collections/README.md) · [Tutorials](6.%20Concurrent%20Collections/) | **Complete** |
| 7 | Concurrency Patterns | 121–140 | [Section guide](7.%20Concurrency%20Patterns/README.md) · [Tutorials](7.%20Concurrency%20Patterns/) | **Complete** |
| 8 | Performance and Debugging | 141–160 | [Section guide](8.%20Performance%20and%20Debugging/README.md) · [Tutorials](8.%20Performance%20and%20Debugging/) | **Complete** |
| 9 | Reactive and Modern Approaches | 161–180 | [Section guide](9.%20Reactive%20and%20Modern%20Approaches/README.md) · [Tutorials](9.%20Reactive%20and%20Modern%20Approaches/) | **Complete** |
| 10 | System Design Scenarios | 181–190 | [Section guide](10.%20System%20Design%20Scenarios/README.md) · [Tutorials](10.%20System%20Design%20Scenarios/) | **Complete** |
| 11 | Leadership and Review Skills | 191–200 | [Section guide](11.%20Leadership%20and%20Review%20Skills/README.md) · [Tutorials](11.%20Leadership%20and%20Review%20Skills/) | **Complete** |

## Section 1 — Thread Fundamentals (individual tutorials)

- [x] [1. Possible thread states](1.%20Thread%20Fundamentals/1.What%20are%20the%20possible%20thread%20states%20in%20Java.MD)
- [x] [2. NEW → RUNNABLE transition](1.%20Thread%20Fundamentals/2.How%20does%20a%20thread%20transition%20from%20NEW%20to%20RUNNABLE.MD)
- [x] [3. TERMINATED state](1.%20Thread%20Fundamentals/3.What%20does%20the%20TERMINATED%20state%20mean.MD)
- [x] [4. Thread priority levels](1.%20Thread%20Fundamentals/4.What%20is%20the%20difference%20between%20thread%20priority%20levels.MD)
- [x] [5. Are priorities guaranteed?](1.%20Thread%20Fundamentals/5.Are%20thread%20priorities%20guaranteed%20across%20all%20JVMs.md)
- [x] [6. User vs daemon threads](1.%20Thread%20Fundamentals/6.What%20is%20the%20difference%20between%20user%20threads%20and%20daemon%20threads.MD)
- [x] [7. Create a daemon thread](1.%20Thread%20Fundamentals/7.How%20do%20you%20create%20a%20daemon%20thread.MD)
- [x] [8. All user threads finish, daemons remain](1.%20Thread%20Fundamentals/8.What%20happens%20if%20all%20user%20threads%20finish%20but%20daemon%20threads%20remain.md)
- [x] [9. Thread groups](1.%20Thread%20Fundamentals/9.What%20are%20thread%20groups%20in%20Java.md)
- [x] [10. Are thread groups recommended?](1.%20Thread%20Fundamentals/10.%20Are%20thread%20groups%20recommended%20for%20modern%20applications.md)
- [x] [11. Create a thread (Thread class)](1.%20Thread%20Fundamentals/11.%20How%20do%20you%20create%20a%20thread%20using%20the%20Thread%20class.md)
- [x] [12. Create a thread (Runnable)](1.%20Thread%20Fundamentals/12.%20How%20do%20you%20create%20a%20thread%20using%20Runnable.md)
- [x] [13. Create a thread (Callable + Future)](1.%20Thread%20Fundamentals/13.%20How%20do%20you%20create%20a%20thread%20using%20Callable%20and%20Future.md)
- [x] [14. Runnable vs extending Thread](1.%20Thread%20Fundamentals/14.%20What%20are%20the%20pros%25cons%20of%20implementing%20Runnable%20vs%20extending%20Thread.md)
- [x] [15. Stop a thread safely](1.%20Thread%20Fundamentals/15.%20How%20do%20you%20stop%20a%20thread%20safely.md)
- [x] [16. Why Thread.stop() is deprecated](1.%20Thread%20Fundamentals/16.%20Why%20is%20Thread.stop%28%29%20deprecated.md)
- [x] [17. Interrupt a running thread](1.%20Thread%20Fundamentals/17.%20How%20do%20you%20interrupt%20a%20running%20thread.md)
- [x] [18. Handle InterruptedException](1.%20Thread%20Fundamentals/18.%20How%20should%20code%20handle%20InterruptedException.md)
- [x] [19. Cooperative thread termination](1.%20Thread%20Fundamentals/19.%20What%20is%20cooperative%20thread%20termination.md)
- [x] [20. Best practices for thread lifecycle](1.%20Thread%20Fundamentals/20.%20What%20are%20best%20practices%20for%20handling%20thread%20lifecycle.md)

## Legacy / standalone tutorials (pre-existing)

- [1. Lifecycle of a Java thread](1.%20Explain%20the%20lifecycle%20of%20a%20Java%20thread.MD)
- [2. Daemon vs User threads](2.%20Explain%20the%20difference%20between%20Daemon%20and%20User%20threads%20in%20Java.MD)
- [3. Thread Groups](3.%20What%20are%20Thread%20Groups%20in%20Java%20and%20how%20are%20they%20used.md)
- [4. Create threads (Runnable/Callable/Thread)](4.How%20do%20you%20create%20threads%20in%20Java%20and%20what%20are%20the%20differences%20between%20Runnable,%20Callable,%20and%20Thread.MD)
- [5. Handle interrupts & stop safely](5.%20How%20do%20you%20handle%20thread%20interrupts%20and%20stop%20threads%20safely%20in%20Java.MD)
- [11. wait() vs sleep()](11.%20What%20is%20the%20difference%20between%20wait%28%29%20and%20sleep%28%29%20in%20Java.md)
- [Java Memory Model — Key Concepts](Java%20Memory%20Model%20—%20Key%20Concepts%20for%20Concurrency.MD)

## Diagrams

Mermaid diagrams are embedded inline in the individual tutorials and section guides. Rendered SVG diagrams live in [`images/`](images/).
