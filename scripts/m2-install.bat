@echo off

set VERSION=0.1.17

echo "Installing testadvisor-lib-%VERSION%"
call mvn install:install-file -Dfile=testadvisor-lib-%VERSION%.pom -DgroupId=com.salesforce.cte -DartifactId=testadvisor-lib -Dversion=%VERSION% -Dpackaging=pom

call mvn install:install-file -Dfile=testadvisor-lib-%VERSION%.jar -DgroupId=com.salesforce.cte -DartifactId=testadvisor-lib -Dsources=testadvisor-lib-%VERSION%-sources.jar -Djavadoc=testadvisor-lib-%VERSION%-javadoc.jar -Dversion=%VERSION% -Dpackaging=jar 

echo "Installing testadvisor-lib-selenium-3-%VERSION%"
call mvn install:install-file -Dfile=testadvisor-lib-selenium-3-%VERSION%.pom -DgroupId=com.salesforce.cte -DartifactId=testadvisor-lib-selenium-3 -Dversion=%VERSION% -Dpackaging=pom

call mvn install:install-file -Dfile=testadvisor-lib-selenium-3-%VERSION%.jar -DgroupId=com.salesforce.cte -DartifactId=testadvisor-lib-selenium-3 -Dsources=testadvisor-lib-selenium-3-%VERSION%-sources.jar -Djavadoc=testadvisor-lib-selenium-3-%VERSION%-javadoc.jar -Dversion=%VERSION% -Dpackaging=jar 

echo "Installing testadvisor-lib-selenium-4-%VERSION%"
call mvn install:install-file -Dfile=testadvisor-lib-selenium-4-%VERSION%.pom -DgroupId=com.salesforce.cte -DartifactId=testadvisor-lib-selenium-4 -Dversion=%VERSION% -Dpackaging=pom

call mvn install:install-file -Dfile=testadvisor-lib-selenium-4-%VERSION%.jar -DgroupId=com.salesforce.cte -DartifactId=testadvisor-lib-selenium-4 -Dsources=testadvisor-lib-selenium-4-%VERSION%-sources.jar -Djavadoc=testadvisor-lib-selenium-4-%VERSION%-javadoc.jar -Dversion=%VERSION% -Dpackaging=jar 
