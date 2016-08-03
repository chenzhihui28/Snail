package com.czh.snail.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.czh.snail.R;
import com.czh.snail.utils.MaterialTheme;

import java.util.ArrayList;
import java.util.List;

/**
 * Dialog fragment used to select a new theme. Upon selection, this starts a new home activity and
 * clears the task stack.
 *
 * @author Steven Byle
 */
public class SetThemeDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
    private static final String KEY_ARG_CURRENT_THEME = "KEY_ARG_CURRENT_THEME";

    private MaterialTheme mCurrentTheme;
    private int mCurrentSelectedThemeIndex;
    private SingleChoiceOnClickListener mSingleChoiceOnClickListener;

    public static SetThemeDialogFragment newInstance(MaterialTheme currentTheme) {

        SetThemeDialogFragment fragment = new SetThemeDialogFragment();
        Bundle args = fragment.getArguments();
        if (args == null) {
            args = new Bundle();
        }

        args.putSerializable(KEY_ARG_CURRENT_THEME, currentTheme);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get passed in parameters
        Bundle args = getArguments();

        // If no parameters were passed in, default them
        if (args == null) {
            mCurrentTheme = null;
        }
        // Otherwise, set incoming parameters
        else {
            mCurrentTheme = (MaterialTheme) args.getSerializable(KEY_ARG_CURRENT_THEME);
        }

        // If this is the first creation, default state variables
        if (savedInstanceState == null) {
        }
        // Otherwise, restore state
        else {
        }

        mSingleChoiceOnClickListener = new SingleChoiceOnClickListener();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        List<String> themeNameList = new ArrayList<String>();
        for (MaterialTheme materialTheme : MaterialTheme.getThemeList()) {
            themeNameList.add(getString(materialTheme.getNameResId()));
        }

        mCurrentSelectedThemeIndex = MaterialTheme.getThemeList().indexOf(mCurrentTheme);
        String[] themeNameArray = themeNameList.toArray(new String[themeNameList.size()]);

        Activity parentActivity = getActivity();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(parentActivity)
                .setTitle(R.string.set_theme_dialog_title)
                .setNegativeButton(R.string.cancel, this)
                .setSingleChoiceItems(themeNameArray, mCurrentSelectedThemeIndex, mSingleChoiceOnClickListener);

        Dialog dialog = alertDialogBuilder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which) {
            case DialogInterface.BUTTON_NEGATIVE:
                // Just let the dialog dismiss
                break;
        }
    }

    private class SingleChoiceOnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // Upon selection, figure out which theme was selected
            mCurrentSelectedThemeIndex = which;
            MaterialTheme newTheme = MaterialTheme.getThemeList().get(mCurrentSelectedThemeIndex);

            // If the theme is new, set it and start a new activity
            if (!mCurrentTheme.equals(newTheme)) {
                Activity parentActivity = getActivity();
                Intent intent = MainActivity.newIntent(parentActivity, newTheme);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                parentActivity.startActivity(intent);
            }
        }
    }
}