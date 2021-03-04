# The exemplary configuration of TestNG library to use its output in Zephyr Scale 

This is an example how to configure testNG library to generate output file that could be used as input for `/automations/executions/junit` endpoint.

### Configuration
TestNG supports JUnit format by default.
In case the default listeners provided by the TestNG library are disabled (e.g. `useDefaultListeners = false` in gradle configuration), it might be necessary to include the `JUnitXMLReporter` in the listeners (e.g. `listeners = ['org.testng.reporters.JUnitXMLReporter']` in gradle).
The `JUnitXMLReporter` is provided by the TestNG library and enabled by default, so there is no need to declare it explicitly when no custom configuration is set.  
To enable TestNG format, it might be necessary to add `org.testng.reporters.XMLReporter` to the listeners used, `listeners = [..., 'org.testng.reporters.XMLReporter']` in case default listeners are disabled.

#### There are a few noteworthy points:
- Tests and classes marked with `@Ignore` annotation are not taken into consideration by the framework when it comes to generating output file - as a result, the test cases linked to those tests won't be present in the test cycle created by the endpoint. 
- A test that depends on a failing tests (e.g. `@Test(dependsOnMethods = {"DEV_T21_testMethod3"})`) is considered skipped (opposed to `@Ignore` annotation) and test case linked to such test will have Skipped (if custom status present) or Blocked status.
- Each testing class (without `@Ignore` annotation) results in one output file in JUnit xml format in `build/test-results/test`.
- Output files can be sent separately or zipped together, using `/automations/executions/junit` endpoint.


### Execute tests in the example
In order to execute tests in the example on your local machine you’ll have to checkout this repository and install java 8 or above. 
To use the code example, simply run `./gradlew test` in the root folder from the terminal or run gradle task `test` from your IDE.
