package com.muver.chars.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.muver.chars.R;
import com.muver.chars.ServiceLocator;
import com.muver.chars.data.SettingsProfile;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    private SettingsProfile _profile;

    private TextView _nameTextView;
    private TextView _infoTextView;
    private RadioButton _rButton;
    private ImageButton _editButton, _deleteButton;

    private ViewHolder(@NonNull View itemView) {
        super(itemView);

        _nameTextView = itemView.findViewById(R.id.name);
        _infoTextView = itemView.findViewById(R.id.info);
        _rButton = itemView.findViewById(R.id.r_button);
        _editButton = itemView.findViewById(R.id.editButton);
        _editButton.setOnClickListener(new ClickHandler());
        _deleteButton = itemView.findViewById(R.id.deleteButton);
        _deleteButton.setOnClickListener(new ClickHandler());
        itemView.setOnClickListener(new ClickHandler());
    }

    public void bind(SettingsProfile profile) {
        this._profile = profile;

        this._nameTextView.setText(this._profile.getName());
        String info = ServiceLocator.getContext().getString(R.string.key) + ": " + this._profile.getKey()
                + "\n" + ServiceLocator.getContext().getString(R.string.type) + ": " + this._profile.getType();
        this._infoTextView.setText(info);
        this._rButton.setChecked(this._profile.getSelected() == 1);
    }

    static ViewHolder create(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item, parent, false);
        return new ViewHolder(view);
    }

    private class ClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.editButton:
                    ServiceLocator.getViewModel().editSettingsProfile(_profile);
                    break;
                case R.id.deleteButton:
                    new AlertDialog.Builder(itemView.getContext())
                            .setTitle(itemView.getContext().getString(R.string.delete_warning_title))
                            .setMessage(itemView.getContext().getString(R.string.delete_warning_text))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ServiceLocator.getViewModel().deleteSettingsProfile(_profile);
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, null)
                            .show();
                    break;
                default:
                    ServiceLocator.getViewModel().setSelected(_profile);
                    break;
            }
        }
    }
}
