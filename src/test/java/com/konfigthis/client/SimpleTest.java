package com.konfigthis.client;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

public class SimpleTest {
    final String MOCK_SERVER_URL = "http://localhost:4010";

    @Test
    public void initClientTest() {
        Configuration configuration = new Configuration();
        configuration.host = "https://myworkspace.myclickfunnels.com/api/v2";
        
        // Configure HTTP bearer authorization: BearerAuth
        configuration.token = "BEARER TOKEN";
        ClickFunnels client = new ClickFunnels(configuration);
        assertNotNull(client);
    }
}
