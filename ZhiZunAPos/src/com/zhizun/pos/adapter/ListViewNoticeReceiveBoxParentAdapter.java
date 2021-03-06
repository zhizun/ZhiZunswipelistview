package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.ch.epw.task.FavTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.BaseTools;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.DateUtil;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.utils.URLs;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.NoticeBox;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.UserInfo;

/**
 * 通知收件箱列表家长端 ListViewAdapter
 */
public class ListViewNoticeReceiveBoxParentAdapter extends MyBaseAdapter {

	public static final String TAG = ListViewNoticeReceiveBoxParentAdapter.class
			.getName();
	private Context context;// 运行上下文
	private List<NoticeBox> listItems; // 数据集合

	public ListViewNoticeReceiveBoxParentAdapter(Context context,
			List<NoticeBox> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
		options = Options.getListOptions();
	}

	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.list_tzgg_jz_item,
					null);

			holder.tv_list_common_title_teachername = (TextView) convertView
					.findViewById(R.id.tv_list_common_title_teachername);
			holder.iv_list_common_title_logo = (ImageView) convertView
					.findViewById(R.id.iv_list_common_title_logo);
			holder.tv_list_common_title_orgname = (TextView) convertView
					.findViewById(R.id.tv_list_common_title_orgname);
			holder.tv_list_common_title_time = (TextView) convertView
					.findViewById(R.id.tv_list_common_title_time);

			holder.tv_list_tzgg_item_content = (TextView) convertView
					.findViewById(R.id.tv_list_tzgg_item_content);
			holder.iv_list_common_ssh_simple_collection = (ImageView) convertView
					.findViewById(R.id.iv_list_common_ssh_simple_collection);
			holder.ll_list_common_ssh_simple_collection = (LinearLayout) convertView
					.findViewById(R.id.ll_list_common_ssh_simple_collection);
			holder.ll_list_common_ssh_simple_share = (LinearLayout) convertView
					.findViewById(R.id.ll_list_common_ssh_simple_share);

			holder.tv_list_common_ssh_simple_collection = (TextView) convertView
					.findViewById(R.id.tv_list_common_ssh_simple_collection);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final NoticeBox noticeBox = listItems.get(position);

		showPicture(noticeBox.getUserPhoto(), holder.iv_list_common_title_logo,
				options);

		if (null != noticeBox.getUserName()
				&& !noticeBox.getUserName().equals("")) {
			holder.tv_list_common_title_teachername.setText(noticeBox
					.getUserName());
		} else {
			holder.tv_list_common_title_teachername.setText("");
		}
		if (null != noticeBox.getOrgName()
				&& !noticeBox.getOrgName().equals("")) {
			holder.tv_list_common_title_orgname.setText("来自"
					+ noticeBox.getOrgName());
		} else {
			holder.tv_list_common_title_orgname.setText("来自");
		}
		if (null != noticeBox.getSendTime()
				&& !noticeBox.getSendTime().equals("")) {
			holder.tv_list_common_title_time.setText(DateUtil
					.getSimpleChineseDateTimeWithoutSec(
							noticeBox.getSendTime(), "yyyy-MM-dd HH:mm:ss"));
		} else {
			holder.tv_list_common_title_time.setText("");
		}
		if (null != noticeBox.getContent()
				&& !noticeBox.getContent().equals("")) {
			holder.tv_list_tzgg_item_content.setText(noticeBox.getContent());
			holder.tv_list_tzgg_item_content.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View arg0) {
					BaseTools.copyText(context, holder.tv_list_tzgg_item_content.getText().toString());
					return false;
				}
			});
		} else {
			holder.tv_list_tzgg_item_content.setText("");
		}
		if (noticeBox.getIsFav().equals(Constant.COLLECTION_YES)) {

			holder.tv_list_common_ssh_simple_collection
					.setText(R.string.dynamic_favorite_yes);
			holder.iv_list_common_ssh_simple_collection
					.setImageResource(R.drawable.haveshoucang);
		} else {
			holder.tv_list_common_ssh_simple_collection
					.setText(R.string.dynamic_favorite_no);
			holder.iv_list_common_ssh_simple_collection
					.setImageResource(R.drawable.shoucang);
		}
		holder.ll_list_common_ssh_simple_collection
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						holder.ll_list_common_ssh_simple_collection
								.setClickable(false);
						String isCacelState = Constant.TYPE_YES; // 需要取消收藏
						String newFavState = Constant.COLLECTION_NO;// 取消收藏后状态为未收藏
						if (Constant.COLLECTION_NO.equals(noticeBox.getIsFav())) {
							isCacelState = Constant.TYPE_NO;
							newFavState = Constant.COLLECTION_YES;
						}
						noticeBox.setIsFav(newFavState);
						new FavTask(context, new TaskCallBack() {
							@Override
							public void onTaskFinshed() {
								holder.ll_list_common_ssh_simple_collection
										.setClickable(true);
								ListViewNoticeReceiveBoxParentAdapter.this
										.notifyDataSetChanged();
							}
						}).execute(AppContext.getApp().getToken(),
								noticeBox.getNoticeId(),
								Constant.COMMNETTYPE_TZGG, isCacelState);
					}
				});
		holder.ll_list_common_ssh_simple_share
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						AppContext.getApp().showShare(context, 
								noticeBox.getOrgId(),
								noticeBox.getNoticeId(), 
								Constant.COMMNETTYPE_TZGG,
								noticeBox.getContent(),
								null);

					}
				});

		return convertView;
	}

	static class ViewHolder {

		ImageView iv_list_common_title_logo;//
		TextView tv_list_common_title_teachername;
		TextView tv_list_common_title_orgname;
		TextView tv_list_common_title_time;
		TextView tv_list_tzgg_item_content;

		LinearLayout ll_list_common_ssh_simple_collection;
		ImageView iv_list_common_ssh_simple_collection;
		LinearLayout ll_list_common_ssh_simple_share;

		TextView tv_list_common_ssh_simple_collection;

	}

}
