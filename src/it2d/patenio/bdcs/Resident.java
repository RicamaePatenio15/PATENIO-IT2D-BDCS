
package it2d.patenio.bdcs;

import java.util.Scanner;


public class Resident {
    
    public void addResident(){
    
        Scanner sc = new Scanner(System.in);
        String response;
        boolean exit=true;
        
        do{
        System.out.println("1. ADD");
        System.out.println("2. VIEW");
        System.out.println("3. UPDATE");
        System.out.println("4. DELETE");
        System.out.println("5. EXIT");
        
        
        System.out.print("Enter Action: ");
        int action= sc.nextInt();
        Resident resident=new Resident();
        
        switch(action){
            case 1:
                resident.addResidents();               
                break;
                
            case 2:
                resident.viewResidents();                 
                break;
                
            case 3:
                resident.viewResidents();   
                resident.updateResidents();
                resident.viewResidents();
                break;
                
            case 4:
                resident.viewResidents();
                resident.deleteResidents();
                resident.viewResidents();
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
         System.out.print("Do you want to continue? (yes/no): ");
         response=sc.next();
        }while(response.equalsIgnoreCase("yes"));
         System.out.print("Thank you, See you soonest!\n"); 
    }

     public void addResidents(){
        Scanner sc = new Scanner(System.in);
        
        config conf = new config();
        
        System.out.print("First Name: ");
        String fname = sc.nextLine();
        System.out.print("Last Name: ");
        String lname = sc.nextLine();
        System.out.print("Address: ");
        String address=sc.nextLine();
        System.out.print("Civil Status: ");
        String status = sc.nextLine();

        String sql = "INSERT INTO tbl_resident (r_fname, r_lname, r_address, r_cstatus) VALUES (?, ?, ?, ?)";


        conf.addResident(sql, fname, lname, address, status);

    }
      
     private void viewResidents() {
         config conf= new config();
         
        String residentQuery = "SELECT * FROM tbl_resident";
        String[] residentHeaders = {"Resident", "First Name", "Last Name", "Address", "Status"};
        String[] residentColumns = {"r_id", "r_fname", "r_lname", "r_address", "r_cstatus"};

        conf.viewResident(residentQuery, residentHeaders, residentColumns);
    }
    
     private void updateResidents(){

        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Resident ID to update: ");
        int id = sc.nextInt();

        System.out.print("New First Name: ");
        String fname = sc.nextLine() ;
        System.out.print("New Last Name: ");
        String lname = sc.nextLine();
        System.out.print("New Address: ");
        String address = sc.nextLine();
        System.out.print("New Civil Status: ");
        String status = sc.next();

        String sql = "UPDATE tbl_resident SET r_fname = ?, r_lname = ?, r_address = ?, r_cstatus = ? WHERE r_id = ?";
        conf.updateResident(sql, fname, lname, address, status, id); //parameterize ang name sa question mark
    }
      public void deleteResidents() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Resident ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM tbl_resident WHERE r_id = ?";
        conf.deleteResident(sql, id);
    }
      
}
    


    

