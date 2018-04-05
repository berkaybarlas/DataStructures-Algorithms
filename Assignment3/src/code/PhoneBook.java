package code;

import java.util.ArrayList;
import java.util.List;

import given.ContactInfo;
import given.DefaultComparator;

/*
 * A phonebook class that should:
 * - Be efficiently (O(log n)) searchable by contact name
 * - Be efficiently (O(log n)) searchable by contact number
 * - Be searchable by e-mai (can be O(n)) 
 * 
 * The phonebook assumes that names and numbers will be unique
 * Extra exercise (not to be submitted): Think about how to remove this assumption
 * 
 * You need to use your own data structures to implement this
 * 
 * Hint: You need to implement a multi-map! 
 * 
 */
public class PhoneBook {

  private BinarySearchTree<String, ContactInfo> byNumber;
  private BinarySearchTree<String, ContactInfo> byName ;
  int size = 0 ;

  public PhoneBook() {
    byNumber = new BinarySearchTree<String, ContactInfo>();
    byName = new BinarySearchTree<String, ContactInfo>();
    byNumber.setComparator(new DefaultComparator<String>());
    byName.setComparator(new DefaultComparator<String>());
  }

  // Returns the number of contacts in the phone book
  public int size() {
    return size;
  }

  // Returns true is the phone book is empty, false otherwise
  public boolean isEmpty() {
    return size==0;
  }

  //Adds a new contact overwrites an existing contact with the given info
  // Args should be given in the order of e-mail and address which is handled for you
  // Note that it can also be empty. If you do not want to update a field pass null
  public void addContact(String name, String number, String... args) {
    int numArgs = args.length;
    String email = null;
    String address = null;

    if (numArgs > 0)
      if (args[0] != null)
        email = args[0];
    if (numArgs > 1)
      if (args[1] != null)
        address = args[1];
    if (numArgs > 2)
      System.out.println("Ignoring extra arguments");

    ContactInfo newContact = new ContactInfo(name,number);
    newContact.setAddress(address);
    newContact.setEmail(email);
    if(byName.put(name,newContact)==null) {
      size++;
      byNumber.put(number,newContact);
    }else{
      /*if(byNumber.put(number,newContact)==null){*/
      BinaryTreeNode<String,ContactInfo> node =null;
      for(BinaryTreeNode<String,ContactInfo> n: byNumber.getNodesInOrder()){
        if(n.getValue().getName().equals(name)) {
          node = n;
          break;
        }
      }
      if(node!=null) {
        byNumber.remove(node.getKey());
        byNumber.put(number,newContact);
      }
    }
  }

  public ContactInfo searchByName(String name) {
    return byName.getNode(name).getValue();
  }

  public ContactInfo searchByPhone(String phoneNumber) {
    return byNumber.getNode(phoneNumber).getValue();
  }

  // Return the contact info with the given e-mail
  // Return null if it does not exists // Can be O(n)
  public ContactInfo searchByEmail(String email) {
    List<BinaryTreeNode<String, ContactInfo>> inOrderNodes = byName.getNodesInOrder();
    for(BinaryTreeNode<String, ContactInfo> node : inOrderNodes){
      if(node.getValue().getEmail()!=null && node.getValue().getEmail().equals(email)){
        return node.getValue();
      }
    }
    return null;
  }

  public boolean removeByName(String name) {
    ContactInfo deletedPerson = byName.remove(name);
    if(deletedPerson == null)
      return false;
    byNumber.remove(deletedPerson.getNumber());
    size--;
    return  true;
  }

  public boolean removeByNumber(String phoneNumber) {
    ContactInfo deletedPerson = byNumber.remove(phoneNumber);
    if(deletedPerson == null)
      return false;
    byName.remove(deletedPerson.getName());
    size--;
    return  true;
  }

  // Returns the number associated with the name
  public String getNumber(String name) {
    ContactInfo info = searchByName(name);
    if(info== null)
      return null;
    return info.getNumber();
  }

  // Returns the name associated with the number
  public String getName(String number) {
    ContactInfo info = searchByPhone(number);
    if(info== null)
      return null;
    return info.getName();
  }
  
  // Update the email of the contact with the given name
  // Returns true if there is a contact with the given name to update, false otherwise
  public boolean updateEmail(String name, String email) {
    ContactInfo updatedPerson = searchByName(name);
    if(updatedPerson == null)
      return false;
    updatedPerson.setEmail(email);
    return true;
  }
  
  // Update the address of the contact with the given name
  // Returns true if there is a contact with the given name to update, false otherwise
  public boolean updateAddress(String name, String address) {
    ContactInfo updatedPerson = searchByName(name);
    if(updatedPerson == null)
      return false;
    updatedPerson.setAddress(address);
    return true;
  }

  // Returns a list containing the contacts in sorted order by name
  public List<ContactInfo> getContacts() {
    List<ContactInfo> inOrderContacts = new ArrayList<>();

    List<BinaryTreeNode<String, ContactInfo>> inOrderNodes = byName.getNodesInOrder();
    for(BinaryTreeNode<String, ContactInfo> node : inOrderNodes)
      inOrderContacts.add(node.getValue());
    return inOrderContacts;
  }

  // Prints the contacts in sorted order by name
  public void printContacts() {

    List<ContactInfo> contacts = getContacts();
    for (ContactInfo person : contacts){

      System.out.println(person.getName() + " " + person.getNumber() );
      /*
      System.out.println("Name: " + person.getName() );
      System.out.println("Number: " + person.getNumber() );

      if (person.getAddress()!= null){
        System.out.println("Address: " + person.getAddress() );
      }

      if (person.getEmail()!= null){
        System.out.println("E-mail: " + person.getEmail() );
      }
    */
    }

  }
}
