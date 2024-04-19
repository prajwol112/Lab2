package ca.saultcollege.Lab3.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.saultcollege.Lab3.data.Registry;
import ca.saultcollege.Lab3.repositories.RegistryRepository;

import java.util.List;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookstoreController {


    @GetMapping("/about")
    public ResponseEntity<String> getAbout(){ // map a URL to a method
        return ResponseEntity.ok("getAbout() : About");
    }

    // save data
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

    // get data

    @GetMapping("/publiccontent")
    public ResponseEntity<String> getPublicContent() {
        return ResponseEntity.ok(getRegistry("public_content"));
    }

    @GetMapping("/staffcontent")
    public ResponseEntity<String> getStaffContent() {
        return ResponseEntity.ok(getRegistry("staff_content"));
    }





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

    private String getRegistry(String registryKey) {
        //Find the record for the registry entry based on the supplied key
        List<Registry> registryEntries = registryRepository.findByRegistryKey(registryKey);

        Registry registryEntry = new Registry();

        if (registryEntries.size() == 0) {
            return "";
        }

        return registryEntries.get(0).getRegistryValue();
    }

}
