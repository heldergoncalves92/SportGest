package studentcompany.sportgest;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.daos.User_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.User;


/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    //Keep track of the login task to ensure we can cancel it if requested.
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AppCompatAutoCompleteTextView mUsernameView;
    private AppCompatEditText mPasswordView;

    private String TAG = "LOGIN_ACTIVITY";
    private String[] prevAttempts = {"admin"};
    private DialogFragment mDialog;

    private User_DAO userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        try {
            userDao = new User_DAO(getApplicationContext());
            if(userDao.getAll().isEmpty()) {
                insertUserTest(userDao);
            }
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

        // Set up the login form.
        mUsernameView = (AppCompatAutoCompleteTextView) findViewById(R.id.username);
        populateAutoComplete();

        mPasswordView = (AppCompatEditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        AppCompatButton mUsernameSignInButton = (AppCompatButton) findViewById(R.id.username_sign_in_button);
        mUsernameSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void populateAutoComplete() {
        if (prevAttempts.length > 0) {
            List<String> list = new ArrayList<String>();
            for(String s: prevAttempts)
                list.add(s);

            addUsernamesToAutoComplete(list);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_username));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUsernameValid(String username) {
        //TODO: Replace this with your own logic
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private void addUsernamesToAutoComplete(List<String> usernameCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, usernameCollection);

        mUsernameView.setAdapter(adapter);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
                try {
                    return userDao.login(mUsername,mPassword)!=null;
                } catch (GenericDAOException e) {
                    return false;
                }
            } catch (InterruptedException e) {
                return false;
            }

            //if(userDao.checkUser(mUsername,mPassword) == null)
                //return false;

            //return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /************************************
     ****      Dialog Functions      ****
     ************************************/

    public static class ProgressDialogFragment extends DialogFragment {

        public static ProgressDialogFragment newInstance() {
            return new ProgressDialogFragment();
        }

        // Build ProgressDialog
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            //Create new ProgressDialog
            final ProgressDialog dialog = new ProgressDialog(getActivity());

            // Set Dialog message
            dialog.setMessage("Verifying Data");

            // Dialog will be displayed for an unknown amount of time
            dialog.setIndeterminate(true);

            return dialog;
        }
    }

    private void showProgress(final boolean show) {

        if(show){
            // Create a new ProgressDialogFragment
            mDialog = ProgressDialogFragment.newInstance();

            // Show new ProgressDialogFragment
            mDialog.show(getSupportFragmentManager(), "Login");

        } else
            mDialog.dismiss();
    }

    /************************************
     ****        Test Functions      ****
     ************************************/

    private void insertUserTest(User_DAO u_dao) throws GenericDAOException {

        u_dao.insert(new User("admin","password","default.jpg","António Abreu","admin@email.com",null));
    }
}

