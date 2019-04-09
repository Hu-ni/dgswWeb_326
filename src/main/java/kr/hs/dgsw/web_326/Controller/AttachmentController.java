package kr.hs.dgsw.web_326.Controller;

import kr.hs.dgsw.web_326.Domain.Comment;
import kr.hs.dgsw.web_326.Domain.User;
import kr.hs.dgsw.web_326.Protocol.AttachmentProtocol;
import kr.hs.dgsw.web_326.Repository.CommentRepository;
import kr.hs.dgsw.web_326.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
public class AttachmentController {

    @Autowired
    UserRepository ur;

    @Autowired
    CommentRepository cr;

    @PostMapping("/attachment")
    public AttachmentProtocol upload(@RequestPart MultipartFile srcFile){
        String destFilename = "D:\\WorkSpace\\Java\\2019\\IdeaProjects\\web_326\\upload\\"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern(("yyyy/MM/dd/")))
                + UUID.randomUUID().toString() + "_"
                + srcFile.getOriginalFilename();

        try {
            File destFile = new File(destFilename);
            destFile.getParentFile().mkdirs();
            srcFile.transferTo(destFile);
            return new AttachmentProtocol(destFilename, srcFile.getOriginalFilename());
        } catch (IOException e) {
            return null;
        }
    }

    @GetMapping("/attachment/{type}/{id}")
    public void download(@PathVariable String type, @PathVariable Long id, HttpServletRequest req, HttpServletResponse resp){
        if(type.equals("user")){
            try {
                User user = ur.findById(id).orElse(null);

                File file = new File(user.getStoredPath());
                if(!file.exists()) return;

                String mimeType = URLConnection.guessContentTypeFromName(file.getName());
                if(mimeType == null) mimeType = "application.octet-stream";

                resp.setContentType(mimeType);
                resp.setHeader("Content-Disposition", "inline: filename=\"" + user.getOriginalFilename() +"\"");
                resp.setContentLength((int)file.length());

                InputStream is = null;

                is = new BufferedInputStream(new FileInputStream(file));
                FileCopyUtils.copy(is, resp.getOutputStream());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }else{
            try {
                Comment comment = cr.findById(id).orElse(null);

                File file = new File(comment.getStoredPath());
                if(!file.exists()) return;

                String mimeType = URLConnection.guessContentTypeFromName(file.getName());
                if(mimeType == null) mimeType = "application.octet-stream";

                resp.setContentType(mimeType);
                resp.setHeader("Content-Disposition", "inline: filename=\"" + comment.getOriginalFilename() +"\"");
                resp.setContentLength((int)file.length());

                InputStream is = null;

                is = new BufferedInputStream(new FileInputStream(file));
                FileCopyUtils.copy(is, resp.getOutputStream());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
