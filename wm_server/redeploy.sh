#!/bin/sh
mvn -Dmaven.test.skip=true package
# This is done to let glassfish know the project should be redeployed
touch target/wm_server-1.0-SNAPSHOT/.reload
