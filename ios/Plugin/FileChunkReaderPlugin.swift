import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FileChunkReaderPlugin)
public class FileChunkReaderPlugin: CAPPlugin {
    private let implementation = FileChunkReader()


    @objc func readFileChunk(_ call: CAPPluginCall) {
        let path = call.getString("path", "")
        let start = UInt64(call.getInt("start", 0))
        let end = UInt64(call.getInt("end", 0))
        let length = Int(end - start + 1)
        
        // GET THE FILE HANDLE AND READ THE CHUNK
        guard let fileHandle = FileHandle(forReadingAtPath: path) else {
            call.reject("Unable to open file")
            return;
        }
        defer {
            fileHandle.closeFile()
        }
        fileHandle.seek(toFileOffset: start)
        var data = fileHandle.readData(ofLength: length)
        fileHandle.closeFile()
        call.resolve(["data":data.base64EncodedString()])
    }

     @objc func readFileSize(_ call: CAPPluginCall) {
         let path = call.getString("path", "");
         // Use if let to safely unwrap the optional and avoid potential issues
         if let fileUrlPath = URL(string: path)?.path {
             do {
                 let attr = try FileManager.default.attributesOfItem(atPath: fileUrlPath)
                 if let size = attr[.size] as? UInt64 {
                     call.resolve(["size": size])
                     return // Add return statement to exit the function after resolving
                 }
             } catch {
                 // Handle any potential errors from FileManager or attribute retrieval
                 call.reject("Error getting file size: \(error.localizedDescription)")
             }
         }
         
         // If the above code doesn't succeed, reject the call
         call.reject("Unable to get file size")
     }


        
        
}
