package edu.dartmouth.cs.a21days.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import edu.dartmouth.cs.a21days.R;

/**
 * activity for users to sign in to the app
 * based off of example code from Firebase
 */
public class SignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String DEBUG_TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;

    // Facebook objects
    private LoginButton facebookLoginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // set listeners for buttons
        findViewById(R.id.google_sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);


        // configure Google sign in options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // get instance of Firebase
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(DEBUG_TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    // launch main activity
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // User is signed out
                    Log.d(DEBUG_TAG, "onAuthStateChanged:signed_out");
                }
                updateUI(user);
            }
        };

        // set size of sign in button
        SignInButton signInButton = (SignInButton) findViewById(R.id.google_sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        // setting up facebook login button
        facebookLoginButton = (LoginButton) findViewById(R.id.facebook_sign_in_button);
        facebookLoginButton.setReadPermissions("email");

        // Setting up facebook callback
        callbackManager = CallbackManager.Factory.create();

        // Handle facebook button results
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(DEBUG_TAG, "Facebook login successful");
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Log.d(DEBUG_TAG, "Facebook login cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(DEBUG_TAG, "Facebook login threw error");
            }
        });
    }

    // what to do when a button is clicked
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.google_sign_in_button) {
            signIn();
        } else if (i == R.id.sign_out_button) {
            signOut();
        } else if (i == R.id.disconnect_button) {
            revokeAccess();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // add listener
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public void onStop() {
        super.onStop();
        // remove listener if needed
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    // what to do when get result from signing in
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Log.i(DEBUG_TAG, "onActivityResult: failed to sign in");
                // Google Sign In failed, update UI appropriately
                updateUI(null);
            }
        } else {
            // facebook callback, forward activity result
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    // authenticate into Firebase with Google
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(DEBUG_TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(DEBUG_TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(DEBUG_TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignInActivity.this, R.string.authentication_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // notify that user sign in is successful
                        Toast.makeText(SignInActivity.this, R.string.google_sign_in_success,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(DEBUG_TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // sign in user
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut() {
        // sign out of Firebase
        mAuth.signOut();

        // sign out of Google
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(null);
                    }
                });

        // show message user has signed out
        Toast.makeText(SignInActivity.this, "User has signed out.",
                Toast.LENGTH_SHORT).show();
    }


    // revoke access
    private void revokeAccess() {
        // sign out of Firebase
        mAuth.signOut();

        // revoke access for Google
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(null);
                    }
                });

        // show message that user has revoked access
        Toast.makeText(SignInActivity.this, "User has disconnected.",
                Toast.LENGTH_SHORT).show();
    }

    // update the buttons that are shown depending on whether a user is signed in or not
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            findViewById(R.id.google_sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
            findViewById(R.id.skip_sign_in_button).setVisibility(View.GONE);
        } else {
            findViewById(R.id.google_sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.skip_sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    // what to do when connection fails
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(DEBUG_TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    // Called when skip button is clicked
    public void onSkipClicked(View view){
        // Starts main activity
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
