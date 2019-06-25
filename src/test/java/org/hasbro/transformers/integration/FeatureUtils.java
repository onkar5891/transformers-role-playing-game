package org.hasbro.transformers.integration;

import java.io.ByteArrayInputStream;

class FeatureUtils {
    private FeatureUtils() {

    }

    static void populateStream(String data) {
        ByteArrayInputStream interactiveStream = new ByteArrayInputStream(data.getBytes());
        System.setIn(interactiveStream);
    }

    static void resetStream() {
        System.setIn(System.in);
    }
}
