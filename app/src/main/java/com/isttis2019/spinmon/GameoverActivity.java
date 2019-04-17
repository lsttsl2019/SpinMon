package com.isttis2019.spinmon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class GameoverActivity extends AppCompatActivity {


    ImageView iv;
    TextView tvChampion;
    TextView tvYourscore;

    boolean isChampion=false; //챔피언인가??



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        iv=findViewById(R.id.iv);
        tvChampion=findViewById(R.id.tv_champion);
        tvYourscore=findViewById(R.id.tv_yourscore);


        Intent intent=getIntent();
        int score=intent.getIntExtra("Score",0);
        int coin=intent.getIntExtra("Coin",0);

        int yourscore=score+coin*10;

        if(yourscore>G.champion){  //최고점인가?
            G.champion=yourscore;
            isChampion=true;
        }

        String s=String.format("%07d",G.champion);
        tvChampion.setText(s);

        s=String.format("%07d", yourscore);
        tvYourscore.setText(s);


    }

    public void clickImg(View view) {
        //새로운 챔피언이 되었을때.. 명예의 전당 사진 등록이 가능하다!!
        if (!isChampion) return;

        //Gallery앱을 실행하여 디바이스에 있는 사진을 선택하고 등록
        //이미지뷰에 보여주기~~~!
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");  //이미지 선택기
        startActivityForResult(intent, 100);  //결과를 가지고 돌아오는 intent

    }

    //startActivityForResult()메소드를 실행한 액티비가
    //종료되면 자동으로 호출되는 메소드


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 100:

                //갤러리 화면에서 이미지를 선택하고 돌아왔는지 체크
                //두번째 파리미터 :resultCode
                if(resultCode== RESULT_OK){

                    //선택된 이미지의 경로(Uri)객체를 얻어오기!!
                    //결과를 가지고 돌아온 Intent 객체(세번째 파라미터 : data)
                    //로부터 선택된 데이터를 얻어오기
                    Uri uri =data.getData();

                    //특정 디바이스들에서 선택한 이미지의 Uri를 주지않고
                    //Bitmap데이터를 주는 경우가 있음..
                    //만약 Bitmap데이터로 주는 디바이스라면.. uri가 null로 전달됨
                    if( uri != null ){
                        //Uri경로로 전달되었다면..
                        //iv.setImageURI(uri);
                        Glide.with(this).load(uri).into(iv);

                        Toast.makeText(this, "URI...", Toast.LENGTH_SHORT).show();

                    }else{
                        //Intent(data)에 Extra데이터로 Bitmap이 전달되어 옴
                        Bundle bundle= data.getExtras();
                        Bitmap bm= (Bitmap)bundle.get("data");
                        //iv.setImageBitmap(bm);
                        Glide.with(this).load(bm).into(iv);

                        Toast.makeText(this, "Bitmap...", Toast.LENGTH_SHORT).show();
                    }

                }

                break;
        }

    }

    public void clickRetry(View view) {
            //GameActivity를 다시 실행하기!
        Intent intent=new Intent(this, GameActivity.class);
        startActivity(intent);

        finish();

    }

    public void clickExit(View view) {
        finish();
    }


    void saveData(){
        SharedPreferences pref= getSharedPreferences("Data", MODE_PRIVATE);

        //이제부터 작성 시작!!
        SharedPreferences.Editor editor= pref.edit();

        editor.putInt("Gem", G.gem);
        editor.putInt("Champion", G.champion);
        editor.putString("ChampionImagePath", G.championImagePath);

        editor.putBoolean("Music", G.isMusic);
        editor.putBoolean("Sound", G.isSound);
        editor.putBoolean("Vibrate", G.isVibrate);

        //작성완료
        editor.commit();
    }

    //화면에서 안보일댸 영구히 저장할 데이터들 저장하기
    @Override
    protected void onPause() {
        super.onPause();

        //TODO: 데이터 영구 저장하기!!  [G.gem, G.champion....]
        //SharedPreference를 이용하여 저장!!
        saveData();

    }


}
























