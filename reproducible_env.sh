#!/bin/sh
#The  shell  shall write a message to standard error when it tries to expand a variable that is not set and immediately
# exit.
set -u
#######################
# reproducible_env.sh #
#######################
# This file is supposed to create the entire development environment for WikiModels from scratch
# This includes removing the

DOWNLOAD_DIR=$PWD/reproducible_env_downloads
GLASSFISH_JAR=glassfish-installer-v2.1.1-b31g-linux.jar
SCRIPT_DIR=${PWD}

echo "#check necessary environment variables"
echo "JAVA_HOME="$JAVA_HOME
echo "JDK_HOME="$JDK_HOME
echo "PWD="$PWD
#if a simple command fails for any of the reasons listed in Consequences of Shell Errors or returns an  exit  status
# #value  >0, then the shell shall immediately exit.
set -e

echo "Check necessary programs"

echo "before declaring function"

check_command_presence() 
{
  echo "Checking for the presence of $1 in PATH"
  hash $1 2>/dev/null || { echo >&2 "I require $1 but it's not installed.  Aborting."; exit 1; }
}

check_command_presence java
check_command_presence javac
check_command_presence ant
check_command_presence wget
check_command_presence md5sum
check_command_presence sudo     

#Prompt user for the sudo password at the beginning of the script so it does not have to be asked later 
sudo -v

############# install glassfish #############
#rm -rf ~/glassfish
if [ -d $DOWNLOAD_DIR ]; then
    echo "Download directory was created before"
else
    mkdir $DOWNLOAD_DIR
fi

if [ -f $DOWNLOAD_DIR/$GLASSFISH_JAR ]; then
    echo "Glassfis 2.1.1 was downloaded before"
else
    wget -P $DOWNLOAD_DIR http://download.java.net/javaee5/v2.1.1_branch/promoted/Linux/$GLASSFISH_JAR 
fi

md5sum -c $DOWNLOAD_DIR/$GLASSFISH_JAR.md5
RETVAL=$?
if [ "$RETVAL" -eq 0 ]; then
	echo "Glassfish 2.1.1 integrity checked!"
else
	echo >&2 "Glassfish 2.1.1 has an incorrect md5 hash. Aborting."
	exit $RETVAL
fi

# conmmands from http://glassfish.java.net/downloads/v2.1.1-final.html
# To install and configure GlassFish you need to have JDK 5 or JDK 6 installed on your system. The configuration processing depends on Ant (1.6.5).  The bundle includes an Ant distribution that has been extended with tasks to facilitate developing Java EE 5 applications for the application server.

#1) Download one of the bundles to disk, set JAVA_HOME to the JDK you have installed on your system.
# Run:
cd $DOWNLOAD_DIR
java -Xmx256m -jar ./$GLASSFISH_JAR -console
if [ -d ~/glassfish ]; then
	echo "~/glassfish already exists."
	rm -r $HOME/glassfish
fi
mv ./glassfish $HOME/
rm $HOME/glassfish/setup.xml
cp ./setup.xml $HOME/glassfish/	
# This command will unbundle GlassFish and create a new directory structure rooted under a directory named 'glassfish'.
cd $HOME/glassfish
# If you are using a machine with an operating system that is a derivative of UNIX(tm), set the execute permission for the Ant binaries that are included with the GlassFish bundle.
chmod -R +x lib/ant/bin
lib/ant/bin/ant -f setup.xml 

cd $SCRIPT_DIR
############# install postgres #############

sudo apt-get -y install postgresql-9.1 

############# install maven #############

sudo apt-get -y install maven2
check_command_presence mvn

############# create authentication realm #############
cd $SCRIPT_DIR
sh $SCRIPT_DIR/create-userauth-realm.sh

############# configure sdb and knowledgebase #############
cd $SCRIPT_DIR
sh $SCRIPT_DIR/install_config_kb.sh

############# setup databases #############
#sh $SCRIPT_DIR/setup-databases.sh 

