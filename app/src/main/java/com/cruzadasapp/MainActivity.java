package com.cruzadasapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //

    private EditText Date;
    Spinner spinnerAct, spinnerDpto, spinnerEquip, spinnerfinal;
    EditText ids, ot, btsvisit, btsconect, municip, Hllegada, HSalida, Tecnico, Vlan, NemOrg, NemDes;
    private Button btnadd, ImgBtn, ImgBtn2, ImgBtn3, ImgBtn4, EndBtn;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    CollectionReference documentReference = fStore.collection("Actividades");

    StorageReference storageReference;
    FirebaseStorage storage;
    private Uri filePath, filePath2, filePath3, filePath4;
    private ImageView Img_One, Img_Two,Img_Three, Img_Four;
    private final int PICK_IMAGE_REQUEST = 1;
    private final int PICK_IMAGE_REQUEST2= 2;
    private final int PICK_IMAGE_REQUEST3= 3;
    private final int PICK_IMAGE_REQUEST4= 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Select the activity date

        Date = findViewById(R.id.Date);

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        Date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                DatePickerDialog fecha = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + " / " + month + " / " + year;
                        Date.setText(date);
                    }
                }, year, month, day);
                fecha.show();
            }
        }); //end select date


        //Spinners

        //Activity Spinner
        final List<String> Actividad = Arrays.asList("Actividad realizada", "Cruzada", "Certificación");
        spinnerAct = findViewById(R.id.spinnerAct);
        ArrayAdapter adapteract = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, Actividad);
        adapteract.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAct.setAdapter(adapteract);
        spinnerAct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }});

        //Department spinner
        final List<String> Departamento = Arrays.asList("Departamento", "HUILA", "TOLIMA", "CAQUETÁ");
        spinnerDpto = findViewById(R.id.spinnerDpto);
        ArrayAdapter adapterdpto = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, Departamento);
        adapteract.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDpto.setAdapter(adapterdpto);
        spinnerDpto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }});

        // Installed Equip Spinner
        final List<String> Equipo = Arrays.asList("Equipo Instalado", "DEMARCADOR", "SW1", "SW2", "N/A");
        spinnerEquip = findViewById(R.id.spinnerequip);
        ArrayAdapter adapterequip = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, Equipo);
        adapterequip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEquip.setAdapter(adapterequip);
        spinnerEquip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }});

        // Success Activity Spinner
        final List<String> Exito = Arrays.asList("¿Actividad Exitosa?", "Sí", "No");
        spinnerfinal = findViewById(R.id.spinnerext);
        ArrayAdapter adapterfinal = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, Exito);
        adapterfinal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerfinal.setAdapter(adapterfinal);
        spinnerfinal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }});

        //end Spinners

        //Start save data

        ids = findViewById(R.id.Ids);
        ot = findViewById(R.id.Ot);
        btnadd = findViewById(R.id.BtnAdd);
        btsvisit = findViewById(R.id.BtsVisit);
        btsconect = findViewById(R.id.btsconecta);
        municip = findViewById(R.id.municipio);
        Hllegada = findViewById(R.id.ArriveTime);
        HSalida = findViewById(R.id.LeftTime);
        Tecnico = findViewById(R.id.tecnic);
        Vlan = findViewById(R.id.vlan);
        NemOrg = findViewById(R.id.nemorg);
        NemDes = findViewById(R.id.nemdes);
        EndBtn = findViewById(R.id.endbtn);

        Img_One          = findViewById(R.id.img_one);
        Img_Two          = findViewById(R.id.img_two);
        Img_Three        = findViewById(R.id.img_three);
        Img_Four         = findViewById(R.id.img_four);
        ImgBtn           = findViewById(R.id.imgBtn);
        ImgBtn2          = findViewById(R.id.imgbtn2);
        ImgBtn3          = findViewById(R.id.imgbtn3);
        ImgBtn4          = findViewById(R.id.imgbtn4);
        storageReference = FirebaseStorage.getInstance().getReference();
        storage          = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        ImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
        ImgBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage2();
            }
        });
        ImgBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage3();
            }
        });
        ImgBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage4();
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertdata();}
        });

        EndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });


    }
    // upload img
    private void SelectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione una imagen de su galería"),PICK_IMAGE_REQUEST);

    }//
    private void SelectImage2(){
        Intent intent2 = new Intent();
        intent2.setType("image/*");
        intent2.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent2, "Seleccione una imagen de su galería"), PICK_IMAGE_REQUEST2);
    }
    private void SelectImage3(){
        Intent intent3 = new Intent();
        intent3.setType("image/*");
        intent3.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent3, "Seleccione una imagen de su galería"), PICK_IMAGE_REQUEST3);
    }
    private void SelectImage4(){
        Intent intent4 = new Intent();
        intent4.setType("image/*");
        intent4.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent4, "Seleccione una imagen de su galería"), PICK_IMAGE_REQUEST4);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //img 1
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the Uri of data
            filePath  = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Img_One.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }else if (resultCode == RESULT_OK && requestCode == 2 && data != null && data.getData() != null){
            filePath2 = data.getData();
            try {
                Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath2);
                Img_Two.setImageBitmap(bitmap2);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }else if (resultCode == RESULT_OK && requestCode == 3 && data != null && data.getData() != null){
            filePath3 = data.getData();
            try {
                Bitmap bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath3);
                Img_Three.setImageBitmap(bitmap3);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }else if (resultCode == RESULT_OK && requestCode == 4 && data != null && data.getData() != null){
            filePath4 = data.getData();
            try {
                Bitmap bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath4);
                Img_Four.setImageBitmap(bitmap4);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //save data
    private void insertdata() {
        String btsvisita = btsvisit.getText().toString().trim();
        String txtspinAct = spinnerAct.getSelectedItem().toString().trim();
        String txtspinDpto = spinnerDpto.getSelectedItem().toString().trim();
        String txtspinEquip = spinnerEquip.getSelectedItem().toString().trim();
        String txtspinFinal = spinnerfinal.getSelectedItem().toString().trim();
        String date = Date.getText().toString().trim();
        String btsconecta = btsconect.getText().toString().trim();
        String municipio = municip.getText().toString().trim();
        String hllegada = Hllegada.getText().toString().trim();
        String hsalida = HSalida.getText().toString().trim();
        String tecnico = Tecnico.getText().toString().trim();
        String Vlans = Vlan.getText().toString().trim();
        String NemOrig = NemOrg.getText().toString().trim();
        String NemDest = NemDes.getText().toString().trim();
        if (txtspinAct.equals("Actividad realizada")) {
            Toast.makeText(MainActivity.this, "Por favor ingrese la Actividad ", Toast.LENGTH_SHORT).show();
        } else if (date.isEmpty()) {
            Toast.makeText(MainActivity.this, "Por favor ingrese la Fecha de visita ", Toast.LENGTH_SHORT).show();
        } else if (txtspinDpto.equals("Departamento")) {
            Toast.makeText(MainActivity.this, "Por favor ingrese el Departamento", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(municip.getText())) {
            Toast.makeText(MainActivity.this, "Por favor ingrese el Municipio", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(btsvisit.getText())) {
            Toast.makeText(MainActivity.this, "Por favor ingrese la BTS a Visitar", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(btsconect.getText())) {
            Toast.makeText(MainActivity.this, "Por favor ingrese la BTS Conectante", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Tecnico.getText())) {
            Toast.makeText(MainActivity.this, "Por favor ingrese el Nombre del Técnico en Sitio", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(ids.getText())) {
            Toast.makeText(MainActivity.this, "Por favor ingrese el/los IDs de la actividad", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(ot.getText())) {
            Toast.makeText(MainActivity.this, "Por favor ingrese la Orden de Trabajo (OT)", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Hllegada.getText())) {
            Toast.makeText(MainActivity.this, "Por favor ingrese la Hora de Llegada", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(HSalida.getText())) {
            Toast.makeText(MainActivity.this, "Por favor ingrese la Hora de Salida", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Vlan.getText())) {
            Toast.makeText(MainActivity.this, "Por favor ingrese las Vlans. Si no aplican solo escriba N/A", Toast.LENGTH_SHORT).show();
        } else if (txtspinEquip.equals("Equipo Instalado")) {
            Toast.makeText(MainActivity.this, "Por favor mencione el Equipo Instalado (seleccione N/A si no aplica) ", Toast.LENGTH_SHORT).show();
        } else if (txtspinFinal.equals("¿Actividad Exitosa?")) {
            Toast.makeText(MainActivity.this, "Por favor mencione si la Actividad fue Exitosa", Toast.LENGTH_SHORT).show();
        }  else if (Img_One.getDrawable() == null) {
            Toast.makeText(MainActivity.this, "La Primera y Segunda Imagen son obligatorias", Toast.LENGTH_SHORT).show();
        } else if (Img_Two.getDrawable() == null) {
            Toast.makeText(MainActivity.this, "La Primera y Segunda Imagen son obligatorias", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, Object> items = new HashMap<>();
            items.put("Actividad", txtspinAct);
            items.put("Departamento", txtspinDpto);
            items.put("Equipo Instalado", txtspinEquip);
            items.put("¿Actividad Exitosa?", txtspinFinal);
            items.put("Fecha de visita", date);
            items.put("ID/s", ids.getText().toString().trim());
            items.put("OT", ot.getText().toString().trim());
            items.put("BTS a Visitar", btsvisita);
            items.put("BTS Conectante", btsconecta);
            items.put("Municipio", municipio);
            items.put("Nombre del Técnico", tecnico);
            items.put("Hora de llegada", hllegada);
            items.put("Hora de Salida", hsalida);
            items.put("Vlans Probadas", Vlans);
            items.put("Nemónico de Origen", NemOrig);
            items.put("Nemónico de Destino", NemDest);


            documentReference.add(items).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(MainActivity.this, "Se han guardado los datos de " + btsvisita, Toast.LENGTH_SHORT).show();
                }
            });//

                //Save image One
            if (filePath != null) {
                // Code for showing progressDialog while uploading
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Cargando...");
                progressDialog.show();
                // Defining the child of storageReference
                StorageReference ref = storageReference.child("Actividades/ " + btsvisita + " " + date +" "+ UUID.randomUUID().toString());
                // adding listeners on upload
                // or failure of image
                ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Image uploaded successfully
                        // Dismiss dialog
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Imágen 1 guardada con éxito", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error, Image not uploaded
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Error al cargar la 1 imagen" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                    // Progress Listener for loading
                    // percentage on the dialog box
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Cargando imagen " + (int)progress + " % ");
                    }
                });//
            }
            //Img Two
            if (filePath2 != null) {
                // Code for showing progressDialog while uploading
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Cargando...");
                progressDialog.show();
                // Defining the child of storageReference
                StorageReference ref = storageReference.child("Actividades/ " + btsvisita + " " + date +" " + UUID.randomUUID().toString());
                // adding listeners on upload
                // or failure of image
                ref.putFile(filePath2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Image uploaded successfully
                        // Dismiss dialog
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Imágen 2 guardada con éxito", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error, Image not uploaded
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Error al cargar la 2 imagen" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                    // Progress Listener for loading
                    // percentage on the dialog box
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Cargando imagen " + (int)progress + " % ");
                    }
                });//
            }if (filePath3 != null) {
                // Code for showing progressDialog while uploading
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Cargando...");
                progressDialog.show();
                // Defining the child of storageReference
                StorageReference ref = storageReference.child("Actividades/ " + btsvisita + " " + date +" " + UUID.randomUUID().toString());
                // adding listeners on upload
                // or failure of image
                ref.putFile(filePath3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Image uploaded successfully
                        // Dismiss dialog
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Imágen 3 guardada con éxito", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error, Image not uploaded
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Error al cargar la 3 imagen" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                    // Progress Listener for loading
                    // percentage on the dialog box
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Cargando imagen " + (int)progress + " % ");
                    }
                });//
            }if (filePath4 != null) {
                // Code for showing progressDialog while uploading
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Cargando...");
                progressDialog.show();
                // Defining the child of storageReference
                StorageReference ref = storageReference.child("Actividades/ " + btsvisita + " " + date +" " + UUID.randomUUID().toString());
                // adding listeners on upload
                // or failure of image
                ref.putFile(filePath4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Image uploaded successfully
                        // Dismiss dialog
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Imágen 4 guardada con éxito", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error, Image not uploaded
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Error al cargar la 4 imagen" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                    // Progress Listener for loading
                    // percentage on the dialog box
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Cargando imagen " + (int)progress + " % ");
                    }
                });//
            }
        }
    }

}