package com.example.myapplication.ui.notifications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.DataBaseHandler;
import com.example.myapplication.EditUserDataActivity;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.User;
import com.google.android.material.textfield.TextInputEditText;


public class NotificationsFragment extends Fragment {
    private NotificationsViewModel notificationsViewModel;
    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private TextInputEditText emailAdress;
    private TextInputEditText birthDate;
    private TextInputEditText height;
    private TextInputEditText weight;
    private RadioButton male;
    private RadioButton female;
    private DataBaseHandler dataBaseHandler;

    private Button logoutButton;
    private Button editUserDataButton;
    private Button changePasswordButton;
    private Button deleteUserButton;

    Integer id;
    User user;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        firstName = root.findViewById(R.id.userData_firstName);
        lastName = root.findViewById(R.id.userData_lastName);
        emailAdress = root.findViewById(R.id.userData_emailAdress);
        birthDate = root.findViewById(R.id.userData_birthDate);
        height = root.findViewById(R.id.userData_height);
        weight = root.findViewById(R.id.userData_weight);
        male = root.findViewById(R.id.userData_male);
        female = root.findViewById(R.id.userData_female);

        dataBaseHandler = new DataBaseHandler();

        //requestQueue = Volley.newRequestQueue(this.getContext());

        logoutButton = root.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Wylogowano", Toast.LENGTH_LONG).show();
                Intent mainAppIntent = new Intent();
                mainAppIntent.setClass(getActivity(), LoginActivity.class);
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.finish();
                startActivity(mainAppIntent);
            }
        });

        editUserDataButton = root.findViewById(R.id.editUserDataButton);
        editUserDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getRepoList("MadziaWesołek85");
                Intent mainAppIntent = new Intent();
                mainAppIntent.setClass(getActivity(), EditUserDataActivity.class);
                onDetach();
                startActivity(mainAppIntent);
            }
        });

        changePasswordButton = root.findViewById(R.id.changePasswordButton);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Zmieniamy hasło", Toast.LENGTH_LONG).show();
            }
        });

        deleteUserButton = (Button)root.findViewById(R.id.deleteUserButton);
        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.dialog_deleteuser,null);

                builder.setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //wyslyłamy do bazy, że użytkownik jest usunięty
                        Toast.makeText(getContext(), "Użytkownik usunięty", Toast.LENGTH_LONG).show();
                        Intent mainAppIntent = new Intent();
                        mainAppIntent.setClass(getActivity(), LoginActivity.class);
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.finish();
                        startActivity(mainAppIntent);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), "Użytkownik nie usunięty", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                builder.setView(mView);
                builder.create();
                builder.show();
            }
        });


        MainActivity mainActivity = (MainActivity) getActivity();
        id = mainActivity.getData();


        if (dataBaseHandler.findUser(id) != null) {
            setUserData(dataBaseHandler.findUser(id));
        }

        return root;
    }

    private void setUserData(User user) {
        //pobieranie danych o użytkowniku z bazy (byID)

        firstName.setText(user.getFirst_name());
        lastName.setText(user.getLast_name());
        emailAdress.setText(user.getEmail());
        //birthDate.setText(user.getBirthDate().toString());
        height.setText(user.getHeight().toString());
        weight.setText(user.getWeight().toString());
        if (user.getSex() == 1) {
            male.setChecked(false);
            female.setChecked(true);
        } else {
            male.setChecked(true);
            female.setChecked(false);
        }
    }
}
