'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const FileChunkReader = core.registerPlugin('FileChunkReader', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.FileChunkReaderWeb()),
});

class FileChunkReaderWeb extends core.WebPlugin {
    async readFileChunk(_) {
        throw this.unavailable("Not implemented on web.");
    }
    async readFileSize(_) {
        throw this.unavailable("Not implemented on web.");
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    FileChunkReaderWeb: FileChunkReaderWeb
});

exports.FileChunkReader = FileChunkReader;
//# sourceMappingURL=plugin.cjs.js.map
