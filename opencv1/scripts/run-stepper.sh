#!/bin/bash

if [ $# -eq 1 ] && [ "$1" == "-h" ] ; then
	echo "Usage: $0 [portname]"
	echo "  e.g., $0 /dev/ttyACM0"
	exit 1
fi

CHECKIN_PROJ_DIR=/home/brian/git/stronghold/opencv1
export CLASSPATH=${CHECKIN_PROJ_DIR}/bin:${CHECKIN_PROJ_DIR}/lib/jssc-2.8.0.jar
export JAVA_HOME=/usr/local/share/java/jdk1.8.0_66
export PATH=${JAVA_HOME}/bin:${PATH}
java org.macy.test.StepperMotorExample $@
