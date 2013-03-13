#!/bin/sh
export GLASSFISH_HOME=$HOME/glassfish
export DERBY_HOME=$GLASSFISH_HOME/javadb
export DERBY_PORT=1527
export DB_HOME=$HOME/tmp
export DB_HOST=0.0.0.0
export SQL_SCRIPT=$PWD/wm_setup/userauth.sql

$GLASSFISH_HOME/bin/asadmin stop-database
[ -d $DB_HOME ] && mkdir $DB_HOME
$GLASSFISH_HOME/bin/asadmin start-database --dbhost $DB_HOST --dbport $DERBY_PORT --dbhome $DB_HOME

RETVAL=$?
[ $RETVAL -eq 0 ] && sh $GLASSFISH_HOME/javadb/bin/ij $SQL_SCRIPT
[ $RETVAL -ne 0 ] && echo "\nFailure: the Derby Database System could not be started!"
exit $RETVAL
