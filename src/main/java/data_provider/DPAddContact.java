package data_provider;

import dto.ContactDTOLombok;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static utils.RandomUtils.generatePhone;
import static utils.RandomUtils.generateString;

public class DPAddContact {
    @DataProvider
    public ContactDTOLombok[] addNewContactDP(){
        ContactDTOLombok contact1 = ContactDTOLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email("fjfff.com")
                .address(generateString(20))
                .description(generateString(10))
                .build();
        ContactDTOLombok contact2 = ContactDTOLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email("ggrrdsww@mailcom")
                .address(generateString(20))
                .description(generateString(10))
                .build();
        return new ContactDTOLombok[]{contact1,contact2};
    }



        @DataProvider
        public Iterator<ContactDTOLombok> addNewContactDP_File(){
            List<ContactDTOLombok> contactList = new ArrayList<>();
            BufferedReader bufferedReader;
            try {
                bufferedReader = new BufferedReader(new FileReader("src/test/resources/wrong_email.csv"));
                String line = bufferedReader.readLine();
                while (line != null){
                    //frodo1,baggins1,1234567890,frodo1mail.com,address 1,description1
                    String[] splitArray = line.split(",");
                    contactList.add(ContactDTOLombok.builder()
                            .name(splitArray[0])
                            .lastName(splitArray[1])
                            .phone(splitArray[2])
                            .email(splitArray[3])
                            .address(splitArray[4])
                            .description(splitArray[5])
                            .build());
                    line = bufferedReader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return contactList.listIterator();
        }
}
