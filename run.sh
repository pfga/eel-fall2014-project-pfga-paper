#!/bin/sh
sbt package
rm -rf op
export HADOOP_CLASSPATH=/home/preethu19th/.ivy2/cache/org.scala-lang/scala-library/jars/scala-library-2.10.4.jar:/home/preethu19th/.ivy2/cache/commons-configuration/commons-configuration/jars/commons-configuration-1.10.jar:/home/preethu19th/.ivy2/cache/commons-lang/commons-lang/jars/commons-lang-2.6.jar
LIBJARS=/home/preethu19th/.ivy2/cache/org.scala-lang/scala-library/jars/scala-library-2.10.4.jar,/home/preethu19th/.ivy2/cache/commons-configuration/commons-configuration/jars/commons-configuration-1.10.jar,/home/preethu19th/.ivy2/cache/commons-lang/commons-lang/jars/commons-lang-2.6.jar

/opt/hadoop-0.20.2/bin/hadoop jar /home/preethu19th/IdeaProjects/e2/target/scala-2.10/eel-fall2014-project_2.10-1.0.jar Main.RunDataParser -libjars $LIBJARS src/main/resources/410119.csv op src/main/resources/parse-config.properties
