package com.smartbear.testngexamples;

import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;


@Ignore
public class IgnoredExemplaryTest {

    @Test(groups = { "group-1", "group-2" })
    public void testMethod1() {
        assertThat(true).isTrue();
    }

    @Test()
    public void testMethod2() {
        assertThat(true).isTrue();
    }

}
