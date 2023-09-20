var capacitorFileChunkReader = (function (exports, core) {
    'use strict';

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

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
