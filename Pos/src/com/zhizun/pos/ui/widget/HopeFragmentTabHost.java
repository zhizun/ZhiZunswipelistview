package com.zhizun.pos.ui.widget;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

public class HopeFragmentTabHost extends TabHost implements TabHost.OnTabChangeListener {
	private boolean mAttached;
	private int mContainerId;
	private Context mContext;
	private FragmentManager mFragmentManager;
	private TabInfo mLastTab;
	private TabHost.OnTabChangeListener mOnTabChangeListener;
	private FrameLayout mRealTabContent;
	private final ArrayList<TabInfo> mTabs = new ArrayList();

	public HopeFragmentTabHost(Context paramContext) {
		super(paramContext, null);
		initFragmentTabHost(paramContext, null);
	}

	public HopeFragmentTabHost(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		initFragmentTabHost(paramContext, paramAttributeSet);
	}

	private FragmentTransaction doTabChanged(String paramString, FragmentTransaction paramFragmentTransaction) {
		Object localObject = null;
		int i = 0;
		for (;;) {
			if (i >= this.mTabs.size()) {
				if (localObject != null) {
					break;
				}
				throw new IllegalStateException("No tab known for tag " + paramString);
			}
			TabInfo localTabInfo = (TabInfo) this.mTabs.get(i);
			if (localTabInfo.tag.equals(paramString)) {
				localObject = localTabInfo;
			}
			i += 1;
		}
		paramString = paramFragmentTransaction;
		if (this.mLastTab != localObject) {
			paramString = paramFragmentTransaction;
			if (paramFragmentTransaction == null) {
				paramString = this.mFragmentManager.beginTransaction();
			}
			if ((this.mLastTab != null) && (this.mLastTab.fragment != null)) {
				paramString.hide(this.mLastTab.fragment);
			}
			if (localObject != null) {
				if (((TabInfo) localObject).fragment != null) {
					break label200;
				}
				((TabInfo) localObject).fragment = Fragment.instantiate(this.mContext,
						((TabInfo) localObject).clss.getName(), ((TabInfo) localObject).args);
				paramString.add(this.mContainerId, ((TabInfo) localObject).fragment, ((TabInfo) localObject).tag);
			}
		}
		for (;;) {
			this.mLastTab = ((TabInfo) localObject);
			return paramString;
			label200: paramString.show(((TabInfo) localObject).fragment);
		}
	}

	private void ensureContent() {
		if (this.mRealTabContent == null) {
			this.mRealTabContent = ((FrameLayout) findViewById(this.mContainerId));
			if (this.mRealTabContent == null) {
				throw new IllegalStateException("No tab content FrameLayout found for id " + this.mContainerId);
			}
		}
	}

	private void ensureHierarchy(Context paramContext) {
		if (findViewById(16908307) == null) {
			LinearLayout localLinearLayout = new LinearLayout(paramContext);
			localLinearLayout.setOrientation(1);
			addView(localLinearLayout, new FrameLayout.LayoutParams(-1, -1));
			Object localObject = new TabWidget(paramContext);
			((TabWidget) localObject).setId(16908307);
			((TabWidget) localObject).setOrientation(0);
			localLinearLayout.addView((View) localObject, new LinearLayout.LayoutParams(-1, -2, 0.0F));
			localObject = new FrameLayout(paramContext);
			((FrameLayout) localObject).setId(16908305);
			localLinearLayout.addView((View) localObject, new LinearLayout.LayoutParams(0, 0, 0.0F));
			paramContext = new FrameLayout(paramContext);
			this.mRealTabContent = paramContext;
			this.mRealTabContent.setId(this.mContainerId);
			localLinearLayout.addView(paramContext, new LinearLayout.LayoutParams(-1, 0, 1.0F));
		}
	}

	private void initFragmentTabHost(Context paramContext, AttributeSet paramAttributeSet) {
		paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, new int[] { 16842995 }, 0, 0);
		this.mContainerId = paramContext.getResourceId(0, 0);
		paramContext.recycle();
		super.setOnTabChangedListener(this);
	}

	public void addTab(TabHost.TabSpec paramTabSpec, Class<?> paramClass, Bundle paramBundle) {
		paramTabSpec.setContent(new DummyTabFactory(this.mContext));
		String str = paramTabSpec.getTag();
		paramClass = new TabInfo(str, paramClass, paramBundle);
		if (this.mAttached) {
			paramClass.fragment = this.mFragmentManager.findFragmentByTag(str);
			if ((paramClass.fragment != null) && (!paramClass.fragment.isDetached())) {
				paramBundle = this.mFragmentManager.beginTransaction();
				paramBundle.hide(paramClass.fragment);
				paramBundle.commit();
			}
		}
		this.mTabs.add(paramClass);
		addTab(paramTabSpec);
	}

	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		String str = getCurrentTabTag();
		Object localObject1 = null;
		int i = 0;
		if (i >= this.mTabs.size()) {
			this.mAttached = true;
			localObject1 = doTabChanged(str, (FragmentTransaction) localObject1);
			if (localObject1 != null) {
				((FragmentTransaction) localObject1).commit();
				this.mFragmentManager.executePendingTransactions();
			}
			return;
		}
		TabInfo localTabInfo = (TabInfo) this.mTabs.get(i);
		localTabInfo.fragment = this.mFragmentManager.findFragmentByTag(localTabInfo.tag);
		Object localObject2 = localObject1;
		if (localTabInfo.fragment != null) {
			if (!localTabInfo.tag.equals(str)) {
				break label126;
			}
			this.mLastTab = localTabInfo;
			localObject2 = localObject1;
		}
		for (;;) {
			i += 1;
			localObject1 = localObject2;
			break;
			label126: localObject2 = localObject1;
			if (localObject1 == null) {
				localObject2 = this.mFragmentManager.beginTransaction();
			}
			((FragmentTransaction) localObject2).hide(localTabInfo.fragment);
		}
	}

	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		this.mAttached = false;
	}

	protected void onRestoreInstanceState(Parcelable paramParcelable) {
		paramParcelable = (SavedState) paramParcelable;
		super.onRestoreInstanceState(paramParcelable.getSuperState());
		setCurrentTabByTag(paramParcelable.curTab);
	}

	protected Parcelable onSaveInstanceState() {
		SavedState localSavedState = new SavedState(super.onSaveInstanceState());
		localSavedState.curTab = getCurrentTabTag();
		return localSavedState;
	}

	public void onTabChanged(String paramString) {
		if (this.mAttached) {
			FragmentTransaction localFragmentTransaction = doTabChanged(paramString, null);
			if (localFragmentTransaction != null) {
				localFragmentTransaction.commit();
			}
		}
		if (this.mOnTabChangeListener != null) {
			this.mOnTabChangeListener.onTabChanged(paramString);
		}
	}

	public void setOnTabChangedListener(TabHost.OnTabChangeListener paramOnTabChangeListener) {
		this.mOnTabChangeListener = paramOnTabChangeListener;
	}

	@Deprecated
	public void setup() {
		throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
	}

	public void setup(Context paramContext, FragmentManager paramFragmentManager) {
		ensureHierarchy(paramContext);
		super.setup();
		this.mContext = paramContext;
		this.mFragmentManager = paramFragmentManager;
		ensureContent();
	}

	public void setup(Context paramContext, FragmentManager paramFragmentManager, int paramInt) {
		ensureHierarchy(paramContext);
		super.setup();
		this.mContext = paramContext;
		this.mFragmentManager = paramFragmentManager;
		this.mContainerId = paramInt;
		ensureContent();
		this.mRealTabContent.setId(paramInt);
		if (getId() == -1) {
			setId(16908306);
		}
	}

	static class DummyTabFactory implements TabHost.TabContentFactory {
		private final Context mContext;

		public DummyTabFactory(Context paramContext) {
			this.mContext = paramContext;
		}

		public View createTabContent(String paramString) {
			paramString = new View(this.mContext);
			paramString.setMinimumWidth(0);
			paramString.setMinimumHeight(0);
			return paramString;
		}
	}

	static class SavedState extends View.BaseSavedState {
		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() {
			public HopeFragmentTabHost.SavedState createFromParcel(Parcel paramAnonymousParcel) {
				return new HopeFragmentTabHost.SavedState(paramAnonymousParcel, null);
			}

			public HopeFragmentTabHost.SavedState[] newArray(int paramAnonymousInt) {
				return new HopeFragmentTabHost.SavedState[paramAnonymousInt];
			}
		};
		String curTab;

		private SavedState(Parcel paramParcel) {
			super();
			this.curTab = paramParcel.readString();
		}

		SavedState(Parcelable paramParcelable) {
			super();
		}

		public String toString() {
			return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab="
					+ this.curTab + "}";
		}

		public void writeToParcel(Parcel paramParcel, int paramInt) {
			super.writeToParcel(paramParcel, paramInt);
			paramParcel.writeString(this.curTab);
		}
	}

	static final class TabInfo {
		private final Bundle args;
		private final Class<?> clss;
		private Fragment fragment;
		private final String tag;

		TabInfo(String paramString, Class<?> paramClass, Bundle paramBundle) {
			this.tag = paramString;
			this.clss = paramClass;
			this.args = paramBundle;
		}
	}
}