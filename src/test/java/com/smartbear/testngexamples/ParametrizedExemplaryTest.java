package com.smartbear.testngexamples;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParametrizedExemplaryTest {

    @DataProvider(name = "peopleDataProvider")
    public Object[][] createData() {
        return new Object[][] {
                { "Cedric", 36},
                { "", 1},
                { "Anne", 37},
                { "Doris", -15},
        };
    }

    @Test(dataProvider = "peopleDataProvider")
    public void parametrizedTest(String name, Integer age) {
        assertThat(name).isNotBlank();
        assertThat(age).isPositive();
    }

    @Ignore
    @Test(dataProvider = "peopleDataProvider")
    public void ignoredParametrizedTest(String name, Integer age) {
        assertThat(name).isNotBlank();
        assertThat(age).isPositive();
    }

}
