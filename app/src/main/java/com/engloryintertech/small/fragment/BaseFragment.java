package com.engloryintertech.small.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.engloryintertech.small.R;
import com.engloryintertech.small.activity.BaseActivity;

public abstract class BaseFragment extends Fragment{

	public BaseActivity mActivity;
	private View view;
	private ProgressDialog progressDialog;

	/**此方法可以得到上下文对象*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**返回一个需要展示的View */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mActivity = (BaseActivity)getActivity();
		if (view == null) {
			view = inflater.inflate(initView(), container, false);
		}
		initFindViewById(view);
		return view;
	}

	/**子类可以复写此方法初始化事件 */
	protected  void initEvent(){
	}

	/**当Activity初始化之后可以在这里进行一些数据的初始化操作*/
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
		initEvent();
	}

	/**子类实现此抽象方法返回 layoutId 进行展示*/
	public abstract int initView();

	/**初始化控件*/
	protected abstract void initFindViewById(View view);

	/**子类在此方法中实现数据的初始化 */
	public abstract void initData() ;

	/**startActivity
	 * @param   data       需要传值传，不需要时 传null
	 *          intentKey  Intent Key(在Constans中定义全局变量)
	 * */
	protected void startActivityWithData(Bundle data, Class<?> clas,int enterAnim,int exitAnim) {
		Intent intent = new Intent(getActivity(), clas);
		if (null != data) {
			intent.putExtras(data);
		}
		startActivity(intent);
		getActivity().overridePendingTransition(enterAnim, exitAnim);
	}

	/**startActivityForResult
	 * @param   data       需要传值传，不需要时 传null
	 *          intentKey  Intent Key(在Constans中定义全局变量)
	 *          requestCode  请求码 (在Constans中定义全局变量)
	 * */
	protected void startActivityForResultWithData(Bundle data, Class<?> clas,int enterAnim,
												  int exitAnim,int requestCode) {
		Intent intent = new Intent(getActivity(), clas);
		if (null != data) {
			intent.putExtras(data);
		}
		startActivityForResult(intent, requestCode);
		getActivity().overridePendingTransition(enterAnim,exitAnim);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(view != null){
			((ViewGroup)view.getParent()).removeView(view);
		}
	}

	/**在其包含的 Fragment 中统计页面：*/
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	public void showProgressDialog(Context context,boolean CanceledOnTouchOutside) {
		if(progressDialog==null) {
			progressDialog = new ProgressDialog(context);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setOnCancelListener(progressDialogCancerlListner);
			progressDialog.setCanceledOnTouchOutside(CanceledOnTouchOutside);
			progressDialog.setCancelable(true);
		}
		progressDialog.setMessage(getString(R.string.loading));
		progressDialog.show();
	}

	public void dismissProgressDialog() {
		if(progressDialog!=null){
			progressDialog.dismiss();
		}
	}

	private DialogInterface.OnCancelListener progressDialogCancerlListner=new DialogInterface.OnCancelListener() {
		@Override
		public void onCancel(DialogInterface dialog) {
			dismissProgressDialog();
		}
	};
}
