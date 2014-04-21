package com.pureexe.hrnet.Dialog;

import com.pureexe.hrnet.DataManager;
import com.pureexe.hrnet.R;
import com.pureexe.hrnet.R.layout;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class SourceCodeDialog extends DialogPreference  {

	public SourceCodeDialog(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	public void onClick() {
		DialogUtil.openWeb(getContext(), "https://www.github.com/pureexe/hrnet");
	}

}