package com.smartbear.testngexamples;

import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ExemplaryTest {

    @Test(groups = { "group-1", "group-2" })
    public void DEV_T19_testMethod1() {
        assertThat(true).isTrue();
    }

    @Test(dependsOnMethods = {"DEV_T19_testMethod1"})
    public void DEV_T20_testMethod2() {
        assertThat(true).isTrue();
    }

    @Test()
    public void DEV_T21_testMethod3() {
        fail("failing test");
    }

    @Test(dependsOnMethods = {"DEV_T21_testMethod3"})
    public void DEV_T22_testMethod4() {
        assertThat(true).isTrue();
    }

    @Test()
    @Ignore
    public void DEV_T23_ignoredTestMethod() {
        assertThat(true).isTrue();
    }
}
