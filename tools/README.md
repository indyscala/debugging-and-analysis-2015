
# Setup

    sudo pacman -S jdk8-openjdk cmake
    cd perf-map-agent
    export JAVA_HOME=/usr/lib/jvm/java-8-openjdk
    cmake .
    make
    cp out/libperfmap.so ../..
