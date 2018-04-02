

public abstract class Animal implements iHabitat {
	
	protected String name;
	protected int moveSpeed;
	
	//Example static variable, it is setup such as not to be modified outside the constructor!
	private static int number = 0;
	
	//Example default constructor
	public Animal()
	{
		this(0);
	}
	
	//Constructor overloading
	public Animal(int moveSpeed)
	{
		name = "Generic animal";
		number++;
		if(moveSpeed < 0)
			throw new IllegalArgumentException("Move speed of an animal cannot be less than 0!");
		this.moveSpeed = moveSpeed;
	}
	
	/*
	 * Methods with default implementation
	 */
	
	//Example static method
	public static int totalNumberOfAnimals()
	{
		return number;
	}
	
	//Protected method. Visible to all of its subclasses but not the outside world
	protected void move()
	{
		System.out.println("Animals can move");
	}
	
	public int getMovespeed()
	{
		return moveSpeed;
	}
	
	public void description()
	{
		System.out.println("This animal is a " + name);
		if(moveSpeed == 0)
			System.out.println("This animal cannot move!");
		else
		{
			move();
		}
	}
	
	//Providing a default implementation for one of the interface methods
	public boolean isMigratory()
	{
		return false; //assuming most animals are not migratory, can override in the child classes
	}

	/*
	 *   Abstract methods. 
	 */
	
	public abstract String food(); //what do they eat?
	public abstract String dietaryStyle(); //is it carnivorous, herbivorous or omnivorous
	
	// Interface methods
	public abstract String location();
	public abstract String temperature();
	
}
