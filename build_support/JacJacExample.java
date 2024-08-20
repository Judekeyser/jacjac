public class JacJacExample
{
	
  public static void main(String... args) {
    try(var jacjac = new jacjac.JacJac()) {
      var input = "Hello";
	  var output = jacjac.stringSize(input);
	  
      System.out.printf("Estimated size of %s is %d%n", input, output);
    }
  }
}

