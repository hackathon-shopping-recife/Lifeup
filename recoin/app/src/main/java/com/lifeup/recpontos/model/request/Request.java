package com.lifeup.recpontos.model.request;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeup.recpontos.R;
import com.lifeup.recpontos.model.api.Api;
import com.lifeup.recpontos.model.domain.Compra;
import com.lifeup.recpontos.model.domain.Promo;
import com.lifeup.recpontos.ui.adapter.ComprasAdapter;
import com.lifeup.recpontos.ui.adapter.PromoAdapter;
import com.lifeup.recpontos.util.Constant;
import com.lifeup.recpontos.util.Util;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Request {

    private static final Request INSTANCE = new Request();
    private Request(){ }
    public static Request getInstance(){
        return INSTANCE;
    }
    private Retrofit retrofit = new Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL)
            .build();
    Api api = retrofit.create(Api.class);

    public void insertTicket(final Activity activity, final String ticket){
        if(Util.isConnected(activity)){
            Call<Void> call = api.insertTicket(Constant.TOKEN_, ticket);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.code()==201){
                        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage("Estamos processando seus pontos e validando a nota fiscal, em breve sua nova pontuação está disponível!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        // Create the AlertDialog object and return it
                        builder.create().show();
                    }else{
                        Toast.makeText(activity.getApplicationContext(), "Aconteceu um problema ao salvar seu Ticket, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("ERROR", "------------");
                    t.printStackTrace();
                    Log.e("ERROR", "------------");
                    if(t.getCause() instanceof SocketTimeoutException){
                        Toast.makeText(activity, "A solicitação está demorando muito, talvez nossos serviços estejam com problemas!", Toast.LENGTH_LONG).show();
                        insertTicket(activity, ticket);
                    }else{
                        Toast.makeText(activity.getApplicationContext(), "Por favor, verifique sua conexão com a internet!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void getPromo(final Activity activity, final ListView promoList) {
        if(Util.isConnected(activity)){
            Call<List<Promo>> que = api.getPromo("");
            que.enqueue(new Callback<List<Promo>>() {
                @Override
                public void onResponse(Call<List<Promo>> call, Response<List<Promo>> response) {
                    if(response.code() == 200){
                        ArrayAdapter<Promo> adapterPromo = new PromoAdapter(R.layout.item_promo, activity, response.body());
                        promoList.setAdapter(adapterPromo);
                    }else{
                        Toast.makeText(activity, "Aconteceu um problema ao buscar as promoções!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Promo>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    public void getCompras(final Activity activity, final ListView compraList) {
        if(Util.isConnected(activity)){
            Call<List<Compra>> que = api.getCompras(Constant.TOKEN_);
            que.enqueue(new Callback<List<Compra>>() {
                @Override
                public void onResponse(Call<List<Compra>> call, Response<List<Compra>> response) {
                    if(response.code() == 200){
                        ArrayAdapter<Compra> adapterCompra = new ComprasAdapter(R.layout.item_promo, activity, response.body());
                        compraList.setAdapter(adapterCompra);
                    }else{
                        Toast.makeText(activity, "Aconteceu um problema ao buscar as promoções!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Compra>> call, Throwable t) {

                }
            });
        }
    }

    public void getTotalValue(final Activity activity,final TextView textView) {
        if(Util.isConnected(activity)){
            Call<List<Compra>> que = api.getCompras(Constant.TOKEN_);
            que.enqueue(new Callback<List<Compra>>() {
                @Override
                public void onResponse(Call<List<Compra>> call, Response<List<Compra>> response) {
                    if(response.code() == 200){
                        int total = 0;
                        for (Compra c :
                                response.body()) {
                            total += c.getTotalValue();
                        }
                        if(total > 1) {
                            textView.setText("Até agora você arrecadou " + Integer.toString(total) + " pontos!");
                        }else if(total == 1){
                            textView.setText("Até agora você arrecadou " + Integer.toString(total) + " ponto!");
                        }else{
                            textView.setText("Você ainda não pontuou, cadastre uma nova nota fiscal para ganhar seus pontos e começar a receber os descontos!");
                        }
                    }else{
                        Toast.makeText(activity, "Aconteceu um problema ao buscar as promoções!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Compra>> call, Throwable t) {

                }
            });
        }
    }
}

