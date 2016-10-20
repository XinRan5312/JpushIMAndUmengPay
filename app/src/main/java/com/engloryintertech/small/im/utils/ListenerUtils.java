package com.engloryintertech.small.im.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import io.rong.message.LocationMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * Created by qixinh on 16/10/17.
 */
public class ListenerUtils {

    public final static String TAG = ListenerUtils.class.getSimpleName();

    /**
     * 获取发出消息监听器
     * 设置自己发出消息的监听器，必须在 connect 连接成功以后调用。
     */
    public static void setDefaultSendMessageListener() {
        if (RongIM.getInstance() != null) {
            //设置自己发出的消息监听器。
            RongIM.getInstance().setSendMessageListener(new QSendMessageListener());
        }
    }

    public static void setSendMessageListener(RongIM.OnSendMessageListener sendMessageListener) {
        if (RongIM.getInstance() != null) {
            //设置自己发出的消息监听器。

            if (sendMessageListener != null) {
                RongIM.getInstance().setSendMessageListener(sendMessageListener);
            } else {
                RongIM.getInstance().setSendMessageListener(new QSendMessageListener());
            }
        }
    }

    /**
     * 接收消息监听
     * 接收消息的监听器，在调用 connect 方法前进行设置。
     */
    public static void setDefaultReceiveMessageListenr() {

        //设置接收消息的监听器。
        RongIM.setOnReceiveMessageListener(new QReceiveMessageListener());

    }

    public static void setReceiveMessageListenr(RongIMClient.OnReceiveMessageListener receiveMessageListenr) {
        //设置接收消息的监听器。
        if (receiveMessageListenr != null) {
            RongIM.setOnReceiveMessageListener(receiveMessageListenr);
        } else {
            RongIM.setOnReceiveMessageListener(new QReceiveMessageListener());
        }
    }

    /**
     * 连接状态监听
     * 设置连接状态监听，必须在 init 后进行调用。
     */
    public static void setDefaultConnectionStatusListener() {
        if (RongIM.getInstance() != null) {
            /**
             * 设置连接状态变化的监听器.
             */
            RongIM.getInstance().setConnectionStatusListener(new QConnectionStatusListener());
        }
    }

    public static void setConnectionStatusListener(RongIMClient.ConnectionStatusListener connectionStatusListener) {
        if (RongIM.getInstance() != null) {
            /**
             * 设置连接状态变化的监听器.
             */
            if (connectionStatusListener != null) {
                RongIM.getInstance().setConnectionStatusListener(connectionStatusListener);
            } else {
                RongIM.getInstance().setConnectionStatusListener(new QConnectionStatusListener());
            }
        }
    }

    /**
     * 会话列表操作监听
     * 会话列表操作监听，在调用 connect 前进行设置。
     */
    public static void setDefaultConversationListBehaviorListener() {
        /**
         * 设置会话列表界面操作的监听器。
         */
        RongIM.setConversationListBehaviorListener(new QConversationListBehaviorListener());

    }

    public static void setConversationListBehaviorListener(RongIM.ConversationListBehaviorListener conversationListBehaviorListener) {

        /**
         * 设置会话列表界面操作的监听器。
         */
        if (conversationListBehaviorListener != null) {
            RongIM.setConversationListBehaviorListener(conversationListBehaviorListener);
        } else {
            RongIM.setConversationListBehaviorListener(new QConversationListBehaviorListener());
        }

    }

    /**
     * 会话界面操作的监听器
     * 会话界面操作的监听器，在调用 connect 前进行设置。
     */
    public static void setDefaultConversationBehaviorListener() {
       /**
        * 设置会话界面操作的监听器。
        */
        RongIM.setConversationBehaviorListener(new QConversationBehaviorListener());
    }
    public static void setConversationBehaviorListener(RongIM.ConversationBehaviorListener conversationBehaviorListener) {
        /**
         * 设置会话界面操作的监听器。
         */
        if(conversationBehaviorListener!=null){
            RongIM.setConversationBehaviorListener(conversationBehaviorListener);
        }else{
            RongIM.setConversationBehaviorListener(new QConversationBehaviorListener());
        }

    }

    /**
     * 未读消息数监听器
     未读消息数监听器，必须在 connect 连接成功以后调用。融云提供两个监听设置方法，一是设置所有未读消息的监听器，另一个是按会话类型设置消息监听。
     */
    public static void setDefaultReceiveUnreadCountChangedListene(Conversation.ConversationType... conversationTypes){
          if(conversationTypes==null){
              /**
               * 接收未读消息的监听器。
               *
               * @param listener          接收所有未读消息消息的监听器。
               */
              RongIM.getInstance().setOnReceiveUnreadCountChangedListener(new QReceiveUnreadCountChangedListener());
          }else{
              /**
               * 设置接收未读消息的监听器。
               *
               * @param listener          接收未读消息消息的监听器。
               * @param conversationTypes 接收指定会话类型的未读消息数。
               */
              RongIM.getInstance().setOnReceiveUnreadCountChangedListener(new QReceiveUnreadCountChangedListener(), conversationTypes);
          }
    }

    public static void setReceiveUnreadCountChangedListene(RongIM.OnReceiveUnreadCountChangedListener receiveUnreadCountChangedListener,Conversation.ConversationType... conversationTypes){
        if(conversationTypes==null){
            /**
             * 接收未读消息的监听器。
             *
             * @param listener  接收所有未读消息消息的监听器。
             */
            if(receiveUnreadCountChangedListener!=null){
                RongIM.getInstance().setOnReceiveUnreadCountChangedListener(receiveUnreadCountChangedListener);
            }else{
                RongIM.getInstance().setOnReceiveUnreadCountChangedListener(new QReceiveUnreadCountChangedListener());
            }

        }else{
            /**
             * 设置接收未读消息的监听器。
             * @param listener          接收未读消息消息的监听器。
             * @param conversationTypes 接收指定会话类型的未读消息数。
             */
            if(receiveUnreadCountChangedListener!=null){
                RongIM.getInstance().setOnReceiveUnreadCountChangedListener(receiveUnreadCountChangedListener, conversationTypes);
            }else{
                RongIM.getInstance().setOnReceiveUnreadCountChangedListener(new QReceiveUnreadCountChangedListener(), conversationTypes);
            }

        }
    }
    public static class QReceiveUnreadCountChangedListener implements RongIM.OnReceiveUnreadCountChangedListener {

        /**
         * @param count           未读消息数。
         */
        @Override
        public void onMessageIncreased(int count) {

        }
    }
    public static class QConversationBehaviorListener implements RongIM.ConversationBehaviorListener {

        /**
         * 当点击用户头像后执行。
         *
         * @param context          上下文。
         * @param conversationType 会话类型。
         * @param userInfo         被点击的用户的信息。
         * @return 如果用户自己处理了点击后的逻辑，则返回 true，否则返回 false，false 走融云默认处理方式。
         */
        @Override
        public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
//            Intent in = new Intent(context, UserPortraitClickAct.class);
//            context.startActivity(in);
            return false;
        }

        /**
         * 当长按用户头像后执行。
         *
         * @param context          上下文。
         * @param conversationType 会话类型。
         * @param userInfo         被点击的用户的信息。
         * @return 如果用户自己处理了点击后的逻辑，则返回 true，否则返回 false，false 走融云默认处理方式。
         */
        @Override
        public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
            return false;
        }

        /**
         * 当点击消息时执行。
         *
         * @param context 上下文。
         * @param view    触发点击的 View。
         * @param message 被点击的消息的实体信息。
         * @return 如果用户自己处理了点击后的逻辑，则返回 true， 否则返回 false, false 走融云默认处理方式。
         */
        @Override
        public boolean onMessageClick(Context context, View view, Message message) {
            //点击消息处理事件，示例代码展示了如何获得消息内容
            if (message.getContent() instanceof LocationMessage) {
//                Intent intent = new Intent(context, MessageClickAct.class);
//                intent.putExtra("location", message.getContent());
//                context.startActivity(intent);

            } else if (message.getContent() instanceof RichContentMessage) {
                RichContentMessage mRichContentMessage = (RichContentMessage) message.getContent();
                Log.d("Begavior", "extra:" + mRichContentMessage.getExtra());

            }

            Log.d("Begavior", message.getObjectName() + ":" + message.getMessageId());
            return false;
        }

        /**
         * 当长按消息时执行。
         *
         * @param context 上下文。
         * @param view    触发点击的 View。
         * @param message 被长按的消息的实体信息。
         * @return 如果用户自己处理了长按后的逻辑，则返回 true，否则返回 false，false 走融云默认处理方式。
         */
        @Override
        public boolean onMessageLongClick(Context context, View view, Message message) {
            return false;
        }

        /**
         * 当点击链接消息时执行。
         *
         * @param context 上下文。
         * @param link    被点击的链接。
         * @return 如果用户自己处理了点击后的逻辑处理，则返回 true， 否则返回 false, false 走融云默认处理方式。
         */
        @Override
        public boolean onMessageLinkClick(Context context, String link) {
            return false;
        }
    }

    public static class QConversationListBehaviorListener implements RongIM.ConversationListBehaviorListener {
        /**
         * 当点击列表用户头像后执行。
         *
         * @param context          上下文。
         * @param conversationType 会话类型。
         * @param userInfo         被点击的用户的信息。
         * @return 如果用户自己处理了点击后的逻辑，则返回 true，否则返回 false，false 走融云默认处理方式。
         */
        @Override
        public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String userInfo) {
            return false;
        }

        /**
         * 当长按列表用户头像后执行。
         *
         * @param context          上下文。
         * @param conversationType 会话类型。
         * @param userInfo         被点击的用户的信息。
         * @return 如果用户自己处理了点击后的逻辑，则返回 true，否则返回 false，false 走融云默认处理方式。
         */
        @Override
        public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String userInfo) {
            return false;
        }

        /**
         * 长按会话列表中的 item 时执行。
         *
         * @param context        上下文。
         * @param view           触发点击的 View。
         * @param uiConversation 长按时的会话条目。
         * @return 如果用户自己处理了长按会话后的逻辑处理，则返回 true， 否则返回 false，false 走融云默认处理方式。
         */
        @Override
        public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
            return false;
        }

        /**
         * 点击会话列表中的 item 时执行。
         *
         * @param context        上下文。
         * @param view           触发点击的 View。
         * @param uiConversation 会话条目。
         * @return 如果用户自己处理了点击会话后的逻辑处理，则返回 true， 否则返回 false，false 走融云默认处理方式。
         */
        @Override
        public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
            return false;
        }
    }

    public static class QConnectionStatusListener implements RongIMClient.ConnectionStatusListener {

        @Override
        public void onChanged(ConnectionStatus connectionStatus) {

            switch (connectionStatus) {

                case CONNECTED://连接成功。

                    break;
                case DISCONNECTED://断开连接。

                    break;
                case CONNECTING://连接中。

                    break;
                case NETWORK_UNAVAILABLE://网络不可用。

                    break;
                case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线

                    break;
            }
        }
    }

    public static class QReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {

        /**
         * 收到消息的处理。
         *
         * @param message 收到的消息实体。
         * @param left    剩余未拉取消息数目。
         * @return 收到消息是否处理完成，true 表示走自已的处理方式，false 走融云默认处理方式。
         */
        @Override
        public boolean onReceived(Message message, int left) {
            //开发者根据自己需求自行处理
            return false;
        }
    }

    public static class QSendMessageListener implements RongIM.OnSendMessageListener {

        /**
         * 消息发送前监听器处理接口（是否发送成功可以从 SentStatus 属性获取）。
         *
         * @param message 发送的消息实例。
         * @return 处理后的消息实例。
         */
        @Override
        public Message onSend(Message message) {
            //开发者根据自己需求自行处理逻辑
            return message;
        }

        /**
         * 消息在 UI 展示后执行/自己的消息发出后执行,无论成功或失败。
         *
         * @param message              消息实例。
         * @param sentMessageErrorCode 发送消息失败的状态码，消息发送成功 SentMessageErrorCode 为 null。
         * @return true 表示走自已的处理方式，false 走融云默认处理方式。
         */
        @Override
        public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {

            if (message.getSentStatus() == Message.SentStatus.FAILED) {
                if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_CHATROOM) {
                    //不在聊天室
                } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_DISCUSSION) {
                    //不在讨论组
                } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_GROUP) {
                    //不在群组
                } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.REJECTED_BY_BLACKLIST) {
                    //你在他的黑名单中
                }
            }

            MessageContent messageContent = message.getContent();

            if (messageContent instanceof TextMessage) {//文本消息
                TextMessage textMessage = (TextMessage) messageContent;
                Log.d(TAG, "onSent-TextMessage:" + textMessage.getContent());
            } else if (messageContent instanceof ImageMessage) {//图片消息
                ImageMessage imageMessage = (ImageMessage) messageContent;
                Log.d(TAG, "onSent-ImageMessage:" + imageMessage.getRemoteUri());
            } else if (messageContent instanceof VoiceMessage) {//语音消息
                VoiceMessage voiceMessage = (VoiceMessage) messageContent;
                Log.d(TAG, "onSent-voiceMessage:" + voiceMessage.getUri().toString());
            } else if (messageContent instanceof RichContentMessage) {//图文消息
                RichContentMessage richContentMessage = (RichContentMessage) messageContent;
                Log.d(TAG, "onSent-RichContentMessage:" + richContentMessage.getContent());
            } else {
                Log.d(TAG, "onSent-其他消息，自己来判断处理");
            }

            return false;
        }
    }
}
