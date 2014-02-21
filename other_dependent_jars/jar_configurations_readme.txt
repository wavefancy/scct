
The running of some java programs need AKKA(http://akka.io) framework and Trove primitive collections framework. 
1. You can configure the classpath at each time when run java program
 OR 
2. (Recommend) Please put the following jars in $JDK_HOME/jre/lib/ext (if you installed JDK) or $JRE_HOME/lib/ext (if you only installed JRE)

scala-library.jar
config-0.3.0.jar
akka-actor-2.0.4.jar
