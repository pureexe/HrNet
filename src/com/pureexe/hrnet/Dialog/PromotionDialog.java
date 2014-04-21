package com.pureexe.hrnet.Dialog;

import com.pureexe.hrnet.DataManager;
import com.pureexe.hrnet.R;
import com.pureexe.hrnet.R.layout;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class PromotionDialog extends DialogPreference  {

	public PromotionDialog(Context context, AttributeSet attrs) {
		super(context, attrs);
		setDialogLayoutResource(R.layout.dialog_layout);
		setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);    
        
        setDialogIcon(null);

	}

	@Override
	public void onClick() {
		DataManager dm = new DataManager(getContext());
		if(dm.getInt("DataPlan")==0){
			DialogUtil.setDataPlan(getContext());
		} else {
			DialogUtil.warn_setDataPlan(getContext());
		}
	}

}