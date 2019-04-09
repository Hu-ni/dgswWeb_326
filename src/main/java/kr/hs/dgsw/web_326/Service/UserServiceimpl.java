package kr.hs.dgsw.web_326.Service;

import kr.hs.dgsw.web_326.Domain.User;
import kr.hs.dgsw.web_326.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceimpl implements UserService{

    @Autowired
    UserRepository ur;

    @Override
    public List<User> listAllUser() {
        return ur.findAll(new Sort(Sort.Direction.DESC, "created"));
    }

    @Override
    public User view(Long id) {
        return this.ur.findById(id)
                .orElse(null);
    }

    @Override
    public User addUser(User u) {
        Optional<User> found = this.ur.findByEmail(u.getEmail());
        if(found.isPresent())   return null;
        else    return this.ur.save(u);
    }

    public User updateUser(Long id,User u){
        return this.ur.findById(id)
                .map(user -> {
                    user.setName(Optional.ofNullable(u.getName()).orElse(user.getName()));
                    user.setEmail(Optional.ofNullable(u.getEmail()).orElse(user.getEmail()));
                    user.setStoredPath(Optional.ofNullable(u.getStoredPath()).orElse(user.getStoredPath()));
                    user.setOriginalFilename(Optional.ofNullable(u.getOriginalFilename()).orElse(user.getOriginalFilename()));
                    user.setPassword(Optional.ofNullable(u.getPassword()).orElse(user.getPassword()));
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
