package it2d.patenio.bdcs;

import java.util.Scanner;

public class IT2DPATENIOBDCS {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = true;

        do {
            System.out.println("\nWELCOME TO BARANGAY DOCUMENT REQUESTING SYSTEM");
            System.out.println("\nMENU:");
            System.out.println("1. Add Resident");
            System.out.println("2. Add Type of Document");
            System.out.println("3. Document Request");
            System.out.println("4. View Individual Report ");
            System.out.println("5. Exit");

            System.out.print("Enter Action: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                sc.next(); 
                System.out.print("Enter Action: ");
            }
            int act = sc.nextInt();

            if (act < 1 || act > 5) {
                System.out.println("Invalid option. Please try again.");
                continue; 
            }

            switch (act) {
                case 1:
                    Resident rs = new Resident();
                    rs.addResident();
                    break;
                case 2:
                    TypeofDocument td = new TypeofDocument();
                    td.addTypeofDocument();
                    break;
                case 3:
                    DocumentRequest dr = new DocumentRequest();
                    dr.addDocumentRequest();
                    break;
                case 4:
                    ViewReports vr= new ViewReports();
                    vr.viewReport();
                    break;
                case 5:
                    System.out.print("Exiting...Are you sure? (yes/no): ");
                    String resp = sc.next();
                     while (!(resp.equalsIgnoreCase("yes") || resp.equalsIgnoreCase("no"))) {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                    System.out.print("Do you want to make another transaction? (yes/no): ");
                    resp = sc.next();
                }
                    if (resp.equalsIgnoreCase("yes")) {
                        exit = false;
                    }
                    break;
            }
        } while (exit);
        System.out.println("Thank you, come again!");
    }
}
