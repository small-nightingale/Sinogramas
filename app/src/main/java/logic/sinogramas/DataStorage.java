package logic.sinogramas;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import data.sinogramas.Unihan;

/**
 * This class manage storage of the favorite sinograms adds by the user
 * @author small-nightingale
 */
public class DataStorage {

    private Context contextToWorkWith;
    private File favoriteSinograms;
    private File path;
    private FileReader input;
    private FileWriter output;
    private LinkedList<Unihan> sinograms;
    private final String fileName = "favoriteSinograms.txt";

    /**
     * Class constructor, it initiates FileReader and FileWriter with a File that is created
     * @param contextToWorkWith: Context necessary to store files and make toasts
     * @param sinograms: LinkedList of sinograms to store
     */
    public DataStorage(Context contextToWorkWith, LinkedList<Unihan> sinograms) {
        //this.path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM+File.separator+"Sinogramas");
        this.contextToWorkWith = contextToWorkWith;
        this.path = new File(contextToWorkWith.getFilesDir(), "sinogramas");
        this.favoriteSinograms= new File(path, fileName);
        this.sinograms = sinograms;
        try {
            if (!path.exists()) path.mkdir();
            if (!favoriteSinograms.exists()) {
                favoriteSinograms.createNewFile();
                Toast.makeText(contextToWorkWith,"Stored at:"+favoriteSinograms.getAbsoluteFile(), Toast.LENGTH_LONG).show();
            }
            this.input = new FileReader(favoriteSinograms);
            this.output = new FileWriter(favoriteSinograms);
        } catch (IOException ioException) {
            Toast.makeText(contextToWorkWith,ioException.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public LinkedList<Unihan> getSinograms() {
        return this.sinograms;
    }

    /**
     * Removes characters from the list and stores, parses them and stores them into a File
     * @return true if here is no error
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean store() {
        boolean success = false;
        try {
            while (this.sinograms.size()>0) {
                Unihan currentChar = this.sinograms.poll();
                if (currentChar!=null) this.output.append(currentChar.print()+System.lineSeparator());
            }
            this.output.flush();
            success = true;
            Toast.makeText(contextToWorkWith,favoriteSinograms.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException ioException) {
            if (this.output==null) {
                Toast.makeText(contextToWorkWith,"Escritura nula", Toast.LENGTH_LONG).show();
            }
        }
        return success;
    }

    /**
     * Parses a text, reads a line and stores it into a list
     * @return true if it was successful
     */
    public boolean retrieve() {
        this.sinograms = new LinkedList<>();
        boolean success = false;
        try {
            BufferedReader parseInformation = new BufferedReader(input);
            String line;
            while ((line = parseInformation.readLine()) != null) {
                this.sinograms.offer(parseText(line));
            }
            success = true;
        } catch (IOException ioException) {
            if (this.input==null) {
                Toast.makeText(contextToWorkWith,"Lectura nula", Toast.LENGTH_LONG).show();
            }
        }
        Toast.makeText(contextToWorkWith,sinograms.toString(), Toast.LENGTH_LONG).show();
        return success;
    }

    private String[] strToArray(String strToConvert) {
        strToConvert = strToConvert.substring(1,strToConvert.length()-1);
        String[] array = strToConvert.split(",");
        for (int i=0; i<array.length; i++) array[i] = array[i].trim();
        return array;
    }

    private Unihan parseText(String strToParse) {
        String[] data = strToParse.split(":");
        return new Unihan(Integer.valueOf(data[0]),data[1],data[2],data[3],data[4],
                strToArray(data[5]),strToArray(data[6]),strToArray(data[7]));
    }
}
