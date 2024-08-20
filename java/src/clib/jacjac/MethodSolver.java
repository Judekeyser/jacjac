package jacjac;

import java.lang.foreign.*;
import java.lang.invoke.*;

import static java.util.Objects.requireNonNull;

class MethodSolver implements AutoCloseable
{ /* This class takes ownership of the arena, hence why it implements AutoCloseable */
  static class ForeignError extends Error /* Reasoning: C does not throws, but it segfaults */ { ForeignError(Throwable cause) { super(cause); } }
	
  String libName;
  final Arena arena; final Linker linker;
  final java.util.HashMap<String, MethodHandle> methodLookup = new java.util.HashMap<>();
  MethodSolver(String libName, Arena arena, Linker linker) { this.libName = requireNonNull(libName); this.arena = requireNonNull(arena); this.linker = requireNonNull(linker); }
  MethodSolver(String libName,              Linker linker) { this(          libName,                              defaultArena()       ,               linker               ); }
  MethodSolver(String libName, Arena arena               ) { this(          libName,                              arena                ,               defaultLinker()      ); }
  MethodSolver(String libName                            ) { this(          libName,                              defaultArena()       ,               defaultLinker()      ); }
	
  private static Arena  defaultArena()  { return Arena.ofConfined();    }
  private static Linker defaultLinker() { return Linker.nativeLinker(); }
	
  @Override public void close() { arena.close(); }
	
  private MemoryLayout getPrimitiveCType(String cTypeName) {
    var layout = linker.canonicalLayouts().get(cTypeName); if(layout != null) return layout;
      else throw new java.util.NoSuchElementException("No type equivalence for C type %s".format(cTypeName));
  }
	
  private MethodHandle solve(String[] signature) {
    var handle = methodLookup.get(signature[1]);
    if(null == handle) {
      FunctionDescriptor cSignature; {
        var argsLayouts = new MemoryLayout[signature.length - 2];
        for(int i = 0; i < argsLayouts.length; i++)
          argsLayouts[i] = getPrimitiveCType(signature[2+i]);
        cSignature = FunctionDescriptor.of(getPrimitiveCType(signature[0]), argsLayouts);
      }
      var libPath = java.nio.file.Path.of(System.getenv("C_LIB_LOCATION"), libName);
      var methodMemorySegment = SymbolLookup.libraryLookup(libPath, arena).find(signature[1]).orElseThrow();
      handle = linker.downcallHandle(methodMemorySegment, cSignature);
      handle = handle.asSpreader(0, Object[].class, signature.length-2);
      methodLookup.put(signature[1], handle);
    } return handle;
  }
  
  Object invoke(String[] signature, Object... inputs) {
    try{ return solve(signature).invoke(inputs); }
      catch(WrongMethodTypeException | ClassCastException e) { throw e; }
      catch(Throwable all) { throw new ForeignError(all); }
  }
}
