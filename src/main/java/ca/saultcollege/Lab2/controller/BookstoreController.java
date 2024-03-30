package ca.saultcollege.Lab2.controller;


import ca.saultcollege.Lab2.data.Content;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.saultcollege.Lab2.data.Registry;
import ca.saultcollege.Lab2.repositories.RegistryRepository;

import java.util.List;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookstoreController {

    @Autowired
    RegistryRepository registryRepository;

    private Boolean updateRegistry(String registryKey, String registryValue) {
        //Find the record for the registry entry based on the supplied key
        List<Registry> registryEntries = registryRepository.findByRegistryKey(registryKey);

        Registry registryEntry = new Registry();

        if (registryEntries.size() == 0) {
            registryEntry.setRegistryKey(registryKey);
        } else {
            registryEntry = registryEntries.get(0);
        }

        registryEntry.setRegistryValue(registryValue);

        //Update the registry table with new value
        registryRepository.save(registryEntry);

        return true;
    }

    //Save the content to the registry table

    @PutMapping("/publiccontent")
    public ResponseEntity<Boolean> savePublicContent(@RequestBody @Valid Registry content) {
        Boolean result = updateRegistry(content.getRegistryKey(), content.getRegistryValue());
        return ResponseEntity.ok(result);
    }

    @PutMapping("/staffcontent")
    public ResponseEntity<Boolean> saveStaffContent(@RequestBody @Valid Registry content) {
        Boolean result = updateRegistry(content.getRegistryKey(), content.getRegistryValue());
        return ResponseEntity.ok(result);
    }



    //pull the data from the database

    @GetMapping("/about")
    public ResponseEntity<String> getAbout(){
        return ResponseEntity.ok("getAbout() : About");
    }
    @GetMapping("/publiccontent")
    public ResponseEntity<String> getPublicContent() {

        return ResponseEntity.ok(getRegistry("public_content"));
    }

    @GetMapping("/staffcontent")
    public ResponseEntity<String> getStaffContent() {
        return ResponseEntity.ok(getRegistry("staff_content"));
    }



    //Find the record for the registry entry based on the supplied key
    private String getRegistry(String registryKey) {

        List<Registry> registryEntries = registryRepository.findByRegistryKey(registryKey);

        Registry registryEntry = new Registry();

        if (registryEntries.size() == 0) {
            return "";
        }

        return registryEntries.get(0).getRegistryValue();
    }


// for the Lab 1

    private String getsigningForm() {

        return "<form hx-post=\"/signin\" hx-target=\"this\" hx-swap=\"outerHTML\">\n" +
                "  <div>\n" +
                "    <label>First Name</label>\n" +
                "    <input type=\"text\" name=\"firstname\" value=\"Prajwol\">\n" +
                "  </div>\n" +
                "  <div class=\"form-group\">\n" +
                "    <label>Last Name</label>\n" +
                "    <input type=\"text\" name=\"lastname\" value=\"GC\">\n" +
                "  </div>\n" +
                "  <div class=\"form-group\">\n" +
                "    <label>Email Address</label>\n" +
                "    <input type=\"email\" name=\"email\" value=\"prajwol@gc.com\">\n" +
                "  </div>\n" +
                "  <div class=\"form-group\">\n" +
                "    <label>Password</label>\n" +
                "    <input type=\"email\" name=\"password\" value=\"prajwol@gc.com\">\n" +
                "  </div>\n" +
                "  <button class=\"btn\">Submit</button>\n" +
                "  <button class=\"btn\" hx-get=\"/signin\">Cancel</button>\n" +
                "</form>";

    }



}

