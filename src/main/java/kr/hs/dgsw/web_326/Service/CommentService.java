package kr.hs.dgsw.web_326.Service;

import kr.hs.dgsw.web_326.Domain.Comment;
import kr.hs.dgsw.web_326.Domain.User;
import kr.hs.dgsw.web_326.Protocol.CommentUsernameProtocol;

import java.util.List;

public interface CommentService {
    List<CommentUsernameProtocol> listAllComment();
    CommentUsernameProtocol view(Long id);
    CommentUsernameProtocol addComment(Comment c);
    CommentUsernameProtocol updateComment(Long id, Comment c);
    boolean deleteComment(Long id);
}
