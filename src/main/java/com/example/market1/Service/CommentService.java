package com.example.market1.Service;

import com.example.market1.DAO.CommentDAO;
import com.example.market1.DAO.GoodsDAO;
import com.example.market1.DAO.UserDAO;
import com.example.market1.Model.Comment;
import com.example.market1.Model.CommentViewModel;
import com.example.market1.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.UserDataHandler;

import java.util.*;

@Service
public class CommentService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GoodsDAO goodsDAO;

    @Autowired
    private CommentDAO commentDAO;

    public int addComment(int userId, int goodsId, String content, Date date){
        return commentDAO.addComment(new Comment(userId, goodsId, 0, content, date));
    }

    public List<CommentViewModel> getCommentByGoodsId(int goodsId){
        List<Comment> comments = commentDAO.getCommentByGoodsId(goodsId);
        List<CommentViewModel> cvmList = new ArrayList<>();
        for (Comment comment: comments){
            User user = userDAO.getUserById(comment.getUserId());
            CommentViewModel commentViewModel = new CommentViewModel(comment.getContent(), user.getName(), user.getHeadUrl(), comment.getCommentTime());
            cvmList.add(commentViewModel);
        }
        return cvmList;
    }
}
