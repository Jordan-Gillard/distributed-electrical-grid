# Event Generators

Generate realistic device and pricing events, modeled on real-world events.

## Running

Show the help

```
 java -jar target/event-generators-0.0.1-SNAPSHOT-jar-with-dependencies.jar  -h
 java -jar target/event-generators-0.0.1-SNAPSHOT-jar-with-dependencies.jar  --help
```

Run the device event generator

```
 java -jar target/event-generators-0.0.1-SNAPSHOT-jar-with-dependencies.jar  \
    events --target http://localhost:8080/event
````

Run the pricing generator

```
 java -jar target/event-generators-0.0.1-SNAPSHOT-jar-with-dependencies.jar  \
    pricing --target http://localhost:8080/event
````

Event generators can also be run with an optional `--seed` flag, that takes a
long, to set the random seed used for generating events, enabling a
reproducible stream of events.

See `--help` for more information.

## Event Information

### Device Events

Device events have a number of fields

* charging: how many watts the device has charged since the last event
* regionId: UUID of the geographic region for the device
* deviceId: UUID of the device

The timestamp of the event is considered to be the time of event's arrival.

## Building the generators

```
 mvn clean package
```

Creates a `-jar-with-dependencies.jar` in the `target/` directory, which can
then be run.
