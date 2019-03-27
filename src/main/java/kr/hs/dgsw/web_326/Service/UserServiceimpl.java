package kr.hs.dgsw.web_326.Service;

import kr.hs.dgsw.web_326.Domain.User;
import kr.hs.dgsw.web_326.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceimpl implements UserService{

    @Autowired
    UserRepository ur;

    @Override
    public List<User> listAllUser() {
        return ur.findAll();
    }

    @Override
    public User view(Long id) {
        return this.ur.findById(id)
                .map(user-> Optional.ofNullable(ur.getOne(id)).orElse(null))
                .orElse(null);
    }

    @Override
    public User addUser(User u) {
        Optional<User> found = this.ur.findByEmail(u.getEmail());
        if(found.isPresent())   return null;
        else    return this.ur.save(u);
    }

    public User updateUser(User u){
        return this.ur.findById(u.getId())
                .map(user -> {
                    user.setName(Optional.ofNullable(u.getName()).orElse(user.getName()));
                    user.setEmail(Optional.ofNullable(u.getEmail()).orElse(user.getEmail()));
                    return this.ur.save(user);
                })
                .orElse(null);
    }

    public boolean deleteUser(Long id){
        try{
            this.ur.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
