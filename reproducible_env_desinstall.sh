#!/bin/sh
GLASSFISH_HOME=$HOME/glassfish
PATH=$GLASSFISH_HOME/bin:$PATH

SCRIPT_DIR=${PWD}

asadmin stop-domain
asadmin stop-database
rm -rf $GLASSFISH_HOME
sudo apt-get autoremove postgresql*

rm -rf $SDB_HOME

# postgresql
sudo -u postgres dropdb wmstore
sudo -u postgres dropuser root    #from regular shell
