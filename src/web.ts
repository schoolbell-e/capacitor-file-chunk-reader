import { WebPlugin } from '@capacitor/core';

import type { FileChunkReaderPlugin } from './definitions';

export class FileChunkReaderWeb
  extends WebPlugin
  implements FileChunkReaderPlugin
{
  async readFileChunk(_:any): Promise<any> {
    throw this.unavailable("Not implemented on web.");
  }
  async readFileSize(_:any): Promise<any> {
    throw this.unavailable("Not implemented on web.");
  }  
}
