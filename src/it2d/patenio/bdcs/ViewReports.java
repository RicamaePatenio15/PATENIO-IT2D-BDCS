package it2d.patenio.bdcs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ViewReports {    
    public void viewReport(){
        Scanner sc = new Scanner(System.in);
        String response;
        boolean exit = true;
         
        
        do{
            
            System.out.println("WELCOME TO VIEW REPORT MENU:");
            System.out.println("1. VIEW GENERAL REPORTS");
            System.out.println("2. VIEW INDIVIDUAL REPORTS");
            System.out.println("3. EXIT");
            
            System.out.print("Enter Action: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                sc.next(); 
                
                System.out.print("Enter Action: ");
            }
            int action = sc.nextInt();
            ViewReports vr = new ViewReports();
        
            switch (action) {
                case 1:
                   vr.GeneralReport();
                    break;

                case 2:
                    vr.GeneralReport();
                    vr.IndividualReport();
                    break;

                 case 3:
                    System.out.print("Exiting...Are you sure? (yes/no): ");
                    String resp = sc.next();
                    if (resp.equalsIgnoreCase("yes")) {
                        exit = false;
                    }
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
              if (action >= 1 && action <= 2) {
            System.out.print("Do you want to make another transaction? (yes/no): ");
            response = sc.next();
            if (!response.equalsIgnoreCase("yes")) {
                exit = false;
            }
        }
        } while (exit);
    }
    
   public void GeneralReport() {
    config conf = new config();  

           System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
           System.out.println("                                                GENERAL REPORT                                                                                ");
           System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");
     
     
    
String reqQuery = "SELECT tbl_resident.r_id, tbl_tdocument.t_name, tbl_documentRequest.dr_numcop, "
                + "tbl_documentRequest.dr_status, tbl_documentRequest.dr_date "
                + "FROM tbl_documentRequest "
                + "LEFT JOIN tbl_resident ON tbl_resident.r_id = tbl_documentRequest.r_id "
                + "LEFT JOIN tbl_tdocument ON tbl_tdocument.t_id = tbl_documentRequest.t_id";

String[] reqHeaders = {"Resident ID", "Type of Document", "Number of Copies", "Status", "Request Date"};

String[] reqColumns = {"r_id", "t_name", "dr_numcop", "dr_status", "dr_date"};


    conf.viewDocreq(reqQuery, reqHeaders, reqColumns);
}


   public void IndividualReport() {
    config conf = new config();
    Scanner sc = new Scanner(System.in); 
    
    int residentId = -1;


    while (residentId <= 0) {
        System.out.print("Enter Resident ID to view their transaction history: ");
        
        // Check if the input is a valid integer
        if (sc.hasNextInt()) {
            residentId = sc.nextInt();
            if (residentId <= 0) {
                System.out.println("Please enter a valid positive Resident ID.");
            }
        } else {
            System.out.println("Invalid input. Please enter a valid Resident ID (a positive number).");
            sc.next(); // Consume the invalid input
        }
    }

    // SQL query to fetch transaction details for a specific resident
    String reqQuery = "SELECT tbl_resident.r_id, tbl_resident.r_fname, tbl_resident.r_lname, tbl_resident.r_address, "
                    + "tbl_tdocument.t_name, tbl_documentRequest.dr_numcop, tbl_documentRequest.dr_cashtor, "
                    + "tbl_documentRequest.dr_status, tbl_documentRequest.dr_date, tbl_documentRequest.dr_releasedate "
                    + "FROM tbl_documentRequest "
                    + "LEFT JOIN tbl_resident ON tbl_resident.r_id = tbl_documentRequest.r_id "
                    + "LEFT JOIN tbl_tdocument ON tbl_tdocument.t_id = tbl_documentRequest.t_id "
                    + "WHERE tbl_resident.r_id = ?";

    // Get the data from the database for the specific resident
    ResultSet resultSet = conf.getData(reqQuery, residentId);

           System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
           System.out.println("                                                      INDIVIDUAL REPORT                                                                       ");
           System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");

    // Initialize variables to store resident info
    String fname = "";
    String lname = "";
    String address = "";

    try {
        // Fetch the resident's information
        if (resultSet.next()) {
            fname = resultSet.getString("r_fname");
            lname = resultSet.getString("r_lname");
            address = resultSet.getString("r_address");
        } else {
            System.out.println("No transactions found for Resident ID " + residentId);
            return;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return;
    }

    // Display the resident's details
    System.out.println("Resident ID: " + residentId);
    System.out.println("Name: " + fname + " " + lname);
    System.out.println("Address: " + address);
    System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------|");

    // Table header for the transaction details with increased column widths by ~30%
    String header = String.format("%-26s%-29s%-26s%-26s%-29s%-29s", 
                                  "Document Type", "Number of Copies", "Amount Paid", "Status", "Request Date", "Release Date");
    System.out.println(header);
    System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------|");

    // Now loop through the resultSet to fetch and display the transactions
    try {
        do {
            String docType = resultSet.getString("t_name");
            int numCopies = resultSet.getInt("dr_numcop");
            double cashPaid = resultSet.getDouble("dr_cashtor");
            String status = resultSet.getString("dr_status");
            String requestDate = resultSet.getString("dr_date");
            String releaseDate = resultSet.getString("dr_releasedate");

            // Format and display each transaction in the report
            String row = String.format("%-26s%-29d%-26.2f%-26s%-29s%-29s", 
                                       docType, numCopies, cashPaid, status, requestDate, releaseDate);
            System.out.println(row);

        } while (resultSet.next());
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}