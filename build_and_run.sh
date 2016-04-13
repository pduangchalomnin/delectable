#!/bin/sh

cd src
javac -cp ../WebContent/WEB-INF/lib/*:.  edu/iit/cs445/delectable/rest/*.java edu/iit/cs445/delectable/entity/*.java edu/iit/cs445/delectable/interactor/*.java;
cd ..
sudo mkdir WebContent/WEB-INF/classes
cp -R src WebContent/WEB-INF/classes
cd WebContent/WEB-INF/classes
find . -name “*.java” -type f -delete
cd ../../../src
find . -name “*.class” -type f -delete
cd ../WebContent
jar -cvf edu.iit.cs445.delectable.war
sudo cp edu.iit.cs445.delectable.war /var/lib/tomcat7/webapps
sudo service tomcat7 restart
