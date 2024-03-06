package one.thousand.rules.plugin;

import org.sonar.api.Plugin;

public class OneThousandRulesPlugin implements Plugin {

  @Override
  public void define(Context context) {
    context.addExtensions(
      RustLanguage.class,
      RustRulesDefinition.class,
      RustSonarWayProfile.class,
      RustSensor.class);
  }

}
