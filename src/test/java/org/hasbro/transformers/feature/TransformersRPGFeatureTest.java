package org.hasbro.transformers.feature;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.hasbro.transformers.utils.RandomUtils;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(Cucumber.class)
@PrepareForTest(RandomUtils.class)
@CucumberOptions(features = "src/test/resources/transformers-rpg.feature")
public class TransformersRPGFeatureTest {
}
