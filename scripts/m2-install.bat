@echo off
if [%1]==[] goto :usage

echo "Installing testadvisor-lib-%1"
call mvn install:install-file -Dfile=testadvisor-lib-%1.pom -DgroupId=com.salesforce.cte -DartifactId=testadvisor-lib -Dversion=%1 -Dpackaging=pom

call mvn install:install-file -Dfile=testadvisor-lib-%1.jar -DgroupId=com.salesforce.cte -DartifactId=testadvisor-lib -Dsources=testadvisor-lib-%1-sources.jar -Djavadoc=testadvisor-lib-%1-javadoc.jar -Dversion=%1 -Dpackaging=jar 
goto :done

:usage
echo m2-install.bat version

:done 