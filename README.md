# The exemplary configuration of TestNG library to use its output in Zephyr Scale 

This is an example how to configure testNG library to generate output file that could be used as input for `/automations/executions/junit` endpoint.

### Configuration 
TestNG supports JUnit format by default.
In case the default listeners provided by the TestNG library are disabled (e.g. `useDefaultListeners = false` in gradle configuration), it might be necessary to include the `JUnitXMLReporter` in the listeners (e.g. `listeners = ['org.testng.reporters.JUnitXMLReporter']` in gradle).
The `JUnitXMLReporter` is provided by the TestNG library and enabled by default, so there is no need to declare it explicitly when no custom configuration is set.  
To enable TestNG format, it might be necessary to add `org.testng.reporters.XMLReporter` to the listeners used in case default listeners are disabled.

#### Gradle

```
test {
    useTestNG()
    options {
        useDefaultListeners = false // in case your configuration require disabling default listeners
        listeners = ['org.testng.reporters.JUnitXMLReporter']
    }
}

```
To include XMLReporter:
`listeners = [..., 'org.testng.reporters.XMLReporter']`

#### Maven
```
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>2.22.2</version>
            <configuration>
                <properties>
                    <property>
                        <name>usedefaultlisteners</name>
                        <value>false</value> <!-- in case your configuration require disabling default listeners -->
                    </property>
                    <property>
                        <name>listener</name>
                        <value>
                            org.testng.reporters.JUnitXMLReporter
                        </value>
                    </property>
                </properties>
            </configuration>
        </plugin>
    </plugins>
</build>
```
#### There are a few noteworthy points:
- Tests and classes marked with `@Ignore` annotation are not taken into consideration by the framework when it comes to generating output file - as a result, the test cases linked to those tests won't be present in the test cycle created by the endpoint. 
- A test that depends on a failing tests (e.g. `@Test(dependsOnMethods = {"DEV_T21_testMethod3"})`) is considered skipped (opposed to `@Ignore` annotation) and test case linked to such test will have Skipped (if custom status present) or Blocked status.
- Each testing class (without `@Ignore` annotation) results in one output file in JUnit xml format in `build/test-results/test`.
- Output files can be sent separately or zipped together, using `/automations/executions/junit` endpoint.

### Naming conventions
Test cases in Zephyr Scale will be matched by the full class name and test name or test case key if a method starts or ends with it, see the example below:
```
public class ExemplaryTest {

    // will match test case with key DEV-T19
    @Test(groups = { "group-1", "group-2" })
    public void DEV_T19_testMethod1() {
        assertThat(true).isTrue();
    }

    // will match test case with key DEV-T21
    @Test()
    public void testMethod3_DEV_T21() {
        fail("failing test");
    }

    // will match test case named com.smartbear.testngexamples.ExemplaryTest.testMethod4
    @Test(dependsOnMethods = {"testMethod3_DEV_T21"})
    public void testMethod4() {
        assertThat(true).isTrue();
    }
}
```

### Execute tests in the example
In order to execute tests in the example on your local machine you’ll have to checkout this repository and install java 8 or above. 
To use the code example, simply run `./gradlew test` in the root folder from the terminal or run gradle task `test` from your IDE.
