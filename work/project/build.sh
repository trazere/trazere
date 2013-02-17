#!/bin/bash

#  Copyright 2006-2013 Julien Dufour
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

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
