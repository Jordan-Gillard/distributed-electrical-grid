# Energy Resources
This module contains code to create the Energy Resources server.

## Usage
In order to use the code in this module, you will need to create the 
`battery_event` Kafka topic. You can do this by running:
```shell
kafka-topics.sh --bootstrap-server localhost:9092 --topic battery_event --create --partitions 3 --replication-factor 1
```
Make sure you have Zookeeper and Kafka running or else the previous command
will not work.
