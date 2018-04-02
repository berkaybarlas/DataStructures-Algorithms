package Autograder;
import java.lang.reflect.Method;

public class agMethod {
	  String name;
      Class<?>[] parameterTypes;
      Class<?> returnType;
      int modifiers;
      
      //Not used for now.
      Class<?>[] checkedExceptions;
      
      public agMethod(String name,
    		  		  Class<?>[] parameterTypes,
    		  		  Class<?> returnType,
    		  		  int modifiers) {
    	  this.name = name;
    	  this.parameterTypes = parameterTypes;
  		  this.returnType = returnType;
  		  this.modifiers = modifiers;
      }
      
      public agMethod(String name,
	  		  Class<?> returnType,
	  		  int modifiers) {
          this(name, new Class<?>[0], returnType, modifiers);
      }
      
      public agMethod(Method m) {
    	  this(m.getName(), m.getParameterTypes(), m.getReturnType(), m.getModifiers());
      }
      
      public String getName()
      {
    	 return name; 
      }
      
      public Class<?> getReturnType() {
          return returnType;
      }
      
      public Class<?>[] getParameterTypes() {
          return parameterTypes.clone();
      }

      
      public boolean equals(Object obj) {
          if (obj != null && obj instanceof Method)  {
              Method other = (Method)obj;
              if ( (getName() == other.getName())) {
                  if (!returnType.equals(other.getReturnType()))
                      return false;
                  return Autograder.equalParamTypes(parameterTypes, other.getParameterTypes());
              }
          } 
          else if (obj != null && obj instanceof agMethod) {
        	  agMethod other = (agMethod)obj;
              if ( (getName() == other.getName())) {
                  if (!returnType.equals(other.getReturnType()))
                      return false;
                  return Autograder.equalParamTypes(parameterTypes, other.getParameterTypes());
              }
          }
		  return false;
      }

      //later on change this so that it saves and loads methods for easier autograding
}
