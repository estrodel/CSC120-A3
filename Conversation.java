import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import org.apache.commons.text.WordUtils;

class Conversation {

  public static void main(String[] arguments) {

    Scanner in = new Scanner(System.in);
    String input = "";
    int rounds = 0;
    ArrayList<String> transcript= new ArrayList<String>();
    String response = "";
    

    System.out.println(); System.out.println("---------------CHATBOT---------------"); System.out.println();
   
  
    // Validate input for rounds
    System.out.println("How many rounds would you like to play?");
    while(true) {
      if (!in.hasNextInt()) {
        String error = in.next();
        System.err.println(error + " is not a number");
        }
        else{
          rounds = in.nextInt();
          in.nextLine();
          break;
        }
    }



    //Start Conversation!
    System.out.println();
    System.out.println("Hi there! What's on your mind?");
    transcript.add("Hi there! What's on your mind?");

    
    //Conversation loop, responds to input and keep track of these on transcript. 
    for (int i = 0; i<rounds ;i++){
      input = in.nextLine();
      response = Conversation.respond(input);
      System.out.println(response); 
      transcript.add(input);
      transcript.add(response);
    }

    //End conversation and print transcript
    System.out.println("Alright, that's our time for today!"); 
    transcript.add("Alright, that's our time for today!");
    System.out.println();
    System.out.println("TRANSCRIPT");

    for(String i : transcript){
      String[] name = {"BOT: ", "USER: "};
      System.out.println(name[transcript.indexOf(i) % 2]+i);
    }
    in.close();

    
  }
  

  
  public static String respond(String input){

    //Initialize canned string for responses to questions and inputs without mirrors, and random variable
    Random ran = new Random();
    ArrayList<String> canned= new ArrayList <String>(Arrays.asList("Mhmm...", "I hear you.","Gotcha","That's... nice?", "I see.","You are such an insightful CSC Grader!"));
    ArrayList<String> cannedQ= new ArrayList <String>(Arrays.asList("I'm not really sure...","I'll have to get back to you on that one...", "Woof, thats a tough one... "));
    
    //Create arraylist that splits punctionation and words
    ArrayList<String> words = new ArrayList<String>(Arrays.asList(input.split("\\b")));

    //Catch exception if nothing is entered 
    if(input.length()==0){
      return "Try typing something!";
    }

    //Send canned question response if there is a question mark
    if(input.contains("?")){
      return (cannedQ.get(ran.nextInt(cannedQ.size()))+ " Anything else on your mind?");
    }

  

    //Loop through and truncate the sentence at the first period. Set the final punction to a question mark
    for(int i =0; i<words.size();i++){
      if(words.get(i).contains(".")){
        words.set(i, "? ");
        return String.join("",words.subList(0, i+1));
      }
    }
      

    //Loop through and change reflection words if the words element has an alphabetical character, also look ahead through next three elements
    //of list to check for contractions.
    for(int i =0; i<words.size();i++){
      if(Character.isLetter(words.get(i).charAt(0))){
        if(i<words.size()-2){
          if (String.join("", words.subList(i, i+3)).equalsIgnoreCase("i'm")){
            words.set(i, "you're");
            words.remove(i+1);
            words.remove(i+1);
          } else if (String.join("", words.subList(i, i+2)).equalsIgnoreCase("you're")){
            words.set(i, "I'm");
            words.remove(i+1);
            words.remove(i+1);
          }  else if (String.join("", words.subList(i, i+2)).equalsIgnoreCase("you've")){
            words.set(i, "I've");
            words.remove(i+1);
            words.remove(i+1);
          } else if (String.join("", words.subList(i, i+2)).equalsIgnoreCase("i've")){
            words.set(i, "you've");
            words.remove(i+1);
            words.remove(i+1);
          }       
        } 
        if(words.get(i).equalsIgnoreCase("I")){
          words.set(i, "you");
        } else if(words.get(i).equalsIgnoreCase("am")){
          words.set(i, "are");
        } else if(words.get(i).equalsIgnoreCase("me")){
          words.set(i, "you");
        } else if(words.get(i).equalsIgnoreCase("you")){
          if(i<words.size()/2){words.set(i, "I");}
          else words.set(i, "me");
        } else if(words.get(i).equalsIgnoreCase("my")){
          words.set(i, "you're");
        } else if(words.get(i).equalsIgnoreCase("your")){
          words.set(i, "my");
        } else if(words.get(i).equalsIgnoreCase("im")){
          words.set(i, "you're");
        } 
      }
    }

    //If no mirror words detected and changed then send back a canned response
    if (words.equals(Arrays.asList(input.split("\\b")))){
      return (canned.get(ran.nextInt(canned.size())));
    }

    //Capitalize the first word, if the last element of the words array is a letter add question mark, otherwise swap the last character with a question mark.
    boolean firstword = true;
    for(int i =0; i<words.size();i++){
      if (firstword && Character.isLetter(words.get(i).charAt(0))){
        words.set(i, WordUtils.capitalizeFully(words.get(i)));
        firstword = false;
      }
      if(Character.isLetter(words.get(words.size()-1).charAt(0))){
        words.add("?");
      } else {words.set(words.size()-1,"?");}
    }

    return String.join("", words);    

  }
}


