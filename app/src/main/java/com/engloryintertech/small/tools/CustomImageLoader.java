package com.engloryintertech.small.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CustomImageLoader {

	private volatile static CustomImageLoader instance;
	private ImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public static CustomImageLoader getInstance(Context context) {
		if (instance == null) {
			synchronized (CustomImageLoader.class) {
				if (instance == null) {
					instance = new CustomImageLoader();
				}
			}
		}
		return instance;
	}

	protected CustomImageLoader() {
		imageLoader= ImageLoader.getInstance();
	}

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 800);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	public void displayImage(String uri, ImageView imageView,
			DisplayImageOptions options) {
		imageLoader.displayImage(uri, imageView, options, animateFirstListener);
	}

	public void displayImage(String uri, ImageView imageView) {
		imageLoader.displayImage(uri, imageView,animateFirstListener);
	}
}
