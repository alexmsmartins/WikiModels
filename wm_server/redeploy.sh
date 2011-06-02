#!/bin/sh
mvn -Dmaven.test.skip=true package
MVNRESULT=$?
# This is done to let glassfish know the project should be redeployed
if [ $MVNRESULT -eq 0 ]; then
  echo "Maven built this project's war."
	touch target/wm_server-0.1-SNAPSHOT/.reload
  echo "File \".reload\" was touched."
  echo "Glassfish should now reload the web application from the target directory."
else
  echo "Maven could not build this project's war."
 	echo "No deployment. Compilation failed."
fi
