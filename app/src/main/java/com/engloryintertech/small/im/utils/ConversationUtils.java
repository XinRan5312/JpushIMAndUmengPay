package com.engloryintertech.small.im.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.CSCustomServiceInfo;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.PublicServiceProfile;
import io.rong.imlib.model.PublicServiceProfileList;

/**
 * Created by qixinh on 16/10/17.
 */
public class ConversationUtils {
    //以下会话都要在
    // RongIM.init(this)，接口已经执行。
    //RongIM.connect(....)，接口已经执行且 onSuccess() 被回调。
    /***
     * #################################################
     * 单聊：
     * 指两个用户一对一进行聊天，会话关系由融云负责建立并保持，退出聊天界面或者离线后可以收到推送通知。
     */
    /**
     * 启动会话一对一界面
     * context - 应用上下文。
     * targetUserId - 要与之聊天的用户 Id。
     * title - 聊天的标题，如果传入空值，则默认显示与之聊天的用户名称。
     * <p/>
     * 问题1：为什么 getConversationTitle()
     * 自已做会话列表，conversationTitle 是为空的，二个会话你要通过 targetId 去你的用户系统里去取，这就是需要设置 setUserInfoProvider 的原因。
     */
    public static void startOneConversation(Context activity, String targetId, String targetName) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().startPrivateChat(activity, targetId, targetName);
        }
    }
/**
 * ###################################################
 *  聊天室:

   聊天室与群组最大的不同在于，聊天室的消息没有 Push 通知，也没有成员的概念。想参与聊天室聊天，接收聊天室消息，加入聊天室即可；不参与聊天室聊天，不接收消息，
 退出聊天室即可。IMKit 组件中已经内置了加入和退出聊天室的接口调用，可直接启动
 */
    /**
     * 开启聊天室
     *
     * @param context          上下文对象
     * @param chatRoomId       聊天室id
     * @param createIfNotExist 没有是否创建
     */
    public static void startRoomChat(Context context, String chatRoomId, boolean createIfNotExist) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().startChatRoomChat(context, chatRoomId, createIfNotExist);
        }
    }

    /**
     * 开启聊天室：如果没有就创建
     *
     * @param context    上下文对象
     * @param chatRoomId 聊天室id
     */
    public static void startRoomChat(Context context, String chatRoomId) {
        startRoomChat(context, chatRoomId, true);
    }

    /**
     * 启动会话列表界面
     *
     * @param activity
     */
    public static void startConversationList(Context activity) {

        if (RongIM.getInstance() != null)
            RongIM.getInstance().startConversationList(activity, null);
    }

    /**
     * @param activity
     * @param supportedConversation:定义会话列表支持显示的会话类型，及对应的会话类型是否聚合显示。
     *                             例如：supportedConversation.put(Conversation.ConversationType.PRIVATE.getName(), false) 非聚合式显示 private 类型的会话。
     */
    public static void startConversationList(Context activity, Map<String, Boolean> supportedConversation) {

        if (RongIM.getInstance() != null)
            RongIM.getInstance().startConversationList(activity, supportedConversation);
    }

    /**
     * 启动聚合会话列表界面
     *
     * @param activity
     */
    public static void startSubConversationList(Context activity) {
        if (RongIM.getInstance() != null)
            RongIM.getInstance().startSubConversationList(activity, Conversation.ConversationType.GROUP);
    }

    /**
     * 开启系统会话(系统会话消息由应用服务端发送，客户端只能接收消息，不能进行回复)
     *
     * @param context  上下文对象
     * @param targetId 会话id
     * @param title    会话title
     */
    public static void startSystemConversation(Context context, String targetId, String title) {
        startConversation(context, Conversation.ConversationType.SYSTEM, targetId, title);
    }

    public static void startPersonServer(Context context, String targetId, String title) {
        if (RongIM.getInstance() != null) {
            //首先需要构造使用客服者的用户信息
            CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
            CSCustomServiceInfo csInfo = csBuilder.nickName("Small-客服").build();
            RongIM.getInstance().startCustomerServiceChat(context, targetId, title, csInfo);

        }
    }

    /**
     * 开启一个指定类型的会话 （这个方法尽量不要用，除非本工具类中没有其它方法满足）
     *
     * @param context          上下文对象
     * @param conversationType 会话类型（详情见Conversation.ConversationType）
     * @param targetId         会话id
     * @param title            会话title
     */
    public static void startConversation(Context context, Conversation.ConversationType conversationType, String targetId, String title) {
        if (RongIM.getInstance() != null)
            RongIM.getInstance().startConversation(context, conversationType, targetId, title);
    }
/**
 * ##########################################################
 * 讨论组：
 * 指两个以上用户一起进行聊天，用户可以自行添加好友生成一个讨论组聊天，会话关系由融云负责建立并保持，
 * 退出聊天界面或者离线后可以收到推送通知，同一个用户最多可加入 500 个讨论组。
 */
    /**
     * 启动讨论组聊天界面。
     *
     * @param activity           应用上下文。
     * @param targetDiscussionId 要聊天的讨论组 Id。
     * @param title              聊天的标题，如果传入空值，则默认显示讨论组名称
     */
    public static void startDiscussionChat(Context activity, String targetDiscussionId, String title) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().startDiscussionChat(activity, targetDiscussionId, title);
        }
    }

    /**
     * 创建讨论组会话并进入会话界面。
     * 讨论组创建成功后，会返回讨论组 id。
     *
     * @param context       应用上下文。
     * @param targetUserIds 要与之聊天的讨论组用户 Id 列表。
     * @param title         聊天的标题，如果传入空值，则默认显示与之聊天的用户名称。
     * @param callback      讨论组回调，成功时，返回讨论组 id。
     *                      同一个用户最多可加入 500 个讨论组。
     */
    public static void createDiscussionChat(final Context context, final List<String> targetUserIds, final String title, final QCreateDiscussionCallback callback) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().createDiscussionChat(context, targetUserIds, title, callback);
        }
    }

    public static void createDiscussionChat(final Context context, final List<String> targetUserIds, final String title) {
        createDiscussionChat(context, targetUserIds, title, new QCreateDiscussionCallback());
    }

    public static class QCreateDiscussionCallback extends RongIMClient.CreateDiscussionCallback {

        @Override
        public void onSuccess(String discussionId) {
            //在这里可以保存用户创建的discussionId以便以后所用
        }

        @Override
        public void onError(RongIMClient.ErrorCode errorCode) {
            //处理错误
        }
    }

    /**
     * 添加一个成员到指定讨论组
     *
     * @param discussionId 讨论组id
     * @param userId       成员id
     */
    public static void addOneFriendToDiscussion(String discussionId, String userId) {
        ArrayList<String> userIds = new ArrayList<String>();
        userIds.add(userId);
        addFriendsToDiscussion(discussionId, userIds);
    }

    /**
     * 添加一组成员到指定讨论组
     *
     * @param discussionId 讨论组id
     * @param userIds      一组成员的ids
     */
    public static void addFriendsToDiscussion(String discussionId, ArrayList<String> userIds) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().addMemberToDiscussion(discussionId, userIds, new QOperationCallback(1));
        }

    }

    /**
     * 添加一组成员到指定讨论组
     *
     * @param discussionId 讨论组id
     * @param userIds      一组成员的ids
     * @param callback     回调Back
     */
    public static void addFriendsToDiscussion(String discussionId, ArrayList<String> userIds, QOperationCallback callback) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().addMemberToDiscussion(discussionId, userIds, callback);
        }

    }

    public static class QOperationCallback extends RongIMClient.OperationCallback {
        private int type;
        public static final int ADD_MEMBER=010;
        public static final int REMOVE_MEMBER=011;
        public QOperationCallback(int type) {
            this.type = type;
        }

        @Override
        public void onSuccess() {
            //成功提示
        }

        @Override
        public void onError(RongIMClient.ErrorCode errorCode) {
            //错误处理
            //移出自己或者调用者非讨论组创建者将产生 {@link RongIMClient.ErrorCode#UNKNOWN} 错误

        }
    }

    /**
     * 移除一成员到指定讨论组
     *
     * @param discussionId 讨论组id
     * @param userId       一成员的id
     */
    public static void removeOneFriendFromDiscussion(String discussionId, String userId) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().removeMemberFromDiscussion(discussionId, userId, new QOperationCallback(0));
        }
    }
/**
 * ###########################################################
 * 群组聊天:
 指两个以上用户一起进行聊天，与讨论组不同的是，参与群组聊天的群成员 Id 集合由您的 App 提供并维系，
 融云只负责将消息传达给群聊中的所有用户，退出聊天界面或者离线后可以收到推送通知。
 支持同一用户最多可加入 500 个群，每个群最大至 3000 人的大型社群需求，App 内的群组数量没有限制。

 所以，当界面组件创建会话需要显示群组信息时，需要向 App 获取。App 需要设置一个群组信息提供者给 IMKit，以便 IMKit 读取好友关系。
 跟用户信息提供者很类似 RongIM.setUserInfoProvider(this, true);
 */


    /**
     * 开启群组会话
     *
     * @param context 上下文对象
     * @param groupId 群id
     * @param title   群title
     */
    public static void startGroupChat(Context context, String groupId, String title) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().startGroupChat(context, groupId, title);
        }
    }
/**
 * 客户端的所有群组操作都需要请求您的 App Server（包括增删改查）， 您的 App Server 可以根据自己的逻辑进行管理和控制，然后通过 Server API 接口进行群组操作，并将结果返回给客户端。
 * 官网有开发指南：http://www.rongcloud.cn/docs/android.html#_UI_自定义   群组
 * #################################################################
 */


    /**#############################################################
     * 公众号：
     * 公众服务包括：应用公众服务和公众服务平台。

     应用公众服务：是为应用开发者提供的 App 内建公众服务能力，通过在融云开发者站点创建 App 公众号，实现应用内的公众服务。

     公众服务平台：是在应用开发者和公众帐号运营者之间建立的对接平台，应用开发者可以通过平台引入公众服务资源，
     帮助 App 快速覆盖用户需求，公众帐号持有者通过平台可以有机会向所有集成融云 SDK 的 App 提供服务，进而获得更加精准更加丰富的受众渠道
     */
    /**
     * 启动应用公众服务会话界面。
     *
     * @param context  应用上下文。
     * @param targetId 目标 Id。
     * @param title    聊天的标题。
     */
    public static void startAppPublicService(Context context, String targetId, String title) {
        startConversation(context, Conversation.ConversationType.APP_PUBLIC_SERVICE, targetId, title);
    }

    /**
     * 启动公众服务会话界面。
     *
     * @param context  应用上下文。
     * @param targetId 目标 Id。
     */
    public static void startPublicService(Context context, String targetId, String title) {
        startConversation(context, Conversation.ConversationType.PUBLIC_SERVICE, targetId, title);
    }

    /**
     * 打开应用公众服务信息界面：
     *
     * @param context  应用上下文。
     * @param targetId 目标 Id。
     */
    public static void startAppPublicServiceInfo(Context context, String targetId) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().startPublicServiceProfile(context, Conversation.ConversationType.APP_PUBLIC_SERVICE, targetId);
        }

    }

    /**
     * 打开公众服务信息界面：
     *
     * @param context  应用上下文。
     * @param targetId 目标 Id。
     */
    public static void startPublicServiceInfo(Context context, String targetId) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().startPublicServiceProfile(context, Conversation.ConversationType.PUBLIC_SERVICE, targetId);
        }

    }

    /**
     * 搜索所有会话类型的公众号
     * <p/>
     * searchType - 搜索类型枚举。包括模糊和精确两种
     * keywords - 搜索关键字。
     * callback - 搜索结果回调。
     */
    public static void searchPublicService(RongIMClient.SearchType searchType, String keyords, QSearchCallBack callback) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().searchPublicService(searchType, keyords, callback);
        }

    }

    /**
     * 按会话类型搜索公众号
     * <p/>
     * conType -会话类型
     * searchType - 搜索类型枚举。包括模糊和精确两种
     * keywords - 搜索关键字。
     * callback - 搜索结果回调。
     */
    public static void searchPublicServiceByType(Conversation.PublicServiceType conType, RongIMClient.SearchType searchType, String keyords, QSearchCallBack callback) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().searchPublicServiceByType(conType, searchType, keyords, callback);
        }

    }

    /**
     * 获取己关注公共账号列表
     * @param callback
     */
    public static void getAttentionPublicServiceList(QSearchCallBack callback){
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().getPublicServiceList(callback);
        }
    }
    /**
     * 按公众服务类型搜索公众服务。
     * @param publicServiceId    公众号 Id。
     */
    public static void getOnePublicService(String publicServiceId) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().getPublicServiceProfile(Conversation.PublicServiceType.PUBLIC_SERVICE, publicServiceId, new RongIMClient.ResultCallback<PublicServiceProfile>() {
                @Override
                public void onSuccess(PublicServiceProfile publicServiceProfile) {

                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }
    public static class QSearchCallBack extends RongIMClient.ResultCallback<PublicServiceProfileList> {
        private int type;//0是指全部类型搜索；1是指按会话类型搜索；2是指获取已关注公众号列表
        public final static int ALL_SEARCH=0100;//是指全部类型搜索
        public final static int CONVERSAION_TYPE_SEARCH=0101;//指按会话类型搜索
        public final static int ATTENTION_SEARCH=0110;//指获取已关注公众号列表
        public QSearchCallBack(int type) {
            this.type = type;
        }

        @Override
        public void onSuccess(PublicServiceProfileList publicServiceProfileList) {

        }

        @Override
        public void onError(RongIMClient.ErrorCode errorCode) {

        }
    }

    /**
     * 收到RongPushReceiver消息后
     * 只有一个联系人发来一条或者多条消息时，会通过 intent 隐式启动会话 activity，intent 的 uri 如下：
     * 对应启动ConversationActivity 聊天会话
     *
     * @param activity
     * @param targetName
     * @param targetId
     */
    public static void receiverOneConversation(Context activity, String targetName, String targetId) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri.Builder builder = Uri.parse("rong://" + activity.getPackageName()).buildUpon();

        builder.appendPath("conversation")
                .appendQueryParameter("targetId", targetId)
                .appendQueryParameter("title", targetName)
                .appendQueryParameter("isFromPush","true");
        Uri uri = builder.build();
        uri = builder.build();
        intent.setData(uri);
        activity.startActivity(intent);
    }

    /**
     * 收到RongPushReceiver消息后
     * 多个联系人发来多条消息时，通过 intent 隐式启动会话列表 activity，intent 的 uri 配置如下：
     * 对应启动ConversationListActivity会话列表
     *
     * @param activity
     */
    public static void receiverMoreConversation(Context activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri.Builder builder = Uri.parse("rong://" + activity.getPackageName()).buildUpon();
        builder.appendPath("conversationlist")
                .appendQueryParameter("isFromPush","true");
        Uri uri = builder.build();
        intent.setData(uri);
        activity.startActivity(intent);
    }

    /**
     * 换取 Token 需要提供 App Key 和 App Secret，在客户端请求 Token，您的客户端代码一旦被反编译，会导致您的 App Key 和 App Secret 泄露。所以，请务必确保在服务端换取 Token。
     *
     * @param appKey
     * @param appSecret
     * @return
     */

    public static String GetRongCloudToken(String appKey, String appSecret) {
        return "";
    }


}
