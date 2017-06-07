package logrest.controller;

import logrest.model.LogDTO;
import logrest.model.User;
import logrest.services.DataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by vitiv on 6/6/17.
 */
@RestController
@RequestMapping("/users/me/")
public class LoginController {

    @Autowired
    DataServices dataServices;

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<?> login(@RequestBody User user, HttpSession httpSession) {
        String username = user.getUsername();
        String password = user.getPassword();
        try {
            User listUser = dataServices.getUserByName(username);
            if(listUser != null && listUser.getPassword().equals(password)) {
                httpSession.setAttribute("username", username);
                return new ResponseEntity<>(listUser, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseEntity<?> logout(HttpSession httpSession) {

        try {
            httpSession.setAttribute("username", null);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> me(HttpSession httpSession) {
        User currentUser = dataServices.getUserByName((String)httpSession.getAttribute("username"));

        try {
            User u = new User();
            if(currentUser != null)
                u.setUsername((String)httpSession.getAttribute("username"));
            return new ResponseEntity<>(u, HttpStatus.OK);
        } catch (Exception e) {
            // e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
