#!/bin/sh

file1=`md5sum $1|cut -b 1-32`
file2=`cut -d* -f1 $1.md5`

echo "Checking file: $1"
echo "Using MD5 file: $1.md5"
echo $file1
echo $file2

if [ "${file1}" != "${file2}" ]
then
	echo "md5 sums mismatch" 
	exit 1
else
	echo "checksums OK"
fi

