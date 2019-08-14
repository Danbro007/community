package lfie.danbro.community.community.mapper;

import lfie.danbro.community.community.dto.NotificationDto;

import java.util.List;

public interface NotificationExtMapper {
    List<NotificationDto> getNotifications(Integer reciever);
}
