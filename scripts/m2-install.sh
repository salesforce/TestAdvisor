#!/bin/bash
if [ -z "$1" ]
  then
    echo "Usage: m2-install.sh <version>"
    exit 0
fi

echo "Installing testadvisor-lib-$1"
mvn install:install-file -Dfile=testadvisor-lib-$1.pom \
            -DgroupId=com.salesforce.cte \
            -DartifactId=testadvisor-lib \
            -Dversion=$1 \
            -Dpackaging=pom 

mvn install:install-file -Dfile=testadvisor-lib-$1.jar \
            -DgroupId=com.salesforce.cte \
            -DartifactId=testadvisor-lib \
            -Dsources=testadvisor-lib-$1-sources.jar \
            -Djavadoc=testadvisor-lib-$1-javadoc.jar \
            -Dversion=$1 \
            -Dpackaging=jar 

echo "Installing testadvisor-lib-selenium-3-$1"
mvn install:install-file -Dfile=testadvisor-lib-selenium-3-$1.pom \
            -DgroupId=com.salesforce.cte \
            -DartifactId=testadvisor-lib-selenium-3 \
            -Dversion=$1 \
            -Dpackaging=pom 

mvn install:install-file -Dfile=testadvisor-lib-selenium-3-$1.jar \
            -DgroupId=com.salesforce.cte \
            -DartifactId=testadvisor-lib-selenium-3 \
            -Dsources=testadvisor-lib-selenium-3-$1-sources.jar \
            -Djavadoc=testadvisor-lib-selenium-3-$1-javadoc.jar \
            -Dversion=$1 \
            -Dpackaging=jar 

echo "Installing testadvisor-lib-selenium-4-$1"
mvn install:install-file -Dfile=testadvisor-lib-selenium-4-$1.pom \
            -DgroupId=com.salesforce.cte \
            -DartifactId=testadvisor-lib-selenium-4 \
            -Dversion=$1 \
            -Dpackaging=pom 

mvn install:install-file -Dfile=testadvisor-lib-selenium-4-$1.jar \
            -DgroupId=com.salesforce.cte \
            -DartifactId=testadvisor-lib-selenium-4 \
            -Dsources=testadvisor-lib-selenium-4-$1-sources.jar \
            -Djavadoc=testadvisor-lib-selenium-4-$1-javadoc.jar \
            -Dversion=$1 \
            -Dpackaging=jar 
