package ca.saultcollege.Lab2.controller;


import ca.saultcollege.Lab2.data.Account;
import ca.saultcollege.Lab2.repositories.AccountRepository;
import ca.saultcollege.Lab2.security.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ca.saultcollege.Lab2.data.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController {

    @GetMapping("/login")
    public ResponseEntity<String> getLogin(){ // map a URL to a method
        String s=

                "<form hx-post=\"/signup\" hx-target=\"this\" hx-swap=\"outerHTML\">" +
                        "    <div>" +
                        "        <label>Username</label>" +
                        "        <input type=\"text\" name=\"username\" value=\"user\">" +
                        "    </div>" +
                        "    <div class=\"form-group\">" +
                        "        <label>Password</label>" +
                        "        <input type=\"password\" name=\"password\" value=\"password\">" +
                        "    </div>" +
                        "    <button class=\"btn\">Submit</button>" +
                        "    <button class=\"btn\" hx-get=\"/signup\">Cancel</button>" +
                        "</form>";
        return ResponseEntity.ok(s);
    }


    @GetMapping("/signin")
    public ResponseEntity<String> getSignin(){ // map a URL to a method
        String s="<form hx-post=\"/auth/login\" hx-target=\"this\" hx-swap=\"outerHTML\">" +
                "    <div>" +
                "        <label>Username</label>" +
                "        <input type=\"text\" name=\"username\" value=\"user\">" +
                "    </div>" +
                "    <div class=\"form-group\">" +
                "        <label>Password</label>" +
                "        <input type=\"password\" name=\"password\" value=\"password\">" +
                "    </div>" +
                "    <button class=\"btn\">Submit</button>" +
                "    <button class=\"btn\" hx-get=\"/signup\">Cancel</button>" +
                "</form>";
        return ResponseEntity.ok(s);
    }

//    // just here for reference: call it with http://localhost:8080/signup2?email=f.c@g.c

    @GetMapping("/signup2")
    @ResponseBody
    public String createAccount2(@RequestParam String email) {
        return "email: " + email;
    }

    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtTokenUtil jwtUtil;

    @PostMapping("/test_form")
    public String test_form(@ModelAttribute Account account, Model model) {
        model.addAttribute("email", account);
        return "result";
    }
    @PostMapping(path="/auth/login")
    public ResponseEntity<?> login(@ModelAttribute Account acc, Model model) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            acc.getEmail(), acc.getPassword())
            );

            Account account = (Account) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(account);

            AuthResponse response = new AuthResponse(account.getEmail(), accessToken);

            return ResponseEntity.ok().body(response);
        } catch( Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



    @PostMapping("/signup")
    public ResponseEntity<String> createAccount(@RequestBody Account signUpFormData) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(signUpFormData.getPassword());

        signUpFormData.setPassword(password);

        Account savedAccount = accountRepository.save(signUpFormData);

        return ResponseEntity.ok("createAccount(): " + signUpFormData.getEmail());
    }



}

