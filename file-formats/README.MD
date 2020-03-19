# Camel File Formats Demo

This demo is so show how Fuse/Camel can take 2 different types of files, in this case a flat file and a csv and transform them into a common format for processing using a java object. 

## Pre-Reqs:

1. Maven version 3.6+
2. Jdk 1.8 + 

## To run the demo

1. From within the file-format directory run `mvn spring-boot:run`
2. Copy the file fixedwidth-sample.txt in file-formats/src/main/resources to file-formats/src/main/resources/fixed
3. Watch the logs to see it processed to a json print out of the java object
4. Copy the file sample.csv in file-formats/src/main/resources to file-formats/src/main/resources/csv
5. Watch the logs to see it processed to a json print out of the same java object as the fixed width file