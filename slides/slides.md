% title: Debugging and Perf Analysis
% subtitle: Tools for Development and Production
% author: Brad Fritz
% author: bfritz on {twitter,github,freenode}

---
title: Debugging and Perf Analysis
build_lists: true

Let's narrow it down...

- Tools I've used
- ...or wanted to use.
- Free

---
title: Share!

Your experiences are more interesting than my slides and simple demos.

---
title: On My List - Development
build_lists: true

- logging, e.g. `println()` or [log4s](http://log4s.org/)
- REPL, e.g. `scala` or `sbt console`
- debugger and breakpoints, e.g. [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- JVM profiler, e.g. [VisualVM](http://visualvm.java.net/)

---
title: On My List - Production
build_lists: true

- logging ***with structure***, e.g. [log4s](http://log4s.org/) with JSON output
    - [log4j jsonevent layout](https://github.com/logstash/log4j-jsonevent-layout)
    - [logback JSON encoder](https://github.com/logstash/logstash-logback-encoder)
    - [mapped diagnostic contexts](http://www.slf4j.org/manual.html#mdc)
- application metrics, e.g. [dropwizard-metrics](http://metrics.dropwizard.io/3.1.0/)
- low-overhead CPU profiling, e.g. [perf_events](http://www.brendangregg.com/linuxperf.html) and [flame graphs](http://www.brendangregg.com/flamegraphs.html)
    - [Netflix Vector](https://github.com/Netflix/vector)
- cluster-wide profiling, e.g. [Etsy's statsd-jvm-profiler](https://github.com/etsy/statsd-jvm-profiler)

---
title: Logging

Ever done this?

<pre class="prettyprint" data-lang="scala">
println("HERE: 1")
if (cond) {
  println("HERE: 2")
  // huh, why isn't this running?
  // code, code, code
}
</pre>

For many cases, this is probably better...

<pre class="prettyprint" data-lang="scala">
logger.info(s"Created order $orderId.")
if (cond) {
  logger.debug(s"Handlng order $orderId as urgent.")
  // huh, why isn't this running?
  // code, code, code
}
</pre>

---
title: Logging - Mapped Diagnostic Context

And sometimes this makes more sense, especially with structured
logging in production.

<pre class="prettyprint" data-lang="scala">
val requestId = java.util.UUID.randomUUID

MDC.withCtx ("request-id" -> requestId.toString, "request-user" -> user) {
  validateOrderRequest(req)
  val orderId = nextId()

  MDC.withCtx ("order-id" -> orderId) {
    logger.info(s"Created order $orderId.")

    if (cond) {
      logger.debug(s"Handlng order $orderId as urgent.")
    }
  }
}
</pre>

---
title: REPL

Sometimes its easiest/fastest to type `scala` or `sbt console`,
paste in a block of code, and watch what happens.

---
title: Debugger

<pre class="prettyprint" data-lang="scala">
println(s"HERE (X)      : $x")
println(s"HERE (X.count): $x.count")
println(s"HERE (X.sum   : $x.sum")
</pre>

Might be time to reach for the debugger.

---
title: Profiler

Signs you might want to reach for a profiler...

- Notebook fan just kicked on and the room feels warmer.
- App worked great...until it blocked all activity for 30 seconds.
- The HDD LED has been glowing non-stop since the app started.

---
title: Thanks!

Slides and code will be published at:

[https://github.com/indyscala/debugging-and-analysis-2015](https://github.com/indyscala/debugging-and-analysis-2015)

