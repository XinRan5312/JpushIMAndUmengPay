package com.engloryintertech.small.tools;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;

import com.engloryintertech.small.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public final class ImageLoaderOptions {

	public final static DisplayImageOptions User_Pic_Option() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		DisplayImageOptions PUBLISHER_SMALL_OPTION = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.mipmap.no_user_header)
				.considerExifParams(true)
				.showImageForEmptyUri(R.mipmap.no_user_header)
				.showImageOnFail(R.mipmap.no_user_header)
				.cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Config.RGB_565)
				.decodingOptions(options).considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		return PUBLISHER_SMALL_OPTION;
	}

	public final static DisplayImageOptions Resuce_Pic_Option_Card() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		DisplayImageOptions PUBLISHER_SMALL_OPTION = new DisplayImageOptions.Builder()
				.considerExifParams(true)
				.showImageForEmptyUri(R.mipmap.card_l)
				.showImageOnFail(R.mipmap.card_l)
				.cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
				.decodingOptions(options).considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		return PUBLISHER_SMALL_OPTION;
	}

	public final static DisplayImageOptions Resuce_Pic_Option_Authority() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		DisplayImageOptions PUBLISHER_SMALL_OPTION = new DisplayImageOptions.Builder()
				.considerExifParams(true)
				.showImageForEmptyUri(R.mipmap.jiangbei_l)
				.showImageOnFail(R.mipmap.jiangbei_l)
				.cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
				.decodingOptions(options).considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		return PUBLISHER_SMALL_OPTION;
	}

	public final static DisplayImageOptions Resuce_Pic_Option_Numberapprove() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		DisplayImageOptions PUBLISHER_SMALL_OPTION = new DisplayImageOptions.Builder()
				.considerExifParams(true)
				.showImageForEmptyUri(R.mipmap.phone_l)
				.showImageOnFail(R.mipmap.phone_l)
				.cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
				.decodingOptions(options).considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		return PUBLISHER_SMALL_OPTION;
	}
}
