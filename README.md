# java-test-utils
Java code such as our WebDriverFactory for use in customer test projects

## Development
0. Pull from right branch
 a. When your project is based on WebDriver 3.x or newer, pull from 'master'
 b. When your project is based on WebDriver 2.x, pull from 'selenium2'
1. Run `mvn eclipse:eclipse` to generate .project file and download required jars
for development in Eclipse.
2. Run `mvn clean` to install any dependencies.
3. Start up eclipse, import project java-test-utils.
4. Compile project and create jar file:
`mvn compile jar:jar` which creates jar file target/java-test-utils-0.0.2-SNAPSHOT.jar
5. Deploy jar file to local repository:
`mvn install:install-file -Dfile=target/java-test-utils-0.0.2-SNAPSHOT.jar -DpomFile=pom.xml`

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