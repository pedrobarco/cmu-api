package pt.ulisboa.tecnico.cmu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.cmu.models.Code;
import pt.ulisboa.tecnico.cmu.models.User;
import pt.ulisboa.tecnico.cmu.repositories.CodeRepository;
import pt.ulisboa.tecnico.cmu.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserRepository userRepository;

    private final CodeRepository codeRepository;

    @Autowired
    public UserController(UserRepository userRepository, CodeRepository codeRepository) {
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody User user){
        StringBuilder msg = new StringBuilder();
        ResponseEntity err = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        ResponseEntity success = ok(user);
        ResponseEntity response;

        Optional<Code> code = codeRepository.findBySecret(user.getCode().getSecret());
        Optional<User> userUsingSameUsername = userRepository.findByUsername(user.getUsername());
        Optional<User> userUsingSameCode = userRepository.findByCode(user.getCode());

        if(userUsingSameUsername.isPresent()) {
            msg.append("Username already exists.");
            response = err;
        } else if(code.isPresent() && !userUsingSameCode.isPresent()) {
            user.setCode(code.get());
            userRepository.save(user);
            response = success;
        } else {
            msg.append("Invalid password.");
            response = err;
        }

        return response;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user){
        Optional<User> actualUser = userRepository.findByUsernameAndAndCode(user.getUsername(), user.getCode());

        if(actualUser.isPresent()){
            User u = actualUser.get();
            String sessionId = actualUser.get().generateSessionId();
            u.setSessionId(sessionId);
            userRepository.save(u);
            return ResponseEntity.ok(sessionId);
        } else {
            String msg = "Invalid username or password.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @DeleteMapping("/all")
    public String deleteAll(){
        userRepository.deleteAll();
        return "All users deleted successfully!";
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable("id") String id){
        return userRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") String id){
        userRepository.deleteById(id);
        return "User deleted successfully!";
    }

}
