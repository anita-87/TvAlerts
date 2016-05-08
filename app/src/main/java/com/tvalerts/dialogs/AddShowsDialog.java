package com.tvalerts.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.tvalerts.R;

/**
 * Created by Anita on 23/08/15.
 */
public class AddShowsDialog extends DialogFragment implements TextView.OnEditorActionListener{

    private static final String TAG = "AddShowsDialog";

    private EditText searchedShow;

    public interface AddShowsDialogListener {
        void onFinishedAddShowsDialog(String show);
    }

    public AddShowsDialog(){
        // Empty constructor required for DialogFragment
    };

    public static AddShowsDialog newInstance(String title){
        AddShowsDialog frag = new AddShowsDialog();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return  frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        return inflater.inflate(R.layout.search_show, container);
    }

   @Override
   public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
       super.onViewCreated(view, savedInstanceState);
       //Get field from view
       searchedShow = (EditText) view.findViewById(R.id.addShowEditText);
       //Fetch arguments from bundle and set title
       getDialog().setTitle(R.string.add_show);
       //Show soft keyboard automatically and request focus to field
       searchedShow.requestFocus();
       getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
       searchedShow.setOnEditorActionListener(this);
   }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
        if (EditorInfo.IME_ACTION_DONE == actionId){
            Log.d(TAG, "onEditorAction");
            Log.d(TAG, "Selected show is: " + searchedShow.getText().toString());
            AddShowsDialogListener listener = (AddShowsDialogListener) getActivity();
            listener.onFinishedAddShowsDialog(searchedShow.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
    }
}
