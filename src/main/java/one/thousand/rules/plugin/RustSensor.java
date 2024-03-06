package one.thousand.rules.plugin;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import one.thousand.rules.plugin.rules.CheckList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Phase;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;

@Phase(name = Phase.Name.PRE)
public class RustSensor implements Sensor {

  private static final Logger LOG = LoggerFactory.getLogger(RustSensor.class);
  private final CheckFactory checkFactory;

  public RustSensor(CheckFactory checkFactory) {
    this.checkFactory = checkFactory;
  }

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor.name("Rust 1000 rules");
  }

  @Override
  public void execute(SensorContext context) {
    List<Class<?>> allChecks = CheckList.getChecks();

    Checks<Object> createdChecks = checkFactory.create("rust").addAnnotatedChecks(allChecks);

    Set<String> createdCheckNames = createdChecks.all().stream()
      .map(check -> check.getClass().getSimpleName())
      .collect(Collectors.toSet());

    List<String> missingCheckNames = allChecks.stream()
      .map(Class::getSimpleName)
      .filter(name -> !createdCheckNames.contains(name))
      .sorted()
      .toList();

    String missingCheckNameList = String.join(", ", missingCheckNames);

    LOG.info("Rust Sensor executed with {} checks {} missing: {}",
      allChecks.size(),
      missingCheckNames.size(),
      missingCheckNameList);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}
