import { registerPlugin } from '@capacitor/core';
const FileChunkReader = registerPlugin('FileChunkReader', {
    web: () => import('./web').then(m => new m.FileChunkReaderWeb()),
});
export * from './definitions';
export { FileChunkReader };
//# sourceMappingURL=index.js.map