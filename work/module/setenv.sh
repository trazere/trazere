#!/bin/sh -e

# OS specific support.
cygwin=false
case "`uname`" in
    CYGWIN*) cygwin=true ;;
esac

# Run the Ant script.
SPINOZA_HOME="`pwd`"

ENDORSED_SPINOZA_HOME="$SPINOZA_HOME"
if $cygwin
then
    ENDORSED_SPINOZA_HOME=`cygpath -m "$ENDORSED_SPINOZA_HOME"`
fi
export ANT_OPTS="-Xmx1024m -Djava.endorsed.dirs=$ENDORSED_SPINOZA_HOME/endorsed"
