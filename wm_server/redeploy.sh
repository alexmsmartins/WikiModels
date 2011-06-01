#!/bin/sh
mvn -Dmaven.test.skip=true package
MVNRESULT=$?
echo "Maven could not build this project's war."
# This is done to let glassfish know the project should be redeployed
if [ $MVNRESULT -eq 0 ]; then
	touch target/wm_server-0.1-SNAPSHOT/.reload
else
 	echo "No deployment. Compilation failed."
fi
