package com.engloryintertech.small.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PictureProcesse {

	public static final String SDCARD_DIR = Environment.getExternalStorageDirectory() + "";
	public static final String IMG_FOR_SHARE_DIR = SDCARD_DIR + "/SMall/Download/Img/";

	public static String getSitSizeImagePath(String webUrl,int width,int height){
		try{
			if(Common.isStringNull(webUrl))return webUrl;
			int index = webUrl.lastIndexOf("_");
			String uri2 = webUrl.substring(0, index + 1);
			if(index == -1){
				index = webUrl.lastIndexOf("/");
				uri2 = webUrl.substring(0,index + 1);
				webUrl = uri2 + width + "x" + height + ".jpg";
			}else{
				String str = width + "A" + height + "A0A100.webp";
				webUrl = uri2 + str;
			}
			return webUrl;
		}catch (Exception e){
			e.printStackTrace();
		}
		return webUrl;
	}

	public interface LoadImageDelegate{
		public void LoadDate(int state, String pictruePath);
	}

	public static void loadImage( String url, final LoadImageDelegate loadImageDelegate){
		if(Common.isStringNull(url) || loadImageDelegate == null){
			return;
		}
		String str2 = url.replace("/", "");
		str2 = str2.replace(":","");
		final String path = IMG_FOR_SHARE_DIR + str2;
		final File file = new File(path);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}else{
			if(loadImageDelegate != null)
				loadImageDelegate.LoadDate(1,path);
			return ;
		}
		ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
			@Override
			public void onLoadingStarted(String s, View view) {
			}
			@Override
			public void onLoadingFailed(String s, View view, FailReason failReason) {
				if (loadImageDelegate != null)
					loadImageDelegate.LoadDate(0, null);
			}
			@Override
			public void onLoadingComplete(String s, View view, Bitmap bitmap) {
				//路径写死，之后保存图片的时候会替换上次的图片
				try {
					file.createNewFile();
					FileOutputStream fos = new FileOutputStream(file);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
					fos.flush();
					fos.close();
					if (loadImageDelegate != null)
						loadImageDelegate.LoadDate(1, path);
				} catch (IOException e) {
					e.printStackTrace();
					if (loadImageDelegate != null)
						loadImageDelegate.LoadDate(0, null);
				}
			}
			@Override
			public void onLoadingCancelled(String s, View view) {
				if (loadImageDelegate != null)
					loadImageDelegate.LoadDate(0, null);
			}
		});
	}

	public static Bitmap getBitmap(File file){
		int degree = readPictureDegree(file.getAbsolutePath());
		Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), getBitmapOption(2));
		Bitmap newbitmap = rotaingImageView(degree, bitmap);
		return newbitmap;
	}

	public static BitmapFactory.Options getBitmapOption(int inSampleSize){
		System.gc();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inSampleSize = inSampleSize;
		return options;
	}

	/**
	 * 读取图片属性：旋转的角度
	 * @param path 图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree  = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	/*
		* 旋转图片
		* @param angle
		* @param bitmap
		* @return Bitmap
		*/
	public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
		//旋转图片 动作
		Matrix matrix = new Matrix();;
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * 从本地path中获取bitmap，压缩后保存小图片到本地
	 * @return 返回压缩后图片的存放路径
	 */
	public static String saveBitmap(Bitmap bitmap,File mFile) {
		String compressdPicPath = "";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while ( baos.toByteArray().length / 1024 > 100) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();//重置baos即清空baos
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;//每次都减少10
		}
		try {
			FileOutputStream out = new FileOutputStream(mFile);
			out.write(baos.toByteArray());
			out.flush();
			out.close();
			compressdPicPath = mFile.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return compressdPicPath;
	}
}
