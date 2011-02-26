#!/bin/bash

# OS specific support.
cygwin=false
case "$(uname)" in
    CYGWIN*) cygwin=true ;;
esac

# Compute the project home.
PROJECT_HOME=$(cd $(dirname "$0") ; pwd)
if $cygwin
then
    PROJECT_HOME=$(cygpath -m "$PROJECT_HOME")
fi

# Check ANT_HOME.
if [ -z "$ANT_HOME" ]
then
    echo "Please set the ANT_HOME environment variable."
    exit 1
fi

# Run the Ant script.
export ANT_OPTS="-Xmx1024m -Djava.endorsed.dirs=$PROJECT_HOME/endorsed"
exec "$ANT_HOME/bin/ant" "$@"
