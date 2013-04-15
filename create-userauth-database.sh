#!/bin/sh
###############################
# create-userauth-database.sh #
###############################
export GLASSFISH_HOME=$HOME/glassfish
export DERBY_HOME=$GLASSFISH_HOME/javadb
export DERBY_PORT=1527
export DB_HOME=$HOME/tmp
export DB_HOST=0.0.0.0
export SQL_SCRIPT=$PWD/wm_setup/userauth.sql
export GLASSFISH_PASS_FILE=$PWD/passwordfile.txt
export PATH=$GLASSFISH_HOME/bin:$PATH
export ASADMIN_CMD="asadmin -u admin --passwordfile=$GLASSFISH_PASS_FILE"

echo "AS_ADMIN_PASSWORD=adminadmin
AS_ADMIN_ADMINPASSWORD=adminadmin
AS_ADMIN_USERPASSWORD=value 
AS_ADMIN_MASTERPASSWORD=value" > $GLASSFISH_PASS_FILE

#configure userauth realm

$ASADMIN_CMD


#configure userauth database
asadmin stop-database
[ -d $DB_HOME ] && mkdir $DB_HOME
asadmin start-database --dbhost $DB_HOST --dbport $DERBY_PORT --dbhome $DB_HOME

RETVAL=$?
[ $RETVAL -eq 0 ] && sh $GLASSFISH_HOME/javadb/bin/ij $SQL_SCRIPT
[ $RETVAL -ne 0 ] && echo "\nFailure: the Derby Database System could not be started!"
exit $RETVAL




