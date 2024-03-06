package one.thousand.rules.plugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

public class GenerateRules {

  public static final int RULE_COUNT = 1000;

  public static void main(String[] args) throws IOException {
    Path rulesPackage = Path.of("src", "main/java/one/thousand/rules/plugin/rules".split("/"));
    Path rulesRspec = Path.of("src", "main/resources/org/sonar/l10n/rust/rules/rust".split("/"));
    if (Files.exists(rulesPackage)) {
      FileUtils.deleteDirectory(rulesPackage.toFile());
    }
    if (Files.exists(rulesRspec)) {
      FileUtils.deleteDirectory(rulesRspec.toFile());
    }
    Files.createDirectories(rulesPackage);
    Files.createDirectories(rulesRspec);
    var ruleList = new ArrayList<String>();
    var quotedRuleIds = new ArrayList<String>();
    for (int i = 1; i <= RULE_COUNT; i++) {
      String ruleName = String.format("Rule%04d", i);
      String ruleId = String.format("S%04d", i);
      String ruleRspecId = String.format("RSPEC-%04d", i);
      ruleList.add(ruleName + ".class");
      quotedRuleIds.add("\"" + ruleId + "\"");
      Files.writeString(rulesPackage.resolve(ruleName + ".java"),
        String.format("""
          package one.thousand.rules.plugin.rules;

          import org.sonar.check.Rule;

          @Rule(key = "%s")
          public class %s {
          }
          """, ruleId, ruleName));
      Files.writeString(rulesRspec.resolve(ruleId + ".json"),
        String.format("""
          {
            "title": "The rule %s",
            "type": "CODE_SMELL",
            "code": {
              "impacts": {
                "MAINTAINABILITY": "MEDIUM"
              },
              "attribute": "CONVENTIONAL"
            },
            "status": "ready",
            "remediation": {
              "func": "Constant\\/Issue",
              "constantCost": "0min"
            },
            "tags": [],
            "defaultSeverity": "Major",
            "ruleSpecification": "%s",
            "sqKey": "%s",
            "scope": "Main",
            "quickfix": "unknown"
          }
          """, ruleId, ruleRspecId, ruleId));
      Files.writeString(rulesRspec.resolve(ruleId + ".html"),
        String.format("""
          <h2>Why is this an issue?</h2>
          <p>This is the %s rule description.</p>
          """, ruleId));
    }
    Files.writeString(rulesPackage.resolve("CheckList.java"),
      String.format("""
        package one.thousand.rules.plugin.rules;

        import java.util.List;

        public class CheckList {
          public static List<Class<?>> getChecks() {
            return List.of(
              %s);
          }
        }
        """, String.join(",\n      ", ruleList)));
    Files.writeString(rulesRspec.resolve("Sonar_way_profile.json"),
      String.format("""
        {
          "name": "Sonar way",
          "ruleKeys": [
            %s
          ]
        }
        """, String.join(",\n    ", quotedRuleIds)));
  }

}
