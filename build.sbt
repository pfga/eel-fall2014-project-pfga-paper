name := "eel-fall2014-project"

version := "1.0"

scalaVersion := "2.10.4"

resolvers ++= Seq(Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snaspshots"),
  "mvn" at "http://repo1.maven.org/maven2/",
  "cloudera" at "https://repository.cloudera.com/content/repositories/releases")

libraryDependencies ++= Seq(
  "com.google.guava" % "guava" % "18.0-rc2",
  "org.mortbay.jetty" % "jetty" %  "7.0.0.pre5",
  "org.mortbay.jetty" % "jetty-util" % "7.0.0.pre5",
  "com.sun.jersey" % "jersey-core" % "1.18.1",
  "com.sun.jersey" % "jersey-server" % "1.18.1",
  "commons-cli" % "commons-cli" % "1.2",
  "commons-configuration" % "commons-configuration" % "1.10",
  "commons-io" % "commons-io" % "1.3.2",
  "commons-lang" % "commons-lang" % "2.6",
  "commons-daemon" % "commons-daemon" % "1.0.15",
  "javax.servlet.jsp" % "jsp-api" % "2.2",
  "com.google.protobuf" % "protobuf-java" % "2.5.0",
  "javax.servlet" % "servlet-api" % "2.5",
  "org.codehaus.jackson" % "jackson-core-asl" % "1.9.13",
  "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.13",
  "tomcat" % "jasper-runtime" % "5.5.23",
  "xmlenc" % "xmlenc" % "0.52",
  "org.apache.hadoop" % "hadoop-core" % "0.20.2",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test")

instrumentSettings