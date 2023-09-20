package com.schoolbell_e.plugins.file_chunk_reader;

import android.util.Log;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
public class FileChunkReader {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }

    public String readChunkForContentUri () {
        ContentResolver contentResolver = getContentResolver(); // Replace with your context or activity

        Uri contentUri = Uri.parse("content://your_content_provider/your_resource");
        String[] projection = {MediaStore.Images.Media.DATA}; // Replace with your desired columns
        Cursor cursor = contentResolver.query(contentUri, projection, null, null, null);
        
        String data = "";
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
        }        
        return data;
        
    }

    public Void getStatFromContentUri () {
        // Replace "your_content_uri" with the actual content URI you want to retrieve information for.
        Uri contentUri = Uri.parse("content://your_content_provider/your_resource");

        ContentResolver contentResolver = getContentResolver(); // Replace with your context or activity

        // Define the columns you want to retrieve
        String[] projection = {
            MediaStore.Files.FileColumns.SIZE, // File size in bytes
            MediaStore.Files.FileColumns.DATE_MODIFIED // Last modification time in seconds since epoch
        };

        Cursor cursor = contentResolver.query(contentUri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE);
            int mtimeIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED);

            long fileSize = cursor.getLong(sizeIndex);
            long lastModifiedTimeInSeconds = cursor.getLong(mtimeIndex);

            // Convert the last modified time to milliseconds
            long lastModifiedTimeMillis = lastModifiedTimeInSeconds * 1000;

            // Now you have the file size and last modification time
            // You can use fileSize and lastModifiedTimeMillis as needed

            cursor.close(); // Close the cursor when done
        }        
    }
}
