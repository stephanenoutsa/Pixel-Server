/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pixel.resource;

import com.pixel.database.MyDBHandler;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author stephnoutsa
 * 
 */

@Path("ussd")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class USSDResource {
    
    MyDBHandler dbHandler = new MyDBHandler();
    
    /**
     * Steps to follow for Jobs&Scholarships services:
     * 
     * 1. Verify if MSISDN corresponds to that of a subscriber.
     * 
     * 2. a) If it is,
     * 
     *       i) Determine the service they subscribed to.
     * 
     *       ii) Determine if it is the first time they are accessing the USSD platform.
     * 
     *           a) If it is, ask them their preferred language.
     * 
     *              i) Ask them to choose an alert category.
     * 
     *              ii) Ask them to choose a career level
     * 
     *              iii) Ask them to enter their age.
     * 
     *              iv) Ask them to select their gender.
     * 
     *              v) Ask them to enter their town of residence.
     * 
     *           b) Else present the main menu to them.
     * 
     *              i) Show the options with the different categories.
     * 
     *              ii) List the available jobs for the selected category.
     * 
     *    b) Else ask them to subscribe to a service by texting "JOBS", "SCHOLAR" or "LOVE"
     *       to our shortcode.
     * 
     * @param uriInfo
     * @return 
     */
    
    // For MTN subscribers
    @GET
    @Path("j-s/mtn")
    public String getJSParams(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        
        String phone = queryParams.getFirst("MSISDN");
        String ussdString = queryParams.getFirst("USSD_STRING");
        String serviceCode = queryParams.getFirst("serviceCode");
        
        String[] lvl = ussdString.split("\\*");
        String[] level = new String[8];
        
        /** Level definitions
         * 
         * level[0] == 
         * level[1] ==
         * 
         */        
        level[0] = lvl[0];
        try {
            level[1] = lvl[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            level[1] = "";
        }
        try {
            level[2] = lvl[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            level[2] = "";
        }
        try {
            level[3] = lvl[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            level[3] = "";
        }
        try {
            level[4] = lvl[4];
        } catch (ArrayIndexOutOfBoundsException e) {
            level[4] = "";
        }
        try {
            level[5] = lvl[5];
        } catch (ArrayIndexOutOfBoundsException e) {
            level[5] = "";
        }
        try {
            level[6] = lvl[6];
        } catch (ArrayIndexOutOfBoundsException e) {
            level[6] = "";
        }
        try {
            level[7] = lvl[7];
        } catch (ArrayIndexOutOfBoundsException e) {
            level[7] = "";
        }
        
        String response = "";
        
        try {            
            System.out.println("String is: " + ussdString);
            System.out.println("Service Code is: " + serviceCode);
            System.out.println("Phone is: " + phone);
            
            if (ussdString.equals("")) {
                System.out.println("USSD_STRING is empty");
                
                if(dbHandler.subscriberExists(phone)) {
                    // Determine the category the user subscribed to
                    /* Code to get user's category */
                    
                    // Determine if it is user's first time on platform
                    /* Code to get user's first time status */
                    boolean first = true; // This is just a placeholder
                    
                    if (first) {
                        response = "CON Veuillez choisir votre langue de préférence.\n"
                                + "Please select your preferred language.\n\n"
                                + "1. Francais\n"
                                + "2. English";
                    } else {
                        // Present main menu to user
                    }
                } else {
                    response = "END Subscribe to our service by sending a message to 8040:\n"
                            + "\"JOBS\" to receive job offers\n"
                            + "\"SCHOLAR\" to receive scholarship alerts\n"
                            + "\"LOVE\" to follow up your pregnan[cy or your child under the age"
                            + "of 6 months.\n"
                            + "(150frs/week)";
                }
            }
            
            if (!level[0].isEmpty() && !level[0].equals("") && level[1].isEmpty()) {
                
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        
        return response;
    }
    
    
    
    /**
     * Steps to follow for PES service:
     * 
     * 1. Verify if MSISDN corresponds to that of a parent/guardian.
     * 
     * 2. a) If it is, verify how many students the parent/guardian is linked to and ask
     *       them to choose the one they want to follow (NB: Choosing implies subscribing
     *       to follow that particular student...upon confirmation).
     * 
     *       i) If parent/guardian is already subscribed, the options page is displayed.
     * 
     *       ii) Else ask them to confirm subscription to follow that particular student.
     * 
     *           .) If they confirm, the options page is displayed.
     * 
     *           ..) Else, ask them to call the school to be included in the database of
     *               parents/guardians to follow a student.
     * 
     *    b) If it isn't ask them to call the school to be included in the database of
     *       parents/guardians to follow a student.
     * 
     * @param uriInfo
     * @return 
     */
    @GET
    @Path("pes")
    public String getPESParams(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        
        String phone = queryParams.getFirst("MSISDN");
        String ussdString = queryParams.getFirst("USSD_STRING");
        String serviceCode = queryParams.getFirst("serviceCode");
        
        String[] studentIds;
        
        String[] lvl = ussdString.split("\\*");
        String[] level = new String[8];
        
        /** Level definitions
         * 
         * level[0] == selectedStudent
         * level[1] == selectedOption
         * level[2] == nextSelectedOption
         * level[3] == yetAnotherSelectedOption
         * 
         */
        level[0] = lvl[0];
        try {
            level[1] = lvl[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            level[1] = "";
        }
        try {
            level[2] = lvl[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            level[2] = "";
        }
        try {
            level[3] = lvl[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            level[3] = "";
        }
        try {
            level[4] = lvl[4];
        } catch (ArrayIndexOutOfBoundsException e) {
            level[4] = "";
        }
        try {
            level[5] = lvl[5];
        } catch (ArrayIndexOutOfBoundsException e) {
            level[5] = "";
        }
        try {
            level[6] = lvl[6];
        } catch (ArrayIndexOutOfBoundsException e) {
            level[6] = "";
        }
        try {
            level[7] = lvl[7];
        } catch (ArrayIndexOutOfBoundsException e) {
            level[7] = "";
        }
        
        String response = "";
        
        try {
            System.out.println("String is: " + ussdString);
            System.out.println("Service Code is: " + serviceCode);
            System.out.println("Phone is: " + phone);
            
            if (ussdString.equals("")) {
                System.out.println("USSD_STRING is empty string");
                
                // Verify if phone number belongs to parent/guardian
                if (dbHandler.isParent(phone)) {
                    // Get the Ids of the students under this guardian
                    studentIds = dbHandler.getStudentIds(phone);
                    
                    response = "CON Choose the student whose profile you want to access:\n";
                    
                    for (int i = 0; i < studentIds.length; i++) {
                        response += i+1 + ". " + dbHandler.getStudentName(studentIds[i]);
                        
                        if (i < studentIds.length - 1) {
                            response += "\n";
                        }
                    }
                } else {
                    // Unregistered parent/guardian is asked to call school
                    response = "END Your number is not registered in any school\'s "
                            + "database as that of a parent/guardian.\n"
                            + "Please call the school your child is in to get registered "
                            + "first.\n"
                            + "Thank you!";
                }                
            }
            
            if (!level[0].isEmpty() && !level[0].equals("") && level[1].isEmpty()) {
                System.out.println("&student has been selected");
                
                // Get the IDs of the students under this parent/guardian
                studentIds = dbHandler.getStudentIds(phone);
                    
                String id = "";
                
                // Get the ID of the student that the parent/guardian wants to follow
                for (int i = 0; i < studentIds.length; i++) {
                    if (String.valueOf(i + 1).equals(level[0])) {
                        id = studentIds[i];
                    }
                }
                
                // Check if the parent/guardian is subscribed
                if (dbHandler.isSubscribed(phone)) {
                    // Subscribed parent/guardian is presented options
                    response = "CON Select an option:\n"
                    + "1. Fee Structure\n"
                    + "2. Fee Balance\n"
                    + "3. News\n"
                    + "4. Events\n"
                    + "5. Results\n";
                } else {
                    // Non-subscribed parent/guardian is asked to subscribe
                    response = "CON You have not subscribed to receive regular updates "
                            + "on your child\'s welfare.\n"
                            + "97. Subscribe\n"
                            + "98. Cancel";
                }
            }
            
            else if (!level[1].isEmpty() && !level[1].equals("") && level[2].isEmpty()) {
                System.out.println("Option has been selected.");
                
                // Determine what option the user chose and proceed from there
                switch (level[1]) {
                    case "1":
                        // Retrieve fee structure from database
                        response = "END Fee Structure";
                        
                        break;
                        
                    case "2":
                        // Retrieve fee balance from database
                        response = "END Fee Balance";
                        
                        break;
                        
                    case "3":
                        // Retrieve news feed from database
                        response = "END News";
                        
                        break;
                        
                    case "4":
                        // Retrieve list of events from database
                        response = "END Events";
                        
                        break;
                        
                    case "5":
                        // Retrieve student's results from database
                        response = "END Results";
                        
                        break;
                        
                    case "97":
                        // Subscribe parent/guardian to service
                        response = "CON You\'ve been successfully subscribed!\n"
                                + "1. Proceed\n"
                                + "2. Exit\n";
                        
                        break;
                        
                    case "98":
                        // Parent/guardian chooses not to subscribe
                        response = "END Thank you for using our service. We hope "
                                + "you subscribe soon to better follow up your child.";
                    
                        break;
                        
                    default:
                        // User enters invalid response
                        response = "END Sorry, your request could not be processed.";
                        
                        break;
                }
            }
            
            else if (!level[2].isEmpty() && !level[2].equals("") && level[3].isEmpty()) {
                System.out.println("Next option selected");
                
                // Determine what option was selected and proceed from there
                switch (level[1]) {
                    case "1":
                        
                        break;
                        
                    case "2":
                        
                        break;
                        
                    case "3":
                        
                        break;
                        
                    case "4":
                        
                        break;
                        
                    case "5":
                        
                        break;
                        
                    case "97":
                        switch(level[2]) {
                            case "1":
                                // Subscribed parent/guardian is presented options
                                response = "CON Select an option:\n"
                                + "1. Fee Structure\n"
                                + "2. Fee Balance\n"
                                + "3. News\n"
                                + "4. Events\n"
                                + "5. Results\n";
                                
                                break;
                                
                            case "2":
                                // Subscribed parent/guardian exits application
                                response = "END Thank you for using our service. We will keep "
                                        + "you updated on your child\'s activities in school.";

                                break;

                            default:
                                // User enters invalid response
                                response = "END Sorry, your request could not be processed.";

                                break;
                        }
                        
                        break;
                        
                    case "98":
                        // Subscribed parent/guardian chooses to exit application
                        response = "END Thank you for using our service. We will keep "
                                + "you updated on your child\'s activities in school.";
                        
                        break;
                        
                    default:
                        // User enters invalid response
                        response = "END Sorry, your request could not be processed.";
                        
                        break;
                }                
            }
            
            else if (!level[3].isEmpty() && !level[3].equals("") && level[4].isEmpty()) {
                System.out.println("Yet another option selected");
                
                // Determine what option the user chose and proceed from there
                switch (level[3]) {
                    case "1":
                        // Retrieve fee structure from database
                        response = "END Fee Structure";
                        
                        break;
                        
                    case "2":
                        // Retrieve fee balance from database
                        response = "END Fee Balance";
                        
                        break;
                        
                    case "3":
                        // Retrieve news feed from database
                        response = "END News";
                        
                        break;
                        
                    case "4":
                        // Retrieve list of events from database
                        response = "END Events";
                        
                        break;
                        
                    case "5":
                        // Retrieve student's results from database
                        response = "END Results";
                        
                        break;
                        
                    case "97":
                        // Subscribe parent/guardian to service
                        response = "CON You\'ve been successfully subscribed!\n"
                                + "1. Proceed\n"
                                + "2. Exit\n";
                        
                        break;
                        
                    case "98":
                        // Parent/guardian chooses not to subscribe
                        response = "END Thank you for using our service. We hope "
                                + "you subscribe soon to better follow up your child.";
                    
                        break;
                        
                    default:
                        // User enters invalid response
                        response = "END Sorry, your request could not be processed.";
                        
                        break;
                }
            }
            
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        
        return  response;
    }
    
}
