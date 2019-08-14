package lfie.danbro.community.community.dto;

import lfie.danbro.community.community.model.Notification;
import lfie.danbro.community.community.model.User;
import lombok.Data;


@Data
public class NotificationDto extends Notification {
    private User notifierUser;
}



