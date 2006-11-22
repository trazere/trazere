#!/bin/sh -e

# Check JAVA_HOME
if [ -z "$JAVA_HOME" ]
then
    echo "Please set the JAVA_HOME environment variable."
    exit
fi

# Check ANT_HOME
if [ -z "$ANT_HOME" ]
then
    echo "Please set the ANT_HOME environment variable."
    exit
fi

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

"$ANT_HOME/bin/ant" $*
