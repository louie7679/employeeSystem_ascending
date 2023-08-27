package org.ascending.training.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.ascending.training.model.User;
import org.ascending.training.repository.exception.UserNotFoundException;
import org.ascending.training.service.JWTService;
import org.ascending.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = {"/auth"})
public class AuthController {
    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity userLogin(@RequestBody User user) throws Exception {
        try {
            // String digestPassword = DigestUtils.md5Hex(user.getPassword().trim());
            // User user = userService.getUserByCredentials(user.getEmail(), digestPassword);
            User u = userService.getUserByCredentials(user.getEmail(), user.getPassword());
            if (u == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
            }
            return ResponseEntity.ok().body(jwtService.generateToken(u));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }
}
