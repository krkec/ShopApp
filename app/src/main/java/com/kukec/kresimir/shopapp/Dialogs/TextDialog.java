package com.kukec.kresimir.shopapp.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.kukec.kresimir.shopapp.MainActivity;
import com.kukec.kresimir.shopapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextDialog extends DialogFragment {
    @BindView(R.id.et_text)
    EditText vEditText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("ListName");

        LayoutInflater li = getActivity().getLayoutInflater();
        View v = li.inflate(R.layout.dialog_edittext, null);
        builder.setView(v);

        ButterKnife.bind(this, v); // vEditText = (EditText) v.findViewById(R.id.et_text);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setTextInActivity(vEditText.getText().toString());
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        return builder.create();
    }

    private void setTextInActivity(String value) {
        MainActivity activity = (MainActivity) getActivity();
        //String currentText = activity.getTextField();
        //activity.setTextField(currentText + value);
    }
}
