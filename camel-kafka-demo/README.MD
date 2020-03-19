# Camel Kafka Demo

Pre-Reqs For Running on OCP:

1. Install AMQ Streams Operator
2. Create a Kafka Cluster using the operator (default name is my-cluster)
3. Create a Kafka Topic using the operator (default name is my-topic)

To use this project on openshift first login to ocp using 
`oc login <api url>` or the odo command

Then create or select your project using
`oc new-project <your-project-name>`

Next you need to edit the following line in the camel-context.xml file:

`<property name="brokers" value="my-cluster-kafka-bootstrap.camel-kafka.svc:9092"/>`

The format of this time should be the following

` <property name="brokers" value="<clusterName>-kafka-bootstrap.<projectName>.svc:9092"/>`

Also, replace "my-topic" with your topic name in both the camel-context.xml and SampleCamelRouter files as necessary. 

Then go into the directory you cloned into and run the following command

```
$ cd camel-kafka-demo
$ mvn clean fabric8:plugin
```

In OpenShift you can see your pod start up.  In the logs you should see the Hello World message being produced and consumed.  

Next, expose your openshift service using a route.  This can be done under Networking --> Routes.  

Once you have the route you can click on it and append /camel/goodbye to the end to test your rest endpoint
