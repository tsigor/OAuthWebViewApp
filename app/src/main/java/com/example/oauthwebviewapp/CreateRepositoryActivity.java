package com.example.oauthwebviewapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oauthwebviewapp.repository.CreateRepositoryRequest;
import com.example.oauthwebviewapp.repository.CreateRepositoryResponse;
import com.example.oauthwebviewapp.repository.Repository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class CreateRepositoryActivity extends AppCompatActivity {

    public static final String SP_USER_INFO = "user_info";
    public static final String SP_ACCESS_TOKEN_PARAM ="accessToken";

    private ProgressDialog pd;

    EditText etRepoName;
    EditText etRepoDescr;
    Button btnCreateRepo;
    Button btnOpenRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_repository);

        etRepoName = findViewById(R.id.etRepoName);
        etRepoDescr = findViewById(R.id.etRepoDescr);
        btnCreateRepo = findViewById(R.id.btnCreateRepo);

        SharedPreferences preferences = this.getSharedPreferences(SP_USER_INFO, 0);
        String accessToken = preferences.getString(SP_ACCESS_TOKEN_PARAM, null);
        if(accessToken!=null){
            ApiUtils.getApiService(accessToken).getUser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> pd = ProgressDialog.show(CreateRepositoryActivity.this, "", CreateRepositoryActivity.this.getString(R.string.loading), true))
                    .doFinally(() -> {if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }})
                    .subscribe(user -> CreateRepositoryActivity.this.setTitle(user.getLogin()),
                            throwable -> CreateRepositoryActivity.this.setTitle(getString(R.string.error)));
        }

        btnCreateRepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isValidInput())
                    return;
                //create Reository object which will be later added to
                Repository repository = new Repository(etRepoName.getText().toString()
                        , etRepoDescr.getText().toString()
                        , false);
                ApiUtils.getApiService(accessToken).registration(repository)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> pd = ProgressDialog.show(CreateRepositoryActivity.this, "", getString(R.string.sending), true))
                        .doFinally(() -> {if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }})
                        .subscribe(Toast.makeText(CreateRepositoryActivity.this, R.string.created_successfully, Toast.LENGTH_LONG)::show, throwable -> {
                            if (throwable instanceof HttpException) {
                                retrofit2.Response response = ((HttpException) throwable).response();

                                if (response.code() == 422){
                                    Toast.makeText(CreateRepositoryActivity.this, getString(R.string.r_with_name) + repository.getName() + getString(R.string.already_exist), Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(CreateRepositoryActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                                }
                            }
                        });



/*
                if(etRepoName.getText().toString().isEmpty()) return;
                CreateRepositoryRequest request = new CreateRepositoryRequest();
                request.setName(etRepoName.getText().toString());
                App.getGitHubApi().createRepository(request)
                        .enqueue(new Callback<CreateRepositoryResponse>() {
                            @Override
                            public void onResponse(Call<CreateRepositoryResponse> call, final Response<CreateRepositoryResponse> response) {
                                Toast.makeText(CreateRepositoryActivity.this, "Repository created successfully", Toast.LENGTH_LONG).show();
                                btnOpenRepository.setVisibility(View.VISIBLE);
                                btnOpenRepository.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.body().getHtmlUrl()));
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<CreateRepositoryResponse> call, Throwable t) {

                            }
                        });
*/
            }//onCLick
        });



    }
    private boolean isValidInput(){
        boolean isOk = true;
        if (etRepoName.getText().length() == 0){
            isOk = false;
            etRepoName.setError(getString(R.string.not_empty));
        }

        return isOk;
    }


}
