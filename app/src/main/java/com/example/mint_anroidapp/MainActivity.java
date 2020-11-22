package com.example.mint_anroidapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.pytorch.IValue;
import org.pytorch.Module;
import org.pytorch.Tensor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;

import brainflow.AggOperations;
import brainflow.BoardIds;
import brainflow.BoardShim;
import brainflow.BrainFlowError;
import brainflow.BrainFlowInputParams;
import brainflow.DataFilter;
import brainflow.FilterTypes;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech text_to_speech;
    private TextInputLayout one_voice;
    private TextInputLayout two_voice;
    private TextInputLayout three_voice;
    private TextInputLayout four_voice;
    private Button one_title;
    private Button two_title;
    private Button three_title;
    private Button four_title;
    private ImageView goto_bluetooth_button;
    private ImageView goto_settings_button;

    private Module module; // PyTorch model

    // BrainFlow
    public static BoardShim boardShim = null;
    public static int samplingRate = 0;
    public static int[] channels = null;
    String address = "MY_IP_ADDRESS"; // TODO: set this
    String port = "MY_PORT"; // TODO: set this
    private int windowSize = 2;
    private int timeSleep = 1; // 20fps
    private Runnable worker = null;
    private final Handler handler = new Handler();
    private static int downnsamplingOrder = 1;

    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        one_voice = (TextInputLayout) findViewById(R.id.command_one_voice);
        two_voice = (TextInputLayout) findViewById(R.id.command_two_voice);
        three_voice = (TextInputLayout) findViewById(R.id.command_three_voice);
        four_voice = (TextInputLayout) findViewById(R.id.command_four_voice);
        one_title  = (Button) findViewById(R.id.command_one);

        myDialog = new Dialog(this);

        text_to_speech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    text_to_speech.setLanguage(Locale.CANADA);
                }
            }
        });

        goto_bluetooth_button = (ImageView) findViewById(R.id.bluetooth);
        goto_bluetooth_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenBluetooth();
            }
        });

        goto_settings_button =  (ImageView) findViewById(R.id.settings);
        goto_settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSettings();
            }
        });

//        Tensor inputTensor = null;
//        long[] shape = new long[]{1,1,8,500};

        // Initiate PyTorch model
        try {
            module = Module.load(assetFilePath(this, "xxx_model.pt"));
        } catch (IOException e) {
            Log.e("PyTorch", "Error reading assets", e);
            finish();
        }

        onResume();
    }

    public void OpenSettings() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void OpenBluetooth() {
        Intent intent = new Intent(this, bluetooth.class);
        startActivity(intent);
    }

    public void SpeakOne(View v) {
        String to_speak = one_voice.getEditText().getText().toString();
        text_to_speech.speak(to_speak, TextToSpeech.QUEUE_FLUSH,
                null);
        invokeAutomationCommand(to_speak);
    }

    public void SpeakTwo(View v) {
        String to_speak = two_voice.getEditText().getText().toString();
        text_to_speech.speak(to_speak, TextToSpeech.QUEUE_FLUSH, null);
        invokeAutomationCommand(to_speak);
    }

    public void SpeakThree(View v) {
        String to_speak = three_voice.getEditText().getText().toString();
        text_to_speech.speak(to_speak, TextToSpeech.QUEUE_FLUSH, null);
        invokeAutomationCommand(to_speak);
    }

    public void SpeakFour(View v) {
        String to_speak = four_voice.getEditText().getText().toString();
        text_to_speech.speak(to_speak, TextToSpeech.QUEUE_FLUSH, null);
        invokeAutomationCommand(to_speak);
    }

    public void OneShowPopup(View v) {
        TextView text_close;
        Button btnFollow = (Button) myDialog.findViewById(R.id.one_save_button);
        myDialog.setContentView(R.layout.command_one);
        text_close =(TextView) myDialog.findViewById(R.id.text_close);
        text_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        one_voice = (TextInputLayout) myDialog.findViewById(R.id.command_one_voice);
        Button save = (Button) myDialog.findViewById(R.id.one_save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout voiceText = (TextInputLayout) myDialog.findViewById(R.id.command_one_title);
                String text =  voiceText.getEditText().getText().toString();

                // button title
                one_title = findViewById(R.id.command_one);
                one_title.setText(text);

                // popup title
                TextView one_popup = myDialog.findViewById(R.id.one_popup_title);
                one_popup.setText(text);

                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void TwoShowPopup(View v) {
        TextView text_close;
        Button button_save = (Button) myDialog.findViewById(R.id.two_save_button);
        myDialog.setContentView(R.layout.command_two);
        text_close =(TextView) myDialog.findViewById(R.id.text_close);
        text_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        two_voice = (TextInputLayout) myDialog.findViewById(R.id.command_two_voice);
        Button save = (Button) myDialog.findViewById(R.id.two_save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout voiceText = (TextInputLayout) myDialog.findViewById(R.id.command_two_title);
                String text =  voiceText.getEditText().getText().toString();

                // button title
                two_title = myDialog.findViewById(R.id.command_two);
                two_title.setText(text);

                // popup title
                TextView two_popup = findViewById(R.id.two_popup_title);
                two_popup.setText(text);

                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void ThreeShowPopup(View v) {
        TextView text_close;
        Button button_save = (Button) myDialog.findViewById(R.id.three_save_button);
        myDialog.setContentView(R.layout.command_three);
        text_close =(TextView) myDialog.findViewById(R.id.text_close);
        text_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        three_voice = (TextInputLayout) myDialog.findViewById(R.id.command_three_voice);
        Button save = (Button) myDialog.findViewById(R.id.three_save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout voiceText = (TextInputLayout) myDialog.findViewById(R.id.command_three_title);
                String text =  voiceText.getEditText().getText().toString();

                // button title
                three_title = myDialog.findViewById(R.id.command_three);
                three_title.setText(text);

                // popup title
                TextView three_popup = findViewById(R.id.three_popup_title);
                three_popup.setText(text);

                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void FourShowPopup(View v) {
        TextView text_close;
        Button button_save = (Button) myDialog.findViewById(R.id.four_save_button);
        myDialog.setContentView(R.layout.command_four);
        text_close =(TextView) myDialog.findViewById(R.id.text_close);
        text_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        four_voice = (TextInputLayout) myDialog.findViewById(R.id.command_four_voice);
        Button save = (Button) myDialog.findViewById(R.id.four_save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout voiceText = (TextInputLayout) myDialog.findViewById(R.id.command_four_title);
                String text =  voiceText.getEditText().getText().toString();

                // button title
                four_title = myDialog.findViewById(R.id.command_four);
                four_title.setText(text);

                // popup title
                TextView four_popup = findViewById(R.id.four_popup_title);
                four_popup.setText(text);

                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void invokeAutomationCommand(String command) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.12:3000/assistant";

        String user = "Victor"; // TODO: modify this when we want multiple/different users
        JSONObject data = new JSONObject();

        Log.d("AutomationCommand", "SENDING  " + url);

        try {
            data.put("command", command);
            data.put("converse", true);
            data.put("user", user);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO: Check for success on response
                        Log.d("AutomationCommand", response.toString());
                    }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                });

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onDestroy() {
        if (text_to_speech != null) {
            text_to_speech.stop();
            text_to_speech.shutdown();
        }
        super.onDestroy();
    }

    /**
     * Copies specified asset to the file in /files app directory and returns this file absolute path.
     *
     * @return absolute file path
     */
    public static String assetFilePath(Context context, String assetName) throws IOException {
        File file = new File(context.getFilesDir(), assetName);
        if (file.exists() && file.length() > 0) {
            return file.getAbsolutePath();
        }

        try (InputStream is = context.getAssets().open(assetName)) {
            try (OutputStream os = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
            return file.getAbsolutePath();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private int predict(double[][] data) {
        Tensor inputTensor = null;
        long[] shape = new long[]{1,1,8,500};

        // preparing tensor
        double[] inputDataArr = Arrays.stream(data) // flatten to 1D array
                .flatMapToDouble(Arrays::stream)
                .toArray();

        inputTensor = Tensor.fromBlob(inputDataArr, shape);

        // running the model
        final Tensor outputTensor = module.forward(IValue.from(inputTensor)).toTensor();

        // getting tensor content as java array of floats
        final float[] scores = outputTensor.getDataAsFloatArray();

        // searching for the index with maximum score
        float maxScore = -Float.MAX_VALUE;
        int maxScoreIdx = -1;
        for (int i = 0; i < scores.length; i++) {
//            Log.d("MainActivity", "Output score at index " + i + ": " +  scores[i]);
            if (scores[i] > maxScore) {
                maxScore = scores[i];
                maxScoreIdx = i;
            }
        }

        return maxScoreIdx;
    }

    private void handlePrediction(int maxScoreIdx) {
        switch (maxScoreIdx) {
            case 0:
                Log.d("MainActivity", "handlePrediction: Passive");
                break;
            case 1:
                Log.d("MainActivity", "handlePrediction: Left Arm");
                SpeakOne(null);
                break;
            case 2:
                Log.d("MainActivity", "handlePrediction: Left Leg");
                SpeakThree(null);
                break;
            case 3:
                Log.d("MainActivity", "handlePrediction: Right Arm");
                SpeakTwo(null);
                break;
            case 4:
                Log.d("MainActivity", "handlePrediction: Right Leg");
                SpeakFour(null);
                break;
            default:
                Log.d("MainActivity", "handlePrediction: DEFAULT (passive)");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // read settings
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int boardId = BoardIds.CYTON_WIFI_BOARD.get_code();

        try {
            BrainFlowInputParams params = new BrainFlowInputParams();
            boolean done = false;
            int count = Integer.parseInt(port);
            params.ip_address = address;
            params.ip_port = count;
            boardShim = new BoardShim(boardId, params);
            // prepare_session is relatively long operation, doing it in UI thread lead to black window for a few seconds
            boardShim.prepare_session();
            boardShim.start_stream();
            samplingRate = BoardShim.get_sampling_rate(boardId);
            channels = BoardShim.get_exg_channels(boardId);
            while (true) {
                run_a();
            }
        } catch (Exception e) {
//            Log.e(getString(R.string.log_tag), e.getMessage());
        }
    }

    @Override
    protected void onPause() {
        try {
            if (boardShim != null) {
                boardShim.release_session();
            }
        } catch (BrainFlowError e) {
//            Log.e(getString(R.string.log_tag), e.getMessage());
        }
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void run_a() throws IOException {

        // don't plot too many datapoints
        int desiredSamplingRate = 250;
        if (MainActivity.samplingRate > desiredSamplingRate) {
            downnsamplingOrder = MainActivity.samplingRate / desiredSamplingRate;
        }

        try {
            double[][] tempArray = MainActivity.boardShim.get_current_board_data(MainActivity.samplingRate * windowSize);
            double[][] tmpArray = new double[tempArray.length][MainActivity.samplingRate * windowSize];
            double[][] dataArray = null;
            // prepend with zeroes if less datapoints
            if (tempArray[0].length != MainActivity.samplingRate * windowSize) {
                for (int i = 0; i < tempArray.length; i++) {
                    for (int j = 0; j < MainActivity.samplingRate * windowSize - tempArray[i].length; j++) {
                        tmpArray[i][j] = 0.0;
                    }
                    for (int j = 0; j < tempArray[i].length; j++) {
                        tmpArray[i][j + MainActivity.samplingRate * windowSize - tempArray[i].length] = tempArray[i][j];
                    }
                }
            } else {
                tmpArray = tempArray;
            }
            for (int i = 0; i < MainActivity.channels.length; i++) {
                DataFilter.perform_bandstop(tmpArray[MainActivity.channels[i]], MainActivity.samplingRate, 50.0, 4.0, 4,
                        FilterTypes.BUTTERWORTH.get_code(), 0.0);
                DataFilter.perform_bandstop(tmpArray[MainActivity.channels[i]], MainActivity.samplingRate, 60.0, 4.0, 4,
                        FilterTypes.BUTTERWORTH.get_code(), 0.0);
                DataFilter.perform_bandpass(tmpArray[MainActivity.channels[i]], MainActivity.samplingRate, 19.0, 11.0, 4,
                        FilterTypes.BUTTERWORTH.get_code(), 0.0);
            }
            // downsampling
            if (downnsamplingOrder > 1) {
                dataArray = new double[tmpArray.length][];
                for (int i = 0; i < tmpArray.length; i++) {
                    dataArray[i] = DataFilter.perform_downsampling(tmpArray[i], downnsamplingOrder, AggOperations.MEDIAN.get_code());
                }
            } else {
                dataArray = tmpArray;
            }
            int num_rows = MainActivity.channels.length;
            double[][] data = dataArray;

            // Run model
            int prediction = predict(data);
            handlePrediction(prediction);

//            for (int i = 0; i < num_rows; i++)
//            {
//                System.out.println (Arrays.toString (data[i]));
//            }
//            System.out.println(data[0].length + "x" + data.length);

        } catch (BrainFlowError e) {
//                   Log.e(getString(R.string.log_tag), e.getMessage());
        }
    }

}
