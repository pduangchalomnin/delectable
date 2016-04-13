#!/bin/sh

cd src
javac -cp ../WebContent/WEB-INF/lib/*:/usr/share/java/junit4-4.11.jar:. edu/iit/cs445/delectable/unitTest/*.java
java -cp /usr/share/java/junit4-4.11.jar:. org.junit.runner.JUnitCore unitTest.TestSuit
find . -name “*.class” -type f -delete