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


#### Data that is available in TestNG-format report but not in JUnitXML-format report
- TestNG allows defining groups that are present in the report, so that user can group their tests together.  
  ```
  <groups>    
    <group name="group-2">  
      <method signature="ExemplaryTest.DEV_T19_testMethod1()[pri:0, instance:com.smartbear.testngexamples.ExemplaryTest@55f58881]" name="DEV_T19_testMethod1" class="com.smartbear.testngexamples.ExemplaryTest"/>  
    </group> <!-- group-2 -->  
    <group name="group-1">  
      <method signature="ExemplaryTest.DEV_T19_testMethod1()[pri:0, instance:com.smartbear.testngexamples.ExemplaryTest@55f58881]" name="DEV_T19_testMethod1" class="com.smartbear.testngexamples.ExemplaryTest"/>  
    </group> <!-- group-1 -->  
  </groups>
  ```
- There is `ignored` counter in the TestNg report (next to the `skipped` counter), that indicates how many tests have @Ignore annotation. Nevertheless, the test names are also not present in the report, so that's not a huge advantage.
- When a method is skipped due to failed test it depends on, there is additional info like 
  ```
   depends-on-methods="com.smartbear.testngexamples.ExemplaryTest.DEV_T21_testMethod3"
  ``` 
  plus message 
  ```
  Method ExemplaryTest.DEV_T22_testMethod4()[pri:0, instance:com.smartbear.testngexamples.ExemplaryTest@b1fecdf] depends on not successfully finished methods
  ```
  and a stacktrace.
- With the proper configuration of XMLReporter (`generateTestResultAttributes=true`, but gradle does not support passing this parameter), it is possible to contain additional information set in `@Test` annotation, such as priority, successPercentage, invocationCount, dependsOnMethods and many more. 
 