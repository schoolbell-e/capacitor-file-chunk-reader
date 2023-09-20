export interface ReadFileChunkParams {
  path: string;
  start:number;
  end:number;
}

export interface FileChunkReaderPlugin {
  readFileChunk(params: ReadFileChunkParams): Promise<{data:string}>;  
  readFileSize (params: { path: string }): Promise<{size:number}>;
}
