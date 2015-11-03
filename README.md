### To view the slides:

    git clone https://github.com/indyscala/debugging-and-analysis-2015.git
    open ./debugging-and-analysis-2015/slides/index.html

### To run the tests:

    # in terminal 1
    sbt
    > test-only org.indyscala.dpa.FillQueue -- -oD

    # in terminal 2
    nc localhost 9900

netcat (the `nc` command) is used to unblock the tests prior to the timeouts in `waitForConnection()` calls.
E.g. to continue execution from [this wait](https://github.com/indyscala/debugging-and-analysis-2015/blob/master/src/test/scala/org/indyscala/dpa/FillQueue.scala#L19)
after taking a heap dump in VisualVM.
