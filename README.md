# java-test-utils
Java code such as our WebDriverFactory for use in customer test projects

## Development
1. Run `mvn eclipse:eclipse` to generate .project file and download required jars
for development in Eclipse.
2. Run `mvn clean` to install any dependencies.
3. Start up eclipse, import project java-test-utils.
4. Compile project and create jar file:
a. When your project is based on WebDriver 3.x or newer, run
`mvn compile jar:jar` which creates jar file target/java-test-utils-0.0.1-SNAPSHOT.jar
b. When your project is based on WebDriver 2.x, run
`mvn compile jar:jar -f pom-selenium2.xml` which creates jar file target/java-test-utils-selenium2-0.0.1-SNAPSHOT.jar
5. Deploy jar file to local repository:
a. When your project is based on WebDriver 3.x or newer, run
`mvn install:install-file -Dfile=target/java-test-utils-0.0.1-SNAPSHOT.jar -DpomFile=pom.xml`
b. When your project is based on WebDriver 2.x, run
`mvn install:install-file -Dfile=target/java-test-utils-selenium2-0.0.1-SNAPSHOT.jar -DpomFile=pom-selenium2.xml`
