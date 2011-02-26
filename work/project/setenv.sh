#!/bin/bash

# OS specific support.
cygwin=false
case "$(uname)" in
    CYGWIN*) cygwin=true ;;
esac

# Compute the project home.
PROJECT_HOME=$(pwd)
if $cygwin
then
    PROJECT_HOME=$(cygpath -m "$PROJECT_HOME")
fi

# Set the Ant options.
export ANT_OPTS="-Xmx1024m -Djava.endorsed.dirs=$PROJECT_HOME/endorsed"
