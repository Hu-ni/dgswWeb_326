package kr.hs.dgsw.web_326.Controller;

import kr.hs.dgsw.web_326.Domain.Comment;
import kr.hs.dgsw.web_326.Domain.User;
import kr.hs.dgsw.web_326.Protocol.CommentUsernameProtocol;
import kr.hs.dgsw.web_326.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService cs;

    @GetMapping("/list")
    public List<CommentUsernameProtocol> listComments(){
        return this.cs.listAllComment();
    }

    @GetMapping("/viewComment/{id}")
    public CommentUsernameProtocol view(Long id){return this.cs.view(id);}

    @PostMapping("/comment")
    public CommentUsernameProtocol addComment(@RequestBody Comment c){
        return this.cs.addComment(c);
    }

    @PutMapping("/updateComment/{id}")
    public CommentUsernameProtocol updateComment(@PathVariable Long id, @RequestBody Comment c){return this.cs.updateComment(id, c);}

    @DeleteMapping("deleteComment/{id}")
    public boolean deleteComment(@PathVariable Long id){return this.cs.deleteComment(id);}
}
