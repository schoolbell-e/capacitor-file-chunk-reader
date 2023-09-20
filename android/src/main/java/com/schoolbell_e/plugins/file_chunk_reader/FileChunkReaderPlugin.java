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

@CapacitorPlugin(name = "FileChunkReader")
public class FileChunkReaderPlugin extends Plugin {

    private FileChunkReader implementation = new FileChunkReader();

    @PluginMethod
    public void readFileChunk(PluginCall call) {
        String tPath = call.getString("path");
        Integer tOffset = call.getInt("offset", 0);
        Integer tLength = call.getInt("length", 0);
        JSObject tResponse = new JSObject();


        Uri uri = Uri.parse(tPath);
        if (uri.getScheme() == null || u.getScheme().equals("file")) {
            byte[] tBuffer = new byte[tLength];
            try (RandomAccessFile tRandomAccessFile = new RandomAccessFile(tPath, "r")) {
                tRandomAccessFile.seek(tOffset); // MOVE TO OFFSET
                tRandomAccessFile.read(tBuffer, 0, tLength);
                tResponse.put("data",android.util.Base64.encodeToString(tBuffer, android.util.Base64.NO_WRAP));
                call.resolve(tResponse);
            } catch (IOException e) {
                call.reject(e.toLocalizedMessage());
            }
        }   
        else if (u.getScheme().equals("file")) {
            ContentResolver contentResolver = getContentResolver(); // Replace with your context or activity

            Uri contentUri = Uri.parse("content://your_content_provider/your_resource");
            String[] projection = {MediaStore.Images.Media.DATA}; // Replace with your desired columns
            Cursor cursor = contentResolver.query(contentUri, projection, null, null, null);
            
            String data;
            if (cursor != null) {
                if (cursor.moveToPosition(startIndex)) {
                    // Loop through the range
                    for (int i = startIndex; i <= endIndex; i++) {
                        // Read data from the cursor
                        data += cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            
                        // Move to the next position
                        if (!cursor.moveToNext()) {
                            break; // End of the range
                        }
                    }
                }
                cursor.close(); // Close the cursor when done
                if (data == null) {
                    call.reject("data is null");
                }
                else {
                    byte[] encodedBytes = Base64.getEncoder().encode(data.getBytes());
                    tResponse.put("data",new String(encodedBytes));
                    call.resolve(tResponse);
                }

            }    
            else {
                call.reject("cursor is null");
            }
        }     


    }

    @PluginMethod
    public void readFileSize (PluginCall call) {

        String tPath = call.getString("path");
            Uri uri = Uri.parse(path);
            if (uri.getScheme() == null || uri.getScheme().equals("file")) {
                File fileObject = new File(uri.getPath());
            }
            else if (u.getScheme().equals("conent")) {

                ContentResolver contentResolver = getContentResolver(); // Replace with your context or activity
        
                // Define the columns you want to retrieve
                String[] projection = {
                    MediaStore.Files.FileColumns.SIZE, // File size in bytes
                };
        
                Cursor cursor = contentResolver.query(contentUri, projection, null, null, null);
        
                Long fileSize;
                if (cursor != null) { 
                    if (cursor.moveToFirst()) {
                    int sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE);
                    fileSize = cursor.getLong(sizeIndex);

                    }
                    cursor.close(); // Close the cursor when done

                    if (fileSize == null) {
                        call.reject("fileSize is null.");
                    }
                    else {
                        tResponse.put("data",fileSize);
                        call.resolve(tResponse);
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
