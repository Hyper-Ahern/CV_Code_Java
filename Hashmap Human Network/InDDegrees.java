import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;

/** This program takes input of networks of people that know other people and
 * uses a graph to track their connections by degrees of relations. If bob knows
 * steve and steve knows alice, then bob is connected to alice by 2 degrees. It
 * uses a hashmap to keep track of string names and person objects for values
 * author: Hyper A'Hern
 * Date: April 25, 2019
 *
 * Citations: I used professor brodsky's tutorial algorithm to get the degrees
 *            right. I also talked about the problem with fellow classmates but
 *            no code was exchanged, only ideas.
 */
public class InDDegrees {

  public static void main(String[] args) {

    // Declaring and initializing varibales/scanners/hashmap
    Scanner in = new Scanner(System.in);
    String temp = in.next();
    String temp2 = "";
    int tempNum = 0;
    HashMap<String, Person> people = new HashMap<>();

    // While the relationships are being input (section 1 input)
    while (!(temp.equals("done"))) {

      // If the first person is IN the list already
      if (people.containsKey(temp)) {
        //dont make a person
        String relation = in.next();
        relation = in.next();

        // if person AND relation is in list do nothing, else add relation
        if (people.containsKey(relation)) {
          //do nothing
        } else {
          Person tempPers = new Person(relation);
          people.put(relation, tempPers);
        }
        people.get(temp).setRelation(relation);
      } else {

        // Person is NOT in the list - adding perosn to list
        String relation = in.next();
        relation = in.next();
        Person tempPers = new Person(temp);
        people.put(temp, tempPers);

        //If person is not BUT relation is, do nothing, else add relation
        if (people.containsKey(relation)) {
          //do nothing
        } else {
          Person tempPerson = new Person(relation);
          people.put(relation, tempPerson);
        }
        people.get(temp).setRelation(relation);
      }

      // Set the next temp string to the next input to test for "done"
      temp = in.next();
    }

    // Degree holds the degrees to be querried and startName holds the names
    ArrayList<Integer> degree = new ArrayList<>();
    ArrayList<String> startName = new ArrayList<>();

    // While the querries are being input (section 2 input)
    while (in.hasNext() && !(temp2.equals("done"))) {

      // If input is an int, add to degree
      if (in.hasNextInt()) {
        tempNum = in.nextInt();
        degree.add(tempNum);

      // if input is a word, check if it is "done", if not add it to startName
      } else if (in.hasNext()) {
        temp2 = in.next();
        if (temp2.equals("done")) {
          break;
        }
        startName.add(temp2);
      }

      // Making sure that the loop breaks if the input is "done"
      if (temp2.equals("done")) {
        break;
      }
    }

    /* *Printing* For every name in the Arraylist of names to be querried, check
     * if the name is one of the inputs. If it isn't then do nothing. If it is,
     * then make an arraylist to be printed out and call the printTest method
     */
    for(int i = 0; i < startName.size(); i ++){
      if(!(people.keySet().contains(startName.get(i)))){
        System.out.println (startName.get(i) + " is within " + degree.get(i) +
            " degrees of separation from:");
      } else {
        ArrayList<Person> finalResults =
            printTest(people.get(startName.get(i)), degree.get(i), people);

        /* This is a trick I was shown by Drew and several others that allows
         * for a quick deletion of duplicates and allows for quick sorting. It
         * works by putting everything in a hashset which does not allow
         * duplicates, then clearing the original list, then putting everything
         * back without duplicates.
         */
        LinkedHashSet<Person> tempHash = new LinkedHashSet<Person>();
        tempHash.addAll(finalResults);
        finalResults.clear();
        finalResults.addAll(tempHash);
        Person person = people.get(startName.get(i));

        /* Removing the original person's name from the list a second time
         * then sorting and finally printing out the results in a proper format.
         */
        if (finalResults.contains(person)) {
          finalResults.remove(person);
        }
        Collections.sort(finalResults);
        System.out.println(startName.get(i) + " is within " + degree.get(i) +
            " degrees of separation from:");
        for (int j = 0; j < finalResults.size(); j++) {
          System.out.println("  " + finalResults.get(j).name);
        }
      }
    }
  }

  /* This method is used to populate an ArrayList that ultimately gets printed
   * out with the full set of people within a given degree of relations.
   */
  public static ArrayList<Person> printTest
      (Person person, int degree, HashMap<String, Person> people){

    // Making a final arraylist with all names to be printed
    ArrayList<Person> group = new ArrayList<Person>();

    // If the name we are looking for is in the graph, return nothing else add
    if(people.keySet().contains(person)){
      return group;
    }
    group.add(person);

    // Loop through for each degree
    for(int i = 0; i < degree; i++) {
      ArrayList<Person> gPrime = new ArrayList<Person>();

      //For each person in the current list, get their adjacencies (relations)
      for (Person p : group) {
        ArrayList<String> adj = p.relation;

        /*For each person in their adjacencies, check if it is in the list. If
        not then add it. Then add that prime list to the final returnable one */
        for (String name : adj) {
          if (!(gPrime.contains(people.get(name)))) {
            gPrime.add(people.get(name));
          }
        }
      }

      group.addAll(gPrime);
    }

    // Removing the original person from the list and then returning the list
    if(group.contains(person)) {
      group.remove(person);
    }

    return group;
  }

  /* This class is used to create person objects that are stored in the
   * HashMaps as values. They contain a name and an ArrayList as attributes
   * It also implements the Comparable interface to allow for easy sorting with
   * the Collections.sort method later on.
   */
  public static class Person implements Comparable<Person> {

    public String name;
    public ArrayList<String> relation = new ArrayList<String>();

    //Constructor
    public Person(String n){
      this.name = n;
    }

    // Allows for easy sorting of person objects by their name attribute
    public int compareTo(Person f){
      return this.name.compareTo(f.name);
    }

    // Sets this person to know another person by adding it to the arraylist
    public void setRelation(String r){
      this.relation.add(r);
    }
  }
}