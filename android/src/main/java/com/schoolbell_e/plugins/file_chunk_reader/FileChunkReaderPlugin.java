package com.schoolbell_e.plugins.file_chunk_reader;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.util.Base64; 
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import java.io.RandomAccessFile;
import java.io.IOException;
import android.content.ContentResolver;
import java.io.File;
@CapacitorPlugin(name = "FileChunkReader")
public class FileChunkReaderPlugin extends Plugin {

    private FileChunkReader implementation = new FileChunkReader();

    @PluginMethod
    public void readFileChunk(PluginCall call) {
        String path = call.getString("path");
        Integer start = call.getInt("start", 0);
        Integer end = call.getInt("end", 0);
        Integer length = end - start + 1;



        Uri uri = Uri.parse(path);
        if (uri.getScheme() == null || uri.getScheme().equals("file")) {
            byte[] tBuffer = new byte[length];
            try (RandomAccessFile tRandomAccessFile = new RandomAccessFile(path, "r")) {
                tRandomAccessFile.seek(start); // MOVE TO OFFSET
                tRandomAccessFile.read(tBuffer, 0, length);

                JSObject response = new JSObject();
                response.put("data",android.util.Base64.encodeToString(tBuffer, android.util.Base64.NO_WRAP));
                call.resolve(response);
            } catch (IOException e) {
                call.reject(e.getLocalizedMessage());
            }
        }   
        else if (uri.getScheme().equals("content")) {
            ContentResolver contentResolver = getContext().getContentResolver(); // Replace with your context or activity

            String[] projection = {MediaStore.Images.Media.DATA}; // Replace with your desired columns
            Cursor cursor = contentResolver.query(uri, projection, null, null, null);
            
            String data = "";
            if (cursor != null) {
                if (cursor.moveToPosition(start)) {
                    // Loop through the range
                    for (int i = start; i <= end; i++) {
                        // Read data from the cursor
                        String dataBit = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                        data += dataBit;

                        // Move to the next position
                        if (!cursor.moveToNext()) {
                            break; // End of the range
                        }
                    }
                }
                cursor.close(); // Close the cursor when done
                if (data == "") {
                    call.reject("data is null");
                }
                else {
                    byte[] encodedBytes = Base64.getEncoder().encode(data.getBytes());
                    String base64EncodedString = new String(encodedBytes);
                    JSObject response = new JSObject();
                    response.put("data",base64EncodedString);
                    call.resolve(response);
                }

            }    
            else {
                call.reject("cursor is null");
            }
        }     


    }

    @PluginMethod
    public void readFileSize (PluginCall call) {

        String path = call.getString("path");
            Uri uri = Uri.parse(path);
            if (uri.getScheme() == null || uri.getScheme().equals("file")) {
                File fileObject = new File(uri.getPath());
            }
            else if (uri.getScheme().equals("content")) {

                ContentResolver contentResolver = getContext().getContentResolver(); // Replace with your context or activity
        
                // Define the columns you want to retrieve
                String[] projection = {
                    MediaStore.Files.FileColumns.SIZE, // File size in bytes
                };
        
                Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        
                long fileSize = 0;
                if (cursor != null) { 
                    if (cursor.moveToFirst()) {
                    int sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE);
                    fileSize = cursor.getLong(sizeIndex);

                    }
                    cursor.close(); // Close the cursor when done

                    if (fileSize == 0) {
                        call.reject("fileSize is null.");
                    }
                    else {
                        JSObject response = new JSObject();
                        response.put("size",fileSize);
                        call.resolve(response);
                    }
                }        
                else {
                    call.reject("file does not exist.");
                }



            }
            else {
                call.reject("invalid path.");
            }
    }
}
