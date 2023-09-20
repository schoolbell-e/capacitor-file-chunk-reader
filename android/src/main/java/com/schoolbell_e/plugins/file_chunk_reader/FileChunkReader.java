package com.schoolbell_e.plugins.file_chunk_reader;

import android.util.Log;

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
}
