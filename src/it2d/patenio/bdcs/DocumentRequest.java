
package it2d.patenio.bdcs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentRequest {
    public void addDocumentRequest() {
    Scanner sc = new Scanner(System.in);
    String response;
    boolean exit = true;

    do {
        System.out.println("\nDOCUMENT REQUEST MENU:");
        System.out.println("1. ADD");
        System.out.println("2. VIEW");
        System.out.println("3. UPDATE");
        System.out.println("4. DELETE");
        System.out.println("5. EXIT");

        System.out.print("Enter Action: ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number between 1 and 5: ");
            sc.next(); 
        }
        int action = sc.nextInt();
        DocumentRequest dr = new DocumentRequest();

        switch (action) {
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
                String resp = sc.next();
                
                 while (!(resp.equalsIgnoreCase("yes") || resp.equalsIgnoreCase("no"))) {
                    System.out.println("Invalid input. Please enter 'yes' or 'no': ");
                    System.out.print("Do you want to make another transaction? (yes/no): ");
                    resp = sc.next();
                }
                if (resp.equalsIgnoreCase("yes")) {
                    exit = false;
                }
                break;

            default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }

            if (action >= 1 && action <= 4) {
                System.out.print("Do you want to make another transaction? (yes/no): ");
                response = sc.next();

                while (!(response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no"))) {
                    System.out.println("Invalid input. Please enter 'yes' or 'no': ");
                    response = sc.next();
                }

              
                if (response.equalsIgnoreCase("no")) {
                    exit = false;
                }
            }

        } while (exit); 

        System.out.println("Thank you for using!");
    }

public void addDocreqs() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();
    
    
    Resident rs = new Resident();
    rs.viewResidents();
    
    System.out.print("Enter the ID of the Selected Resident: ");
    int rid = sc.nextInt();
    
    String rsql = "SELECT r_id FROM tbl_resident WHERE r_id=?";
    while (conf.getSingleValue(rsql, rid) == 0) {
        System.out.print("Resident ID not exist! Try again: ");
        rid = sc.nextInt();
    }
    
    
    TypeofDocument dr = new TypeofDocument();
    dr.viewDoctype();
                 
    System.out.print("Enter the ID of the Selected Type of Document: ");
    int tid = sc.nextInt();
    
    String tsql = "SELECT t_id FROM tbl_tdocument WHERE t_id=?";
    while (conf.getSingleValue(tsql, tid) == 0) {
        System.out.print("T.O.D ID not exist! Try again: ");
        tid = sc.nextInt();
    }

    
    System.out.print("Number of Copies: ");
    while (!sc.hasNextDouble()) {
        System.out.print("Invalid input. Please enter a valid number of copies:");
        sc.next();
    }
    double numcop = sc.nextDouble();
    if (numcop <= 0) {
        System.out.print("Number of copies must be greater than zero: ");
        return;
    }
    
    
    String priceq = "SELECT t_price FROM tbl_tdocument WHERE t_id=?";
    double price = conf.getSingleValue(priceq, tid);
    double cashtor = price * numcop;

    System.out.println("--------------------");
    System.out.println("Total: " + cashtor);
    System.out.println("--------------------");

  
    System.out.print("Enter the received cash: ");
    double cash = sc.nextDouble();

    while (cash < cashtor) { 
        System.out.print("The received cash is insufficient. Try again: ");
        cash = sc.nextDouble();
    }

    System.out.println("------------------------------");
    System.out.println("Cash Received: " + cash);
    System.out.println("Change: " + (cash - cashtor));
    System.out.println("------------------------------");

  
    String rdate = "";
    boolean validDate = false;
    String datePattern = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/(\\d{4})$";
    Pattern pattern = Pattern.compile(datePattern);

    while (!validDate) {
        System.out.print("Release Date (MM/DD/YYYY): ");
        sc.nextLine(); 
        rdate = sc.nextLine();
        
        Matcher matcher = pattern.matcher(rdate);
        if (matcher.matches()) {
           
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            try {
                LocalDate releaseDate = LocalDate.parse(rdate, formatter);
                LocalDate minReleaseDate = LocalDate.now().plusDays(3); 
                if (releaseDate.isBefore(minReleaseDate)) {
                    System.out.println("Release date must be at least 3 days from today: " + minReleaseDate.format(formatter));
                } else {
                    validDate = true; 
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use MM/DD/YYYY.");
            }
        } else {
            System.out.println("Invalid date format. Please use MM/DD/YYYY.");
        }
    }

  
    String sql = "INSERT INTO tbl_documentRequest (r_id, t_id, dr_numcop, dr_cash, dr_cashtor, dr_date, dr_status, dr_releasedate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    conf.addDocreq(sql, rid, tid, numcop, cash, cashtor, LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), "Pending", rdate);
}

private void viewDocreqs() {
    config conf = new config();

    String reqQuery = "SELECT tbl_documentRequest.dr_id, tbl_resident.r_id, tbl_tdocument.t_name, tbl_documentRequest.dr_status "
            + "FROM tbl_documentRequest "
            + "LEFT JOIN tbl_resident ON tbl_resident.r_id = tbl_documentRequest.r_id "
            + "LEFT JOIN tbl_tdocument ON tbl_tdocument.t_id = tbl_documentRequest.t_id";

    String[] reqHeaders = {"Document Request ID", "Resident ID", "Type of Document", "Status"};
    String[] reqColumns = {"dr_id", "r_id", "t_name", "dr_status"};

    conf.viewDocreq(reqQuery, reqHeaders, reqColumns);
}



private void updateDocreqs() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    System.out.print("Enter Document Request ID to update: ");
    while (!sc.hasNextInt()) {
        System.out.print("Invalid input. Please enter a valid Document Request ID: ");
        sc.next();
        System.out.print("Enter Document Request ID to update: ");
    }
    int id = sc.nextInt();

    System.out.print("New Status (Pending/Approved): ");
    String status = sc.next();

    System.out.print("New Release Date (MM/DD/YYYY): ");
    String date = sc.next();

    String sql = "UPDATE tbl_documentRequest SET dr_releasedate = ? WHERE dr_id = ?";
    conf.updateDocreq(sql, status, date, id);
}


public void deleteDocreqs() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    while (true) {
        System.out.print("Enter Document Request ID to delete: ");

        while (!sc.hasNextInt()) {
            System.out.println("Invalid input! Please enter a valid Document Request ID: ");
            sc.next();
            System.out.print("Enter Document Request ID to delete: ");
        }

        int id = sc.nextInt();
        if (!conf.documentExists(id)) {
            System.out.println("Document Request ID " + id + " does not exist.");
            return;
        }

        if (id <= 0) {
            System.out.print("Invalid Document Request ID. Please enter a positive integer.");
            continue;
        }

        String sql = "DELETE FROM tbl_documentRequest WHERE dr_id = ?";
        conf.deleteDocreq(sql, id);
        System.out.println("Document Request with ID " + id + " has been deleted.");

        break;
    }
}
}
