#!/bin/sh
GLASSFISH_HOME=$HOME/glassfish
PATH=$GLASSFISH_HOME/bin:$PATH

SDB_HOME=$HOME/jena-sdb-1.3.5
SCRIPT_DIR=${PWD}

#asadmin stop-domain
#asadmin stop-database
#rm -rf $GLASSFISH_HOME
sudo apt-get autoremove --purge postgresql*

rm -rf $SDB_HOME
#rm -rf $HOME/.m2

# postgresql
sudo deluser mykbuser

sudo -u postgres dropdb wmstore
sudo -u postgres dropuser mykbuser    #from regular shell

