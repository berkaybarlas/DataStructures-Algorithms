package Autograder;
/*
 * DO NOT CHANGE THIS CODE
 * */

import java.lang.reflect.*;
import java.util.*;

//Very badly written needs to overhauling
public class Autograder {
	
	public static void init() {
		ClearLog();
		initP2W();
		initW2P();
	}
	
	static StringBuilder gradingLog;
	public static ConsoleOutputCapturer coc = new ConsoleOutputCapturer();
	
	public static Map<Class<?>,Class<?>> PrimitiveToWrapper = new HashMap<Class<?>,Class<?>>();
	private static void initP2W() {
		PrimitiveToWrapper.put(boolean.class, Boolean.class);
		PrimitiveToWrapper.put(byte.class, Byte.class);
		PrimitiveToWrapper.put(char.class, Character.class);
		PrimitiveToWrapper.put(double.class, Double.class);
		PrimitiveToWrapper.put(float.class, Float.class);
		PrimitiveToWrapper.put(int.class, Integer.class);
		PrimitiveToWrapper.put(long.class, Long.class);
		PrimitiveToWrapper.put(short.class, Short.class);
		PrimitiveToWrapper.put(void.class, Void.class);
	}
	
	public static Map<Class<?>,Class<?>> WrapperToPrimitive = new HashMap<Class<?>,Class<?>>();
	private static void initW2P() {
		WrapperToPrimitive.put(Boolean.class, boolean.class);
		WrapperToPrimitive.put(Byte.class, byte.class);
		WrapperToPrimitive.put(Character.class, char.class);
		WrapperToPrimitive.put(Double.class, double.class);
		WrapperToPrimitive.put(Float.class, float.class);
		WrapperToPrimitive.put(Integer.class, int.class);
		WrapperToPrimitive.put(Long.class, long.class);
		WrapperToPrimitive.put(Short.class, short.class);
		WrapperToPrimitive.put(Void.class, void.class);
	}
	
	public static void ClearLog()
	{
		gradingLog = new StringBuilder(1000);
	}
	
	public static void Log(String str)
	{
		gradingLog.append(str);
		gradingLog.append(System.lineSeparator());
	}
	
	public static void printLog()
	{
		if(gradingLog.length() == 0)
			System.out.println("Grading log is clear");
		else
			System.out.println(gradingLog.toString());
	}
	
	private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
	    fields.addAll(Arrays.asList(type.getDeclaredFields()));

	    if (type.getSuperclass() != null) {
	        getAllFields(fields, type.getSuperclass());
	    }

	    return fields;
	}
	
	public static boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
        /* Avoid unnecessary cloning */
        if (params1.length == params2.length) {
            for (int i = 0; i < params1.length; i++) {
                if (params1[i] != params2[i])
                    return false;
            }
            return true;
        }
        return false;
    }
	
	public static boolean compareMethods(Method m1, Method m2)
	{
        if ( (m1.getName() == m2.getName())) {
            if (!m1.getReturnType().equals(m2.getReturnType()))
                return false;
            return equalParamTypes(m1.getParameterTypes(), m2.getParameterTypes());
        }
        return false;
	}
	
	public static boolean compareFields(Field f1, Field f2)
	{
        return (f1.getName() == f2.getName()) && (f1.getType() == f2.getType());
	}
	
	public static boolean compareConstructors(Constructor<?> c1, Constructor<?> c2) {
        //Default equals should work since classes should also match
		return c1.equals(c2);
	}
	
	public static boolean testIfImplemented(Class<?> clazz, Class<?> interfaze)
	{
		if(interfaze.isInterface())
			return Arrays.asList(clazz.getInterfaces()).contains(interfaze);
		return false;
	}

	//USE GET FIELD
	public static int numMethodMatches(Map<String,agMethod> methodList, Class<?> clazz)
	{
		int hits = 0;
		for(String key : methodList.keySet())
		{
			try {
				Method m = clazz.getMethod(key, methodList.get(key).getParameterTypes());
				if(methodList.get(key).equals(m))
					hits++;
				else
					Log("Method " + key + " does not match in class " + clazz.getName());
			} catch (NoSuchMethodException e) {
				Log("Method " + key + " is not available in class " + clazz.getName());
				//Log things in the future :)
			}
		}
		return hits;
	}
	
	public static int numConstructorMatches(List<Class<?>[] > constructorParatemeterTypes, Class<?> clazz)
	{
		int hits = 0;
		for(Class<?>[] cp : constructorParatemeterTypes)
		{
			try {
				clazz.getConstructor(cp);
				hits++;
			} catch (NoSuchMethodException e) {
				Log("No constructor match in class " + clazz.getName());
			}
		}
		return hits;
	}
	
	public static int numFieldMatches(Map<String, agField> fieldList, Class<?> clazz)
	{
		int hits = 0;
		for(String key : fieldList.keySet())
		{
			try {
				Field f = clazz.getField(key);
				if(fieldList.get(key).equals(f))
					hits++;
				else
					Log("Field " + key + " does not match in class " + clazz.getName());
			} catch (NoSuchFieldException e) {
				Log("Field " + key + " is not available in class " + clazz.getName());
			}
		}
		return hits;
	}
	public static int numFieldAndValueMatches(Object obj, Map<String, agField> fieldList, Map<String, Object> valueList) throws IllegalArgumentException, IllegalAccessException
	{
		return numFieldAndValueMatches(obj, fieldList, valueList, false);
	}
	
	public static int numFieldAndValueMatches(Object obj, Map<String, agField> fieldList, Map<String, Object> valueList, boolean allFields) throws IllegalArgumentException, IllegalAccessException
	{
		//not checking a lot of potential errors!
		int hits = 0;
		Field[] clazzFields;
		if(allFields) {
			ArrayList<Field> f = new ArrayList<Field>();
			getAllFields(f, obj.getClass());
			clazzFields = f.toArray(new Field[f.size()]);
		}
		else
			clazzFields = obj.getClass().getDeclaredFields();
		
		for(String key : valueList.keySet())
		{
			agField f1 = fieldList.get(key);
			for(Field f : clazzFields)
			{
				if(f1.equals(f)) {
					f.setAccessible(true);
					if(	valueList.get(key).equals(f.get(obj)) )
						hits++;
					else
						Log("Field " + key + " matches but not the values. Current: " + f.get(obj) + " Desired:" + valueList.get(key));
				}
			}
		}
		return hits;
	}
	

}
