% title: Debugging and Perf Analysis
% subtitle: Tools for Development and Production
% author: Brad Fritz
% author: bfritz on {twitter,github,freenode}

---
title: Debugging and Performance Analysis
build_lists: true

Let's narrow it down...

- Tools I've used
- ...or wanted to use
- ...or randomly found on the Internet.
- Bias towards Open Source.

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
- application metrics, e.g. [dropwizard-metrics](http://metrics.dropwizard.io/3.1.0/)
- system metrics, e.g. [collectd](http://collectd.org/), [Peformance Co-Pilot](http://www.pcp.io/) or hundreds of others
- low-overhead CPU profiling, e.g. [perf_events](http://www.brendangregg.com/linuxperf.html) with [flame graphs](http://www.brendangregg.com/flamegraphs.html) (and [Netflix Vector](https://github.com/Netflix/vector))
- cluster-wide profiling, e.g. [Etsy's statsd-jvm-profiler](https://github.com/etsy/statsd-jvm-profiler)

---
title: Development Tools

Tools most useful in development...

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
title: Debugging Tips
build_lists: true

- Download sources and docs for an SBT project:

    sbt update-classifiers

- Connect jconsole to IntelliJ debugger stopped on breakpoint:

    Right click on breakpoint and "Suspend Thread" instead of "Suspend All".

    Credit to [Ryan Scott at Rally](https://www.rallydev.com/blog/engineering/bite-size-pro-tips-intellij-debugger-and-jconsole).

---
title: Profiler

Signs you might want to reach for a profiler...

- Notebook fan just kicked on and the room feels warmer.
- App worked great...until it blocked all activity for 30 seconds.
- The HDD LED has been glowing non-stop since the app started.

---
title: Profiler - VisualVM

Demo:

- CPU profiling
- thread monitoring
- heap dump and analysis

---
title: Production Tools

Tools most useful in production...

---
title: Logging

Even more useful in production than development.

- What was going on when host X threw an `OutOfMemoryException`?
- Joe ordered 10 of 15 widgets but there are only 4 left in stock.
  What happened?
- Load just spiked on the reverse proxies.  What are the referrers?
- And a million other questions.

---
title: Logging - Structure Helps

`grep`, `sed`, `awk` and friends are useful but the log data comes to
life when it is structured and can be easily searched and visualized.

- [log4j jsonevent layout](https://github.com/logstash/log4j-jsonevent-layout)
- [logback JSON encoder](https://github.com/logstash/logstash-logback-encoder)
- [mapped diagnostic contexts](http://www.slf4j.org/manual.html#mdc)
- [Kibana](https://www.elastic.co/products/kibana) and Elasticsearch

---
title: Application Metrics

Many choices.

- [dropwizard-metrics](http://metrics.dropwizard.io/3.1.0/) is popular.
- So are statsd [clients](https://github.com/etsy/statsd/wiki#client-implementations).

---
title: System Metrics

I would focus on key app metrics first.

Probably more choices than app metrics.

- I use [collectd](http://collectd.org/) in production without serious complaint.
- [Peformance Co-Pilot](http://www.pcp.io/) looks interesting.
- A coworker really likes [Zabbix](http://www.zabbix.com/).

---
title: Mixed-mode Java Flame Graphs

Demo

...but you should really checkout [Brendan Gregg's](http://www.brendangregg.com/)
posts and presentations.  [This one](http://techblog.netflix.com/2015/07/java-in-flames.html)
on Netflix's tech blog is a good place to start for Java flame graphs.

---
title: Hadoop^wCluster-wide Profiling

If you're running a fleet of machines processing complex workloads...

Etsy has JVM agent profiler that might be useful:
[https://github.com/etsy/statsd-jvm-profiler/](https://github.com/etsy/statsd-jvm-profiler/)

It was inspired by [Kyle Kingsbury's work](https://github.com/riemann/riemann-jvm-profiler).

Twitter is [also working on](https://youtu.be/szvHghWyuoQ?t=25m15s)
tools that might make it into OpenJDK.

---
title: Thanks!

Slides and code published at:

[https://github.com/indyscala/debugging-and-analysis-2015](https://github.com/indyscala/debugging-and-analysis-2015)

