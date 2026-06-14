package com.campus.market.mapper;

import com.campus.market.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 留言Mapper接口
 */
@Mapper
public interface MessageMapper {

    /**
     * 插入留言
     */
    int insert(Message message);

    /**
     * 根据留言编号查询
     */
    Message selectByMessageId(@Param("messageId") String messageId);

    /**
     * 根据物品编号查询留言列表
     */
    List<Message> selectByProductId(@Param("productId") String productId);

    /**
     * 根据接收者学号查询留言列表
     */
    List<Message> selectByReceiver(@Param("receiverStudentId") String receiverStudentId);

    /**
     * 根据发送者学号查询留言列表
     */
    List<Message> selectBySender(@Param("senderStudentId") String senderStudentId);

    /**
     * 标记留言为已读
     */
    int markAsRead(@Param("messageId") String messageId);

    /**
     * 统计未读留言数量
     */
    Long countUnread(@Param("receiverStudentId") String receiverStudentId);
}
