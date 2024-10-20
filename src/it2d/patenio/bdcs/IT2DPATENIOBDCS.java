
package it2d.patenio.bdcs;

import java.util.Scanner;

public class IT2DPATENIOBDCS {

   
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit=true;
        
        do{
        System.out.println("\nWELCOME TO BARANGAY DOCUMENT REQUESTING SYSTEM");
        System.out.println("\nMENU:");
        System.out.println("1. Add Resident");
        System.out.println("2. Add Type of Document");
        System.out.println("3. Document Request");
        System.out.println("4. View Individual Report ");
        System.out.println("5. Exit");
        
        
        System.out.print("Enter Action: ");
        int act= sc.nextInt();
        
        switch(act){
            case 1:
                Resident rs= new Resident();
                rs.addResident();
                break;
            case 2:
                TypeofDocument td=new TypeofDocument();
                td.addTypeofDocument();
                break;
            case 3:
                DocumentRequest dr= new DocumentRequest();
                dr.addDocumentRequest();
                break;
            case 4:
                break;
            case 5:
                    System.out.print("Exiting...Are you sure? (yes/no): ");
                    String resp= sc.next();
                    if(resp.equalsIgnoreCase("yes")){
                        exit=false;
                    }
                    break;

             default:
                    System.out.println("Invalid option. Please try again.");
                    break;
        }       
        }while (exit);
        System.out.println("Thank you, come again!");
    }
}
            
