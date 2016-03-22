/**
 * Created by ameya on 3/21/16.
 */
import java.io.*;
import java.util.Scanner;

public class bbst {

    /**
     * Reads the input file to create red black tree and reads input from command line
     * @param fileName
     * @throws FileNotFoundException if "fileName" file is not found
     * @throws IOException if unable to read the file
     */

    public void readInputFile(String fileName){

        //Create an object of RedBlackTree class
        RedBlackTree rbTree = new RedBlackTree();
        try {

            ///home/ameya/IdeaProjects/RBT/src/test_100.txt


            //FileReader to read text files in default encoding
            FileReader fileReader = new FileReader(fileName);

            //Wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int n = Integer.parseInt(bufferedReader.readLine());

            String[] inputLine = new String[n];
            for(int arr_i=0; arr_i < n; arr_i++){
                inputLine[arr_i] = bufferedReader.readLine();//read lines one by one from the text file
            }

            // BufferReader close
            bufferedReader.close();

            // Creates Red Black Tree with all the black nodes
            RedBlackTree.root=rbTree.createRBT(inputLine,0,n-1);

            // Finds the deepest nodes to change the color of the nodes to RED
            rbTree.changeColorToRed(rbTree.findDeepestNodes(RedBlackTree.root));

            // create a scanner so that we can read the command-line input
            Scanner sc = new Scanner(System.in);
            String inputCommand;
            inputCommand = sc.nextLine();
            while (!"quit".equals(inputCommand)) {
                String commands[] = inputCommand.split(" ");
                String command = commands[0];

                switch (command) {
                    case "increase":
                        System.out.println(rbTree.increase(Integer.parseInt(commands[1]), Integer.parseInt(commands[2])));
                        break;
                    case "reduce":
                        System.out.println(rbTree.reduce(Integer.parseInt(commands[1]), Integer.parseInt(commands[2])));
                        break;
                    case "count":
                        System.out.println(rbTree.count(Integer.parseInt(commands[1])));
                        break;
                    case "inrange":
                        System.out.println(rbTree.inRange(Integer.parseInt(commands[1]), Integer.parseInt(commands[2])));
                        break;
                    case "next":
                        rbTree.findNext(Integer.parseInt(commands[1]));
                        break;
                    case "previous":
                        rbTree.findPrevious(Integer.parseInt(commands[1]));
                        break;
                    default:
                        System.out.println("\nInvalid command: '" + command + "' ! Enter a valid command or enter 'quit' to exit. ");
                        break;
                }
                inputCommand = sc.nextLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println(
                    "Unable to locate the file '" +
                            fileName + "'");
        }catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }
    }


    public static void main(String[] args) {
        //Create an object of the bbst class
        bbst bst = new bbst();
        if (args.length > 0) {
            String inputFileName = args[0];
            bst.readInputFile(inputFileName);
        }
    }

}