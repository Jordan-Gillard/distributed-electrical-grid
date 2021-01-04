#!/bin/zsh

java -jar event_generators/target/event-generators-0.0.1-SNAPSHOT-jar-with-dependencies.jar  \
    events --target http://localhost:8080/event
