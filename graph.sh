#!/bin/sh

set -e

PID=$1

usage() {
    echo "Usage:"
    echo "  $0 PID"
}

if [ -z "$PID" ]; then
    usage
    exit 1
fi

DATE=$(date +%FZ%TZ)
SVG=$(pwd)/flamegraph-${DATE}-${PID}.svg

# from http://techblog.netflix.com/2015/07/java-in-flames.html

sudo chown root /tmp/perf-${PID}.map

sudo perf script \
    | ./tools/FlameGraph/stackcollapse-perf.pl \
    | ./tools/FlameGraph/flamegraph.pl --color=java --hash \
    > $SVG

sudo chown $USER /tmp/perf-${PID}.map

chromium $SVG
