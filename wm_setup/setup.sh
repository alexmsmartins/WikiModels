#!/bin/bassh

##### Constants
filename=./setup.log

##### Functions
usage() 
{
  echo "WikiModels setup program."
  echo "setup [-k|--fill-kb] [-h|--help]"
}

main() 
{
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
  return $RETVAL
}

fill_knowledgebase() 
{
  #TODO: check if glassfish is available and start it otherwise
  cd ../wm_rest_api
  echo $PWD
  mvn scala:run
  cd -
}

##### Main
# Loop until all parameters are used up
while [ "$1" != "" ]; do
  case $1 in
    -k | --fill-kb )       FILL_KB=0
                           echo "-k or --fill-kb were recognized"
                           ;;
#    -l | --log )          shift #TODO implement logging later if necessary
#                          filename=$1
#                          ;;
    -h | --help )          usage
                           exit
                           ;;
    * )             
                           usage
                           exit 1
  esac
  shift
done

echo "Authenticatioin database and knowledgebase are being configured"
main
RETVAL=$?
echo $FILL_KB

if [ "$FILL_KB" = "0" ]; then
  echo "Test Models are being loaded into the knowledgebase"
  fill_knowledgebase
  RETVAL=$?
fi

exit $RETVAL

