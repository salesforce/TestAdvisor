# TestAdvisor-Lib

TestAdvisor-Lib is designed to help test automation to collect test signals on the Salesforce platform.

## Requirements

1. JDK 8+
2. TestNG 6+ or JUnit 4.7+

## Usage

##### TestNG User

Please add the following listener to your TestNG.xml 

```
<listeners>
 	<listener class-name="com.salesforce.cte.listener.testng.TestListener"/>
</listeners>
```

##### JUnit4 User

No configuration necessary. JUnit listener will be automatically loaded

## Configuration

Please use the following environment variable to control the library behavior

* TEST_ADVISOR_REGISTRY
  The file path to the local test advisor registry folder
