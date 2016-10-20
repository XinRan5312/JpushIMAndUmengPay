package com.engloryintertech.small.im.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.ContactsContract;
import android.view.View;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.ConversationProviderTag;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.PrivateConversationProvider;
import io.rong.imkit.widget.provider.TextMessageItemProvider;
import io.rong.message.TextMessage;

/**
 * Created by qixinh on 16/10/12.
 */
public class CustomUIUtitls {
    /**
     * 头像位置自定义
     * 自定义步骤：
     * <p/>
     * 1、新建一类继承要改变的会话提供者类，然后重写注解，修改 portraitPosition 的值以完成显示方式。
     * <p/>
     * 注解说明：
     *   注解名称：ConversationProviderTag 。属性：conversationType ，portraitPosition 。
     * conversationType 的值不能重复不可修改，它是会话提供者的唯一标识；portraitPosition 用来控制头像的显示方式，它的值可以修改，它的值有：1：靠左显示， 2：靠右显示， 3：不显示。
     * 默认都是靠左显示
     * 例如：单聊会话头像靠右显示。然后在init之后注册新的模板就好RongIM.getInstance().registerConversationTemplate(new QPrivateConversationProvider());
     */
    @ConversationProviderTag(conversationType = "private", portraitPosition = 2)
    public static class QPrivateConversationProvider extends PrivateConversationProvider {
    }

    /**
     * 会话页面自定义
     *   融云 IMKit SDK 中每一种消息类型（要在 UI 展示的）都对应一个 UI 展示的 Provider，开发者可以修改 Provider 的注解属性来完成消息显示的自定义。
     * 自定义步骤：
     * <p/>
     * 1、新建一类并继承要修改的消息提供者类，然后重写注解。
     * <p/>
     * 注解说明：
     *     注解名称：ProviderTag。
     * <p/>
     * 注解属性：
     * messageContent 　　	    对应的消息类型 ( 如：TextMessage.class )。
     * showPortrait	        设置是否显示头像，默认为 true。
     * centerInHorizontal	    消息内容是否横向居中，默认 false。
     * hide	                是否隐藏消息， 默认 false。
     * showProgress	        是否显示发送进度，默认 true。
     * showSummaryWithName	是否在会话的内容体里显示发送者名字，默认 true。
     */
    //文本消息 ( TextMessage ) 不显示头像且消息内容横向居中显示。在init后调用RongIM.getInstance().registerMessageTemplate(new QTextMessageItemProvider);
    @ProviderTag(messageContent = TextMessage.class, showPortrait = false, centerInHorizontal = true)
    public class QTextMessageItemProvider extends TextMessageItemProvider {
    }

    /**
     * 输入框自定义
       在会界面中可以设置输入框的模式。针对聊天会话的语音/文本切换功能、内容输入功能、扩展功能，融云目前提供了 九 种排列组合模式，
     该 九 种形式定义在 rc_conversation_attrs.xml 的 InputView styleable 里查看：
     SCE	语音/文本切换功能+内容输入功能+扩展功能
     ECS	扩展功能+内容输入功能+语音/文本切换功能
     CSE	内容输入功能+语音/文本切换功能+扩展功能
     CES	内容输入功能+扩展功能+语音/文本切换功能
     SC	    语音/文本切换功能+内容输入功能
     CS	    内容输入功能+语音/文本切换功能
     EC	    扩展功能+内容输入功能
     CE	    内容输入功能+扩展功能
     C	    内容输入功能

     用户可以通过更改 rc_fr_messageinput.xml 里 app:RCStyle="SCE" ，更改默认输入显示形式。

     <io.rong.imkit.widget.InputView xmlns:android="http://schemas.android.com/apk/res/android"
     xmlns:app="http://schemas.android.com/apk/res-auto"
     app:style="SCE"
     />
     */


    /**
     * 会话扩展功能自定义
     *   扩展功能在已默认支持照片、拍照、地理位置、语音通话等功能的情况下，新增自定义功能，如开发者插入自己的默认表情包等。
     * 用户需要自定义一个 provider ，继承 ExtendProvider，
     * 然后调用 RongIM.resetInputExtensionProvider(Conversation.ConversationType conversationType, InputProvider.ExtendProvider[] providers)，把你自定义的 provider 加入到 ExtendProvider 数组中即可。
     */
    //自定义的一个通讯录提供者
    public static class QContactsProvider extends InputProvider.ExtendProvider {

        HandlerThread mWorkThread;
        Handler mUploadHandler;
        private int REQUEST_CONTACT = 20;

        public QContactsProvider(RongContext context) {
            super(context);
            mWorkThread = new HandlerThread("QContactsProvider");
            mWorkThread.start();
            mUploadHandler = new Handler(mWorkThread.getLooper());
        }

        /**
         * 设置展示的图标
         *
         * @param context
         * @return
         */
        @Override
        public Drawable obtainPluginDrawable(Context context) {
            //R.drawable.de_contacts 通讯录图标
//        return context.getResources().getDrawable(R.drawable.de_contacts);
            return null;
        }

        /**
         * 设置图标下的title
         *
         * @param context
         * @return
         */
        @Override
        public CharSequence obtainPluginTitle(Context context) {
            //R.string.add_contacts 通讯录
//        return context.getString("");
            return "";
        }

        /**
         * click 事件
         *
         * @param view
         */
        @Override
        public void onPluginClick(View view) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setData(ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, REQUEST_CONTACT);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (resultCode != Activity.RESULT_OK)
                return;

            if (data.getData() != null && "content".equals(data.getData().getScheme())) {
                mUploadHandler.post(new QRunnable(data.getData()));
            }

            super.onActivityResult(requestCode, resultCode, data);
        }

        class QRunnable implements Runnable {

            Uri mUri;

            public QRunnable(Uri uri) {
                mUri = uri;
            }

            @Override
            public void run() {
                String[] contact = getPhoneContacts(mUri);

                String showMessage = contact[0] + "\n" + contact[1];
                final TextMessage content = TextMessage.obtain(showMessage);

                if (RongIM.getInstance() != null)
                    MessageUtils.sendCommonMessage(MessageUtils.createMessage(getCurrentConversation().getTargetId(), getCurrentConversation().getConversationType(), content), null, null);
            }
        }

        private String[] getPhoneContacts(Uri uri) {

            String[] contact = new String[2];
            ContentResolver cr = getContext().getContentResolver();
            Cursor cursor = cr.query(uri, null, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                contact[0] = cursor.getString(nameFieldColumnIndex);

                String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);

                if (phone != null) {
                    phone.moveToFirst();
                    contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phone.close();
                cursor.close();
            }
            return contact;
        }
    }
}
