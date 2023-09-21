package com.schoolbell_e.plugins.file_chunk_reader;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.File;

@CapacitorPlugin(name = "FileChunkReader")
public class FileChunkReaderPlugin extends Plugin {

    private FileChunkReader implementation = new FileChunkReader();

    @PluginMethod
    public void readFileSize (PluginCall call) {

        String path = call.getString("path");
            Uri uri = Uri.parse(path);
            if (uri.getScheme() == null || uri.getScheme().equals("file")) {
                File fileObject = new File(uri.getPath());
                if (!fileObject.exists()) {
                    call.reject("File does not exist");
                    return;
                }
                JSObject response = new JSObject();
                response.put("size",fileObject.length());
                call.resolve(response);

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

    @PluginMethod
    public void readFileChunk(PluginCall call) {
        String path = call.getString("path");
        Integer start = call.getInt("start", 0);
        Integer end = call.getInt("end", 0);
        Integer length = end - start + 1;



        Uri uri = Uri.parse(path);
        if (uri.getScheme() == null || uri.getScheme().equals("file")) {
            byte[] buffer = new byte[length];
            try (RandomAccessFile tRandomAccessFile = new RandomAccessFile(uri.getPath(), "r")) {
                tRandomAccessFile.seek(start); // MOVE TO OFFSET
                tRandomAccessFile.read(buffer, 0, length);
                Logger.info("has read from " + start + " - " +end);

                JSObject response = new JSObject();
                response.put("data",android.util.Base64.encodeToString(buffer, android.util.Base64.NO_WRAP));
                call.resolve(response);
            } catch (IOException e) {
                Logger.error(e.getLocalizedMessage(), e);
                call.reject(e.getLocalizedMessage());
            }
        }
        else if (uri.getScheme().equals("content")) {

            ContentResolver contentResolver = getContext().getContentResolver(); // Replace with your context or activity
            InputStream inputStream = null;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            try {
                inputStream = contentResolver.openInputStream(uri);
                long currentCursor = 0;
                int bytesRead;

                // Seek to the startByte
                while (currentCursor < start) {
                    long bytesToSkip = start - currentCursor;
                    long skipped = inputStream.skip(bytesToSkip);
                    if (skipped <= 0) {
                        // Unable to seek to the desired position
                        throw new IOException("Unable to seek to the desired position");
                    }
                    currentCursor += skipped;
                }

                // Read and write the requested range
                while (currentCursor < end) {
                    byte[] chunkBuffer = new byte[1024];
                    bytesRead = inputStream.read(chunkBuffer);
                    if (bytesRead == -1) break;
                    long bytesToRead = Math.min(end - currentCursor + 1, bytesRead);
                    outputStream.write(chunkBuffer, 0, (int) bytesToRead);
                    currentCursor += (bytesToRead - 1);
                }

                byte[] buffer = outputStream.toByteArray();
                Logger.info("has read from " + start + " - " + currentCursor + "("+end+")");

                JSObject response = new JSObject();
                response.put("data",android.util.Base64.encodeToString(buffer, android.util.Base64.NO_WRAP));
                call.resolve(response);

            }
            catch (IOException e) {
                call.reject(e.getLocalizedMessage());
            }
            finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                        Logger.info("inputStream closed.");
                    }
                    catch (IOException e) {
                        Logger.error(e.getLocalizedMessage());

                    }
                    try {
                        outputStream.close();
                        Logger.info("outputStream closed.");
                    }
                    catch (IOException e) {
                        Logger.error(e.getLocalizedMessage());

                    }
                }
            }
        }


    }
}
