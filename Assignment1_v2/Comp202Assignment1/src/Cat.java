

/*  Modify this file such that:
 * 
 * - This class extends from the Animal class
 * - This class implements the iBehavior interface (which you will implement)
 * - This class has the correct name
 * - The required fields/variables/data are defined
 * - The required methods are defined and implemented
 * 
 * Some useful information
 * - Cats generally make a "meow" sound
 * - Cats are referred as "common cat" if we do not know their breed
 * - Cats on average move with 17 units of speed.
 * - Cats live on "land"
 * - Cats like "mild" temperature
 * - Cats are not migratory
 * - Cats eat "cat food"
 * - Cats are "carnivorous"
 * - The sound a cat makes cannot be less than 3 characters long! 
 * - We consider most cats as pets and not wild animals
 * - Cats can run and climb
 * 
 * */

public class Cat extends Animal implements iBehavior  {
	//Declare fields sound and breed as protected strings
	protected String breed;
	protected String sound;
	
	// Implement Default constructor 
	// Make sure you instantiate all the variables. 
	// Note that some variables may come from the abstract class! 
	public Cat()
	{
		super(17);
		this.name="cat";
		this.breed = "common cat";
		this.sound = "meow";
		//Util.NotImplementedYetSoft();
	}
	
	// Implement over loaded constructor with the given two parameters
	// Remember, the sound a cat makes cannot be less than 3 characters so make sure this is avoided
	public Cat(String breed, String sound)
	{
		super(17);
		this.name="cat";
		this.breed = breed;
		if(sound.length()<3)
			throw new IllegalArgumentException("The sound a cat makes cannot be less than 3 characters!");
		this.sound = sound;
		//Util.NotImplementedYetSoft();
	 }
	
	private void purr()
	{
		System.out.println("Most cats purr to express contentment or pleasure");
	}
	
	public void makeSound()
	{
		System.out.println("The " + breed + " makes a " + sound + " sound");
	}
	
	@Override 
	protected void move()
	{
		System.out.println("Cats can run and climb");
	}
	
	// Override protected description() 
	// Call the superclass function. 
	// In addition, use the methods makeSound(), purr() and the variable breed in it to print additional information about the Cat
	@Override 
	public void description()
	{
		super.description();
		makeSound();
		purr();

		//Util.NotImplementedYetSoft();
	}
	
	// implement all the other methods you need here, they are not even started for you!
	public String location(){
		return"land";
	}
	public String temperature(){
		return "mild";
	}

	public String food(){
		return "cat food";
	}
	public String dietaryStyle(){
		return "carnivorous";
	}

	public boolean isWild(){
		return false;
	}
	public void run(){
		System.out.println("Running with " +
				this.moveSpeed +
				" speed");
	}

	public void sleep(int time) throws Exception{
		//throws an exception when this input is smaller than 1 (feel free to pick an exception type)
		if(time<1){
			throw new Exception("\nMust sleep for a positive number of hours!\n");
		}

		String sleep = "Z";
		for(int i=0;i<time;i++){
			sleep += "z";
		}
		System.out.println(sleep);
	}
	public void setSpeed(int speed){
		super.moveSpeed = speed;
	}
}
