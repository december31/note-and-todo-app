package com.example.note_and_todo_app.ui.note;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note_and_todo_app.ui.main.MainActivity;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.note.Note;
import com.example.note_and_todo_app.database.note.NoteViewModel;
import com.example.note_and_todo_app.ui.note.dialog.TimeDialog;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class NoteDetailFragment extends Fragment {
    public Note update;
    Note add;
    Note note;
    NoteViewModel noteViewModel;
    AppCompatEditText textTitle;
    TextView textDate;
    AppCompatEditText textInfo;
    static String selectedImagePath;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    ImageView imageNote;
    ImageView deleteImageNote;

    ImageView imageClock;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_detail, container, false);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        initUi(view);
        setupUi(view);
        assert getArguments() != null;
        Long idEdit = getArguments().getLong("idEdit");
        update = Database.getInstance(this.getContext()).noteDao().getNote(idEdit);
        selectedImagePath = "";
        if (update != null) {
            try {
                setData();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        Long idAdd = getArguments().getLong("idAdd");
        add = Database.getInstance(this.getContext()).noteDao().getNote(idAdd);
        //

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                view.findViewById(R.id.backAddNote).performClick();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback((LifecycleOwner) requireContext(), callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()
        view.findViewById(R.id.btnImage).setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
            } else {
                selectImage();
            }
        });
        view.findViewById(R.id.backAddNote).setOnClickListener(v -> {
            updateData();
            Navigation.findNavController(view).popBackStack();
        });
        deleteImageNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImagePath = "";
                imageNote.setVisibility(View.GONE);
                deleteImageNote.setVisibility(View.GONE);
            }
        });

        imageClock.setOnClickListener(v -> openDialog());

        return view;
    }
    private void openDialog(){
        FragmentManager fm = getFragmentManager();
        TimeDialog timeDialog = new TimeDialog(update.getId());
        assert fm != null;
        timeDialog.show(fm,null);
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE ) {
            if (data != null) {
                Uri selectImgUri = data.getData();
                if (selectImgUri != null) {
                    try {
                        InputStream inputStream = requireActivity().getContentResolver().openInputStream(selectImgUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageNote.setImageBitmap(bitmap);
                        imageNote.setVisibility(View.VISIBLE);

                        selectedImagePath = getPathFromUri(selectImgUri);
                        deleteImageNote.setVisibility(View.VISIBLE);
                    } catch (Exception exception) {
                        Toast.makeText(getContext(), exception.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        ;
    }

    private void initUi(View view) {
        textTitle = view.findViewById(R.id.titleNoteAdd);
        textDate = view.findViewById(R.id.dateAddNote);
        textInfo = view.findViewById(R.id.typingText);
        imageNote = view.findViewById(R.id.imageNote);
        deleteImageNote = view.findViewById(R.id.deleteImageNote);
        imageClock = view.findViewById(R.id.ic_clock);
    }

    private void setData() throws FileNotFoundException {
        textTitle.setText(update.getTitle());
        textDate.setText(update.getDate());
        textInfo.setText(update.getInfo());
        selectedImagePath = update.getImagePath();
        imageNote.setImageBitmap(BitmapFactory.decodeFile(update.getImagePath()));
        if(Objects.equals(selectedImagePath, "")){
            imageNote.setVisibility(View.GONE);
            deleteImageNote.setVisibility(View.GONE);
        }else {
            imageNote.setVisibility(View.VISIBLE);
            deleteImageNote.setVisibility(View.VISIBLE);
        }

    }

    private void updateData() {
        String title = String.valueOf(textTitle.getText());
        String date = textDate.getText().toString();
        String info = String.valueOf(textInfo.getText());

        if (update != null) {
            note = new Note(update.getId(), title, info, date,selectedImagePath);
        } else {
            note = new Note(add.getId(), title, info, date,selectedImagePath);
        }
        if (info.isEmpty() && title.isEmpty()) {
            noteViewModel.delete(note);
        } else {
            noteViewModel.update(note);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        ImageView menu = getView().findViewById(R.id.menuNote);
        ImageView btnCheck = getView().findViewById(R.id.doneButton);
        EditText editText = getView().findViewById(R.id.typingText);
        EditText title = getView().findViewById(R.id.titleNoteAdd);
        btnCheck.setVisibility(View.GONE);
        focusChange(editText,menu,btnCheck);
        focusChange(title,menu,btnCheck);
        btnCheck.setOnClickListener(v -> {
            menu.setVisibility(View.VISIBLE);
            btnCheck.setVisibility(View.GONE);
            ((MainActivity)requireActivity()).closeKeyboard();
            editText.clearFocus();

        });
        menu.setOnClickListener(v -> deleteNote(getContext()));

    }
    private  void focusChange(EditText editText,ImageView menu,ImageView btnCheck){
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                menu.setVisibility(View.GONE);
                btnCheck.setVisibility(View.VISIBLE);
            }

        });
    }
    private void deleteNote(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    String title = String.valueOf(textTitle.getText());
                    String date = textDate.getText().toString();
                    String info = String.valueOf(textInfo.getText());
                    if (update != null) {
                        note = new Note(update.getId(), title, info, date);
                    } else {
                        note = new Note(add.getId(), title, info, date);
                    }
                    noteViewModel.delete(note);
                    Navigation.findNavController(requireView()).popBackStack();
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_menu_delete)
                .show();

    }
    private void setupUi(View view) {
        TextView textDate = (view).findViewById(R.id.dateAddNote);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US);
        String dateTime = simpleDateFormat.format(calendar.getTime());
        textDate.setText(dateTime);
    }



    private String getPathFromUri(Uri contentUri) {
        String filePath;
        Cursor cursor = requireActivity().getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            filePath = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;

    }

}