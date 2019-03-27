package kr.hs.dgsw.web_326.Controller;

import kr.hs.dgsw.web_326.Domain.Comment;
import kr.hs.dgsw.web_326.Domain.User;
import kr.hs.dgsw.web_326.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService us;

    @PostMapping("/addUser")
    public User addUser(@RequestBody User u){
        return this.us.addUser(u);
    }

    @PutMapping("/updateUser")
    public User updateUser(@RequestBody User u){
        return this.us.updateUser(u);
    }

    @DeleteMapping("/deleteUser/{id}")
    public boolean deleteUser(@PathVariable Long id){return this.us.deleteUser(id);}

    @GetMapping("/listUser")
    public List<User> listAllUser(){return this.us.listAllUser();}

    @GetMapping("/view/{id}")
    public User view(@PathVariable Long id){return this.us.view(id);}

}
