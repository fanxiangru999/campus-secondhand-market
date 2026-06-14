package com.campus.market.service.impl;

import com.campus.market.entity.Message;
import com.campus.market.mapper.MessageMapper;
import com.campus.market.service.MessageService;
import com.campus.market.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 留言服务实现类
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    @Transactional
    public void sendMessage(String productId, String senderStudentId, String receiverStudentId, String content, String parentMessageId) {
        Message message = new Message();
        message.setMessageId(IdGenerator.generateMessageId());
        message.setProductId(productId);
        message.setSenderStudentId(senderStudentId);
        message.setReceiverStudentId(receiverStudentId);
        message.setContent(content);
        message.setParentMessageId(parentMessageId);
        message.setIsRead(0);

        messageMapper.insert(message);
    }

    @Override
    public List<Message> getMessagesByProductId(String productId) {
        return messageMapper.selectByProductId(productId);
    }

    @Override
    public List<Message> getMessagesByReceiver(String receiverStudentId) {
        return messageMapper.selectByReceiver(receiverStudentId);
    }

    @Override
    public List<Message> getMessagesBySender(String senderStudentId) {
        return messageMapper.selectBySender(senderStudentId);
    }

    @Override
    @Transactional
    public void markAsRead(String messageId) {
        messageMapper.markAsRead(messageId);
    }

    @Override
    public Long countUnread(String receiverStudentId) {
        return messageMapper.countUnread(receiverStudentId);
    }
}
