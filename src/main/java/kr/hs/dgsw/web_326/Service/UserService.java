package kr.hs.dgsw.web_326.Service;

import kr.hs.dgsw.web_326.Domain.User;

import java.util.List;

public interface UserService {

    List<User> listAllUser();
    User view(Long id);
    User addUser(User u);
    User updateUser(Long id,User u);
    boolean deleteUser(Long id);

}
