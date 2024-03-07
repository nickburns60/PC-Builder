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

        //Introduce program to user
        System.out.println("                           *** Welcome to the PC builder program ***");
        System.out.println("This application is designed to help a user pick all the pieces they need to build their own Gaming PC");
        System.out.println("We will provide options, important info for each choice, and price points for each part needed to complete the " +
                "build, and keep a running total of the cost of the selected parts.");

        //Create a cart to put all selected parts into
        Cart partCart = new Cart();

        //Loops through each component file for the user to view and select from
        for (File pcComponent : pcComponents) {

            //Informs user what part they will be picking next
            System.out.println("Press enter to view our " + pcComponent.getName() + " options");
            //Makes sure program doesn't print file contents until user is ready
            Scanner input = new Scanner(System.in);
            input.nextLine();
            //Opens current file in file list
            try (Scanner fileReader = new Scanner(pcComponent)) {
                //Initializes line number
                int lineNumber = 0;

                //Loops until the file has no more lines
                while (fileReader.hasNextLine()) {
                    String lineOfInput = fileReader.nextLine();

                    //Ensures only selectable lines have a line number attached
                    if (lineOfInput.contains("0")) {
                        //if else used to swap colors each line to make file easier to read
                        if (lineNumber % 2 == 0) {
                            System.out.println(ANSI_CYAN + lineNumber + ":      " + lineOfInput + ANSI_RESET);
                        } else {
                            System.out.println(ANSI_GREEN + lineNumber + ":      " + lineOfInput + ANSI_RESET);
                        }

                        //If line is not selectable, it prints without a number and in cyan
                    } else {
                        System.out.println(ANSI_CYAN + "      " + lineOfInput + ANSI_RESET);
                    }
                    //Increments line number
                    lineNumber++;
                }
            } catch (FileNotFoundException e) {
                System.err.println("The file does not exist");
            }

            //Initializes lineNotFound to ensure loop starts correctly
            boolean lineNotFound = true;
            System.out.println("Please enter the line number of the part you would like to select");
            //Loops until usable line is selected
            while (lineNotFound){
                try {
                    //sets users line number to variable
                    int partLineSelected = Integer.parseInt(input.nextLine());
                    //if()
                    //reads entire current file and sets selected line to string variable
                    String lineSelected = Files.readAllLines(pcComponent.toPath()).get(partLineSelected);
                    //splits variable into separate parts, each one is its own specification
                    String[] specs = lineSelected.split("\\|");

                    //if else statements to catch and add specs to cart
                    if(pcComponent.getName().equals("Processor")){
                        ProcessorForBuild cpu = new ProcessorForBuild(specs[1], specs[2], specs[3], Double.parseDouble(specs[4]));
                        partCart.add(cpu);
                    } else if (pcComponent.getName().equals("GraphicsCard")) {
                        GraphicsCardForBuild gpu = new GraphicsCardForBuild(specs[1], Integer.parseInt(specs[3]), Integer.parseInt(specs[4]), Integer.parseInt(specs[5])
                                , Double.parseDouble(specs[6]));
                        partCart.add(gpu);

                    } else if (pcComponent.getName().equals("Motherboard")) {
                        MoboForBuild moboSelected = new MoboForBuild(specs[1], specs[2], specs[3], specs[4], Double.parseDouble(specs[5]));
                        //Creates an instance of ProcessorBuild to compare socket spec with mobo selection, if they don't match, compatibility exception is thrown
                        String processorSpecs = Files.readAllLines(selections.toPath()).get(0);
                        String[] processorSelected = processorSpecs.split("\\|");
                        ProcessorForBuild cpu = new ProcessorForBuild(processorSelected[1], processorSelected[2], processorSelected[3], Double.parseDouble(processorSelected[4]));
                        moboSelected.compatible(cpu);
                        partCart.add(moboSelected);

                    } else if (pcComponent.getName().equals("Memory")) {
                        RAMForBuild ramSelected = new RAMForBuild(specs[1], specs[2], Double.parseDouble(specs[6]));
                        //Creates an instance of MoboForBuild to compare RAM type spec with RAM selection, if they don't match, compatibility exception is thrown
                        String moboSpecs = Files.readAllLines(selections.toPath()).get(2);
                        String[] moboSelected = moboSpecs.split("\\|");
                        MoboForBuild mobo = new MoboForBuild(moboSelected[1], moboSelected[2], moboSelected[3], moboSelected[4], Double.parseDouble(moboSelected[5]));
                        ramSelected.compatible(mobo);
                        partCart.add(ramSelected);

                    } else if (pcComponent.getName().equals("SSD")) {
                        SSDForBuild ssdSelected = new SSDForBuild(specs[1], Double.parseDouble(specs[4]));
                        partCart.add(ssdSelected);
                    } else if (pcComponent.getName().equals("PowerSupply")) {
                        PSUForBuild psuSelected = new PSUForBuild(specs[1], Integer.parseInt(specs[2]), Double.parseDouble(specs[5]));
                        //Creates an instance of GraphicsCardForBuild to compare Wattage spec of gpu with power supply selection, if they don't match, exception is thrown
                        String gpuSpecs = Files.readAllLines(selections.toPath()).get(1);
                        String[] gpuSelected = gpuSpecs.split("\\|");
                        GraphicsCardForBuild gpu = new GraphicsCardForBuild(gpuSelected[1], Integer.parseInt(gpuSelected[3]), Integer.parseInt(gpuSelected[4]),
                                Integer.parseInt(gpuSelected[5]), Double.parseDouble(gpuSelected[6]));
                        psuSelected.compatible(gpu);
                        partCart.add(psuSelected);

                    } else if (pcComponent.getName().equals("Case")) {
                        CaseForBuild caseSelected = new CaseForBuild(specs[1], specs[2], Integer.parseInt(specs[5]), Integer.parseInt(specs[6]), Double.parseDouble(specs[8]));
                        partCart.add(caseSelected);
                    } else if (pcComponent.getName().equals("CPU Cooler")) {
                        CPUCoolerForBuild coolerSelected = new CPUCoolerForBuild(specs[1], Integer.parseInt(specs[3]), Double.parseDouble(specs[5]));
                        partCart.add(coolerSelected);
                    }else{
                        FansForBuild fanSelected = new FansForBuild(specs[1], Integer.parseInt(specs[2]), Integer.parseInt(specs[3]), Double.parseDouble(specs[6]));
                        partCart.add(fanSelected);
                    }
                    //Confirms user selected a usable line and ends while loop for current part
                    lineNotFound = false;
                    //Informs user the part they selected is added to their cart
                    System.out.println(specs[1] + ", which costs $" + specs[specs.length - 1] + ", has been added to your cart");
                    System.out.println();


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
        //Resets selections file for next use
        try (PrintWriter userSelections = new PrintWriter(selections)) {
        }catch (FileNotFoundException e){
            System.err.println("Unable to open file for writing");
        }
    }
}