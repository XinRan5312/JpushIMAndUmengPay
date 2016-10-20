package com.engloryintertech.small.tools;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtils {

	/**文件是否存在 false 不存在 ； true 存在*/
	public static boolean fileIsExists(String fileName){
		File file = new File(Environment.getExternalStorageDirectory(),fileName);
		if (!file.exists()) {
			CustomToast.showToast("文件不存在，请修改文件路径");
			return false;
		}
		return true;
	}

	public static File fileIsExistsFormFile(String fileName){
		File file = new File(Environment.getExternalStorageDirectory(),fileName);
		if (!file.exists()) {
			CustomToast.showToast("文件不存在，请修改文件路径");
			return null;
		}
		return file;
	}

	public static File getFile(String fileDir, String fileName, String url) {
		if (Common.isStringNull(fileDir))
			throw new NullPointerException("File Dir is not null.");

		File dir = new File(fileDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		if (Common.isStringNull(fileName)) {
			int separatorIndex = url.lastIndexOf("/");
			fileName = (separatorIndex < 0) ? url : url.substring(separatorIndex + 1, url.length());
			if (Common.isStringNull(fileName) || !fileName.contains("."))
				fileName = String.valueOf(System.currentTimeMillis()) + ".cache";
		}
		return new File(dir, fileName);
	}

	public static File makeFile(File file) {
		if (file.exists()) {
			file.delete();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public static File createTmpFile(Context context){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
			String fileName = "korting"+timeStamp;
			File tmpFile = new File(pic, fileName+".jpg");
			return tmpFile;
		}else{
			File cacheDir = context.getCacheDir();
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
			String fileName = "korting"+timeStamp;
			File tmpFile = new File(cacheDir, fileName+".jpg");
			return tmpFile;
		}
	}

}
