package com.example.firestonearrayjava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements BaseAdapter.ItemEvent {
    final String TAG = MainActivity.class.getSimpleName();
    final String COLLECTION_NAME = "grade";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView rvStudents;
    EditText etName;
    EditText etMath;
    EditText etScience;
    StudentsAdapter adapter;
    int selIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.etName);
        etMath = findViewById(R.id.etMath);
        etScience = findViewById(R.id.etScience);

        rvStudents = findViewById(R.id.rvStudents);
        LinearLayoutManager lm = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvStudents.setLayoutManager(lm);
        adapter = new StudentsAdapter(R.layout.student_item, this);
        rvStudents.setAdapter(adapter);

        addSnapshotListener();
    }

    void addSnapshotListener() {
        CollectionReference cr = db.collection(COLLECTION_NAME);
        cr.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null) {
                    Log.w(TAG, "Error Snapshot event: " + error); //실패
                }
                List<DocumentSnapshot> docSnaps = value.getDocuments();
                showData(docSnaps);
            }
        });
    }

    void showData(List<DocumentSnapshot> snaps) {
        ArrayList<Student> students = new ArrayList();
        for (DocumentSnapshot snap : snaps) {
            Student student = new Gson().fromJson(snap.getData().toString(), Student.class);
            student.id = snap.getId();
            students.add(student);
            Log.d(TAG, snap.getId() + " => " + snap.getData());
        }
        updateRecyclerView(students);
    }

    void readData() {
        selIndex = -1;
        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Student> students = new ArrayList();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Student student = new Gson().fromJson(document.getData().toString(), Student.class);
                                student.id = document.getId();
                                students.add(student);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            updateRecyclerView(students);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    void updateRecyclerView(List<Student> students) {
        adapter.setList(students);
    }

    @Override
    public void onClickItem(int index) {
        selIndex = index;
        Student student = adapter.list.get(index);
        etName.setText(student.name);
        etMath.setText(student.math + "");
        etScience.setText(student.science + "");
    }

    public class Student {
        public String id;
        String name;
        int math;
        int science;

        public Student(String name, int math, int science) {
            this.name = name;
            this.math = math;
            this.science = science;
        }

        public String getInfo() {
            return name + " | Math:" + math + " | Science: " + science;
        }

        public Map<String, Object> getMap() {
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("math", math);
            map.put("science", science);
            return map;
        }
    }

    public void onBtnAdd(View v) {
        Student student = getInput();
        student.id = getNextItemId();
        updateData(student);
    }

    String getNextItemId() {
        if(adapter.list == null || adapter.list.isEmpty()) return "0";
        Student lastItem = adapter.list.get(adapter.list.size()-1);
        return "" + (Integer.parseInt(lastItem.id) + 1);
    }

    public void onBtnUpdate(View v) {
        if(selIndex < 0) return;
        Student student = getInput();
        student.id = adapter.list.get(selIndex).id;
        updateData(student);
        selIndex = -1;
    }

    public void onBtnDel(View v) {
        if(selIndex < 0) return;
        Student student = getInput();
        student.id = adapter.list.get(selIndex).id;
        delData(student);
        selIndex = -1;
    }

    Student getInput() {
        String name = etName.getText().toString();
        int math = Integer.parseInt(etMath.getText().toString());
        int science = Integer.parseInt(etScience.getText().toString());
        return new Student(name, math, science);
    }

    void updateData(Student student) {
        db.collection(COLLECTION_NAME).document(student.id)
                .set(student.getMap())
                .addOnSuccessListener(firestoreSuccessListener)
                .addOnFailureListener(firestoreFailureListener);
    }

    void delData(Student student) {
        db.collection(COLLECTION_NAME).document(student.id)
                .delete()
                .addOnSuccessListener(firestoreSuccessListener)
                .addOnFailureListener(firestoreFailureListener);
    }

    OnSuccessListener firestoreSuccessListener =  new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void unused) {
            Log.d(TAG,"FireStore input success");
        }
    };

    OnFailureListener firestoreFailureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.w(TAG, "Error adding document", e);
        }
    };

}