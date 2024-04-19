package ca.saultcollege.Lab3.controller;


import ca.saultcollege.Lab3.data.Account;
import ca.saultcollege.Lab3.repositories.AccountRepository;
import ca.saultcollege.Lab3.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ca.saultcollege.Lab3.data.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtTokenUtil jwtUtil;

    private final AccessToken token;

    public AccountController(AccessToken accessToken) {
        this.token = accessToken;
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
            System.out.println("AccessToken: "+accessToken);

            token.setAccessToken(accessToken);

            AuthResponse response = new AuthResponse(account.getEmail(), accessToken);
            return ResponseEntity.ok().body(response+"<script>alert('setting var \"accessToken\"');var accessToken='"+accessToken+"'</script>");

        } catch( Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/signin")
    public ResponseEntity<String> getSignin(){ // map a URL to a method
        String s="" +
                "<form hx-post=\"/auth/login\" hx-target=\"this\" hx-swap=\"outerHTML\">" +
                "<h1>Sign in</h1>" +
                "    <div class=\"form-group\">" +
                "        <label>Username (your email)</label>" +
                "        <input type=\"email\" name=\"email\" value=\"fred.carella@gmail.com\">" +
                "    </div>" +
                "    <div class=\"form-group\">" +
                "        <label>Password</label>" +
                "        <input type=\"password\" name=\"password\" value=\"fredspassword\">" +
                "    </div>" +
                "    <button class=\"btn\">Submit</button>" +
                "    <button class=\"btn\" hx-get=\"/signin\">Cancel</button>" +
                "    <div class=\"form-group\">" +
                "        <p>Dont have an account?   Create one by signing up.</p>" +
                "        <a href=\"#\" hx-get=\"/signup\" hx-target=\"#home\"><span class=\"glyphicon glyphicon-log-in\"></span> Create an account</a>" +
                "    </div>" +
                "</form>";
        return ResponseEntity.ok(s);
    }
    @GetMapping("/signup")
    public ResponseEntity<String> getSignUp(){ // map a URL to a method
        String s="" +
                "<form hx-post=\"/createAccount\" hx-target=\"this\" hx-swap=\"outerHTML\">" +
                "<h1>Create an Account</h1>" +
                "    <div>" +
                "        <label>First Name</label>" +
                "        <input type=\"text\" name=\"firstname\" value=\"Fred\">" +
                "    </div>" +
                "    <div class=\"form-group\">" +
                "        <label>Last Name</label>" +
                "        <input type=\"text\" name=\"lastname\" value=\"Carella\">" +
                "    </div>" +
                "    <div class=\"form-group\">" +
                "        <label>Email Address</label>" +
                "        <input type=\"email\" name=\"email\" value=\"fred.carella@gmail.com\">" +
                "    </div>" +
                "    <div class=\"form-group\">" +
                "        <label>Password</label>" +
                "        <input type=\"password\" name=\"password\" value=\"fredspassword\">" +
                "    </div>" +
                "    <div class=\"form-group\">" +
                "        <label>Confirm Password</label>" +
                "        <input type=\"password\" name=\"confirmPassword\" value=\"fredspassword\">" +
                "    </div>" +
                "    <button class=\"btn\">Submit</button>" +
                "    <button class=\"btn\" hx-get=\"/signin\">Cancel</button>" +
                "</form>";
        return ResponseEntity.ok(s);
    }


    @PostMapping("/createAccount")

    public ResponseEntity<?> createAccount(@ModelAttribute Account signUpFormData) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(signUpFormData.getPassword());

        signUpFormData.setPassword(password);

        Account savedAccount = accountRepository.save(signUpFormData);

        return ResponseEntity.ok("createAccount(): " + signUpFormData.getEmail());
    }


    @GetMapping("/protectedPage")
    public ResponseEntity<String> getProtectedPage(Model model){ // map a URL to a method
        String s="" +
                "<h1>Protected page</h1>";
        return ResponseEntity.ok(s);
    }
    @GetMapping("/unProtectedPage")
    public ResponseEntity<String> getUnProtectedPage(@RequestHeader Map<String, String> headers) { // map a URL to a method

        ArrayList<String> h=new ArrayList<>();
        headers.forEach((key, value) -> {
            h.add(String.format("Header '%s' = %s", key, value));
        });
        String s="" +
                "<h1>Un Protected page</h1>";
        return ResponseEntity.ok(s);
    }

}

