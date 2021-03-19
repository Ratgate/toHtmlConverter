import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;


public class Converter {
// HANDLING
//      EVERY CHAR
// 1 - 2 chars open/close mark - check, if have closing, then enclose text with marks
// 2 - 1 char open/close mark - check next char, then do stuff above
//      CUSTOM/UNUSUAL HANDLING
// 3 - 3 x 1 char no closing mark trimming line in 2
// 4 - [Whole Line] [Not Expendable] 1st 1 char no closing mark - makes indexed headliner, needs a index variable
// 5 - [Whole Line] [Not Expendable] 1st char opening for 3 x 1 char no closing mark trimming line in 3
// 6 - [Whole Line] [Not Expendable] [Not in Mark[]] default option - slap <p> and </p>

    public static Mark[] marksTemplate = {
            new Mark(">>", "", "<<", 1, "<q>", "", "</q>"),
        new Mark("*", "", "*", 2, "<em>", "", "</em>"),
        new Mark("**", "", "**", 1, "<strong>", "", "</strong>"),
        new Mark("_!", "", "!_", 1, "<ins>", "", "</ins>"),
        new Mark("-!", "", "!-", 1, "<del>", "", "</del>"),
        new Mark("[", "|", "]", 3, "<a href=\"", "\">", "</a>"),
        new Mark("#", "", "", 4, "<h1 id=\"", "\">", "</h1>"),
        new Mark("{", "|", "}", 5, "", "", ""),
        new Mark("", "", "", 6, "<p>", "", "</p>")
    };


    static Integer header = 1;

    static LinkedList<Mark> marksStock = new LinkedList<Mark>();

    public static void main(String[] args) throws FileNotFoundException {

        for (Mark smark : marksTemplate) {
            System.out.println(smark.toString() + "\n");
        }





        try {
            File source = new File("source.txt");
            Scanner readStuff = new Scanner(source);
            FileWriter writeStuff = new FileWriter("result.html");
            while (readStuff.hasNextLine()){
                String data = readStuff.nextLine();
                writeStuff.write(convertMarks(data) + "\n");
            }
            readStuff.close();
            writeStuff.close();
        } catch (FileNotFoundException e){
            System.out.println("Błąd przy czytaniu pliku");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Błąd przy zapisywaniu pliku");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertMarks(String intake) throws Exception {
        Boolean firstCharDetected = false;
        if(intake.isEmpty()){
            return intake;
        }
        String result = "";
        Character zeroth = intake.charAt(0);

        for (Mark markTemplate:marksTemplate) {
            System.out.println("firstChar " + markTemplate.firstChar + " vs zeroth " + zeroth);
            if(Character.toString(zeroth).equals(markTemplate.firstChar)  && ((markTemplate.handling == 4) || (markTemplate.handling == 5))){
                System.out.println("Chosen firstChar " + markTemplate.firstChar + " for zeroth " + zeroth);
                firstCharDetected = true;
                switch(markTemplate.handling){
                    case 4: {
                        // case #text
                        result += "<h1 id=\"" + header + "\">";
                        marksStock.addLast(markTemplate);
                        header++;
                        intake = intake.substring(1);
                        break;
                    }
                    case 5: {
                        //case {type|title}text
                        if(intake.contains("|") && intake.contains("}") && (intake.indexOf("|") < intake.indexOf("}"))){
                            result += "<aside cat=\""
                                    + intake.substring(intake.indexOf("{") + 1, intake.indexOf("|"))
                                    + "\"><header>"
                                    + intake.substring(intake.indexOf("|") + 1, intake.indexOf("}"))
                                    + "</header><main>"
                                    + intake.substring(intake.indexOf("}") + 1)
                                    + "</main></aside>";
                            return result;
                        } else {
                            firstCharDetected = false;
                        }
                    }
                }
                break;
            }
        }
        if(!firstCharDetected){
            System.out.println("Then <p> it is");
            marksStock.addLast(new Mark("", "", "", 6, "<p>", "", "</p>"));
            result += "<p>";
        }
        System.out.println("\n");

        for(Integer i = 0; i < intake.length(); i++){
            Character selected = intake.charAt(i);

            if(">*_-<![".contains(selected.toString())){
                switch (selected.toString()){
                    case ">" : {
                        break;
                    }
                    case "*" : {
                        break;
                    }
                    case "_" : {
                        break;
                    }
                    case "-" : {
                        break;
                    }
                    case "<" : {
                        break;
                    }
                    case "!" : {
                        break;
                    }
                    case "[" : {
                        break;
                    }
                }




//                System.out.println(selected);
            }
            result += selected;
        }

        //Tu muszę zrobić rozliczenie z markStock, czy jest pusty i z <p>
            if(marksStock.getLast().firstHtml != "<p>" && !marksStock.isEmpty()){
                throw new Exception("Line has ended without successfully resolving markStock. Remaining marks: " + marksStock.toString());
            } else {
                result  += marksStock.getLast().lastHtml;
                return result;
            }
    }
}
