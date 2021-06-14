package com.leantech.challenge.utils;

import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.testng.Assert.assertEquals;

@Test
public class CollectionUtilsTest {

    @DataProvider
    public Object[][] dataStreamProvider() {
        return new Object[][]{
                {
                        Lists.newArrayList(),
                        Stream.empty()
                },
                {
                        Lists.newArrayList("Ryu", "Test"),
                        Stream.of("Ryu", "Test")
                },
                {
                        Lists.newArrayList(null, "Ryu", "Test"),
                        Stream.of("Ryu", "Test")
                }
        };
    }

    @DataProvider
    public Object[][] dataListProvider() {
        return new Object[][] {
            {
                new String[]{},
                Lists.newArrayList()
            },
            {
                new String[]{"Ryu", "Test"},
                Lists.newArrayList("Ryu", "Test")
            },
            {
                new String[]{null, "Ryu", "Test"},
                Lists.newArrayList("Ryu", "Test")
            }
        };
    }

    @Test(dataProvider = "dataStreamProvider")
    public void safeStream(final Collection<String> request, final Stream<String> response) {
        final Stream response2 = CollectionUtils.safeStream(request);
        assertEquals(response2.findFirst(), response.findFirst());
    }

    @Test(dataProvider = "dataListProvider")
    public void safeArrayToList(final String[] request, final List<String> response) {
        final List<String> response2 = CollectionUtils.safeArrayToList(request);
        assertEquals(response, response2);
    }

}
