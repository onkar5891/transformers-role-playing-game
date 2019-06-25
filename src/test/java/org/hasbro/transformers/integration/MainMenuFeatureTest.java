package org.hasbro.transformers.integration;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.hasbro.transformers.factory.TransformersRPGFactory;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(Cucumber.class)
@PrepareForTest(TransformersRPGFactory.class)
@CucumberOptions(features = "src/test/resources/main-menu-flow.feature")
public class MainMenuFeatureTest {
}
