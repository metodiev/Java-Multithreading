$ErrorActionPreference = 'Stop'

$repositoryRoot = Split-Path -Parent $PSScriptRoot
$outputDirectory = Join-Path ([System.IO.Path]::GetTempPath()) ("java-multithreading-" + [guid]::NewGuid().ToString("N"))
$javaSources = @(Get-ChildItem (Join-Path $repositoryRoot 'src') -Recurse -File -Filter '*.java' |
    Select-Object -ExpandProperty FullName)
$java8Demos = @(
    'com.java.javathread.ThreadExample9',
    'com.java.javathread.ThreadExample8',
    'com.java.multiplelocks.synchronizedcodeblocks.App',
    'com.java.threadpools.App',
    'com.java.countdownlatches.App',
    'com.java.producerconsumer.App',
    'com.java.atomic.AtomicOperationsDemo',
    'com.java.collections.ConcurrentCollectionsDemo',
    'com.java.concurrency.CoordinationUtilitiesDemo',
    'com.java.patterns.ActorMessagePassingDemo',
    'com.java.performance.DeadlockDetectionDemo',
    'com.java.modern.CompletableFuturePipelineDemo',
    'com.java.systemdesign.BoundedPipelineDemo',
    'com.java.systemdesign.CircuitBreakerDemo',
    'com.java.systemdesign.ResiliencePatternsDemo',
    'com.java.synchronizekeyword.App',
    'com.java.multithreading.interview.questions.ThreadLifecycleDemo',
    'com.java.multithreading.interview.questions.ThreadStatesDemo',
    'com.java.multithreading.interview.questions.ThreadGroupExample',
    'com.java.multithreading.interview.questions.DaemonExample',
    'com.java.multithreading.interview.questions.InterruptDemo',
    'com.java.multithreading.interview.questions.ReadWriteStampedLockDemo',
    'com.java.multithreading.interview.questions.SpuriousWakeupDemo'
)

New-Item -ItemType Directory -Path $outputDirectory | Out-Null
try {
    Push-Location $repositoryRoot
    try {
        & javac --release 8 -d $outputDirectory @javaSources
        if ($LASTEXITCODE -ne 0) {
            throw "Java 8 source compilation failed with exit code $LASTEXITCODE"
        }

        foreach ($demo in $java8Demos) {
            Write-Host "Running $demo"
            & java -cp $outputDirectory $demo
            if ($LASTEXITCODE -ne 0) {
                throw "Demo $demo failed with exit code $LASTEXITCODE"
            }
        }

        $compilerVersion = (& javac -version 2>&1 | Out-String).Trim()
        if ($compilerVersion -match 'javac\s+(\d+)') {
            $jdkMajor = [int]$Matches[1]
            if ($jdkMajor -ge 21) {
                $modernSource = Join-Path $repositoryRoot 'src-modern\com\java\modern\VirtualThreadsDemo.java'
                $modernOutput = Join-Path $outputDirectory 'modern'
                New-Item -ItemType Directory -Path $modernOutput | Out-Null
                & javac --release 21 -d $modernOutput $modernSource
                if ($LASTEXITCODE -ne 0) {
                    throw "JDK 21 source compilation failed with exit code $LASTEXITCODE"
                }
                Write-Host 'Running com.java.modern.VirtualThreadsDemo (JDK 21+)'
                & java -cp $modernOutput com.java.modern.VirtualThreadsDemo
                if ($LASTEXITCODE -ne 0) {
                    throw "Virtual-thread demo failed with exit code $LASTEXITCODE"
                }
            } else {
                Write-Host "Skipping virtual-thread demo; JDK 21+ required (detected $compilerVersion)."
            }
        } else {
            throw "Could not determine javac version: $compilerVersion"
        }
    } finally {
        Pop-Location
    }
} finally {
    Remove-Item -LiteralPath $outputDirectory -Recurse -Force
}

Write-Host 'All selected examples passed.'
