package com.example.oauthwebviewapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oauthwebviewapp.repository.CreateRepositoryRequest;
import com.example.oauthwebviewapp.repository.CreateRepositoryResponse;
import com.example.oauthwebviewapp.user.UserRequest;
import com.example.oauthwebviewapp.user.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRepositoryActivity extends AppCompatActivity {

    EditText etRepositoryName;
    Button btnCreateRepository;
    Button btnOpenRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_repository);

        etRepositoryName = findViewById(R.id.etRepositoryName);
        btnCreateRepository = findViewById(R.id.btnCreateRepository);
        btnOpenRepository = findViewById(R.id.btnOpenRepository);

        App.getGitHubApi().getUser(new UserRequest())
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        setTitle(response.body().getLogin());
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {

                    }
                });

        btnCreateRepository.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etRepositoryName.getText().toString().isEmpty()) return;
                CreateRepositoryRequest request = new CreateRepositoryRequest();
                request.setName(etRepositoryName.getText().toString());
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
            }
        });



    }

}
