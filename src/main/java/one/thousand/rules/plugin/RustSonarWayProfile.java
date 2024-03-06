package one.thousand.rules.plugin;

import java.util.stream.Collectors;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.sonarsource.analyzer.commons.BuiltInQualityProfileJsonLoader;

public class RustSonarWayProfile implements BuiltInQualityProfilesDefinition {

  @Override
  public void define(Context context) {
    NewBuiltInQualityProfile sonarWay = context.createBuiltInQualityProfile("Sonar way", "rust");
    var ruleKeys = BuiltInQualityProfileJsonLoader.loadActiveKeysFromJsonProfile(RustRulesDefinition.SONAR_WAY_PATH).stream()
      .map(rule -> RuleKey.of("rust", rule))
      .collect(Collectors.toSet());
    ruleKeys.forEach(ruleKey -> sonarWay.activateRule(ruleKey.repository(), ruleKey.rule()));
    sonarWay.done();
  }

}
