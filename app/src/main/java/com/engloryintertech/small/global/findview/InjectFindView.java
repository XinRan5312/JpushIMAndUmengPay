package com.engloryintertech.small.global.findview;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.engloryintertech.small.global.utils.JMArrayAndCollectionUtile;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by qixinh on 16/9/19.
 */
public class InjectFindView {
    private static final String EVAL_ERROR = "eval error";
    private static final String VIEW_NOT_FOUND = "view not found";
    private static final Class<?>[] HALT_CLASSES = {Activity.class, Fragment.class, View.class, Object.class,
            Dialog.class};

    public static void inject(Object container) {
        inject(container, container, false);
    }

    public static void inject(Object container, boolean ignore) {
        inject(container, container, ignore);
    }

    public static void inject(Object container, Object parent) {
        inject(container, parent, false);
    }

    public static void inject(Object container, Object parent, boolean ignore) {
        Class<?> clazz = container.getClass();
        while (!JMArrayAndCollectionUtile.contains(HALT_CLASSES, clazz)) {
            Field[] fields = clazz.getDeclaredFields();
            clazz = clazz.getSuperclass();
            for (Field field : fields) {
                if (field.isAnnotationPresent(FindView.class)) {
                    FindView from = field.getAnnotation(FindView.class);
                    int id = from.value();
                    field.setAccessible(true);
                    View view = inflateView(parent, id);
                    if (!ignore && !from.canNull() && view == null) {
                        throwIdException(field, VIEW_NOT_FOUND, id);
                    } else if (view != null) {
                        try {
                            field.set(container, view);
                        } catch (Exception e) {
                            throwIdException(field, EVAL_ERROR, id);
                        }
                    }
                } else if (field.isAnnotationPresent(FindViewArray.class)) {
                    FindViewArray fromArray = field.getAnnotation(FindViewArray.class);
                    int[] ids = fromArray.value();
                    field.setAccessible(true);
                    Class<?> componentType = field.getType().getComponentType();
                    if (componentType != null) {
                        Object grown = Array.newInstance(componentType, ids.length);
                        ArrayList<View> tempArray = new ArrayList<View>(ids.length);
                        View view;
                        for (int id : ids) {
                            view = inflateView(parent, id);
                            if (!ignore && !fromArray.canNull() && view == null) {
                                throwIdException(field, VIEW_NOT_FOUND, id);
                            }
                            tempArray.add(view);
                        }
                        System.arraycopy(tempArray.toArray(), 0, grown, 0, ids.length);
                        try {
                            field.set(container, grown);
                        } catch (Exception e) {
                            throwIdException(field, EVAL_ERROR, ids);
                        }
                    }

                }
            }

        }
    }

    private static void throwIdException(Field field, String info, int... ids) {
        StringBuilder sb = new StringBuilder("field '");
        if (field != null) {
            sb.append(field.toGenericString() + "=");
        }
        if (ids!=null&&ids.length!=0) {
            sb.append("[");
            for (int id : ids) {
                sb.append("0x" + Integer.toHexString(id));
                sb.append(",");
            }
            sb.append("]");
        }
        sb.append("' not injected! Check your field or layout xml id value!");
        if (!TextUtils.isEmpty(info)) {
            sb.append("[");
            sb.append(info);
            sb.append("]");
        }
        throw new FindViewException(sb.toString());
    }

    private static View inflateView(Object parent, int id) {
        View view;
        view = null;
        if (parent instanceof Activity) {
            view = ((Activity) parent).findViewById(id);
        } else if (parent instanceof Fragment) {
            view = ((Fragment) parent).getView().findViewById(id);
        } else if (parent instanceof View) {
            view = ((View) parent).findViewById(id);
        } else if (parent instanceof Dialog) {
            view = ((Dialog) parent).findViewById(id);
        }
        return view;
    }

}
