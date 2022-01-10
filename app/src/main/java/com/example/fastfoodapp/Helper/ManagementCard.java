package com.example.fastfoodapp.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.fastfoodapp.Model.MonAn;

import java.util.ArrayList;

public class ManagementCard {

    private Context context;
    private TinyDB tinyDB;

    public ManagementCard(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void DeleteListCard() {
         tinyDB.remove("CardList");
    }


    public ArrayList<MonAn> getListCard() {
        return tinyDB.getListObject("CardList");
    }

    public void insertFood(MonAn item){
        ArrayList<MonAn>listFood = getListCard();
        boolean exitAlready = false;
        int n = 0;
        for(int i=0;i<listFood.size();i++){
            if(listFood.get(i).getTenMon().equals(item.getTenMon())){
                exitAlready = true;
                n = i;
                break;
            }
        }
        if (exitAlready) {
            listFood.get(n).setNumberInCard(item.getNumberInCard());
        } else {
            listFood.add(item);
        }

        tinyDB.putListObject("CardList", listFood);
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

    public void plusNumberFood(ArrayList<MonAn> listfood, int position, ChangeNumberItemListener changeNumberItemListener) {
        listfood.get(position).setNumberInCard(listfood.get(position).getNumberInCard() + 1);
        tinyDB.putListObject("CardList", listfood);
        changeNumberItemListener.changed();
    }

    public void MinusNumberFood(ArrayList<MonAn> listfood, int position, ChangeNumberItemListener changeNumberItemListener) {
        if (listfood.get(position).getNumberInCard() == 1) {
            listfood.remove(position);
        } else {
            listfood.get(position).setNumberInCard(listfood.get(position).getNumberInCard() - 1);
        }
        tinyDB.putListObject("CardList", listfood);
        changeNumberItemListener.changed();
    }

    public Integer getTotalFee() {
        ArrayList<MonAn> listFood2 = getListCard();
        int fee = 0;
        for (int i = 0; i < listFood2.size(); i++) {
            fee = fee + (listFood2.get(i).getGia() * listFood2.get(i).getNumberInCard());
        }
        return fee;
    }
}
