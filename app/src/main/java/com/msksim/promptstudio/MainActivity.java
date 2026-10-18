package com.msksim.promptstudio;

import android.app.*;
import android.os.Bundle;
import android.content.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import org.json.JSONObject;
import java.net.*;
import java.io.*;
import java.util.*;

public class MainActivity extends Activity {
    final int bg=Color.rgb(7,21,34), panel=Color.rgb(16,36,54), accent=Color.rgb(74,83,255), white=Color.WHITE, muted=Color.rgb(177,198,219);
    final String[] titles={"1. Задача и назначение","2. Сюжет и объекты","3. Стиль и художники","4. Композиция","5. Свет и атмосфера","6. Цветовая палитра","7. Формат и ограничения"};
    final String[][] presets={
      {"Обложка корпоративного журнала","Рекламный постер","Концепт-арт персонажа","Иллюстрация для статьи","Предметная визуализация"},
      {"Семья из трёх поколений","Энергетик в защитной форме","Современный городской пейзаж","Футуристическая энергетическая система","Минималистичная абстракция"},
      {"Премиальная 3D-графика","Реалистичная художественная фотография","Цифровая иллюстрация","Кинематографический концепт-арт","Минимализм"},
      {"Вертикальная композиция","Центральная симметрия","Правило третей","Свободное пространство под текст","Крупный план"},
      {"Мягкий рассеянный свет","Золотой час","Студийное освещение","Кинематографический контровой свет","Холодный технологичный свет"},
      {"Тёплая светлая","Глубокая синяя и золотая","Нейтральная серая","Природная зелёная","Корпоративная синяя"},
      {"A4 вертикальный","A4 горизонтальный","16:9 горизонтальный","1:1 квадрат","Без текста и логотипов"}
    };
    EditText[] inputs=new EditText[7];
    String profile="Универсальный";
    LinearLayout root, content;
    TextView preview;
    public void onCreate(Bundle b){super.onCreate(b); getWindow().setStatusBarColor(bg); getWindow().setNavigationBarColor(bg); showHome();}
    TextView text(String s,int size, int color){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);return t;}
    GradientDrawable shape(int c,int r){GradientDrawable d=new GradientDrawable();d.setColor(c);d.setCornerRadius(r);return d;}
    LinearLayout column(){LinearLayout l=new LinearLayout(this);l.setOrientation(1);l.setPadding(18,12,18,18);l.setBackgroundColor(bg);return l;}
    void base(String subtitle){ ScrollView sc=new ScrollView(this); root=column(); TextView brand=text("Prompt Studio 2",27,white);brand.setTypeface(null,Typeface.BOLD);root.addView(brand);TextView by=text("by Msksim Strtogiy",13,muted);by.setTypeface(null,Typeface.ITALIC);root.addView(by);TextView sub=text(subtitle,14,muted);sub.setPadding(0,8,0,12);root.addView(sub);sc.addView(root);setContentView(sc); }
    void button(LinearLayout box,String label,Runnable action){Button b=new Button(this);b.setText(label);b.setTextColor(white);b.setAllCaps(false);b.setBackground(shape(accent,22));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,6,0,6);box.addView(b,p);b.setOnClickListener(v->action.run());}
    void showHome(){base("Создавай идеальные промты для нейросетей"); TextView intro=text("Собери описание изображения из 7 блоков. Используй готовые варианты или впиши собственный текст. Сформируй русскую и английскую версии, скопируй или сохрани результат.",16,white);intro.setPadding(0,16,0,24);root.addView(intro);
      TextView prof=text("Профиль нейросети",16,white);root.addView(prof);Spinner sp=new Spinner(this);String[] opts={"Универсальный","ChatGPT Images","Nano Banana","Midjourney","Flux"};sp.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,opts));root.addView(sp);sp.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener(){public void onNothingSelected(android.widget.AdapterView<?> a){} public void onItemSelected(android.widget.AdapterView<?> a,View v,int pos,long id){profile=opts[pos];}});
      button(root,"＋ Создать новый промт",()->showBuilder());button(root,"Автор приложения: Msksim Strtogiy",()->new AlertDialog.Builder(this).setMessage("Prompt Studio 2\nАвтор: Msksim Strtogiy").setPositiveButton("OK",null).show()); }
    void showBuilder(){base("Заполни блоки — нажимай на готовые варианты или вводи свой текст."); content=root;
      for(int i=0;i<7;i++){final int n=i;TextView h=text(titles[i],17,white);h.setTypeface(null,Typeface.BOLD);h.setPadding(0,16,0,6);root.addView(h);
        LinearLayout chips=new LinearLayout(this);chips.setOrientation(1);for(String preset:presets[i]){TextView c=text("＋  "+preset,14,white);c.setPadding(12,11,12,11);c.setBackground(shape(panel,14));LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(-1,-2);cp.setMargins(0,3,0,3);chips.addView(c,cp);c.setOnClickListener(v->{String old=inputs[n].getText().toString().trim();inputs[n].setText(old.isEmpty()?preset:old+", "+preset);});}root.addView(chips);
        inputs[i]=new EditText(this);inputs[i].setTextColor(white);inputs[i].setHintTextColor(muted);inputs[i].setHint("Ваш вариант для блока");inputs[i].setTextSize(14);inputs[i].setMinLines(2);inputs[i].setGravity(Gravity.TOP);inputs[i].setPadding(12,10,12,10);inputs[i].setBackground(shape(panel,14));root.addView(inputs[i]);
      }
      button(root,"Сформировать промт",()->showResult());button(root,"← На главную",()->showHome());
    }
    String prompt(){StringBuilder s=new StringBuilder();s.append("Профиль: ").append(profile).append("\n\n");for(int i=0;i<7;i++){String v=inputs[i].getText().toString().trim();if(!v.isEmpty())s.append(titles[i]).append(": ").append(v).append("\n");}return s.toString().trim();}
    void showResult(){String p=prompt();if(p.trim().isEmpty()){Toast.makeText(this,"Заполни хотя бы один блок",Toast.LENGTH_SHORT).show();return;}base("Готовый промт");TextView ru=text(p,15,white);ru.setPadding(12,12,12,12);ru.setBackground(shape(panel,16));root.addView(ru);button(root,"Копировать русский текст",()->copy(p));button(root,"Перевести на английский",()->translate(p));button(root,"Сохранить в файл TXT",()->save(p));button(root,"← Редактировать",()->showBuilder());}
    void copy(String s){((android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Prompt Studio",s));Toast.makeText(this,"Скопировано",Toast.LENGTH_SHORT).show();}
    void save(String s){try{java.io.File f=new java.io.File(getExternalFilesDir(null),"PromptStudio_prompt.txt");try(FileOutputStream o=new FileOutputStream(f)){o.write(s.getBytes("UTF-8"));}new AlertDialog.Builder(this).setMessage("Файл сохранён:\n"+f.getAbsolutePath()).setPositiveButton("OK",null).show();}catch(Exception e){Toast.makeText(this,"Не удалось сохранить файл",Toast.LENGTH_LONG).show();}}
    void translate(String s){final ProgressDialog d=ProgressDialog.show(this,"Перевод","Переводим на английский…",true,false);new Thread(()->{String out;try{String url="https://api.mymemory.translated.net/get?q="+URLEncoder.encode(s,"UTF-8")+"&langpair=ru|en";HttpURLConnection c=(HttpURLConnection)new URL(url).openConnection();c.setConnectTimeout(10000);c.setReadTimeout(15000);BufferedReader r=new BufferedReader(new InputStreamReader(c.getInputStream(),"UTF-8"));StringBuilder b=new StringBuilder();String line;while((line=r.readLine())!=null)b.append(line);r.close();out=new JSONObject(b.toString()).getJSONObject("responseData").getString("translatedText");}catch(Exception e){out="Не удалось выполнить онлайн-перевод. Проверь подключение к интернету.\n\n"+s;}final String result=out;runOnUiThread(()->{d.dismiss();new AlertDialog.Builder(this).setTitle("English prompt").setMessage(result).setPositiveButton("Копировать", (a,b)->copy(result)).setNegativeButton("Закрыть",null).show();});}).start();}
}
