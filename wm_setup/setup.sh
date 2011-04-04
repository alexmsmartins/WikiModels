#!/bin/sh
#Create the Derby DB for JDBC authentication
sh ./create_userauth.sh
RETVAL=$?
[ $RETVAL -eq 0 ] && echo "\nINFO: The userauth Realm database was configured!"
[ $RETVAL -ne 0 ] && echo -e "\nFailure: the userauth Realm database was not configured!" && exit $RETVAL
 
#Create the models Knowledgebase
sudo /etc/init.d/postgresql stop
sudo /etc/init.d/postgresql start 8.3
#FIXME the postgresql debian script returns 0 even in cases when it was unsuccessful. Find a workaround for that.
RETVAL=$?
[ $RETVAL -eq 0 ] && mvn scala:run
[ $RETVAL -ne 0 ] && echo "\nFailure: the Postgres 8.3 Database System could not be started!"
exit $RETVAL


