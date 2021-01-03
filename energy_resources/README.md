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

### Create a Consumer of Kafka serve
Create a consumer without specifying group
```shell
kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic battery_event
```

### Create the database volume used by MySQL

In order for the database to work, you will need to create a persistent
volume for it. The default volume is `databases-datavolume`. You can create
this volume by running the following command in your terminal:
```shell
docker volume create databases-datavolume
```
