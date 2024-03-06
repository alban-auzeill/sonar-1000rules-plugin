package one.thousand.rules.plugin;

import org.sonar.api.resources.AbstractLanguage;

public class RustLanguage extends AbstractLanguage {

  public RustLanguage() {
    super("rust", "Rust");
  }

  @Override
  public String[] getFileSuffixes() {
    return new String[] {".rs"};
  }
}
