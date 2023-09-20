import { WebPlugin } from '@capacitor/core';
export class FileChunkReaderWeb extends WebPlugin {
    async readFileChunk(_) {
        throw this.unavailable("Not implemented on web.");
    }
    async readFileSize(_) {
        throw this.unavailable("Not implemented on web.");
    }
}
//# sourceMappingURL=web.js.map