#!/bin/sh
mvn -Dmaven.test.skip=true package
MVNRESULT=$?
echo Maven result was $MVNRESULT
# This is done to let glassfish know the project should be redeployed
if [ $MVNRESULT -eq 0 ]; then
	touch target/wm_web_client-0.1-SNAPSHOT/.reload
else
 	echo No deployment. Compilation failed.
fi
