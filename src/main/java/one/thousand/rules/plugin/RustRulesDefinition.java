package one.thousand.rules.plugin;

import one.thousand.rules.plugin.rules.CheckList;
import org.sonar.api.SonarRuntime;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

public class RustRulesDefinition implements RulesDefinition {

  private static final String RESOURCE_BASE_PATH = "/org/sonar/l10n/rust/rules/rust";
  public static final String SONAR_WAY_PATH = RESOURCE_BASE_PATH + "/Sonar_way_profile.json";

  private final SonarRuntime sonarRuntime;

  public RustRulesDefinition(SonarRuntime sonarRuntime) {
    this.sonarRuntime = sonarRuntime;
  }

  @Override
  public void define(Context context) {
    NewRepository repository = context.createRepository("rust", "rust");
    repository.setName("RustAnalyzer");
    RuleMetadataLoader ruleMetadataLoader = new RuleMetadataLoader(RESOURCE_BASE_PATH, SONAR_WAY_PATH, sonarRuntime);
    ruleMetadataLoader.addRulesByAnnotatedClass(repository, CheckList.getChecks());
    repository.done();
  }
}
