import { WebPlugin } from '@capacitor/core';
import type { FileChunkReaderPlugin } from './definitions';
export declare class FileChunkReaderWeb extends WebPlugin implements FileChunkReaderPlugin {
    readFileChunk(_: any): Promise<any>;
    readFileSize(_: any): Promise<any>;
}
