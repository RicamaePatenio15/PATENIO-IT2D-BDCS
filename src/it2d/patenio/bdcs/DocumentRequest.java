
package it2d.patenio.bdcs;

import java.util.Scanner;

public class DocumentRequest {
     public void addDocumentRequest(){
    
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
        DocumentRequest dr=new DocumentRequest();
        
        switch(action){
            case 1:
                dr.addDocreqs();               
                break;
                
            case 2:
                dr.viewDocreqs();                 
                break;
                
            case 3:
                dr.viewDocreqs();   
                dr.updateDocreqs();
                dr.viewDocreqs();
                break;
                
            case 4:
                dr.viewDocreqs();
                dr.deleteDocreqs();
                dr.viewDocreqs();
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

     public void addDocreqs(){
        Scanner sc = new Scanner(System.in);
        
        config conf = new config();
        
         System.out.print("Date: ");
         String date= sc.nextLine();
         
         System.out.print("Document Status: ");
         String docstatus=sc.nextLine();
         
         System.out.print("Release Date: ");
         String rdate= sc.nextLine();

        String sql = "INSERT INTO tbl_documentRequest (dr_date, dr_status, dr_releasedate) VALUES (?, ?, ?)";


        conf.addDocreq(sql, date, docstatus, rdate);

    }
      
     private void viewDocreqs() {
         config conf= new config();
         
        String reqQuery = "SELECT * FROM tbl_documentRequest";
        String[] reqHeaders = {"Date", "Document Status", "Expected Release Date"};
        String[] reqColumns = {"dr_date", "dr_status", "dr_releasedate"};

        conf.viewDocreq(reqQuery, reqHeaders, reqColumns);
    }
    
     private void updateDocreqs(){

        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Resident ID to update: ");
        int id = sc.nextInt();

         System.out.println("New Status: ");
         String status= sc.next();
         
         System.out.println("New Release Date: ");
         String date= sc.next();
        

        String sql = "UPDATE tbl_documentRequest SET dr_status, dr_releasedate = ? WHERE dr_id = ?";
        conf.updateDocreq(sql, status, date); //parameterize ang name sa question mark
    }
      public void deleteDocreqs() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Resident ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM tbl_documentRequest WHERE r_id = ?";
        conf.deleteDocreq(sql, id);
    }
      
}
    


    


    


    
