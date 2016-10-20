package com.engloryintertech.small.im.utils;

import android.net.Uri;

import java.io.File;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ImageMessage;
import io.rong.message.LocationMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * Created by qixinh on 16/10/13.
 */
public class MessageUtils {

    /**
     * 创建文字消息体
     * @param strMsg
     * @return
     */
    public static TextMessage creatTextMessageContent(String strMsg){
        return TextMessage.obtain(strMsg);
    }

    /**
     * 创建地理位置信息题
     * @param lat 经度
     * @param lng 维度
     * @param cityName 城市名字
     * @param uriMap  定位的地图uri
     * @return
     */
    public static LocationMessage createLocationMessageContent(double lat,double lng,String cityName,Uri uriMap){
        return LocationMessage.obtain(lat, lng, cityName, uriMap);
    }

    /**
     * 创建图片消息实体
     */
    public static ImageMessage createImageMessageContent(File imageFileThumb,File imageFileSource){

        return ImageMessage.obtain(Uri.fromFile(imageFileThumb), Uri.fromFile(imageFileSource));
    }
    public static ImageMessage createImageMessageContent(Uri thumb,Uri source){

        return ImageMessage.obtain(thumb, source);
    }
    /**
     * 创建语音消息实体
     */
    public static VoiceMessage createVoiceMessageContent(File voiceMessageFile,int duration){

        return VoiceMessage.obtain(Uri.fromFile(voiceMessageFile), duration);
    }
    public static VoiceMessage createVoiceMessageContent(Uri voiceUri,int duration){

        return VoiceMessage.obtain(voiceUri, duration);
    }

    /**
     * 创建图文消息体
     * @param title
     * @param content
     * @param urlImg
     * @return
     */
    public static RichContentMessage createRichContentMessageContent(String title,String content,String urlImg){
        return RichContentMessage.obtain(title, content, urlImg);
    }
    /**
     * 创建一条发送消息
     * @param targetId  目标Id可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id
     * @param conversationType 聊天类型
     * @param msgContent  消息体包括文字 语音 图片等
     * @return
     */
    public static Message createMessage(String targetId,Conversation.ConversationType conversationType,MessageContent msgContent){

        return Message.obtain(targetId, conversationType, msgContent);
    }

    /**
     * <p>发送消息。
     * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
     * 中的方法回调发送的消息状态及消息体。</p>
     *
     * @param message     将要发送的消息体。
     * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
     *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
     *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
     * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
     *
     */
    public static void sendCommonMessage(Message message,String pushContent,String pushData){
        if(RongIM.getInstance()!=null){
            RongIM.getInstance().sendMessage(message, pushContent, pushData, new IRongCallback.ISendMessageCallback(){
                @Override
                public void onAttached(Message message) {
                    //消息本地数据库存储成功的回调
                }

                @Override
                public void onSuccess(Message message) {
                    //消息通过网络发送成功的回调
                }

                @Override
                public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                    //消息发送失败的回调
                }
            });
        }
    }
    /**
     * <p>发送地理位置消息。
     * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
     * 中的方法回调发送的消息状态及消息体。</p>
     *
     * @param message     将要发送的消息体。
     * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
     *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
     *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
     * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
     * 创建消息的时候要使用LocationMessage消息体，其中的缩略图地址 scheme 只支持 file:// 和 http:// 其他暂不支持。
     */
    public static void sendLocationMessage(Message message,String pushContent,String pushData){

        if(message.getContent() instanceof LocationMessage) {
            if (RongIM.getInstance() != null) {
                RongIM.getInstance().sendLocationMessage(message, pushContent, pushData, new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                        //消息本地数据库存储成功的回调
                    }

                    @Override
                    public void onSuccess(Message message) {
                        //消息通过网络发送成功的回调
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        //消息发送失败的回调
                    }
                });
            }
        }else {
            throw new IllegalArgumentException("sendLocationMessage请使用LocationMessage消息体");
        }
    }
    /**
     * <p>根据会话类型，发送图片消息。</p>
     *
     * @param type        会话类型。
     * @param targetId    目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
     * @param content     消息内容，例如 {@link TextMessage}, {@link ImageMessage}。
     * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
     *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
     *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
     * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
     *
     *   注：图片消息包括两个主要部分：缩略图和大图，缩略图直接 Base64 编码后放入 content 中，大图首先上传到文件服务器（融云 SDK 中默认上传到七牛云存储，图片有效期为 1 个月。），
     *                    然后将云存储上的大图地址放入消息体中。
     */
    public static void sendImageMesssage(Conversation.ConversationType type, String targetId, ImageMessage content, String pushContent, String pushData){
        if(RongIM.getInstance()!=null){
            RongIM.getInstance().sendImageMessage(Conversation.ConversationType.PRIVATE, targetId, content, pushContent, pushData, new RongIMClient.SendImageMessageCallback() {

                @Override
                public void onAttached(Message message) {
                    //保存数据库成功
                }

                @Override
                public void onError(Message message, RongIMClient.ErrorCode code) {
                    //发送失败
                }

                @Override
                public void onSuccess(Message message) {
                    //发送成功
                }

                @Override
                public void onProgress(Message message, int progress) {
                    //发送进度
                }
            });
        }
    }
}
