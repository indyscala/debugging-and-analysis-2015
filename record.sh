#!/bin/sh

set -e

PORT=9900
PID=$1
DURATION=$2

usage() {
    echo "Usage:"
    echo "  export JAVA_HOME=/path/to/java-8-jdk"
    echo "  cp tools/perf-map-agent/out/libperfmap.so ."
    echo "  $0 PID DURATION"
}

if [ -z "$PID" -o -z "$DURATION" -o -z "$JAVA_HOME" -o ! -e "libperfmap.so" ]; then
    usage
    exit 1
fi

# make sure sudo credentials are cached for `sudo perf` below
# prior to tickling waiting code
sudo echo

nc -w 10 localhost $PORT

# from http://techblog.netflix.com/2015/07/java-in-flames.html

sudo perf record -F 99 --pid $PID -g -- sleep $DURATION

java -cp tools/perf-map-agent/out/attach-main.jar:$JAVA_HOME/lib/tools.jar net.virtualvoid.perf.AttachOnce $PID
