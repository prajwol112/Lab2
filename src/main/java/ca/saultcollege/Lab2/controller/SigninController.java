package ca.saultcollege.Lab2.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SigninController {
    @PostMapping("/signin")
    public String signin(@ModelAttribute SigninController.Account values) {

        return "Hello " + values.getEmail() + ":"+values.getPassword()+" you are signed in";
    }

    // signinForm is just here for documentation, its not used in the app
    @GetMapping(path = "/signingForm")
    public String signingForm() {

        return "<form hx-post=\"/signin\" hx-target=\"this\" hx-swap=\"outerHTML\">\n" +
                "  <div>\n" +
                "    <label>First Name</label>\n" +
                "    <input type=\"text\" name=\"firstname\" value=\"Joe\">\n" +
                "  </div>\n" +
                "  <div class=\"form-group\">\n" +
                "    <label>Last Name</label>\n" +
                "    <input type=\"text\" name=\"lastname\" value=\"Blow\">\n" +
                "  </div>\n" +
                "  <div class=\"form-group\">\n" +
                "    <label>Email Address</label>\n" +
                "    <input type=\"email\" name=\"email\" value=\"joe@blow.com\">\n" +
                "  </div>\n" +
                "  <div class=\"form-group\">\n" +
                "    <label>Password</label>\n" +
                "    <input type=\"email\" name=\"password\" value=\"joe@blow.com\">\n" +
                "  </div>\n" +
                "  <button class=\"btn\">Submit</button>\n" +
                "  <button class=\"btn\" hx-get=\"/signin\">Cancel</button>\n" +
                "</form>";

    }

    static class Account {
        private String firstname;
        private String lastname;

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }

        private String confirmPassword;

    }
}

