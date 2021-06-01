# DrillBit-Lib Test Framework Enhancement
DrillBit-Lib test framework enhancement is designed to help UI automation author to create better Selenium 
based UI automation for the Salesforce platform.

## Development
1. Run `mvn eclipse:eclipse` to generate .project file and download required jars for development in Eclipse.
2. Run `mvn clean` to install any dependencies.
3. Start up eclipse, import project DrillBit-Lib.
4. Compile project and create jar file:
`mvn compile jar:jar` which creates jar file target/drillbit-lib-0.0.1-SNAPSHOT.jar
5. Deploy jar file to local repository:
`mvn install:install-file -Dfile=target/drillbit-lib-0.0.1-SNAPSHOT.jar -DpomFile=pom.xml`

## How to publish to Nexus
1. mvn clean package
    This will ensure your module build and package correctly.
2. mvn release:prepare
    This will prepare the release, more doc here:
    http://maven.apache.org/maven-release/maven-release-plugin/examples/prepare-release.html
    You may choose your release version, the default will only a minor version upgrade.
3. mvn release:perform
    This step will build/publish to nexus and also tag release on git repository, more doc here
    (http://maven.apache.org/maven-release/maven-release-plugin/examples/perform-release.html)
4. mvn release:clean
    Cleans up your local env
    
### Documenting a new release version
1. Go to the Releases [section](https://git.soma.salesforce.com/cqe/DrillBit-Lib/releases)
2. Click on the "Draft a new release" button
3. Enter the "tag version" and "Release title" value as drillbit-lib-v0.0.\<new-version\>
4. Write one or more sentences about the changes pushed ([example](https://git.soma.salesforce.com/cqe/DrillBit-Lib/releases/tag/drillbit-lib-v0.0.53)) and publish the release.
