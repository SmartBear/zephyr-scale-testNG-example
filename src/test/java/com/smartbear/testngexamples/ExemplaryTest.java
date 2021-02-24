package com.smartbear.testngexamples;

import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ExemplaryTest {

    @Test(groups = { "group-1", "group-2" })
    public void testMethod1() {
        assertThat(true).isTrue();
    }

    @Test(dependsOnMethods = {"testMethod1"})
    public void testMethod2() {
        assertThat(true).isTrue();
    }

    @Test()
    public void testMethod3() {
        fail("failing test");
    }

    @Test(dependsOnMethods = {"testMethod3"})
    public void testMethod4() {
    }

    @Test(dependsOnMethods = {"testMethod3"})
    public void testMethod5() {
        assertThat(true).isTrue();
    }

    @Test()
    @Ignore
    public void ignoredTestMethod() {
        assertThat(true).isTrue();
    }
}
