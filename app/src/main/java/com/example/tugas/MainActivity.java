package com.example.tugas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText edName;
    private RadioGroup rgType;
    private RadioButton rbTea,rbCoffee,rbSmoothies;
    private CheckBox cbPearl,cbPudding,cbRedBean,cbCoconut;
    private Button btnMinus,btnPlus,btnAdd,btnEdit,btnDelete,btnReset;
    private TextView txtQty,txtTotal,txtName;
    private RecyclerView rvOrder;
    private OrderAdapter adapter;
    private ArrayList<Order> arrOrder=new ArrayList<>();
    private long total=0;
    private int index=-1;
    private int jumlah=1;
    private String type="";
    private int subtot=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edName=(EditText) findViewById(R.id.editText_name);
        rgType=(RadioGroup)findViewById(R.id.radioGroup_type);
        rbTea=(RadioButton)findViewById(R.id.radioButton_tea);
        rbCoffee=(RadioButton)findViewById(R.id.radioButton_coffee);
        rbSmoothies=(RadioButton)findViewById(R.id.radioButton_smoothies);
        cbCoconut=(CheckBox)findViewById(R.id.checkBox_coconut);
        cbPearl=(CheckBox)findViewById(R.id.checkBox_pearl);
        cbPudding=(CheckBox)findViewById(R.id.checkBox_pudding);
        cbRedBean=(CheckBox)findViewById(R.id.checkBox_redbean);
        btnAdd=(Button)findViewById(R.id.button_add);
        btnDelete=(Button)findViewById(R.id.button_del);
        btnMinus=(Button)findViewById(R.id.button_min);
        btnPlus=(Button) findViewById(R.id.button_plus);
        btnReset=(Button) findViewById(R.id.button_res);
        txtName=(TextView)findViewById(R.id.textView_name);
        txtTotal=(TextView)findViewById(R.id.textView_total);
        txtQty=(TextView)findViewById(R.id.textView_qty);
        rvOrder = findViewById(R.id.rvOrder);

        txtName.setText("Hi " + edName.getText()+"!");
        txtTotal.setText("Total : Rp 0");
        //rvOrder.setAdapter(adapter);

        adapter = new OrderAdapter(arrOrder, new RVClickListener() {
            @Override
            public void recyclerViewClick(View v, int pos) {
            type=arrOrder.get(pos).getType();
            txtQty.setText(String.valueOf(arrOrder.get(pos).getQty()));
            ArrayList<String> data=new ArrayList<String>();
            data=arrOrder.get(pos).getToppings();

            rbCoffee.setChecked(false);
            rbSmoothies.setChecked(false);
            rbTea.setChecked(false);
            cbPudding.setChecked(false);
            cbRedBean.setChecked(false);
            cbPearl.setChecked(false);
            cbCoconut.setChecked(false);
            index=pos;

                for (String s: data
                     ) {
                    if (s=="Pudding"){cbPudding.setChecked(true);}
                    if (s=="Red Bean"){cbRedBean.setChecked(true);}
                    if (s=="Coconut Jelly"){cbCoconut.setChecked(true);}
                    if (s=="Pearl"){cbPearl.setChecked(true);}
                }
            if (type=="Tea"){rbTea.setChecked(true);}
            if (type=="Coffee"){rbCoffee.setChecked(true);}
            if (type=="Smoothies"){rbSmoothies.setChecked(true);}


            }
        });

        RecyclerView.LayoutManager lm=new LinearLayoutManager(this);
        rvOrder.setLayoutManager(lm);
        rvOrder.setAdapter(adapter);
        total=0;

        rbTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbCoffee.setChecked(false);
                rbSmoothies.setChecked(false);
                rbTea.setChecked(true);
            }
        });

        rbCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbCoffee.setChecked(true);
                rbSmoothies.setChecked(false);
                rbTea.setChecked(false);
            }
        });

        rbSmoothies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbCoffee.setChecked(false);
                rbSmoothies.setChecked(true);
                rbTea.setChecked(false);
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah-=1;
                txtQty.setText(String.valueOf(jumlah));
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah+=1;
                txtQty.setText(String.valueOf(jumlah));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edName.getText().toString().contentEquals(""))
                {
                    Toast.makeText(MainActivity.this, "Field Name Cannot Be Empty", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    ArrayList<String> temp=new ArrayList<>();
                    if (rbCoffee.isChecked()==true){type="Coffee";subtot+=25000;}
                    if (rbTea.isChecked()==true){type="Tea";subtot+=23000;}
                    if (rbSmoothies.isChecked()==true){type="Smoothies";subtot+=30000;}
                    if (cbPearl.isChecked()==true){temp.add("Pearl");subtot+=3000;}
                    if (cbRedBean.isChecked()==true){temp.add("Red Bean");subtot+=4000;}
                    if (cbPudding.isChecked()==true){temp.add("Pudding");subtot+=3000;}
                    if (cbCoconut.isChecked()==true){temp.add("Coconut Jelly");subtot+=4000;}

                    subtot=jumlah*subtot;
                    arrOrder.add(new Order(type,temp,jumlah,subtot));
                    adapter.notifyDataSetChanged();
                    txtName.setText("Hi " + edName.getText()+"!");

                    subtot=0;
                    jumlah=0;
                    int tot=0;
                    for (int i=0;i<arrOrder.size();i++)
                    {
                        tot+=(int)arrOrder.get(i).getSubtotal();
                    }
                    txtTotal.setText("Rp " + tot+"");
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrOrder.clear();
                adapter.notifyDataSetChanged();
                total=0;
                txtName.setText("Hi, Cust!");
                txtTotal.setText(total+"");
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                rbCoffee.setChecked(false);
//                rbSmoothies.setChecked(false);
//                rbTea.setChecked(false);
//                cbPudding.setChecked(false);
//                cbRedBean.setChecked(false);
//                cbPearl.setChecked(false);
//                cbCoconut.setChecked(false);
//                if (rbCoffee.isChecked()==true){type="Coffee";}
//                if (rbTea.isChecked()==true){type="Tea";}
//                if (rbSmoothies.isChecked()==true){type="Smoothies";}
                arrOrder.remove(index);
                adapter.notifyDataSetChanged();
                int tot=0;
                for (int i=0;i<arrOrder.size();i++)
                {
                    tot+=(int)arrOrder.get(i).getSubtotal();
                }
                txtTotal.setText("Rp " + tot+"");
            }
        });
    }


}
