package lfie.danbro.community.community.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lfie.danbro.community.community.Enum.NotificationStatusEnum;
import lfie.danbro.community.community.dto.NotificationDto;
import lfie.danbro.community.community.mapper.CommentMapper;
import lfie.danbro.community.community.mapper.NotificationExtMapper;
import lfie.danbro.community.community.mapper.NotificationMapper;
import lfie.danbro.community.community.mapper.QuestionMapper;
import lfie.danbro.community.community.model.Notification;
import lfie.danbro.community.community.model.NotificationExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationExtMapper notificationExtMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    NotificationMapper notificationMapper;

    /**
     *
     * @param reciever 消息接受者
     * @param page 页数
     * @param size 每页数量
     * @return 消息列表
     */
    public PageInfo<NotificationDto> getNotifications(Integer reciever,Integer page,Integer size){
        PageHelper.startPage(page,size);
        List<NotificationDto> notifications = notificationExtMapper.getNotifications(reciever);
        return new PageInfo<>(notifications);
    }

    /**
     *
     * @param notificationId 消息id
     * @param userId 用户id
     * @return 消息对象
     */
    public Notification getNotificationByUserId(Long notificationId, Integer userId){
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andIdEqualTo(notificationId).andRecieverEqualTo(userId);
        List<Notification> notifications = notificationMapper.selectByExample(notificationExample);
        notificationRead(notifications.get(0));
        return notifications.get(0);
    }

    /**
     * 返回用户所有未读信息的数量
     * @param userId 用户ID
     * @return 未读信息的数量
     */
    public Long getUnReadCount(Integer userId){
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andNotifierEqualTo(userId).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }


    /**
     * 把通知改成已读状态
     * @param notification 通知对象
     */
    public void notificationRead(Notification notification){
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andIdEqualTo(notification.getId());
        notificationMapper.updateByExampleSelective(notification,notificationExample);
    }


}
