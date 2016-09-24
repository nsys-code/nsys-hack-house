#!/bin/bash

NSYS_BUNDLE_DIR=`pwd`/nsys-hack-house-bundle/target/nsys-hack-house

if [[ ! -d "$NSYS_BUNDLE_DIR" ]]; then
    echo "You need to build Nsys #HackTheHouse at first! Please run command 'build.sh' ..." >&2
    exit 1	
fi

chmod -R 755 $NSYS_BUNDLE_DIR/bin/*.sh
chmod -R 755 $NSYS_BUNDLE_DIR/portal/bin/*.sh
chmod -R 775 $NSYS_BUNDLE_DIR/portal/webapps

sh $NSYS_BUNDLE_DIR/bin/nsys-container.sh run