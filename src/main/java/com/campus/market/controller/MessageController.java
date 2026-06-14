package com.campus.market.controller;

import com.campus.market.dto.Result;
import com.campus.market.entity.Message;
import com.campus.market.service.MessageService;
import com.campus.market.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 留言控制器
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 发送留言
     */
    @PostMapping("/send")
    public Result<Void> sendMessage(@RequestBody Map<String, String> params, HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String senderStudentId = jwtUtil.getStudentIdFromToken(token);
            String productId = params.get("productId");
            String receiverStudentId = params.get("receiverStudentId");
            String content = params.get("content");
            String parentMessageId = params.get("parentMessageId");
            messageService.sendMessage(productId, senderStudentId, receiverStudentId, content, parentMessageId);
            return Result.success("留言发送成功", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据物品编号查询留言列表
     */
    @GetMapping("/product/{productId}")
    public Result<List<Message>> getMessagesByProduct(@PathVariable String productId) {
        try {
            List<Message> messages = messageService.getMessagesByProductId(productId);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询我收到的留言
     */
    @GetMapping("/received")
    public Result<List<Message>> getReceivedMessages(HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String studentId = jwtUtil.getStudentIdFromToken(token);
            List<Message> messages = messageService.getMessagesByReceiver(studentId);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询我发送的留言
     */
    @GetMapping("/sent")
    public Result<List<Message>> getSentMessages(HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String studentId = jwtUtil.getStudentIdFromToken(token);
            List<Message> messages = messageService.getMessagesBySender(studentId);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 标记留言为已读
     */
    @PutMapping("/read/{messageId}")
    public Result<Void> markAsRead(@PathVariable String messageId) {
        try {
            messageService.markAsRead(messageId);
            return Result.success("已标记为已读", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 统计未读留言数量
     */
    @GetMapping("/unread-count")
    public Result<Long> getUnreadCount(HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String studentId = jwtUtil.getStudentIdFromToken(token);
            Long count = messageService.countUnread(studentId);
            return Result.success(count);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 从请求中获取Token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
