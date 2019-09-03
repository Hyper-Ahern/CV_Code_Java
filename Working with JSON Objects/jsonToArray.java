/*
 * @author: James A'Hern
 * @date: Feb 24, 2018
 * @description:
 *   This uses Alex Brodsky's JSON scanner to take the JSON tokens and
 *   print them in a specific way. It puts them in an ArrayList and then
 *   prints them back out as expected.
 * There is a method and a main method:
 *  print( ArrayList, ArrayList): takes two array lists and prints them
 *                                according to specifications on the assignment
 */
import java.util.ArrayList;
import java.util.Stack;
public class JSim {

  public static void main(String[] args) {
    JSONScanner in = new JSONScanner(System.in);
    Stack <String> brackets1 = new Stack <String>();
    ArrayList <String> input1 = new ArrayList <String>();
    ArrayList <String> input2 = new ArrayList <String>();

    /* Takes the first token and since it is a JSON object, it will be a curly
     * brace, places it in the stack and also in the array.
     */
    String start = in.next();
    brackets1.push(start);
    input1.add(start);

    /* These while loops populates the ArrayLists with the tokens given by the
     * JSON data. It will keep track of curly brackets on a stack to see when
     * the first input ends and the second begins.
     */
    while(in.hasNext()) {
      String token = in.next();
      if (brackets1.size() == 0) {        // When the last curly bracket closes
        input2.add(token);
        brackets1.push(token);
        break;
      } else if (token.equals("{")) {     // If open brace, push to stack
        brackets1.push(token);
        input1.add(token);
      } else if(token.equals("}")) {      // If closing brace, pop off stack
        brackets1.pop();
        input1.add(token);
      } else {
        input1.add(token);
      }
    }

    while( in.hasNext()) {                // When the last curly bracket closes
      String token = in.next();
      if (brackets1.size() == 0) {
        break;
      } else if (token.equals("{")) {     // If open brace, push to stack
        brackets1.push(token);
        input2.add(token);
      } else if(token.equals("}")) {      // If closing brace, pop off stack
        brackets1.pop();
        input2.add(token);
      } else {
        input2.add(token);
      }
    }

    print(input1,input2);                 // Calls print and format function
  }

  /* This method takes in 2 array lists and prints them according to the format
   * given on the assignment.
   */
  public static void print (ArrayList<String> input1, ArrayList<String> input2){
    int size1 = input1.size();            // Getting the size of the arrays
    int size2 = input2.size();

    /* Printing input1 (array 1) if array 1 and 2 are equal. Furthermore, if
     *  the token is a {,},[,], or a comma, then a new line is printed and the
     *  print statements are placed properly so that the format remains proper.
     */
    if(input1.equals(input2)) {
      for(int i = 0; i < input1.size(); i++) {
        if(input1.get(i).equals("{")) {
          System.out.print(input1.get(i));
          System.out.println();
        } else if(input1.get(i).equals("}")) {
          System.out.println();
          System.out.print(input1.get(i));
        } else if(input1.get(i).equals("[")) {
          System.out.print(input1.get(i));
          System.out.println();
        } else if(input1.get(i).equals("]")) {
          System.out.println();
          System.out.print(input1.get(i));
        } else if(input1.get(i).equals(",")) {
          System.out.print(input1.get(i));
          System.out.println();
        } else {
          if((i+1) < size1) {
            if (input1.get(i + 1).equals(",")) {
              System.out.print(input1.get(i));
            } else {
              System.out.print(input1.get(i) + " ");
            }
          }
        }
      }
      System.out.println();
      System.out.print("Objects are equal.");

      /* If array 1 and 2 are not equal, prints out array ONE and says that they
       * are not equal. The same formatting applies from if they were equal.
       */
    } else {
      for(int i = 0; i < input1.size(); i++) {
        if(input1.get(i).equals("{")) {         //If "{" char, print new line
          System.out.print(input1.get(i));
          System.out.println();
        } else if(input1.get(i).equals("}")) {  //If "}" char, print new line
          System.out.println();
          System.out.print(input1.get(i));
        } else if(input1.get(i).equals("[")) {  //If "[" char, print new line
          System.out.print(input1.get(i));
          System.out.println();
        } else if(input1.get(i).equals("]")) {  //If "]" char, print new line
          System.out.println();
          System.out.print(input1.get(i));
        } else if(input1.get(i).equals(",")) {  //If "," char, print new line
          System.out.print(input1.get(i));
          System.out.println();
        } else {                                //not printing space after comma
          if((i+1) < size1) {
            if (input1.get(i + 1).equals(",")) {
              System.out.print(input1.get(i));
            } else {
              System.out.print(input1.get(i) + " ");
            }
          }
        }
      }

      System.out.println();

      /* Printing input 2 if they aren't equal. This is the end of the scenario
       * where they arrays are equal.
       */
      for(int i = 0; i < input2.size(); i++) {
        if(input2.get(i).equals("{")) {         //If "{" char, print new line
          System.out.print(input2.get(i));
          System.out.println();
        } else if(input2.get(i).equals("}")) {  //If "}" char, print new line
          System.out.println();
          System.out.print(input2.get(i));
        } else if(input2.get(i).equals("[")) {  //If "[" char, print new line
          System.out.print(input2.get(i));
          System.out.println();
        } else if(input2.get(i).equals("]")) {  //If "]" char, print new line
          System.out.println();
          System.out.print(input2.get(i));
        } else if(input2.get(i).equals(",")) {  //If "," char, print new line
          System.out.print(input2.get(i));
          System.out.println();
        } else {                                //not printing space after comma
          if((i+1) < size2) {
            if (input2.get(i + 1).equals(",")) {
              System.out.print(input2.get(i));
            } else {
              System.out.print(input2.get(i) + " ");
            }
          }
        }
      }

      System.out.println();
      System.out.println("Objects are not equal.");
    }
  }
}
