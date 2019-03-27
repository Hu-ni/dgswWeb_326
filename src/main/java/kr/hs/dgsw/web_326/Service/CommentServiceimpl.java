package kr.hs.dgsw.web_326.Service;

import kr.hs.dgsw.web_326.Domain.Comment;
import kr.hs.dgsw.web_326.Domain.User;
import kr.hs.dgsw.web_326.Protocol.CommentUsernameProtocol;
import kr.hs.dgsw.web_326.Repository.CommentRepository;
import kr.hs.dgsw.web_326.Repository.UserRepository;
import kr.hs.dgsw.web_326.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceimpl implements CommentService {

    @Autowired
    private CommentRepository cr;
    @Autowired
    private UserRepository ur;


    @PostConstruct
    private void init(){
        User u = this.ur.save(new User("aaa","aabbccdd"));
        this.cr.save(new Comment(u.getId(), "hi there1"));
        this.cr.save(new Comment(u.getId(), "hi there2"));
        this.cr.save(new Comment(u.getId(), "hi there3"));

    }

    @Override
    public List<CommentUsernameProtocol> listAllComment() {
        List<Comment> cList = this.cr.findAll();
        List<CommentUsernameProtocol> cupList = new ArrayList<>();
        cList.forEach(comment -> {
            Optional<User> found = this.ur.findById(comment.getUserId());
            String username = found.isPresent() ? found.get().getName() : null;
            CommentUsernameProtocol cup = new CommentUsernameProtocol(comment, username);
            cupList.add(cup);
        });
        return cupList;
    }

    @Override
    public CommentUsernameProtocol view(Long id) {
        Optional<Comment> found = this.cr.findById(id);
        if(found.isPresent()){
            Optional<User> u = this.ur.findById(found.get().getUserId());
            String username = u.isPresent() ? u.get().getName() : null;
            return new CommentUsernameProtocol(found.get(), username);
        }else   return null;
    }


    @Override
    public CommentUsernameProtocol addComment(Comment c){
        cr.save(c);
        String username = this.ur.findById(c.getUserId()).map(User::getName).orElse(null);
        return new CommentUsernameProtocol(c, username);
    }

    @Override
    public CommentUsernameProtocol updateComment(Long id, Comment c) {
        Comment temp = this.cr.findById(id)
                .map(comment -> {
                    comment.setUserId(Optional.ofNullable(c.getUserId()).orElse(comment.getUserId()));
                    comment.setContent(Optional.ofNullable(c.getContent()).orElse(comment.getContent()));
                    return this.cr.save(comment);
                }).orElse(null);
        if(temp != null){
            Optional<User> found = this.ur.findById(temp.getUserId());
            String username = found.isPresent() ? found.get().getName() : null;
            return new CommentUsernameProtocol(temp, username);
        }
        return null;
    }

    @Override
    public boolean deleteComment(Long id) {
        try{
            this.cr.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
