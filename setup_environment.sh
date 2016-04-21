#!/bin/sh

sudo apt-get update
sudo apt-get install openjdk-7-jdk
sudo apt-get install -y jUnit4
sudo apt-get install -y tomcat7
sudo service tomcat7 start