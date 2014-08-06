package com.strider.dataanonymizer.functions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import static java.util.Arrays.asList;
import static java.lang.Math.round;

import static java.lang.Integer.parseInt;
import static java.lang.Math.random;
import static java.lang.String.valueOf;

import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;

/**
 * @author strider
 */
public class Functions {
    
    private static Logger log = getLogger(Functions.class);

    private static final List<String> FIRST_NAME_LIST  = new ArrayList<>();
    private static final List<String> LAST_NAME_LIST   = new ArrayList<>();
    private static final List<String> MIDDLE_NAME_LIST = new ArrayList<>();
    private static final List<String> WORDS            = new ArrayList<>();

    public static void init() {        
        try  {
            log.info("*** Adding list of words into array");
            addWordsIntoArray();
            log.info("*** Array is populated with words from dictionary");
        } catch (Exception ode) {
            log.error("Error occurred while reading dictionary.txt file.\n" + ode.toString());
        }
    }    
    
    
    public static String randomFirstName(String fileName) throws IOException {
        
        if (FIRST_NAME_LIST.isEmpty()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                for(String line; (line = br.readLine()) != null; ) {
                    FIRST_NAME_LIST.add(line);
                }
            }            
        }
        
        int rand = nextIntInRange(0,FIRST_NAME_LIST.size()-1);
        return FIRST_NAME_LIST.get(rand);
    }
    
    public static String randomLastName(String fileName) throws IOException {
        
        if (LAST_NAME_LIST.isEmpty()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                for(String line; (line = br.readLine()) != null; ) {
                    LAST_NAME_LIST.add(line);
                }
            }            
        }
        
        int rand = nextIntInRange(0,LAST_NAME_LIST.size()-1);
        return LAST_NAME_LIST.get(rand);
    }    
    
    public static String randomMiddleName(String fileName) throws IOException {
        
        if (MIDDLE_NAME_LIST.isEmpty()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                for(String line; (line = br.readLine()) != null; ) {
                    MIDDLE_NAME_LIST.add(line);
                }
            }            
        }
        
        int rand = nextIntInRange(0,MIDDLE_NAME_LIST.size()-1);
        return MIDDLE_NAME_LIST.get(rand);
    }        
    
    public static String randomEmail(String domainName) {
        StringBuilder email = new StringBuilder();
        email.append(generateRandomString(1,43).trim()).append("@").append(domainName);
        return email.toString();
    }    
    
    private static int nextIntInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
    
    public static String generateRandomString(int num, int length) {
        List<Integer> randomNumbers = new ArrayList<>();
        Random random = new Random();

        for (int i=0;i<num;i++) {
            int rand = random.nextInt(479617);
            randomNumbers.add(rand);
        }

        StringBuilder randomString = new StringBuilder();
        for (Integer i : randomNumbers) {
            randomString.append(WORDS.get(i));
            randomString.append(" ");
        }
        
        int stringLength = length;
        if (randomString.length() <= length) {
            stringLength = randomString.length();
        }
        
        return randomString.toString().substring(0, stringLength);
    }    
    
    public static String randomDescription(String range) {
        List<String> rangeList = null;
                
        if (range.contains(",")) {
            rangeList = asList(range.split(","));
            // Checking that the list contains two Strings representing Integers
            if (rangeList.size() == 2) {
                if (!rangeList.get(0).isEmpty() && !rangeList.get(1).isEmpty() ) {
                    if (isInteger(rangeList.get(0)) && isInteger(rangeList.get(1))) {
                        StringBuilder desc = new StringBuilder();
                        desc.append(generateRandomString(parseInt(rangeList.get(0)),
                                parseInt(rangeList.get(1))).trim());
                        return desc.toString();
                    }
                }
            }            
        }
        return "";
    }    
 
    /**
     * Generates random 9-digit student number 
     * @return String
     */
    public static String randomStudentNumber()  {
        return valueOf(round(random()*100000000));
    }    
    
    public static String randomPhoneNumber() {
        Random rand = new Random();
        int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
        int num2 = rand.nextInt(743);
        int num3 = rand.nextInt(10000);

        DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
        DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros

        String phoneNumber = df3.format(num1) + "-" + df3.format(num2) + "-" + df4.format(num3);

        return phoneNumber;        
    }
    
   private static void addWordsIntoArray()
    {
        try (Scanner scanner = new Scanner(Functions.class.getClassLoader().getResourceAsStream("dictionary.txt"))) {
            while (scanner.hasNext())
            {
                WORDS.add(scanner.next());
            }
        }
    }    
   
    private static boolean isInteger(String str) {
        try {
            parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {
            log.error(nfe.toString());
        }
        
        return false;
    }   
   
}