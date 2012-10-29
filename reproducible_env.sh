#!/bin/sh
# This file is supposed to create the entire working environment for WikiModels from scratch
# This includes removing the

DOWNLOAD_DIR=tmp_downloads

#The  shell  shall write a message to standard error when it tries to expand a variable that is not set and immediately
# exit.
set -u
echo "#check necessary environment variables"
echo "JAVA_HOME="$JAVA_HOME
echo "JDK_HOME="$JDK_HOME
echo "PWD="$PWD
#if a simple command fails for any of the reasons listed in Consequences of Shell Errors or returns an  exit  status
# #value  >0, then the shell shall immediately exit.
set -e

echo "Check necessary programs"
hash mvn 2>/dev/null || { echo >&2 "I require maven but it's not installed.  Aborting."; exit 1; }
hash java 2>/dev/null || { echo >&2 "I require java but it's not installed.  Aborting."; exit 1; }
hash javac 2>/dev/null || { echo >&2 "I require javac but it's not installed.  Aborting."; exit 1; }
hash ant 2>/dev/null || { echo >&2 "I require ant but it's not installed.  Aborting."; exit 1; }
hash wget 2>/dev/null || { echo >&2 "I require wget but it's not installed.  Aborting."; exit 1; }

############# install glassfish #############
#rm -rf ~/glassfish
if [ -d ./$DOWNLOAD_DIR ]; then
    echo "Download directory was created before"
else
    mkdir ./$DOWNLOAD_DIR
fi

if [ -f ./$DOWNLOAD_DIR/glassfish-installer-v2.1.1-b31g-linux.jar ]; then
    echo "Glassfis 2.1.1 was downloaded before"
else
    wget http://download.java.net/javaee5/v2.1.1_branch/promoted/Linux/glassfish-installer-v2.1.1-b31g-linux.jar ./$DOWNLOAD_DIR/glassfish-installer-v2.1.1-b31g-linux.jar
fi


