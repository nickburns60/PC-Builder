package org.PCBuilder;

import javax.annotation.processing.Processor;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Colors to be used on lines for files
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_CYAN = "\u001B[36m";

        //Create file objects for each file that will be shown to the user
        File PCCase = new File("PCPartFiles/Case");
        File cpuCooler = new File("PCPartFiles/CPU Cooler");
        File fans = new File("PCPartFiles/Fans");
        File graphicsCard = new File("PCPartFiles/GraphicsCard");
        File memory = new File("PCPartFiles/Memory");
        File motherboard = new File("PCPartFiles/Motherboard");
        File powerSupply = new File("PCPartFiles/PowerSupply");
        File processor = new File("PCPartFiles/Processor");
        File drive = new File("PCPartFiles/SSD");

        //Create file object to hold all user part selections
        File selections = new File("PCPartFiles/Selections");
        //Create file object to hold all compatible parts for user to select from
        File compatibleChoices = new File("PCPartFiles/Compatible");

        //Make a list of all files to be used
        List<File> pcComponents = new ArrayList<>();
        pcComponents.add(processor);
        pcComponents.add(graphicsCard);
        pcComponents.add(motherboard);
        pcComponents.add(memory);
        pcComponents.add(drive);
        pcComponents.add(powerSupply);
        pcComponents.add(PCCase);
        pcComponents.add(cpuCooler);
        pcComponents.add(fans);

        //Makes sure File to hold users items is empty
        try (PrintWriter userSelections = new PrintWriter(selections)) {
        }catch (FileNotFoundException e){
            System.err.println("Unable to open file for writing");
        }
        //Introduce program to user
        System.out.println("                           *** Welcome to the PC builder ***");
        System.out.println("This application is designed to help a user pick all the pieces they need to build their own Gaming PC");
        System.out.println("We will provide options, important info for each choice, price points for each part needed to complete the " +
                "build, all while making sure your parts are compatible with each other.");

        //Create a cart to put all selected parts into
        Cart partCart = new Cart();

        //Loops through each component file for the user to view and select from
        for (File pcComponent : pcComponents) {

            //Informs user what part they will be picking next
            System.out.println("Press enter to view our " + pcComponent.getName() + " options");
            //Makes sure the program doesn't print file contents until user is ready
            Scanner input = new Scanner(System.in);
            input.nextLine();

            //Opens current file in file list
            try (Scanner fileReader = new Scanner(pcComponent)) {
                //Initializes line number for current part
                int lineNumber = 0;

                //Loops until the file has no more lines
                while (fileReader.hasNextLine()) {
                    String lineOfInput = fileReader.nextLine();
                    //If line does not contain a selectable option, it is not printed
                    if(!lineOfInput.contains("0")){
                        continue;
                    }
                    //Filters out any motherboards not compatible with selected processor
                    if(pcComponent.getName().equals("Motherboard")){
                        //reads entire written file and sets selected line that matches needed part to string variable
                        String processorSpecs = Files.readAllLines(selections.toPath()).get(0);
                        //Splits info from processor user previously selected into an array
                        String[] processorSelected = processorSpecs.split("\\|");
                        //Uses array to create a new processor variable
                        ProcessorForBuild cpu = new ProcessorForBuild(processorSelected[1], processorSelected[2], processorSelected[3], Double.parseDouble(processorSelected[4]));
                        //Separates the current line of motherboard info into an array
                        String[] specsOfLine = lineOfInput.split("\\|");
                        //Checks to see if the current lines socket info matches the socket info of the selected processor
                        if(specsOfLine[2].equals(cpu.getSocket())){
                            //Prints only lines for motherboards that are compatible with the processor the user selected
                            try(PrintWriter compatibleWriter = new PrintWriter(new FileOutputStream(compatibleChoices, true))){
                                compatibleWriter.println(lineOfInput);
                                LinePrinter line = new LinePrinter(lineNumber, lineOfInput);
                            }
                        }else{  //If line was not a compatible option, it the instance is ended and ine number is not incremented
                            continue;
                        }
                        //Filters out RAM not compatible with motherboard
                    } else if (pcComponent.getName().equals("Memory")) {
                        String moboSpecs = Files.readAllLines(selections.toPath()).get(2);
                        String[] moboSelected = moboSpecs.split("\\|");
                        MoboForBuild mobo = new MoboForBuild(moboSelected[1], moboSelected[2], moboSelected[3], moboSelected[4], Double.parseDouble(moboSelected[5]));
                        String[] specsOfLine = lineOfInput.split("\\|");
                        if(specsOfLine[2].equals(mobo.getCompatibleRAM())){
                            try(PrintWriter compatibleWriter = new PrintWriter(new FileOutputStream(compatibleChoices, true))){
                                compatibleWriter.println(lineOfInput);
                                LinePrinter line = new LinePrinter(lineNumber, lineOfInput);
                            }
                        }else{
                            continue;
                        }
                        //Filters out psu with less wattage than gpu recommendation
                    } else if (pcComponent.getName().equals("PowerSupply")) {
                        String gpuSpecs = Files.readAllLines(selections.toPath()).get(1);
                        String[] gpuSelected = gpuSpecs.split("\\|");
                        GraphicsCardForBuild gpu = new GraphicsCardForBuild(gpuSelected[1], Integer.parseInt(gpuSelected[3]), Integer.parseInt(gpuSelected[4]),
                                Integer.parseInt(gpuSelected[5]), Double.parseDouble(gpuSelected[6]));
                        String[] specsOfLine = lineOfInput.split("\\|");
                        if(Integer.parseInt(specsOfLine[2]) >= gpu.getRecommendedPSU()){
                            try(PrintWriter compatibleWriter = new PrintWriter(new FileOutputStream(compatibleChoices, true))){
                                compatibleWriter.println(lineOfInput);
                                LinePrinter line = new LinePrinter(lineNumber, lineOfInput);
                            }
                        }else{
                            continue;
                        }
                        //Filters out coolers that are too big to fit in selected pc case
                    } else {
                        try(PrintWriter compatibleWriter = new PrintWriter(new FileOutputStream(compatibleChoices, true))){
                            compatibleWriter.println(lineOfInput);
                            LinePrinter line = new LinePrinter(lineNumber, lineOfInput);
                        }
                    }
                    //Increments line number
                    lineNumber++;
                }
            } catch (FileNotFoundException e) {
                System.err.println("The file does not exist");
            } catch (IOException e) { //Catches issues while reading lines from the file that writes as program runs
                throw new RuntimeException(e);
            }

            //Initializes lineNotFound to ensure loop starts correctly
            boolean lineNotFound = true;
            System.out.println("Please enter the line number of the part you would like to select");
            //Loops until usable line is selected
            while (lineNotFound){
                try {
                    //sets users selected line number to variable
                    int partLineSelected = Integer.parseInt(input.nextLine());
                    //reads entire current compatible file and sets selected line to string variable, if line isn't in the file, excption is thrown
                    //and they are asked to try again
                    String  lineSelected = Files.readAllLines(compatibleChoices.toPath()).get(partLineSelected);
                    //splits string into separate parts, each one is its own specification
                    List<String> specs = List.of(lineSelected.split("\\|"));

                    //if else statements to catch and add specs to cart
                    if(pcComponent.getName().equals("Processor")){
                        ProcessorForBuild cpu = new ProcessorForBuild(specs.get(1), specs.get(2), specs.get(3), Double.parseDouble(specs.get(4)));
                        partCart.add(cpu);
                    } else if (pcComponent.getName().equals("GraphicsCard")) {
                        GraphicsCardForBuild gpu = new GraphicsCardForBuild(specs.get(1), Integer.parseInt(specs.get(3)), Integer.parseInt(specs.get(4)), Integer.parseInt(specs.get(5))
                                , Double.parseDouble(specs.get(6)));
                        partCart.add(gpu);

                    } else if (pcComponent.getName().equals("Motherboard")) {
                        MoboForBuild moboSelected = new MoboForBuild(specs.get(1), specs.get(2), specs.get(3), specs.get(4), Double.parseDouble(specs.get(5)));

                        partCart.add(moboSelected);

                    } else if (pcComponent.getName().equals("Memory")) {
                        RAMForBuild ramSelected = new RAMForBuild(specs.get(1), specs.get(2), Double.parseDouble(specs.get(6)));

                        partCart.add(ramSelected);

                    } else if (pcComponent.getName().equals("SSD")) {
                        SSDForBuild ssdSelected = new SSDForBuild(specs.get(1), Double.parseDouble(specs.get(4)));
                        partCart.add(ssdSelected);

                    } else if (pcComponent.getName().equals("PowerSupply")) {
                        PSUForBuild psuSelected = new PSUForBuild(specs.get(1), Integer.parseInt(specs.get(2)), Double.parseDouble(specs.get(5)));
                        partCart.add(psuSelected);

                    } else if (pcComponent.getName().equals("Case")) {
                        CaseForBuild caseSelected = new CaseForBuild(specs.get(1), specs.get(2), Integer.parseInt(specs.get(5)), Integer.parseInt(specs.get(6)), Double.parseDouble(specs.get(8)));
                        //Checks user selection file for motherboard selected and makes sure case is compatible with motherboard
                        String moboSpecs = Files.readAllLines(selections.toPath()).get(2);
                        String[] moboSelected = moboSpecs.split("\\|");
                        MoboForBuild mobo = new MoboForBuild(moboSelected[1], moboSelected[2], moboSelected[3], moboSelected[4], Double.parseDouble(moboSelected[5]));
                        //If case is not compatible with mobo, part compatibility exception is thrown
                        caseSelected.compatible(mobo);
                        partCart.add(caseSelected);

                    } else if (pcComponent.getName().equals("CPU Cooler")) {
                        CPUCoolerForBuild coolerSelected = new CPUCoolerForBuild(specs.get(1), Integer.parseInt(specs.get(3)), Double.parseDouble(specs.get(5)));
                        partCart.add(coolerSelected);

                    }else{
                        FansForBuild fanSelected = new FansForBuild(specs.get(1), Integer.parseInt(specs.get(2)), Integer.parseInt(specs.get(3)), Double.parseDouble(specs.get(6)));
                        partCart.add(fanSelected);
                    }
                    //Confirms user selected a usable line and ends while loop for current part
                    lineNotFound = false;
                    //Informs user the part they selected is added to their cart
                    System.out.println(specs.get(1) + ", which costs $" + specs.get(specs.size() - 1) + ", has been added to your cart");
                    System.out.println();

                    //Resets File that holds compatible parts to select for next part category
                    try(PrintWriter compatibleWriter = new PrintWriter(compatibleChoices)){
                    }catch (FileNotFoundException e){
                        System.err.println("Unable to open file for writing");
                    }

                    //adds user selections to the their selection file
                    try (PrintWriter userSelections = new PrintWriter(new FileOutputStream(selections, true))) {
                        userSelections.println(lineSelected);
                    }catch (FileNotFoundException e){
                        System.err.println("Unable to open file for writing");
                    }
                }
                //catches incorrect number selections if they are empty lines or lines with unusable info ie. title lines
                catch (IndexOutOfBoundsException | NumberFormatException e){
                    System.err.println("Number entered is not a selectable line, please try again");
                    System.out.println();
                }catch (PartCompatibilityException e){
                    System.err.println("Compatibility issue");
                    System.err.println(e.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //Prints final receipt showing all selected items, their prices, and the total cost
        System.out.println(partCart.receipt());
    }
}
