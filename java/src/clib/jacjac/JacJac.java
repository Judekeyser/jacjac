package jacjac;

import java.lang.foreign.*;
import java.lang.invoke.*;

public class JacJac extends MethodSolver
{
  public JacJac(Arena arena, Linker linker) { super("libjacjac", arena, linker); }
  public JacJac(             Linker linker) { super("libjacjac",        linker); }
  public JacJac(Arena arena               ) { super("libjacjac", arena        ); }
  public JacJac(                          ) { super("libjacjac"               ); }

  private static final String[] STRING_SIZE_SIGNATURE = {"int", "jacjac_string_size", "void*"};
  public int stringSize(String string) {
    var arena = Arena.ofAuto();
	return ((Number) invoke(STRING_SIZE_SIGNATURE, arena.allocateFrom(string))).intValue();
  }
}
