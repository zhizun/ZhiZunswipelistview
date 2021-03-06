package com.ch.epw.task;

import android.content.Context;
import android.os.AsyncTask;

import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.bean.ShareResult;

public class CancelShareTask extends AsyncTask<String, Void, ShareResult> {
	AppException e;
	private Context context;
	private TaskCallBack taskCallBack;

	public CancelShareTask(Context context, TaskCallBack callBackFunc) {
		this.context = context;
		this.taskCallBack = callBackFunc;
	}

	@Override
	protected ShareResult doInBackground(String... params) {
		ShareResult result = null;
		try {
			result = AppContext.getApp().cancelShare(params[0]);
		} catch (AppException e) {
			this.e = e;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(ShareResult result) {
		super.onPostExecute(result);
		if (result != null) {
			if (result.getStatus().equals("0")) {
				if (taskCallBack != null) {
					taskCallBack.onTaskFinshed(result);
				}
			} else {
				UIHelper.ToastMessage(context, "分享出错，请稍后再试");
				return;
			}
		} else {
			if (null != e) {
				e.makeToast(context);
			}
			return;
		}
	}
}