package Autograder;
import java.lang.reflect.Field;

public class agField {
	String name;
	Class<?> type;
	Object value;
	
	public agField(String name, Class<?> type) {
		this.name = name;
		this.type = type;
	}
	
	public agField(Field f) {
		this(f.getName(), f.getType());
	}
	
	public boolean isPrimitive() {
		return type.isPrimitive();
	}
	
	public String getName()	{
		return name;
	}
	
	public Class<?> getType() {
		return type;
	}
	
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Field) {
            Field other = (Field)obj;
            return (name == other.getName())
                && (type == other.getType());
        }
        else if (obj != null && obj instanceof agField){
        	agField other = (agField)obj;
            return (name == other.getName())
                && (type == other.getType());
        }
        return false;
    }

}
