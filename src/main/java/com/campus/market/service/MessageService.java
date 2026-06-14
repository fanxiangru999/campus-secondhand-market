package com.campus.market.service;

import com.campus.market.entity.Message;
import java.util.List;

/**
 * 留言服务接口
 */
public interface MessageService {

    /**
     * 发送留言
     */
    void sendMessage(String productId, String senderStudentId, String receiverStudentId, String content, String parentMessageId);

    /**
     * 根据物品编号查询留言列表
     */
    List<Message> getMessagesByProductId(String productId);

    /**
     * 根据接收者查询留言列表
     */
    List<Message> getMessagesByReceiver(String receiverStudentId);

    /**
     * 根据发送者查询留言列表
     */
    List<Message> getMessagesBySender(String senderStudentId);

    /**
     * 标记留言为已读
     */
    void markAsRead(String messageId);

    /**
     * 统计未读留言数量
     */
    Long countUnread(String receiverStudentId);
}
