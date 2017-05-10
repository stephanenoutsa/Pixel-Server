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
     *           b) Else determine preferred language and display corresponding main menu.
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
    @GET
    @Path("j-s")
    public String getJSParams(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        
        String phone = queryParams.getFirst("MSISDN");
        String ussdString = queryParams.getFirst("USSD_STRING");
        String serviceCode = queryParams.getFirst("serviceCode");
        
        String[] lvl = ussdString.split("\\*");
        String[] level = new String[8];
        
        /** Level definitions
         * 
         * level[0] == preferredLanguage / serviceChoice
         * level[1] == proceedChoice / categoryChoice
         * level[2] == categoryChoice / lastChoice
         * level[3] == levelChoice
         * level[4] == age
         * level[5] == gender
         * level[6] == town
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
                
                if(dbHandler.subscriberExists(phone)) {                    
                    // Determine if it is user's first time on platform
                    /* Code to get user's first time status */
                    boolean first = true; // This is just a placeholder
                    
                    if (first) {
                        response = "CON Veuillez choisir votre langue de preference.\n"
                                + "Please select your preferred language.\n\n"
                                + "1. Francais\n"
                                + "2. English";
                    } else {
                        // Determine preferred language and display corresponding menu
                        /* Code to get user's preferred language */
                        String lang = "FR"; // This is just a placeholder
                        
                        switch (lang) {
                            case "FR":
                                response = "CON Veuillez choisir le service auquel vous "
                                        + "voulez acceder:\n"
                                        + "1 Offres d\'emplois\n"
                                        + "2 Bourses scolaires\n\n"
                                        + "77 Mon compte";
                                
                                break;
                                
                            case "EN":
                                response = "CON Please choose a service to access:\n"
                                        + "1 Job offers\n"
                                        + "2 Scholarship opportunities\n\n"
                                        + "77 My Account";
                                
                                break;
                                
                            default:
                                response = "CON Veuillez choisir le service auquel vous "
                                        + "voulez acceder:\n"
                                        + "1 Offres d\'emplois\n"
                                        + "2 Bourses scolaires\n\n"
                                        + "77 Mon compte";
                                
                                break;
                        }
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
                // Determine if it is user's first time on platform
                /* Code to get user's first time status */
                boolean first = true; // This is just a placeholder

                if (first) {
                    // Update user's language preference
                    String langChoice = level[0];
                    String lang;
                    
                    switch (langChoice) {
                        case "1":
                            lang = "FR";
                            
                            // Faire un choix de catégorie d'alerte
                            response = "CON Afin de pouvoir mieux vous servir nous avons "
                                    + "besoin de certaines informations.\n"
                                    + "NB: Informations a ne fournir qu\'une seule fois!\n\n"
                                    + "1 Continuer\n"
                                    + "2 Annuler";
                            
                            break;
                            
                        case "2":
                            lang = "EN";
                    
                            // Ask user to choose an alert category
                            response = "CON In order to serve you better, we need some "
                                    + "more information.\n"
                                    + "NB: This is a one time process!\n\n"
                                    + "1 Proceed\n"
                                    + "2 Cancel";
                            
                            break;
                            
                        default:
                            lang = "FR";
                            
                            // Faire un choix de catégorie d'alerte
                            response = "CON Afin de pouvoir mieux vous servir nous avons "
                                    + "besoin de certaines informations.\n"
                                    + "NB: Informations a ne fournir qu\'une seule fois!\n\n"
                                    + "1 Continuer\n"
                                    + "2 Annuler";
                            
                            break;
                    }
                    
                    dbHandler.updateSubscriberLanguage(phone, lang);
                } else {
                    // If they're not accessing the platform for the first time
                    /* Code to get the user's preferred language */
                    String lang = "FR";
                    String serviceChoice = level[0];
                    
                    switch (lang) {
                        case "FR":
                            switch (serviceChoice) {
                                case "1":
                                    response = "CON Veuillez choisir une categorie:\n"
                                            + "1 Informatique\n"
                                            + "2 Ingenierie\n"
                                            + "3 Marketing\n"
                                            + "4 Comptabilite\n"
                                            + "5 Finance\n"
                                            + "6 Ressources Humaines\n"
                                            + "7 Gestion";
                                    
                                    break;
                                    
                                case "2":
                                    response = "CON Veuillez choisir une categorie:\n"
                                            + "1 Informatique\n"
                                            + "2 Ingenierie\n"
                                            + "3 Marketing\n"
                                            + "4 Comptabilite\n"
                                            + "5 Finance\n"
                                            + "6 Ressources Humaines\n"
                                            + "7 Gestion";
                                    
                                    break;
                                    
                                case "77":
                                    response = "CON Veuillez choisir un champ a modifier:\n"
                                            + "1 Langue\n"
                                            + "2 Categorie\n"
                                            + "3 Niveau\n"
                                            + "4 Age\n"
                                            + "5 Sexe\n"
                                            + "6 Ville";
                                    
                                    break;
                                    
                                default:
                                    response = "END Choix invalide. Veuillez reessayer.";
                                    
                                    break;
                            }
                            
                            break;
                            
                        case "EN":
                            switch (serviceChoice) {
                                case "1":
                                    response = "CON Please select a category:\n"
                                            + "1 IT\n"
                                            + "2 Engineering\n"
                                            + "3 Marketing\n"
                                            + "4 Accounting\n"
                                            + "5 Finance\n"
                                            + "6 Human Resources\n"
                                            + "7 Management";
                                    
                                    break;
                                    
                                case "2":
                                    response = "CON Please select a category:\n"
                                            + "1 IT\n"
                                            + "2 Engineering\n"
                                            + "3 Marketing\n"
                                            + "4 Accounting\n"
                                            + "5 Finance\n"
                                            + "6 Human Resources\n"
                                            + "7 Management";
                                    
                                    break;
                                    
                                case "77":
                                    response = "CON Please select a field to edit:\n"
                                            + "1 Language\n"
                                            + "2 Category\n"
                                            + "3 Level\n"
                                            + "4 Age\n"
                                            + "5 Gender\n"
                                            + "6 Town";
                                    
                                    break;
                                    
                                default:
                                    response = "END Invalid choice. Please try again.";
                                    
                                    break;
                            }
                            
                            break;
                            
                        default:
                            switch (serviceChoice) {
                                case "1":
                                    response = "CON Veuillez choisir une categorie:\n"
                                            + "1 Informatique\n"
                                            + "2 Ingenierie\n"
                                            + "3 Marketing\n"
                                            + "4 Comptabilite\n"
                                            + "5 Finance\n"
                                            + "6 Resources Humaines\n"
                                            + "7 Gestion";
                                    
                                    break;
                                    
                                case "2":
                                    response = "CON Veuillez choisir une categorie:\n"
                                            + "1 Informatique\n"
                                            + "2 Ingenierie\n"
                                            + "3 Marketing\n"
                                            + "4 Comptabilite\n"
                                            + "5 Finance\n"
                                            + "6 Resources Humaines\n"
                                            + "7 Gestion";
                                    
                                    break;
                                    
                                case "77":
                                    response = "CON Veuillez choisir un champ a modifier\n"
                                            + "1 Langue\n"
                                            + "2 Categorie\n"
                                            + "3 Niveau\n"
                                            + "4 Age\n"
                                            + "5 Sexe\n"
                                            + "6 Ville";
                                    
                                    break;
                                    
                                default:
                                    response = "END Choix invalide. Veuillez reessayer.";
                                    
                                    break;
                            }
                            
                            break;
                    }
                }
            }
            
            else if (!level[1].isEmpty() && !level[1].equals("") && level[2].isEmpty()) {
                // Determine if it is user's first time on platform
                /* Code to get user's first time status */
                boolean first = true; // This is just a placeholder

                if (first) {
                    String langChoice = level[0];
                    String proceedChoice = level[1];
                    
                    switch (langChoice) {
                        case "1":
                            // Subscriber prefers French language
                            switch (proceedChoice) {
                                case "1":
                                    // Display categories in French
                                    response = "CON Veuillez choisir une categorie:\n"
                                            + "1 Informatique\n"
                                            + "2 Ingenierie\n"
                                            + "3 Marketing\n"
                                            + "4 Comptabilite\n"
                                            + "5 Finance\n"
                                            + "6 Ressources Humaines\n"
                                            + "7 Gestion";
                                    
                                    break;
                                    
                                case "2":
                                    // Bye bye message
                                    response = "END Merci d\'utiliser notre service.";
                                    
                                    // Update first time status
                                    dbHandler.updateFirstTimeStatus(phone, "NO");
                            }
                            
                            break;
                            
                        case "2":
                            // Subscriber prefers English language
                            switch (proceedChoice) {
                                case "1":
                                    // Display categories in English
                                    response = "CON Select a category:\n"
                                            + "1 IT\n"
                                            + "2 Engineering\n"
                                            + "3 Marketing\n"
                                            + "4 Accounting\n"
                                            + "5 Finance\n"
                                            + "6 Human Resources\n"
                                            + "7 Management";
                                    
                                    break;
                                    
                                case "2":
                                    // Bye bye message
                                    response = "END Thank you for using our service.";
                                    
                                    // Update first time status
                                    dbHandler.updateFirstTimeStatus(phone, "NO");
                            }
                            
                            break;
                            
                        default:
                            // French is the default language
                            switch (proceedChoice) {
                                case "1":
                                    // Display categories in French
                                    response = "CON Veuillez choisir une categorie:\n"
                                            + "1 Informatique\n"
                                            + "2 Ingenierie\n"
                                            + "3 Marketing\n"
                                            + "4 Comptabilite\n"
                                            + "5 Finance\n"
                                            + "6 Ressources Humaines\n"
                                            + "7 Gestion";
                                    
                                    break;
                                    
                                case "2":
                                    // Bye bye message
                                    response = "END Merci d\'utiliser notre service.";
                                    
                                    // Update first time status
                                    dbHandler.updateFirstTimeStatus(phone, "NO");
                            }
                            
                            break;
                    }
                } else {
                    // If they're not accessing the platform for the first time
                    /* Code to get the user's preferred language */
                    String lang = "FR";
                    String serviceChoice = level[0];
                    String catChoice = level[1];
                    
                    switch (lang) {
                        case "FR":
                            switch (serviceChoice) {
                                case "1":
                                    switch (catChoice) {
                                        case "1":
                                            /* Code to fetch list of IT jobs and list them */
                                            response = "END Offres en Informatique";
                                            break;
                                            
                                        case "2":
                                            /* Code to fetch list of Engineering jobs and list them */
                                            response = "END Offres en Ingenierie";
                                            break;
                                            
                                        case "3":
                                            /* Code to fetch list of Marketing jobs and list them */
                                            response = "END Offres en Marketing";
                                            break;
                                            
                                        case "4":
                                            /* Code to fetch list of Accounting jobs and list them */
                                            response = "END Offres en Comptabilite";
                                            break;
                                            
                                        case "5":
                                            /* Code to fetch list of Finance jobs and list them */
                                            response = "END Offres en Finance";
                                            break;
                                            
                                        case "6":
                                            /* Code to fetch list of HR jobs and list them */
                                            response = "END Offres en Ressources Humaines";
                                            break;
                                            
                                        case "7":
                                            /* Code to fetch list of Management jobs and list them */
                                            response = "END Offres en Gestion";
                                            break;
                                            
                                        default:
                                            response = "END Choix invalide. Veuillez reessayer";
                                            break;
                                    }
                                    
                                    break;
                                    
                                case "2":
                                    switch (catChoice) {
                                        case "1":
                                            /* Code to fetch list of IT scholarships and list them */
                                            response = "END Bourses en Informatique";
                                            break;
                                            
                                        case "2":
                                            /* Code to fetch list of Engineering scholarships and list them */
                                            response = "END Bourses en Ingenierie";
                                            break;
                                            
                                        case "3":
                                            /* Code to fetch list of Marketing scholarships and list them */
                                            response = "END Bourses en Marketing";
                                            break;
                                            
                                        case "4":
                                            /* Code to fetch list of Accounting scholarships and list them */
                                            response = "END Bourses en Comptabilite";
                                            break;
                                            
                                        case "5":
                                            /* Code to fetch list of Finance scholarships and list them */
                                            response = "END Bourses en Finance";
                                            break;
                                            
                                        case "6":
                                            /* Code to fetch list of HR scholarships and list them */
                                            response = "END Bourses en Ressources Humaines";
                                            break;
                                            
                                        case "7":
                                            /* Code to fetch list of Management scholarships and list them */
                                            response = "END Bourses en Gestion";
                                            break;
                                            
                                        default:
                                            response = "END Choix invalide. Veuillez reessayer";
                                            break;
                                    }
                                    
                                    break;
                                    
                                case "77":
                                    switch (catChoice) {
                                        case "1":
                                            response = "CON Veuillez choisir votre langue de preference.\n"
                                                    + "Please select your preferred language.\n\n"
                                                    + "1. Francais\n"
                                                    + "2. English";
                                            
                                            break;
                                            
                                        case "2":
                                            response = "CON Veuillez choisir une categorie:\n"
                                                    + "1 Informatique\n"
                                                    + "2 Ingenierie\n"
                                                    + "3 Marketing\n"
                                                    + "4 Comptabilite\n"
                                                    + "5 Finance\n"
                                                    + "6 Ressources Humaines\n"
                                                    + "7 Gestion";
                                            
                                            break;
                                            
                                        case "3":
                                            response = "CON Veuillez choisir votre niveau:\n"
                                                    + "1 Stage\n"
                                                    + "2 Entree\n"
                                                    + "3 Associe\n"
                                                    + "4 Haute direction\n"
                                                    + "5 Expert";
                                            
                                            break;
                                            
                                        case "4":
                                            response = "CON Veuillez entrer votre age s\'il vous plait:";
                                            
                                            break;
                                            
                                        case "5":
                                            response = "CON Veuillez choisir votre sexe:\n"
                                                    + "1 Homme\n"
                                                    + "2 Femme";
                                            
                                            break;
                                            
                                        case "6":
                                            response = "CON Veuillez entrer votre ville de residence:\n"
                                                    + "(ex. Bamenda, Douala, Buea, etc)";
                                            
                                            break;
                                            
                                        default:
                                            response = "END Choix invalide. Veuillez reessayer.";
                                            
                                            break;
                                    }
                                    
                                    break;
                                    
                                default:
                                    response = "END Choix invalide. Veuillez reessayer.";
                                    
                                    break;
                            }
                            
                            break;
                            
                        case "EN":
                            switch (serviceChoice) {
                                case "1":
                                    switch (catChoice) {
                                        case "1":
                                            /* Code to fetch list of IT jobs and list them */
                                            response = "END IT jobs";
                                            break;
                                            
                                        case "2":
                                            /* Code to fetch list of Engineering jobs and list them */
                                            response = "END Engineering jobs";
                                            break;
                                            
                                        case "3":
                                            /* Code to fetch list of Marketing jobs and list them */
                                            response = "END Marketing jobs";
                                            break;
                                            
                                        case "4":
                                            /* Code to fetch list of Accounting jobs and list them */
                                            response = "END Accounting jobs";
                                            break;
                                            
                                        case "5":
                                            /* Code to fetch list of Finance jobs and list them */
                                            response = "END Finance jobs";
                                            break;
                                            
                                        case "6":
                                            /* Code to fetch list of HR jobs and list them */
                                            response = "END HR jobs";
                                            break;
                                            
                                        case "7":
                                            /* Code to fetch list of Management jobs and list them */
                                            response = "END Management jobs";
                                            break;
                                            
                                        default:
                                            response = "END Invalid choice. Please try again.";
                                            break;
                                    }
                                    
                                    break;
                                    
                                case "2":
                                    switch (catChoice) {
                                        case "1":
                                            /* Code to fetch list of IT scholarships and list them */
                                            response = "END IT scholarships";
                                            break;
                                            
                                        case "2":
                                            /* Code to fetch list of Engineering scholarships and list them */
                                            response = "END Engineering scholarships";
                                            break;
                                            
                                        case "3":
                                            /* Code to fetch list of Marketing scholarships and list them */
                                            response = "END Marketing scholarships";
                                            break;
                                            
                                        case "4":
                                            /* Code to fetch list of Accounting scholarships and list them */
                                            response = "END Accounting scholarships";
                                            break;
                                            
                                        case "5":
                                            /* Code to fetch list of Finance scholarships and list them */
                                            response = "END Finance scholarships";
                                            break;
                                            
                                        case "6":
                                            /* Code to fetch list of HR scholarships and list them */
                                            response = "END HR scholarships";
                                            break;
                                            
                                        case "7":
                                            /* Code to fetch list of Management scholarships and list them */
                                            response = "END Management scholarships";
                                            break;
                                            
                                        default:
                                            response = "END Invalid choice. Please try again.";
                                            break;
                                    }
                                    
                                    break;
                                    
                                case "77":
                                    switch (catChoice) {
                                        case "1":
                                            response = "CON Veuillez choisir votre langue de preference.\n"
                                                    + "Please select your preferred language.\n\n"
                                                    + "1. Francais\n"
                                                    + "2. English";
                                            
                                            break;
                                            
                                        case "2":
                                            response = "CON Please select a category:\n"
                                                    + "1 IT\n"
                                                    + "2 Engineering\n"
                                                    + "3 Marketing\n"
                                                    + "4 Accounting\n"
                                                    + "5 Finance\n"
                                                    + "6 Human Resources\n"
                                                    + "7 Management";
                                            
                                            break;
                                            
                                        case "3":
                                            response = "CON Please select a level:\n"
                                                    + "1 Internship\n"
                                                    + "2 Entry level\n"
                                                    + "3 Associate\n"
                                                    + "4 Senior Management\n"
                                                    + "5 Expert";
                                            
                                            break;
                                            
                                        case "4":
                                            response = "CON Please enter your age:";
                                            
                                            break;
                                            
                                        case "5":
                                            response = "CON Please select your gender:\n"
                                                    + "1 Male\n"
                                                    + "2 Female";
                                            
                                            break;
                                            
                                        case "6":
                                            response = "CON Please enter your town of residence:\n"
                                                    + "(e.g. Bamenda, Douala, Buea, etc)";
                                            
                                            break;
                                            
                                        default:
                                            response = "END Invalid choice. Please try again.";
                                            
                                            break;
                                    }
                                    
                                    break;
                                    
                                default:
                                    response = "END Invalid choice. Please try again.";
                                    
                                    break;
                            }
                            
                            break;
                            
                        default:
                            switch (serviceChoice) {
                                case "1":
                                    switch (catChoice) {
                                        case "1":
                                            /* Code to fetch list of IT jobs and list them */
                                            response = "END Offres en Informatique";
                                            break;
                                            
                                        case "2":
                                            /* Code to fetch list of Engineering jobs and list them */
                                            response = "END Offres en Ingenierie";
                                            break;
                                            
                                        case "3":
                                            /* Code to fetch list of Marketing jobs and list them */
                                            response = "END Offres en Marketing";
                                            break;
                                            
                                        case "4":
                                            /* Code to fetch list of Accounting jobs and list them */
                                            response = "END Offres en Comptabilite";
                                            break;
                                            
                                        case "5":
                                            /* Code to fetch list of Finance jobs and list them */
                                            response = "END Offres en Finance";
                                            break;
                                            
                                        case "6":
                                            /* Code to fetch list of HR jobs and list them */
                                            response = "END Offres en Ressources Humaines";
                                            break;
                                            
                                        case "7":
                                            /* Code to fetch list of Management jobs and list them */
                                            response = "END Offres en Gestion";
                                            break;
                                            
                                        default:
                                            response = "END Choix invalide. Veuillez reessayer";
                                            break;
                                    }
                                    
                                    break;
                                    
                                case "2":
                                    switch (catChoice) {
                                        case "1":
                                            /* Code to fetch list of IT scholarships and list them */
                                            response = "END Bourses en Informatique";
                                            break;
                                            
                                        case "2":
                                            /* Code to fetch list of Engineering scholarships and list them */
                                            response = "END Bourses en Ingenierie";
                                            break;
                                            
                                        case "3":
                                            /* Code to fetch list of Marketing scholarships and list them */
                                            response = "END Bourses en Marketing";
                                            break;
                                            
                                        case "4":
                                            /* Code to fetch list of Accounting scholarships and list them */
                                            response = "END Bourses en Comptabilite";
                                            break;
                                            
                                        case "5":
                                            /* Code to fetch list of Finance scholarships and list them */
                                            response = "END Bourses en Finance";
                                            break;
                                            
                                        case "6":
                                            /* Code to fetch list of HR scholarships and list them */
                                            response = "END Bourses en Ressources Humaines";
                                            break;
                                            
                                        case "7":
                                            /* Code to fetch list of Management scholarships and list them */
                                            response = "END Bourses en Gestion";
                                            break;
                                            
                                        default:
                                            response = "END Choix invalide. Veuillez reessayer";
                                            break;
                                    }
                                    
                                    break;
                                    
                                case "77":
                                    switch (catChoice) {
                                        case "1":
                                            response = "CON Veuillez choisir votre langue de preference.\n"
                                                    + "Please select your preferred language.\n\n"
                                                    + "1. Francais\n"
                                                    + "2. English";
                                            
                                            break;
                                            
                                        case "2":
                                            response = "CON Veuillez choisir une categorie:\n"
                                                    + "1 Informatique\n"
                                                    + "2 Ingenierie\n"
                                                    + "3 Marketing\n"
                                                    + "4 Comptabilite\n"
                                                    + "5 Finance\n"
                                                    + "6 Ressources Humaines\n"
                                                    + "7 Gestion";
                                            
                                            break;
                                            
                                        case "3":
                                            response = "CON Veuillez choisir votre niveau:\n"
                                                    + "1 Stage\n"
                                                    + "2 Entree\n"
                                                    + "3 Associe\n"
                                                    + "4 Haute direction\n"
                                                    + "5 Expert";
                                            
                                            break;
                                            
                                        case "4":
                                            response = "CON Veuillez entrer votre age s\'il vous plait:";
                                            
                                            break;
                                            
                                        case "5":
                                            response = "CON Veuillez choisir votre sexe:\n"
                                                    + "1 Homme\n"
                                                    + "2 Femme";
                                            
                                            break;
                                            
                                        case "6":
                                            response = "CON Veuillez entrer votre ville de residence:\n"
                                                    + "(ex. Bamenda, Douala, Buea, etc)";
                                            
                                            break;
                                            
                                        default:
                                            response = "END Choix invalide. Veuillez reessayer.";
                                            
                                            break;
                                    }
                                    
                                    break;
                                    
                                default:
                                    response = "END Choix invalide. Veuillez reessayer.";
                                    
                                    break;
                            }
                            
                            break;
                    }
                }
            }
            
            else if (!level[2].isEmpty() && !level[2].equals("") && level[3].isEmpty()) {
                // Determine if it is user's first time on platform
                /* Code to get user's first time status */
                boolean first = true; // This is just a placeholder

                if (first) {
                    String langChoice = level[0];
                    String categoryChoice = level[2];
                    String category;
                    
                    switch (categoryChoice) {
                        case "1":
                            category = "Informatique";
                            break;
                            
                        case "2":
                            category = "Engineering";
                            break;
                            
                        case "3":
                            category = "Marketing";
                            break;
                            
                        case "4":
                            category = "Accounting";
                            break;
                            
                        case "5":
                            category = "Finance";
                            break;
                            
                        case "6":
                            category = "Human Resources";
                            break;
                            
                        case "7":
                            category = "Management";
                            break;
                            
                        default:
                            category = "";
                            break;
                    }
                    
                    switch (langChoice) {
                        case "1":
                            // Subscriber prefers French language
                            if (!category.equals("")) {
                                response = "CON Veuillez choisir votre niveau:\n"
                                        + "1 Stage\n"
                                        + "2 Entree\n"
                                        + "3 Associe\n"
                                        + "4 Haute Direction\n"
                                        + "5 Expert";
                            } else {
                                response = "END Categorie choisie invalide. Veuillez reessayer.";
                            }
                            
                            break;
                            
                        case "2":
                            // Subscriber prefers English language
                            if (!category.equals("")) {
                                response = "CON Select a career level:\n"
                                        + "1 Internship\n"
                                        + "2 Entry Level\n"
                                        + "3 Associate\n"
                                        + "4 Senior Management\n"
                                        + "5 Expert";
                            } else {
                                response = "END Selected category is invalid. Please try again.";
                            }
                            
                            break;
                            
                        default:
                            // French is the default language
                            if (!category.equals("")) {
                                response = "CON Veuillez choisir votre niveau:\n"
                                        + "1 Stage\n"
                                        + "2 Entree\n"
                                        + "3 Associe\n"
                                        + "4 Haute Direction\n"
                                        + "5 Expert";
                            } else {
                                response = "END Categorie choisie invalide. Veuillez reessayer.";
                            }
                            
                            break;
                    }
                    
                    // Update the subscriber's category if necessary
                    if (!category.equals("")) {
                        dbHandler.updateSubscriberCategory(phone, category);
                    }                    
                } else {
                    // If they're not accessing the platform for the first time
                    /* Code to get the user's preferred language */
                    String lang = "FR";
                    String serviceChoice = level[0];
                    String catChoice = level[1];
                    String lastChoice = level[2];
                    
                    switch (lang) {
                        case "FR":
                            if (serviceChoice.equals("77")) {
                                switch (catChoice) {
                                    case "1":
                                        String prefLang;
                                        
                                        switch (lastChoice) {
                                            case "1":
                                                prefLang = "FR";
                                                
                                                response = "END Votre langue preferee a ete mise a jour.\n"
                                                        + "Merci pour votre fidelite.";
                                                
                                                break;
                                                
                                            case "2":
                                                prefLang = "EN";
                                                
                                                response = "END Your preferred language has been updated.\n"
                                                        + "Thank you for your loyalty.";
                                                
                                                break;
                                                
                                            default:
                                                prefLang = "";
                                                
                                                response = "END Choix invalide. Veuillez reessayer.";
                                                
                                                break;
                                        }
                                        
                                        // Update subscriber's preferred language
                                        dbHandler.updateSubscriberLanguage(phone, prefLang);
                                        
                                        break;
                                        
                                    case "2":
                                        String cat;
                                        
                                        switch (lastChoice) {
                                            case "1":
                                                cat = "Informatique";
                                                break;
                                                
                                            case "2":
                                                cat = "Engineering";
                                                break;
                                                
                                            case "3":
                                                cat = "Marketing";
                                                break;
                                                
                                            case "4":
                                                cat = "Accounting";
                                                break;
                                                
                                            case "5":
                                                cat = "Finance";
                                                break;
                                                
                                            case "6":
                                                cat = "Human Resources";
                                                break;
                                                
                                            case "7":
                                                cat = "Management";
                                                break;
                                                
                                            default:
                                                cat = "";
                                                break;
                                        }
                                        
                                        if (!cat.equals("")) {
                                            // Update subscriber's category
                                            dbHandler.updateSubscriberCategory(phone, cat);
                                            
                                            response = "END Votre categorie a ete mise a jour.\n"
                                                    + "Merci pour votre fidelite.";
                                        } else {
                                            response = "END Choix invalide. Veuillez reessayer.";
                                        }
                                        
                                        break;
                                        
                                    case "3":
                                        String l;
                                        
                                        switch (lastChoice) {
                                            case "1":
                                                l = "Internship";
                                                break;
                                                
                                            case "2":
                                                l = "Entry Level";
                                                break;
                                                
                                            case "3":
                                                l = "Associate";
                                                break;
                                                
                                            case "4":
                                                l = "Senior Management";
                                                break;
                                                
                                            case "5":
                                                l = "Expert";
                                                break;
                                                
                                            default:
                                                l = "";
                                                break;
                                        }
                                        
                                        if (!l.equals("")) {
                                            // Update subscriber's level
                                            dbHandler.updateSubscriberLevel(phone, l);
                                            
                                            response = "END Votre niveau a ete mis a jour.\n"
                                                    + "Merci pour votre fidelite.";
                                        } else {
                                            response = "END Choix invalide. Veuillez reessayer.";
                                        }
                                        
                                        break;
                                        
                                    case "4":
                                        String age = lastChoice;
                                        
                                        if (!age.isEmpty()) {
                                            // Update the subscriber's age
                                            dbHandler.updateSubscriberAge(phone, age);
                                            
                                            response = "END Votre age a ete mis a jour.\n"
                                                    + "Merci pour votre fidelite.";
                                        } else {
                                            response = "END Age invalide. Veuillez reessayer.";
                                        }
                                        
                                        break;
                                        
                                    case "5":
                                        String gender;
                                        
                                        switch (lastChoice) {
                                            case "1":
                                                gender = "Male";
                                                break;
                                                
                                            case "2":
                                                gender = "Female";
                                                break;
                                                
                                            default:
                                                gender = "";
                                                break;
                                        }
                                        
                                        if (!gender.equals("")) {
                                            // Update subscriber's gender
                                            dbHandler.updateSubscriberGender(phone, gender);
                                            
                                            response = "END Votre sexe a ete mis a jour.\n"
                                                    + "Merci pour votre fidelite.";
                                        } else {
                                            response = "END Choix invalide. Veuillez reessayer.";
                                        }
                                        
                                        break;
                                        
                                    case "6":
                                        String town = lastChoice;
                                        
                                        if (!town.isEmpty()) {
                                            // Update subscriber's town of residence
                                            dbHandler.updateSubscriberTown(phone, town);
                                            
                                            response = "END Votre ville de residence a ete mise a jour.\n"
                                                    + "Merci pour votre fidelite.";
                                        } else {
                                            response = "END Ville entree invalide. Veuillez reessayer.";
                                        }
                                        
                                        break;
                                        
                                    default:
                                        response = "END Choix invalide. Veuillez reessayer.";
                                        
                                        break;
                                }
                            } else {
                                response = "END Choix invalide. Veuillez reessayer.";
                            }
                            
                            break;
                            
                        case "EN":
                            if (serviceChoice.equals("77")) {
                                switch (catChoice) {
                                    case "1":
                                        String prefLang;
                                        
                                        switch (lastChoice) {
                                            case "1":
                                                prefLang = "FR";
                                                
                                                response = "END Votre langue preferee a ete mise a jour.\n"
                                                        + "Merci pour votre fidelite.";
                                                
                                                break;
                                                
                                            case "2":
                                                prefLang = "EN";
                                                
                                                response = "END Your preferred language has been updated.\n"
                                                        + "Thank you for your loyalty.";
                                                
                                                break;
                                                
                                            default:
                                                prefLang = "";
                                                
                                                response = "END Invalid choice. Please try again.";
                                                
                                                break;
                                        }
                                        
                                        // Update subscriber's preferred language
                                        dbHandler.updateSubscriberLanguage(phone, prefLang);
                                        
                                        break;
                                        
                                    case "2":
                                        String cat;
                                        
                                        switch (lastChoice) {
                                            case "1":
                                                cat = "Informatique";
                                                break;
                                                
                                            case "2":
                                                cat = "Engineering";
                                                break;
                                                
                                            case "3":
                                                cat = "Marketing";
                                                break;
                                                
                                            case "4":
                                                cat = "Accounting";
                                                break;
                                                
                                            case "5":
                                                cat = "Finance";
                                                break;
                                                
                                            case "6":
                                                cat = "Human Resources";
                                                break;
                                                
                                            case "7":
                                                cat = "Management";
                                                break;
                                                
                                            default:
                                                cat = "";
                                                break;
                                        }
                                        
                                        if (!cat.equals("")) {
                                            // Update subscriber's category
                                            dbHandler.updateSubscriberCategory(phone, cat);
                                            
                                            response = "END Your category has been updated.\n"
                                                    + "Thank you for your loyalty.";
                                        } else {
                                            response = "END Invalid choice. Please try again.";
                                        }
                                        
                                        break;
                                        
                                    case "3":
                                        String l;
                                        
                                        switch (lastChoice) {
                                            case "1":
                                                l = "Internship";
                                                break;
                                                
                                            case "2":
                                                l = "Entry Level";
                                                break;
                                                
                                            case "3":
                                                l = "Associate";
                                                break;
                                                
                                            case "4":
                                                l = "Senior Management";
                                                break;
                                                
                                            case "5":
                                                l = "Expert";
                                                break;
                                                
                                            default:
                                                l = "";
                                                break;
                                        }
                                        
                                        if (!l.equals("")) {
                                            // Update subscriber's level
                                            dbHandler.updateSubscriberLevel(phone, l);
                                            
                                            response = "END Your level has been updated.\n"
                                                    + "Thank you for your loyalty.";
                                        } else {
                                            response = "END Invalid choice. Please try again.";
                                        }
                                        
                                        break;
                                        
                                    case "4":
                                        String age = lastChoice;
                                        
                                        if (!age.isEmpty()) {
                                            // Update the subscriber's age
                                            dbHandler.updateSubscriberAge(phone, age);
                                            
                                            response = "END Your age has been updated.\n"
                                                    + "Thank you for your loyalty.";
                                        } else {
                                            response = "END Invalid age. Please try again.";
                                        }
                                        
                                        break;
                                        
                                    case "5":
                                        String gender;
                                        
                                        switch (lastChoice) {
                                            case "1":
                                                gender = "Male";
                                                break;
                                                
                                            case "2":
                                                gender = "Female";
                                                break;
                                                
                                            default:
                                                gender = "";
                                                break;
                                        }
                                        
                                        if (!gender.equals("")) {
                                            // Update subscriber's gender
                                            dbHandler.updateSubscriberGender(phone, gender);
                                            
                                            response = "END Your gender has been updated.\n"
                                                    + "Thank you for your loyalty.";
                                        } else {
                                            response = "END Invalid choice. Please try again.";
                                        }
                                        
                                        break;
                                        
                                    case "6":
                                        String town = lastChoice;
                                        
                                        if (!town.isEmpty()) {
                                            // Update subscriber's town of residence
                                            dbHandler.updateSubscriberTown(phone, town);
                                            
                                            response = "END Your town of residence has been updated.\n"
                                                    + "Thank you for your loyalty.";
                                        } else {
                                            response = "END The town you entered is not valid. "
                                                    + "Please try again.";
                                        }
                                        
                                        break;
                                        
                                    default:
                                        response = "END Invalid choice. Please try again.";
                                        
                                        break;
                                }
                            } else {
                                response = "END Invalid choice. Please try again.";
                            }
                            
                            break;
                            
                        default:
                            if (serviceChoice.equals("77")) {
                                switch (catChoice) {
                                    case "1":
                                        String prefLang;
                                        
                                        switch (lastChoice) {
                                            case "1":
                                                prefLang = "FR";
                                                
                                                response = "END Votre langue preferee a ete mise a jour.\n"
                                                        + "Merci pour votre fidelite.";
                                                
                                                break;
                                                
                                            case "2":
                                                prefLang = "EN";
                                                
                                                response = "END Your preferred language has been updated.\n"
                                                        + "Thank you for your loyalty.";
                                                
                                                break;
                                                
                                            default:
                                                prefLang = "";
                                                
                                                response = "END Choix invalide. Veuillez reessayer.";
                                                
                                                break;
                                        }
                                        
                                        // Update subscriber's preferred language
                                        dbHandler.updateSubscriberLanguage(phone, prefLang);
                                        
                                        break;
                                        
                                    case "2":
                                        String cat;
                                        
                                        switch (lastChoice) {
                                            case "1":
                                                cat = "Informatique";
                                                break;
                                                
                                            case "2":
                                                cat = "Engineering";
                                                break;
                                                
                                            case "3":
                                                cat = "Marketing";
                                                break;
                                                
                                            case "4":
                                                cat = "Accounting";
                                                break;
                                                
                                            case "5":
                                                cat = "Finance";
                                                break;
                                                
                                            case "6":
                                                cat = "Human Resources";
                                                break;
                                                
                                            case "7":
                                                cat = "Management";
                                                break;
                                                
                                            default:
                                                cat = "";
                                                break;
                                        }
                                        
                                        if (!cat.equals("")) {
                                            // Update subscriber's category
                                            dbHandler.updateSubscriberCategory(phone, cat);
                                            
                                            response = "END Votre categorie a ete mise a jour.\n"
                                                    + "Merci pour votre fidelite.";
                                        } else {
                                            response = "END Choix invalide. Veuillez reessayer.";
                                        }
                                        
                                        break;
                                        
                                    case "3":
                                        String l;
                                        
                                        switch (lastChoice) {
                                            case "1":
                                                l = "Internship";
                                                break;
                                                
                                            case "2":
                                                l = "Entry Level";
                                                break;
                                                
                                            case "3":
                                                l = "Associate";
                                                break;
                                                
                                            case "4":
                                                l = "Senior Management";
                                                break;
                                                
                                            case "5":
                                                l = "Expert";
                                                break;
                                                
                                            default:
                                                l = "";
                                                break;
                                        }
                                        
                                        if (!l.equals("")) {
                                            // Update subscriber's level
                                            dbHandler.updateSubscriberLevel(phone, l);
                                            
                                            response = "END Votre niveau a ete mis a jour.\n"
                                                    + "Merci pour votre fidelite.";
                                        } else {
                                            response = "END Choix invalide. Veuillez reessayer.";
                                        }
                                        
                                        break;
                                        
                                    case "4":
                                        String age = lastChoice;
                                        
                                        if (!age.isEmpty()) {
                                            // Update the subscriber's age
                                            dbHandler.updateSubscriberAge(phone, age);
                                            
                                            response = "END Votre age a ete mis a jour.\n"
                                                    + "Merci pour votre fidelite.";
                                        } else {
                                            response = "END Age invalide. Veuillez reessayer.";
                                        }
                                        
                                        break;
                                        
                                    case "5":
                                        String gender;
                                        
                                        switch (lastChoice) {
                                            case "1":
                                                gender = "Male";
                                                break;
                                                
                                            case "2":
                                                gender = "Female";
                                                break;
                                                
                                            default:
                                                gender = "";
                                                break;
                                        }
                                        
                                        if (!gender.equals("")) {
                                            // Update subscriber's gender
                                            dbHandler.updateSubscriberGender(phone, gender);
                                            
                                            response = "END Votre sexe a ete mis a jour.\n"
                                                    + "Merci pour votre fidelite.";
                                        } else {
                                            response = "END Choix invalide. Veuillez reessayer.";
                                        }
                                        
                                        break;
                                        
                                    case "6":
                                        String town = lastChoice;
                                        
                                        if (!town.isEmpty()) {
                                            // Update subscriber's town of residence
                                            dbHandler.updateSubscriberTown(phone, town);
                                            
                                            response = "END Votre ville de residence a ete mise a jour.\n"
                                                    + "Merci pour votre fidelite.";
                                        } else {
                                            response = "END Ville entree invalide. Veuillez reessayer.";
                                        }
                                        
                                        break;
                                        
                                    default:
                                        response = "END Choix invalide. Veuillez reessayer.";
                                        
                                        break;
                                }
                            } else {
                                response = "END Choix invalide. Veuillez reessayer.";
                            }
                    }
                }
            }
            
            else if (!level[3].isEmpty() && !level[3].equals("") && level[4].isEmpty()) {
                // Determine if it is user's first time on platform
                /* Code to get user's first time status */
                boolean first = true; // This is just a placeholder

                if (first) {
                    String langChoice = level[0];
                    String levelChoice = level[3];
                    String careerLevel;
                    
                    switch (levelChoice) {
                        case "1":
                            careerLevel = "Internship";
                            break;
                            
                        case "2":
                            careerLevel = "Entry Level";
                            break;
                            
                        case "3":
                            careerLevel = "Associate";
                            break;
                            
                        case "4":
                            careerLevel = "Senior Management";
                            break;
                            
                        case "5":
                            careerLevel = "Expert";
                            break;
                            
                        default:
                            careerLevel = "";
                            break;
                    }
                    
                    switch (langChoice) {
                        case "1":
                            // Subscriber prefers French language
                            if (!careerLevel.equals("")) {
                                response = "CON Veuillez entrer votre age s\'il vous plait:";
                            } else {
                                response = "END Niveau choisi invalide. Veuillez reessayer.";
                            }
                            
                            break;
                            
                        case "2":
                            // Subscriber prefers English language
                            if (!careerLevel.equals("")) {
                                response = "CON Please enter your age:";
                            } else {
                                response = "END Selected career level is invalid. Please try again.";
                            }
                            
                            break;
                            
                        default:
                            // French is the default language
                            if (!careerLevel.equals("")) {
                                response = "CON Veuillez entrer votre age s\'il vous plait:";
                            } else {
                                response = "END Niveau choisi invalide. Veuillez reessayer.";
                            }
                            
                            break;
                    }
                    
                    // Update the subscriber's level if necessary
                    if (!careerLevel.equals("")) {
                        dbHandler.updateSubscriberLevel(phone, careerLevel);
                    }
                } else {
                    // Not a first timer
                }
            }
            
            else if (!level[4].isEmpty() && !level[4].equals("") && level[5].isEmpty()) {
                // Determine if it is user's first time on platform
                /* Code to get user's first time status */
                boolean first = true; // This is just a placeholder

                if (first) {
                    String langChoice = level[0];
                    String age = level[4];
                    
                    switch (langChoice) {
                        case "1":
                            // Subscriber prefers French language
                            response = "CON Plus que deux etapes a franchir!\n"
                                    + "Veuillez choisir votre sexe:\n"
                                    + "1 Homme\n"
                                    + "2 Femme";
                            
                            break;
                            
                        case "2":
                            // Subscriber prefers English language
                            response = "CON Two more steps to go!\n"
                                    + "Please select your gender:\n"
                                    + "1 Male\n"
                                    + "2 Female";
                            
                            break;
                            
                        default:
                            // French is the default language
                            response = "CON Plus que deux etapes a franchir!\n"
                                    + "Veuillez choisir votre sexe:\n"
                                    + "1 Homme\n"
                                    + "2 Femme";
                            
                            break;
                    }
                    
                    // Update the subscriber's age 
                    dbHandler.updateSubscriberAge(phone, age);
                } else {
                    // Not a first timer
                }
            }
            
            else if (!level[5].isEmpty() && !level[5].equals("") && level[6].isEmpty()) {
                // Determine if it is user's first time on platform
                /* Code to get user's first time status */
                boolean first = true; // This is just a placeholder

                if (first) {
                    String langChoice = level[0];
                    String genderChoice = level[5];
                    String gender;
                    
                    switch (genderChoice) {
                        case "1":
                            gender = "Male";
                            break;
                            
                        case "2":
                            gender = "Female";
                            break;
                            
                        default:
                            gender = "";
                            break;
                    }
                    
                    switch (langChoice) {
                        case "1":
                            // Subscriber prefers French language
                            if (!gender.equals("")) {
                                response = "CON Bravo! Voici la derniere etape!\n"
                                        + "Veuillez entrer votre ville de residence:\n"
                                        + "(ex. Bamenda, Douala, Buea, etc)";
                            } else {
                                response = "END Sexe choisi invalide. Veuillez reessayer.";
                            }
                            
                            break;
                            
                        case "2":
                            // Subscriber prefers English language
                            if (!gender.equals("")) {
                                response = "CON Congratulations! This is the last step!\n"
                                        + "Please enter your town of residence:\n"
                                        + "(e.g. Bamenda, Douala, Buea, etc)";
                            } else {
                                response = "END Selected gender is invalid. Please try again.";
                            }
                            
                            break;
                            
                        default:
                            // French is the default language
                            if (!gender.equals("")) {
                                response = "CON Bravo! Voici la derniere etape!\n"
                                        + "Veuillez entrer votre ville de residence:\n"
                                        + "(ex. Bamenda, Douala, Buea, etc)";
                            } else {
                                response = "END Sexe choisi invalide. Veuillez reessayer.";
                            }
                            
                            break;
                    }
                    
                    // Update the subscriber's gender if necessary
                    if (!gender.equals("")) {
                        dbHandler.updateSubscriberGender(phone, gender);
                    }
                } else {
                    // Not a first timer
                }
            }
            
            else if (!level[6].isEmpty() && !level[6].equals("") && level[7].isEmpty()) {
                // Determine if it is user's first time on platform
                /* Code to get user's first time status */
                boolean first = true; // This is just a placeholder

                if (first) {
                    String langChoice = level[0];
                    String town = level[6];
                    
                    switch (langChoice) {
                        case "1":
                            // Subscriber prefers French language
                            response = "END Felicitations! Nous pourrons desormais vous "
                                    + "fournir des informations plus precises.\n"
                                    + "Merci pour votre fidelite!";
                            
                            break;
                            
                        case "2":
                            // Subscriber prefers English language
                            response = "END Congratulations! We can now provide you with "
                                    + "more accurate information.\n"
                                    + "Thank you for your loyalty!";
                            
                            break;
                            
                        default:
                            // Default language is French
                            response = "END Felicitations! Nous pourrons desormais vous "
                                    + "fournir des informations plus precises.\n"
                                    + "Merci pour votre fidelite!";
                            
                            break;
                    }
                    
                    // Update the subscriber's town of residence
                    dbHandler.updateSubscriberTown(phone, town);
                } else {
                    // Not a first timer
                }
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
