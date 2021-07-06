package com.github.perschola;

import org.junit.Assert;
import org.junit.Test;

public class MySqlConnectionTest {
    @Test
    public void testRun() { // TODO
        // Given
        MySqlConnection mySqlConnection = new MySqlConnection();

        // when
        mySqlConnection.run();

        //then
        Assert.assertNotNull(mySqlConnection.toString());
    }
}
